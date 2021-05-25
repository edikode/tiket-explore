package tourtle.ticketing2.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class TiketWisata(val id: String, val idWisata: String, val nama: String,
                      val harga: Int, val kuota: Int) : Parcelable {

}