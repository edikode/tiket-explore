package tourtle.ticketing2.model

import android.os.Parcel
import android.os.Parcelable

data class LaporanModel(val namaTiket:String,val jumlah:Int,val biaya:Int):Parcelable{

    constructor(parcel: Parcel) : this(
            parcel.readString().toString(),
            parcel.readInt(),
            parcel.readInt()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(namaTiket)
        parcel.writeInt(jumlah)
        parcel.writeInt(biaya)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LaporanModel> {
        override fun createFromParcel(parcel: Parcel): LaporanModel {
            return LaporanModel(parcel)
        }

        override fun newArray(size: Int): Array<LaporanModel?> {
            return arrayOfNulls(size)
        }
    }

}

