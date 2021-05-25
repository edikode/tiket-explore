package tourtle.ticketing2.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import tourtle.ticketing2.R
import tourtle.ticketing2.model.TiketWisata
import java.text.DecimalFormat

class TiketAdapter(val list: List<TiketWisata>, val context: Context) :
        RecyclerView.Adapter<TiketAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.content_tiket, parent, false)
        return ViewHolder(v); }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val id: String = list[position].id
        val idWisata: String = list[position].idWisata
        val nama: String = list[position].nama
        val harga: Int = list[position].harga
        val kuota: Int = list[position].kuota

        val decimalFormat = DecimalFormat("#,##0")

        holder.tvNama.text = nama
        holder.tvHarga.text = "Rp " + decimalFormat.format(harga)

        holder.itemView.setOnClickListener {
            val bundle = Bundle()
//            bundle.putString("id_tiket", id)
//            bundle.putString("tanggal_visit", tanggal_visit)
//            bundle.putString("tanggal_dibeli", tanggal_dibeli)
//            bundle.putInt("jumlah_orang", jumlah_orang)
//            bundle.putInt("biaya_tiket", biaya_tiket)
//            bundle.putInt("biayaAdmin", biayaAdmin)
//            bundle.putInt("jumlahBiaya", jumlahBiaya)
//            bundle.putString("nama_wisata", nama_wisata)
//            bundle.putParcelableArrayList("list_detail", list_detail)
//            bundle.putString("from_act", "0")

//            val intent = Intent(context, DetailTiket::class.java)
//            intent.putExtras(bundle)
//            context.startActivity(intent)
        }


    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNama: TextView = itemView.findViewById(R.id.tvNama)
        val tvHarga: TextView = itemView.findViewById(R.id.tvHarga)
    }


}