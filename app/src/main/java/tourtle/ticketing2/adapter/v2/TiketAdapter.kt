package tourtle.ticketing2.adapter.v2

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import tourtle.ticketing2.R
import tourtle.ticketing2.model.Realm.Tiket

class TiketAdapter(val context: Context): RecyclerView.Adapter<TiketAdapter.ViewHolder>() {

    private val tiket: MutableList<Tiket> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_tiket_v2, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindModel(tiket[position])
    }

    override fun getItemCount(): Int {
        return tiket.size
    }

    fun setTiket(data: List<Tiket>) {
        tiket.clear()
        tiket.addAll(data)
        notifyDataSetChanged()
    }

    inner class ViewHolder(i: View): RecyclerView.ViewHolder(i) {
        val tvId : TextView = i.findViewById(R.id.tv_id)
        val tvBiayaTiket : TextView = i.findViewById(R.id.tv_biaya_tiket)
        val tvBiayaParkir : TextView = i.findViewById(R.id.tv_biaya_parkir)
        val tvTotalBiaya : TextView = i.findViewById(R.id.tv_total_biaya)

        fun bindModel(t: Tiket) {
            tvId.text = t.getId().toString()
            tvBiayaTiket.text = t.getBiayaTiket().toString()
            tvBiayaParkir.text = t.getBiayaParkir().toString()
            tvTotalBiaya.text = t.getTotalBiaya().toString()
        }
    }
}