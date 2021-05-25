package tourtle.ticketing2.adapter

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import tourtle.ticketing2.DetailTiketOnline
import tourtle.ticketing2.R
import tourtle.ticketing2.model.ModelTiket
import tourtle.ticketing2.model.RiwayatTiketModel
import java.text.DecimalFormat

class RiwayatTiketOnlineAdapter (val list: List<RiwayatTiketModel>, val context: Context) :
        RecyclerView.Adapter<RiwayatTiketOnlineAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.content_riwayat_tiket_online, parent, false)
        return ViewHolder(v);  }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val id:String = list[position].id
        val jumlah_orang:Int = list[position].jumlah_orang
        val biaya_tiket:Int = list[position].biaya_tiket
        val tanggal_visit:String = list[position].tanggal_visit
        val id_wisata:String = list[position].id_wisata
        val nama_wisata:String = list[position].nama_wisata
        val status:String = list[position].status
        val list_detail:ArrayList<ModelTiket> = list[position].detail_tiket
        val masuk = list[position].masuk
        val namaUser = list[position].namaUser
        val product = list[position].product

        val decimalFormat = DecimalFormat("#,##0")

        holder.tvIdTiket.text = "ID Tiket: "+id
        holder.tvBiayaTiket.text = "Rp "+decimalFormat.format(biaya_tiket)
        holder.tvNamaWisata.text = nama_wisata
        if (masuk.equals("1")){
            holder.tvStatus.text = "Masuk"
            holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.green))
        }else{
            holder.tvStatus.text = "Belum"
            holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.merah))
        }

        holder.itemView.setOnClickListener{
            val bundle = Bundle()
            bundle.putString("id_tiket",id)
            bundle.putString("tanggal_visit",tanggal_visit)
            bundle.putInt("jumlah_orang",jumlah_orang)
            bundle.putInt("biaya_tiket",biaya_tiket)
            bundle.putString("nama_wisata",nama_wisata)
            bundle.putParcelableArrayList("list_detail",list_detail)
            bundle.putString("from_act","0")
            bundle.putString("namaUser",namaUser)
            bundle.putString("product",product)

            val intent = Intent(context, DetailTiketOnline::class.java)
            intent.putExtras(bundle)
            intent.flags = FLAG_ACTIVITY_NEW_TASK

            context.startActivity(intent)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvIdTiket: TextView = itemView.findViewById(R.id.tvIdTiket)
        val tvBiayaTiket:TextView = itemView.findViewById(R.id.tvBiayaTiket)
        val tvNamaWisata:TextView = itemView.findViewById(R.id.tvNamaWisata)
        val tvStatus:TextView = itemView.findViewById(R.id.tvStatus)
    }


}