package tourtle.ticketing2.model

import com.google.firebase.database.Exclude
import java.util.*

class TiketBulan(private var idTiket: String?, private var jumlahOrang: Int?, private var jumlahKendaraan: Int?, private var idWisata: String?, private var totalBiaya: Int?,
                 private var biayaTiket: Int?, private var biayaParkir: Int?, private var wisdom: Int?, private var wisman: Int?, private var rodaDua: Int?, private var rodaEmpat: Int?,
                 private var bus: Int?, private var jalan: Int?) {
    fun setIdTiket(idTiket: String?) {
        this.idTiket = idTiket
    }

    fun setJumlahOrang(jumlahOrang: Int?) {
        this.jumlahOrang = jumlahOrang
    }

    fun setWisdom(wisdom: Int?) {
        this.wisdom = wisdom
    }

    fun setWisman(wisman: Int?) {
        this.wisman = wisman
    }

    fun setJumlahKendaraan(jumlahKendaraan: Int?) {
        this.jumlahKendaraan = jumlahKendaraan
    }

    fun setRodaDua(rodaDua: Int?) {
        this.rodaDua = rodaDua
    }

    fun setRodaEmpat(rodaEmpat: Int?) {
        this.rodaEmpat = rodaEmpat
    }

    fun setBus(bus: Int?) {
        this.bus = bus
    }

    fun setJalan(jalan: Int?) {
        this.jalan = jalan
    }

    fun setIdWisata(idWisata: String?) {
        this.idWisata = idWisata
    }

    fun setTotalBiaya(totalBiaya: Int?) {
        this.totalBiaya = totalBiaya
    }

    fun setBiayaTiket(biayaTiket: Int?) {
        this.biayaTiket = biayaTiket
    }

    fun setBiayaParkir(biayaParkir: Int?) {
        this.biayaParkir = biayaParkir
    }

    fun getIdTiket(): String? {
        return idTiket
    }

    fun getJumlahOrang(): Int? {
        return jumlahOrang
    }

    fun getWisdom(): Int? {
        return wisdom
    }

    fun getWisman(): Int? {
        return wisman
    }

    fun getJumlahKendaraan(): Int? {
        return jumlahKendaraan
    }

    fun getRodaDua(): Int? {
        return rodaDua
    }

    fun getRodaEmpat(): Int? {
        return rodaEmpat
    }

    fun getBus(): Int? {
        return bus
    }

    fun getJalan(): Int? {
        return jalan
    }

    fun getIdWisata(): String? {
        return idWisata
    }

    fun getTotalBiaya(): Int? {
        return totalBiaya
    }

    fun getBiayaTiket(): Int? {
        return biayaTiket
    }

    fun getBiayaParkir(): Int? {
        return biayaParkir
    }

    @Exclude
    fun toMap(): MutableMap<String?, Any?>? {
        val result = HashMap<String?, Any?>()
        result["idWisata"] = idWisata
        result["idTiket"] = idTiket
        result["jumlahOrang"] = jumlahOrang
        result["jumlahKendaraan"] = jumlahKendaraan
        result["totalBiaya"] = totalBiaya
        result["biayaTiket"] = biayaTiket
        result["biayaParkir"] = biayaParkir
        result["wisdom"] = wisdom
        result["wisman"] = wisman
        result["rodaDua"] = rodaDua
        result["rodaEmpat"] = rodaEmpat
        result["bus"] = bus
        result["jalan"] = jalan
        return result
    }

}