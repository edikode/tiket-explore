package tourtle.ticketing2.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Transaksi(val id: String, val idWisata: String, val idUser: String, val idJenisTiket: String,
                     val datetime: String, val jumlah: Int, val biaya: Int) : Parcelable {

    companion object {
        const val TABLE = "tiket"
        const val ID = "id"
        const val ID_WISATA = "idWisata"
        const val ID_USER = "idUser"
        const val ID_JENIS_TIKET = "idJenisTiket"
        const val DATETIME = "datetime"
        const val JUMLAH = "jumlah"
        const val BIAYA = "BIAYA"
    }
}