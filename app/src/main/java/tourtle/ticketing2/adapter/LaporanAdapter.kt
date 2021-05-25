package tourtle.ticketing2.adapter

import tourtle.ticketing2.model.LaporanModel
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import tourtle.ticketing2.R
import java.text.DecimalFormat

data class LaporanAdapter(val list: List<LaporanModel>, val context: Context) :
        RecyclerView.Adapter<LaporanAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.content_laporan_tiket, parent, false)
        return ViewHolder(v);  }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val jumlah = list.get(position).jumlah
        val biaya = list.get(position).biaya
        val nama_tiket = list.get(position).namaTiket

        val decimalFormat = DecimalFormat("#,##0")

        holder.tvNamaTiket.text = jumlah.toString()+"  "+nama_tiket
        holder.tvHarga.text = "Rp "+decimalFormat.format(biaya)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNamaTiket: TextView = itemView.findViewById(R.id.tvNamaTiket)
        val tvHarga: TextView = itemView.findViewById(R.id.tvHarga)
    }


}