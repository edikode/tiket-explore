package tourtle.ticketing2.model

import android.os.Parcel
import android.os.Parcelable

data class ModelTiket(
        var idTiket: String?,
        var namaTiket: String?,
        var hargaTiket:Int,
        var jumlahTiket:Int
):Parcelable{

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readInt()

    )
    constructor():this("","",0,0)

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(idTiket)
        parcel.writeString(namaTiket)
        parcel.writeInt(hargaTiket)
        parcel.writeInt(jumlahTiket)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ModelTiket> {
        override fun createFromParcel(parcel: Parcel): ModelTiket {
            return ModelTiket(parcel)
        }

        override fun newArray(size: Int): Array<ModelTiket?> {
            return arrayOfNulls(size)
        }
    }
}