package tourtle.ticketing2

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Response
import org.alkaaf.btprint.BluetoothPrint
import org.json.JSONException
import org.json.JSONObject
import tourtle.ticketing2.adapter.TabAdapter
import tourtle.ticketing2.fragment.LaporanFragment
import tourtle.ticketing2.fragment.RiwayatOnlineFragment
import tourtle.ticketing2.model.LaporanModel
import tourtle.ticketing2.model.ModelTiket
import tourtle.ticketing2.model.RiwayatTiketModel
import tourtle.ticketing2.utils.OkHttpRequest
import tourtle.ticketing2.utils.Session
import java.io.IOException
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class TicketOnline : AppCompatActivity() {
    //ui
    private lateinit var fabScan: FloatingActionButton
    private lateinit var viewPager: ViewPager
    private lateinit var tabLayout: TabLayout
    private lateinit var etTanggal: EditText

    //data
    private lateinit var listTiket: ArrayList<RiwayatTiketModel>
    private lateinit var listLaporan: ArrayList<LaporanModel>
    private lateinit var df: SimpleDateFormat

    private lateinit var jsonData: JSONObject
    private lateinit var jsonTiket: JSONObject
    var scannedResult: String = ""
    var totalTiket: String = ""
    var cal = Calendar.getInstance()
    var idWisata: String = ""

    var jumlahOrang = 0
    var biayaTiket = 0
    var masuk = 0
    var belum = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket_online)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.putih))
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar?.setDisplayShowHomeEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        val session = Session(this@TicketOnline)
        idWisata = session.idWisata

        viewPager = findViewById(R.id.viewPager)
        tabLayout = findViewById(R.id.tabLayout)
        etTanggal = findViewById(R.id.etTanggal)

        etTanggal.setFocusable(false)
        etTanggal.setClickable(true)

        val dateListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(
                    view: DatePicker, year: Int, monthOfYear: Int,
                    dayOfMonth: Int
            ) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInViewFrom()
            }
        }


        etTanggal.setOnClickListener {
            DatePickerDialog(
                    this@TicketOnline,
                    dateListener,
                    // set DatePickerDialog to point to today's date when it loads up
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)
            ).show()

        }

        listTiket = arrayListOf()
        listLaporan = arrayListOf()


        val myFormat = "dd MMM yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.getDefault())
        etTanggal.setText(sdf.format(cal.time))
        val calendar = GregorianCalendar()
        df = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val newDate: Date = calendar.time
        getTiket(df.format(newDate))


        fabScan = findViewById(R.id.fab)
        fabScan.setOnClickListener {
            run {
                IntentIntegrator(this@TicketOnline).initiateScan();
            }
        }

    }

    private fun setupViewPager() {
//        tabLayout.removeAllTabs()
        val adapter = TabAdapter(supportFragmentManager)

        val firstFragmet: RiwayatOnlineFragment = RiwayatOnlineFragment.newInstance(listTiket, totalTiket)
        val secondFragmet: LaporanFragment = LaporanFragment.newInstance(listLaporan, jumlahOrang, biayaTiket, masuk, belum)

        adapter.addFragment(firstFragmet, "Riwayat")
        adapter.addFragment(secondFragmet, "Laporan")

        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)

    }

    private fun getTiket(tanggal: String) {
//        val request = OkHttpRequest()
        val url = "https://banyuwangitourism.com/bankdata/api_ticketing/getRiwayatTiket2"
        val map: HashMap<String, String> = hashMapOf("idWisata" to idWisata, "tanggal" to tanggal)

//        request.POST(url, map, object : Callback {
//            override fun onResponse(call: Call?, response: Response) {
//                val responseData = response.body()?.string()
//                runOnUiThread {
//                    try {
//                        val json = JSONObject(responseData)
//                        jsonTiket = json
//                        this@TicketOnline.fetchTiket()
//
//                    } catch (e: JSONException) {
////                        loading.visibility = View.GONE
//                        e.printStackTrace()
//
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call?, e: IOException?) {
//                println("Activity Failure.")
//            }
//        })
    }

    fun fetchTiket() {
        listLaporan.clear()
        listTiket.clear()

        if (jsonTiket.getString("status") == "ada") {
            totalTiket = "ada"
            val lengthTiket = jsonTiket.getJSONArray("tiket")
            for (x in 0 until lengthTiket.length()) {
                val objectOutlet = lengthTiket.getJSONObject(x)
                val id = objectOutlet.getString("id")
                val jumlah_orang = objectOutlet.getString("jumlah_orang")
                val biaya_tiket = objectOutlet.getString("biaya_tiket")
                val tanggal_visit = objectOutlet.getString("tanggal_visit")
                val id_wisata = objectOutlet.getString("id_wisata")
                val nama_wisata = objectOutlet.getString("nama_wisata")
                val status = objectOutlet.getString("status")
                val namaUser = objectOutlet.getString("namaUser")
                val product = objectOutlet.getString("product")

                val detail_tiket = objectOutlet.getJSONArray("detail_tiket")
                val detail_data: ArrayList<ModelTiket> = arrayListOf()

                for (i in 0 until detail_tiket.length()) {
                    val objectTiket = detail_tiket.getJSONObject(i)
                    val id_jenis_tiket = objectTiket.getString("id_jenis_tiket")
                    val nama_tiket = objectTiket.getString("nama_tiket")
                    val jumlah = objectTiket.getString("jumlah")
                    val biaya = objectTiket.getString("biaya")

                    val tiket = ModelTiket(id_jenis_tiket, nama_tiket, biaya.toInt(), jumlah.toInt())
                    detail_data.add(tiket)
                }

                val masuk = objectOutlet.getString("masuk")
                val riwayatTiketModel = RiwayatTiketModel(id, jumlah_orang.toInt(), biaya_tiket.toInt(), tanggal_visit,
                        id_wisata, nama_wisata, status, detail_data, masuk,namaUser,product)
                listTiket.add(riwayatTiketModel)

            }

            val laporan = jsonTiket.getJSONArray("laporanTiket")
            for (x in 0 until laporan.length()) {
                val objectData = laporan.getJSONObject(x)
                val nama = objectData.getString("nama_tiket")
                val jumlah = objectData.getInt("jumlah")
                val biaya = objectData.getInt("biaya")

                val laporanModel = LaporanModel(nama, jumlah, biaya)
                listLaporan.add(laporanModel)
            }

            jumlahOrang = jsonTiket.getInt("jumlahOrang")
            biayaTiket = jsonTiket.getInt("biayaTiket")
            masuk = jsonTiket.getInt("masuk")
            belum = jsonTiket.getInt("belum")


        } else {
            totalTiket = "kosong"
        }

        setupViewPager()


    }

    private fun checkTicket(idTicket: String) {
//        val client = OkHttpClient()
//        val request = OkHttpRequest(client)
//        val url = "https://banyuwangitourism.com/bankdata/api_ticketing/checkTicket"
//        val map: HashMap<String, String> = hashMapOf("idTicket" to idTicket)
//
//        request.POST(url, map, object : Callback {
//            override fun onResponse(call: Call?, response: Response) {
//                val responseData = response.body()?.string()
//                runOnUiThread {
//                    try {
//                        val json = JSONObject(responseData)
//                        jsonData = json
//                        jsonData.put("valid", true)
//                        this@TicketOnline.fetchComplete()
//
//                    } catch (e: JSONException) {
////                        loading.visibility = View.GONE
//                        e.printStackTrace()
//
//                    }
//                }
//            }
//
//            override fun onFailure(call: Call?, e: IOException?) {
//                println("Activity Failure.")
//            }
//        })
    }

    fun fetchComplete() {
        println("coba $jsonData")

        val pesan = jsonData.getString("pesan")
        if (pesan == "ada") {
            val dataObject = jsonData.getJSONObject("tiket")
            val id = dataObject.getString("id")
            val jumlah_orang = dataObject.getString("jumlah_orang")
            val biaya_tiket = dataObject.getString("biaya_tiket")
            val tanggal_visit = dataObject.getString("tanggal_visit")
            val id_wisata = dataObject.getString("id_wisata")
            val nama_wisata = dataObject.getString("nama_wisata")
            val status = dataObject.getString("status")
            val namaUser = dataObject.getString("namaUser")
            val product = dataObject.getString("product")

            val detail_tiket = dataObject.getJSONArray("detail_tiket")
            val detail_data: ArrayList<ModelTiket> = arrayListOf()

            for (i in 0 until detail_tiket.length()) {
                val objectTiket = detail_tiket.getJSONObject(i)
                val id_jenis_tiket = objectTiket.getString("id_jenis_tiket")
                val nama_tiket = objectTiket.getString("nama_tiket")
                val jumlah = objectTiket.getString("jumlah")
                val biaya = objectTiket.getString("biaya")

                val tiket = ModelTiket(id_jenis_tiket, nama_tiket, biaya.toInt(), jumlah.toInt())
                detail_data.add(tiket)
            }

            val masuk = dataObject.getString("masuk")

            val riwayatTiketModel = RiwayatTiketModel(id, jumlah_orang.toInt(), biaya_tiket.toInt(), tanggal_visit,
                    id_wisata, nama_wisata, status, detail_data, masuk,namaUser,product)

            listTiket.add(riwayatTiketModel)
//            adapter?.notifyDataSetChanged()

//            linNoData.visibility = View.GONE

            val drawable = ContextCompat.getDrawable(
                    applicationContext, // Context
                    R.drawable.ic_tick // Drawable
            )
            showDialog("Berhasil, silahkan masuk", drawable!!,product,id,jumlah_orang.toInt(),biaya_tiket.toInt(),namaUser,detail_data)


        } else if (pesan == "sudah") {
            val drawable = ContextCompat.getDrawable(
                    applicationContext, // Context
                    R.drawable.ic_close // Drawable
            )
            val detail_data: ArrayList<ModelTiket> = arrayListOf()
            showDialog("Maaf, tiket sudah digunakan", drawable!!,"","",0,0, "",detail_data)
        }


    }

    fun showDialog(pesan: String, drawable: Drawable,product:String,id:String,jumlahOrang:Int,biayaTiket:Int,namaUser:String,detailData:ArrayList<ModelTiket>) {

        val dialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val dialogLayout = inflater.inflate(R.layout.alerdialog_pesan, null)
        val imgIcon: ImageView = dialogLayout.findViewById(R.id.imgIcon)
        imgIcon.setImageDrawable(drawable)
        val tvPesan: TextView = dialogLayout.findViewById(R.id.tvPesan)
        tvPesan.text = pesan
        val tvProduct:TextView = dialogLayout.findViewById(R.id.tvProduct)
        tvProduct.text = product

        val btnPrint:Button = dialogLayout.findViewById(R.id.btnPrint)
        if (id == ""){
            btnPrint.visibility = View.GONE
            tvProduct.visibility = View.GONE
        }

        btnPrint.setOnClickListener {
            val builder = BluetoothPrint.Builder(BluetoothPrint.Size.WIDTH58)
            builder.addLine()
            builder.setAlignMid()
            val session = Session(this)
            builder.addText(session.namaWisata)

            val sdf = SimpleDateFormat("dd MMM yyyy hh:mm:ss")
            val currentDate = sdf.format(Date())

            val decimalFormat = DecimalFormat("#,##0")

            builder.addLine()
            builder.setAlignLeft()
            builder.addFrontEnd("ID Transaksi: ", id)
            builder.addFrontEnd("Tanggal: ", currentDate)
            builder.addFrontEnd("Nama: ", namaUser)
            builder.addLine()
            for (x in 0 until detailData.size){
                val hargaTiket = detailData[x].hargaTiket
                val jumlahTiket = detailData[x].jumlahTiket
                builder.addFrontEnd(detailData[x].namaTiket+" X "+jumlahTiket+" : ",decimalFormat.format(hargaTiket))
            }
            builder.addLine()
            builder.setAlignRight()
            builder.addTextln("Total: " + decimalFormat.format(biayaTiket))
            builder.addLine()
            builder.setAlignMid()
            if (session.footer != null) {
                builder.addTextln(session.footer)
            } else {
                builder.addTextln("Ayo ke Banyuwangi, anda pasti ingin kembali")
            }
            builder.addTextln("")
            builder.addTextln("")
            BluetoothPrint.with(this)
                    .autoCloseAfter(0)
                    .setData(builder.byte)
                    .print()
            


        }

        dialog.setNegativeButton("tutup") { dialogInterface, i ->
            dialogInterface.dismiss()
        }
        dialog.setView(dialogLayout)
        dialog.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        val result: IntentResult? = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

        if (result != null) {

            if (result.contents != null) {
                scannedResult = result.contents
                checkTicket(scannedResult)
            } else {
                Toast.makeText(this, "Tiket tidak ditemukan", Toast.LENGTH_LONG).show()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString("scannedResult", scannedResult)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        savedInstanceState.let {
            scannedResult = it.getString("scannedResult").toString()
//            txtValue.text = scannedResult
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    private fun updateDateInViewFrom() {
        val myFormat = "dd MMM yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.getDefault())
        etTanggal.setText(sdf.format(cal.time))
//        from = df.format(cal.time)

        getTiket(df.format(cal.time))
    }


}