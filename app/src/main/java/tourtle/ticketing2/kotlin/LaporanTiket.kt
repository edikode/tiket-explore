package tourtle.ticketing2.kotlin

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.appeaser.sublimepickerlibrary.datepicker.SelectedDate
import com.appeaser.sublimepickerlibrary.helpers.SublimeOptions
import com.appeaser.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker.RecurrenceOption
import com.google.firebase.database.*
import tourtle.ticketing2.R
import tourtle.ticketing2.fragment.SublimePickerFragment
import tourtle.ticketing2.model.Tiket
import tourtle.ticketing2.utils.Session
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*


class LaporanTiket : AppCompatActivity() {

    private lateinit var etTanggal: EditText
    private lateinit var databaseReference: DatabaseReference
    private lateinit var query: Query
    private lateinit var tvJumlahOrang: TextView

    var mDateEnd = ""
    var mDateStart = ""
    var idWisata = ""
    var idUser = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_laporan_tiket)

        databaseReference = FirebaseDatabase.getInstance().reference.child("tiket")
        val session = Session(this)
        idWisata = session.idWisata
        idUser = session.idUser

        initView()

        etTanggal.keyListener = null
        etTanggal.setOnClickListener {
            openDateRangePicker()
        }

        getData()
    }

    private fun initView(){
        tvJumlahOrang = findViewById(R.id.tvJumlahOrang)
        etTanggal = findViewById(R.id.etTanggal)
    }

    private fun openDateRangePicker() {
        val pickerFrag = SublimePickerFragment()
        pickerFrag.setCallback(object : SublimePickerFragment.Callback {
            override fun onCancelled() {}
            override fun onDateTimeRecurrenceSet(selectedDate: SelectedDate, hourOfDay: Int, minute: Int,
                                                 recurrenceOption: RecurrenceOption?,
                                                 recurrenceRule: String?) {
                @SuppressLint("SimpleDateFormat") val formatDate = SimpleDateFormat("dd MMMM yyyy")
                val formatTanggal = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                mDateStart = formatDate.format(selectedDate.startDate.time)
                mDateEnd = formatDate.format(selectedDate.endDate.time)

//                getData(formatTanggal.format(selectedDate.getStartDate().getTime()),formatTanggal.format(selectedDate.getEndDate().getTime()));
                // set date start ke textview
//                GetDataTask(idRestoran, formatTanggal.format(selectedDate.startDate.time), formatTanggal.format(selectedDate.endDate.time)).execute()
                etTanggal.setText("$mDateStart - $mDateEnd")
                // set date end ke textview
            }
        })

        // ini configurasi agar library menggunakan method Date Range Picker
        val options = SublimeOptions()
        options.setCanPickDateRange(true)
        options.pickerToShow = SublimeOptions.Picker.DATE_PICKER
        val bundle = Bundle()
        bundle.putParcelable("SUBLIME_OPTIONS", options)
        pickerFrag.arguments = bundle
        pickerFrag.setStyle(DialogFragment.STYLE_NO_TITLE, 0)
        pickerFrag.show(supportFragmentManager, "SUBLIME_PICKER")
    }

    private fun getData() {
        query = databaseReference.orderByChild("iHari").startAt("07-07-2020_$idWisata"+"_$idUser").endAt("07-07-2020_$idWisata"+"_$idUser")
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var totalBiaya = 0
                var jumlahPengunjung = 0
                var pendapatanTiket = 0
                var jumlahKendaraan = 0
                var pendapatanParkir = 0
                var manca = 0
                var domestik = 0
                var mobil = 0
                var motor = 0
                var bus = 0
                for (ds in dataSnapshot.children) {
                    val tiket = ds.getValue(Tiket::class.java)!!
                    jumlahPengunjung += tiket.jumlahOrang
                    jumlahKendaraan += tiket.jumlahKendaraan
                    pendapatanTiket += tiket.biayaTiket
                    pendapatanParkir += tiket.biayaParkir
                    if (tiket.idNegara == "1") {
                        domestik += tiket.jumlahOrang
                    } else {
                        manca += tiket.jumlahOrang
                    }
                    if (tiket.jenisKendaraan == "2") {
                        motor += tiket.jumlahKendaraan
                    } else if (tiket.jenisKendaraan == "3") {
                        mobil += tiket.jumlahKendaraan
                    } else if (tiket.jenisKendaraan == "4") {
                        bus += tiket.jumlahKendaraan
                    }
                }
                val decimalFormat = DecimalFormat("#,##0" +
                        "")
                totalBiaya = pendapatanParkir + pendapatanTiket
                val pajak = pendapatanTiket * 0.1 + pendapatanParkir * 0.3
//                tvTotalPendapatan.setText(decimalFormat.format(totalBiaya).toString())
                tvJumlahOrang.text = jumlahPengunjung.toString()
//                tvJumlahKendaraan.setText(jumlahKendaraan.toString())
//                tvPendapatanTiket.setText(decimalFormat.format(pendapatanTiket).toString())
//                tvPendapatanParkir.setText(decimalFormat.format(pendapatanParkir).toString())
//                tvPajak.setText(decimalFormat.format(pajak).toString())
//                tvDomestik.setText(domestik.toString())
//                tvManca.setText(manca.toString())
//                tvRodaDua.setText(decimalFormat.format(motor))
//                tvRodaEmpat.setText(decimalFormat.format(mobil))
//                tvBus.setText(decimalFormat.format(bus))
//                progressDialog.dismiss()
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })

    }
}