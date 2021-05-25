package tourtle.ticketing2

import android.graphics.Bitmap
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
import kotlinx.android.synthetic.main.activity_detail_tiket_online.*
import tourtle.ticketing2.adapter.DetailTiketOnlineAdapter
import tourtle.ticketing2.model.ModelTiket
import java.text.DecimalFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class DetailTiketOnline : AppCompatActivity() {

    private lateinit var list_detail:ArrayList<ModelTiket>
    private var from_act:String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_tiket_online)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.putih))
        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar!!.title = "Detail Tiket"
        actionBar.setDisplayShowHomeEnabled(true)
        actionBar.setDisplayHomeAsUpEnabled(true)

        val bundle = intent.extras

        list_detail = arrayListOf()
        list_detail = bundle?.getParcelableArrayList("list_detail")!!

        val id_tiket = bundle.getString("id_tiket")
        val tanggal_visit = bundle.getString("tanggal_visit")
        val jumlah_orang = bundle.getString("jumlah_orang")
        val biaya_tiket =  bundle.getInt("biaya_tiket")
        val nama_wisata = bundle.getString("nama_wisata")
        val namaUser = bundle.getString("namaUser")
        val product = bundle.getString("product")

        val decimalFormat = DecimalFormat("#,##0")


        tvDetail.text = nama_wisata+" - Rp"+decimalFormat.format(biaya_tiket)

        var format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        try {
            val newDate = format.parse(tanggal_visit)
            format = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
            val date: String = format.format(newDate)
            tvTanggal.text = date
        } catch (e: ParseException) {
            e.printStackTrace()
        }


//        tvTanggal.text = "Tanggal Berkunjung: "+tanggal_visit

        val writer = QRCodeWriter()
        val bitMatrix = writer.encode(id_tiket, BarcodeFormat.QR_CODE, 512, 512)
        val width = bitMatrix.width
        val height = bitMatrix.height
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
        for (x in 0 until width) {
            for (y in 0 until height) {
                bitmap.setPixel(x, y, if (bitMatrix.get(x, y)) Color.BLACK else Color.WHITE)
            }
        }
        qrCode.setImageBitmap(bitmap)

        tvIdTiket.text = "ID Tiket: $id_tiket"
        tvProduct.text = "Pembayaran: $product"
        tvNamaUser.text = "Nama: $namaUser"

        rvDetailTiket.layoutManager =
                LinearLayoutManager(this@DetailTiketOnline, LinearLayoutManager.VERTICAL, false)
        rvDetailTiket.setHasFixedSize(true)

        val adapter = DetailTiketOnlineAdapter(list_detail,this)
        rvDetailTiket.adapter = adapter
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()

    }
}