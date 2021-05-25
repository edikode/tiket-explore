package tourtle.ticketing2.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import tourtle.ticketing2.R
import tourtle.ticketing2.adapter.LaporanAdapter
import tourtle.ticketing2.adapter.RiwayatTiketOnlineAdapter
import tourtle.ticketing2.model.LaporanModel
import tourtle.ticketing2.model.RiwayatTiketModel
import java.text.DecimalFormat

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val ARG_PARAM3 = "param3"
private const val ARG_PARAM4 = "param4"
private const val ARG_PARAM5 = "param5"

class LaporanFragment : Fragment() {

    //widget
    private lateinit var rvLaporan: RecyclerView
    private lateinit var adapter: LaporanAdapter
    private lateinit var tvJumlahOrang:TextView
    private lateinit var tvBiayaTiket:TextView

    //variable
    private lateinit var listTiket: ArrayList<LaporanModel>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_laporan, container, false)

        tvJumlahOrang = view.findViewById(R.id.tvJumlahOrang)
        tvBiayaTiket = view.findViewById(R.id.tvBiayaTiket)

        val decimalFormat = DecimalFormat("#,##0")

        var biayaTiket = 0
        var jumlahOrang = 0

        for (x in 0 until listTiket.size){
            biayaTiket += listTiket[x].biaya
            jumlahOrang += listTiket[x].jumlah
        }

        val biayaTiketFormated = decimalFormat.format(biayaTiket)
        tvJumlahOrang.text = "Jumlah Orang: $jumlahOrang"
        tvBiayaTiket.text = "Biaya Tiket: Rp $biayaTiketFormated"


        rvLaporan = view.findViewById(R.id.rvLaporan)
        rvLaporan.layoutManager =
                LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        adapter = LaporanAdapter(listTiket, activity!!.applicationContext)
        rvLaporan.adapter = adapter
        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        arguments?.let {
            listTiket = it.getParcelableArrayList(ARG_PARAM1)!!
        }
    }


    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: ArrayList<LaporanModel>, jumlahOrang: Int, biayaTiket: Int, masuk: Int, belum: Int) =
                LaporanFragment().apply {
                    arguments = Bundle().apply {
                        putParcelableArrayList(ARG_PARAM1, param1)
                    }
                }
    }
}