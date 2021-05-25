package tourtle.ticketing2.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import tourtle.ticketing2.R
import tourtle.ticketing2.model.ModelTiket
import java.text.DecimalFormat

data class DetailTiketOnlineAdapter(val list: List<ModelTiket>, val context: Context) :
        RecyclerView.Adapter<DetailTiketOnlineAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.content_detail_tiket_online_adapter, parent, false)
        return ViewHolder(v);  }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val jumlah = list.get(position).jumlahTiket
        val biaya = list.get(position).hargaTiket
        val nama_tiket = list.get(position).namaTiket


        val decimalFormat = DecimalFormat("#,##0")

        holder.tvNamaTiket.text = jumlah.toString()+" "+nama_tiket
        holder.tvHarga.text = decimalFormat.format(biaya)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNamaTiket: TextView = itemView.findViewById(R.id.tvNamaTiket)
        val tvHarga: TextView = itemView.findViewById(R.id.tvHarga)
    }


}