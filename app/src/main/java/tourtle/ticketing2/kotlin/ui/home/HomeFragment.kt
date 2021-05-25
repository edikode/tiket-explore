package tourtle.ticketing2.kotlin.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import tourtle.ticketing2.R
import tourtle.ticketing2.model.Transaksi
import tourtle.ticketing2.utils.DBHelper
import java.util.*

class HomeFragment : Fragment() {
    
    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        val dbHelper = DBHelper(requireActivity().applicationContext, null)
        val textView: TextView = root.findViewById(R.id.text_home)
        val btnNext: Button = root.findViewById(R.id.btnNext)
        btnNext.setOnClickListener {
            val id = createTransactionID()
            val transaksi = Transaksi(id, "", "", "", "", 0, 0)
            dbHelper.addTransaksi(transaksi)
        }

        return root
    }

    @Throws(Exception::class)
    fun createTransactionID(): String {
        return UUID.randomUUID().toString().replace("-", "").toUpperCase(Locale.getDefault())
    }
}