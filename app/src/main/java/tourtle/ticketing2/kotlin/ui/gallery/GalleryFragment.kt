package tourtle.ticketing2.kotlin.ui.gallery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import tourtle.ticketing2.R
import tourtle.ticketing2.adapter.RiwayatTiketAdapter2
import tourtle.ticketing2.model.Transaksi
import tourtle.ticketing2.utils.DBHelper

class GalleryFragment : Fragment() {

    private lateinit var rvRiwayatTiket: RecyclerView
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val dbHelper = DBHelper(requireActivity().applicationContext, null)

        val root = inflater.inflate(R.layout.fragment_gallery, container, false)
        rvRiwayatTiket = root.findViewById(R.id.rvRiwayatTiket)
        rvRiwayatTiket.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)

        val transaksi:ArrayList<Transaksi> = dbHelper.getAllTransaksi()
        val adapter = RiwayatTiketAdapter2(transaksi, requireActivity().applicationContext)
        rvRiwayatTiket.adapter = adapter

        return root
    }
}