package tourtle.ticketing2.kotlin.ui.slideshow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.util.HashMap
import tourtle.ticketing2.R
import tourtle.ticketing2.adapter.TiketAdapter
import tourtle.ticketing2.model.TiketWisata
import tourtle.ticketing2.utils.DBHelper
import tourtle.ticketing2.utils.OkHttpRequest
import java.io.IOException
import java.util.concurrent.TimeUnit


class SlideshowFragment : Fragment() {
    private lateinit var adapter: TiketAdapter
    private lateinit var listTiket: ArrayList<TiketWisata>
    private lateinit var jsonTiket: JSONObject

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val dbHelper = DBHelper(requireActivity().applicationContext, null)

        val root = inflater.inflate(R.layout.fragment_slideshow, container, false)
        val btnGetData: Button = root.findViewById(R.id.btnGetData)
        val rvTiket: RecyclerView = root.findViewById(R.id.rvTiket)
        rvTiket.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        listTiket = arrayListOf()
        listTiket = dbHelper.getAllTiket()


        adapter = TiketAdapter(listTiket, requireActivity().applicationContext)
        rvTiket.adapter = adapter

        btnGetData.setOnClickListener {
            val map: HashMap<String, String> = hashMapOf("idWisata" to "-L7bJiigo0Ztm9M9L2rn")
//            someTask(map, adapter).execute()
            getTiket(map)
        }



        return root
    }

    private fun getTiket(parameters: HashMap<String, String>) {
        val client = OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build()

        val request = OkHttpRequest(client)
        val url = "https://banyuwangitourism.com/bankdata/api_ticketing/getDaftarTiketWisata"


        request.POST(url, parameters, object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println("Activity Failure.")
            }

            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body?.string()
                activity?.runOnUiThread {
                    try {
                        val json = JSONObject(responseData)
                        println("SUCCESS - $json")
                        jsonTiket = json
                        fetchTiket()

                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
            }

        })
    }

    fun fetchTiket() {
        listTiket.clear()

        val lengthTiket = jsonTiket.getJSONArray("tiketWisata")
        for (x in 0 until lengthTiket.length()) {
            val c = lengthTiket.getJSONObject(x)
            val id: String = c.getString("id")
            val idWisata: String = c.getString("id_wisata")
            val nama: String = c.getString("nama")
            val harga: Int = c.getInt("harga")
            val kuota: Int = c.getInt("kuota")

            val model = TiketWisata(id, idWisata, nama, harga, kuota)
            listTiket.add(model)

        }

        adapter.notifyDataSetChanged()


    }

//    class someTask internal constructor(private var parameters: HashMap<String, String>, private var adapter: TiketAdapter) : AsyncTask<Void, Void, String>() {
//        private lateinit var tiket:ArrayList<TiketWisata>
//
//        override fun doInBackground(vararg params: Void?): String? {
//            val jsonObject:JSONObject? = OkHttpRequest().getData("https://banyuwangitourism.com/bankdata/api_ticketing/getDaftarTiketWisata",parameters)
//
//            try {
//                if (jsonObject!!.length() > 0) {
//                    val jsonArray = jsonObject.getJSONArray("tiketWisata")
//                    for (x in 0 until jsonArray.length()){
//                        val c:JSONObject = jsonArray.getJSONObject(x)
//                        val id:String = c.getString("id")
//                        val idWisata:String = c.getString("idWisata")
//                        val nama:String = c.getString("nama")
//                        val harga:Int = c.getInt("harga")
//                        val kuota:Int = c.getInt("kuota")
//
//                        val model = TiketWisata(id,idWisata, nama, harga, kuota)
//                        tiket.add(model)
//                    }
//                }
//            } catch (ignored: JSONException) {
//            }
//
//            return null
//        }
//
//        override fun onPreExecute() {
//            super.onPreExecute()
//        }
//
//        override fun onPostExecute(result: String?) {
//            super.onPostExecute(result)
//            adapter.notifyDataSetChanged()
//
//        }
//    }
}