package tourtle.ticketing2.model

import android.os.Parcel
import android.os.Parcelable

data class RiwayatTiketModel(
        val id: String,
        val jumlah_orang: Int,
        val biaya_tiket: Int,
        val tanggal_visit: String,
        val id_wisata: String,
        val nama_wisata: String,
        val status: String,
        val detail_tiket: ArrayList<ModelTiket>,
        val masuk: String,
        val namaUser:String,
        val product:String
) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString().toString(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readString().toString(),
            parcel.readString().toString(),
            parcel.readString().toString(),
            parcel.readString().toString(),
            arrayListOf<ModelTiket>().apply {
                parcel.readArrayList(ModelTiket::class.java.classLoader)
            },
            parcel.readString().toString(),
            parcel.readString().toString(),
            parcel.readString().toString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeInt(jumlah_orang)
        parcel.writeInt(biaya_tiket)
        parcel.writeString(tanggal_visit)
        parcel.writeString(id_wisata)
        parcel.writeString(nama_wisata)
        parcel.writeString(status)
        parcel.writeArray(arrayOf(detail_tiket))
        parcel.writeString(masuk)
        parcel.writeString(namaUser)
        parcel.writeString(product)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RiwayatTiketModel> {
        override fun createFromParcel(parcel: Parcel): RiwayatTiketModel {
            return RiwayatTiketModel(parcel)
        }

        override fun newArray(size: Int): Array<RiwayatTiketModel?> {
            return arrayOfNulls(size)
        }
    }

}