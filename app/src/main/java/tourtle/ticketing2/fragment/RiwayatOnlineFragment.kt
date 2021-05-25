package tourtle.ticketing2.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import tourtle.ticketing2.R
import tourtle.ticketing2.adapter.RiwayatTiketOnlineAdapter
import tourtle.ticketing2.model.RiwayatTiketModel

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class RiwayatOnlineFragment : Fragment() {

    private var param2: String? = null
    private lateinit var rvTiketOnline: RecyclerView
    private lateinit var linNoData: LinearLayout
    private lateinit var tvMasuk: TextView
    private lateinit var tvBelum: TextView

    private var adapter: RiwayatTiketOnlineAdapter? = null

    private lateinit var listTiket: ArrayList<RiwayatTiketModel>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_riwayat_online, container, false)

        linNoData = view.findViewById(R.id.linNoData)
        tvMasuk = view.findViewById(R.id.tvMasuk)
        tvBelum = view.findViewById(R.id.tvBelum)

        rvTiketOnline = view.findViewById(R.id.rvTiketOnline)
        rvTiketOnline.layoutManager =
                LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        adapter = RiwayatTiketOnlineAdapter(listTiket,activity!!.applicationContext)
        rvTiketOnline.adapter = adapter

        var masuk = 0
        var belum = 0

        for (x in 0 until listTiket.size){
            if (listTiket[x].masuk == "1"){
                masuk += listTiket[x].jumlah_orang
            }else{
                belum += listTiket[x].jumlah_orang
            }
        }


        if (listTiket.size==0){
            linNoData.visibility = View.VISIBLE
            rvTiketOnline.visibility = View.GONE
        }else{
            linNoData.visibility = View.GONE
            rvTiketOnline.visibility = View.VISIBLE
        }

        tvMasuk.text = "Jumlah Masuk: $masuk "
        tvBelum.text = "Jumlah Belum: $belum "

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.let {
            listTiket = it.getParcelableArrayList(ARG_PARAM1)!!
            param2 = it.getString(ARG_PARAM2)
        }
    }




    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(list: ArrayList<RiwayatTiketModel>, param2: String) =
                RiwayatOnlineFragment().apply {
                    arguments = Bundle().apply {
                        putParcelableArrayList(ARG_PARAM1, list)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}