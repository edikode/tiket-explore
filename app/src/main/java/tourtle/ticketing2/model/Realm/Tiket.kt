package tourtle.ticketing2.model.Realm

import io.realm.RealmObject
import io.realm.annotations.RealmClass

@RealmClass
open class Tiket: RealmObject() {

    private var _id:Int = 0
    private var idWisata: Int = 0
    private var biayaParkir: Int = 0
    private var biayaTiket: Int = 0
    private var totalBiaya: Int = 0
    private var jalan: Int = 0
    private var rodaDua: Int = 0
    private var rodaEmpat: Int = 0
    private var bus: Int = 0
    private var jumlahKendaraan: Int = 0
    private var jumlahOrang: Int = 0
    private var wisdom: Int = 0
    private var wisman: Int = 0
    private var timestamp: String = ""
    private var taxbanyuwangi: String = ""

    fun setId(id:Int) {
        this._id = id
    }

    fun getId(): Int {
        return _id
    }

    fun setIdWisata(idwisata:Int) {
        this.idWisata = idwisata
    }

    fun getIdWisata(): Int {
        return idWisata
    }

    fun setBiayaParkir(b_parkir:Int) {
        this.biayaParkir = b_parkir
    }

    fun getBiayaParkir(): Int {
        return biayaParkir
    }

    fun setBiayaTiket(b_tiket:Int) {
        this.biayaTiket = b_tiket
    }

    fun getBiayaTiket(): Int {
        return biayaTiket
    }

    fun setTotalBiaya(total:Int) {
        this.totalBiaya = total
    }

    fun getTotalBiaya(): Int {
        return totalBiaya
    }

    fun setJalan(nilai:Int) {
        this.jalan = nilai
    }

    fun getJalan(): Int {
        return jalan
    }
    fun setRodaDua(nilai:Int) {
        this.rodaDua = nilai
    }

    fun getRodaDua(): Int {
        return rodaDua
    }
    fun setRodaEmpat(nilai:Int) {
        this.rodaEmpat = nilai
    }

    fun getRodaEmpat(): Int {
        return rodaEmpat
    }
    fun setBus(nilai:Int) {
        this.bus = nilai
    }

    fun getBus(): Int {
        return bus
    }
    fun setJumlahKendaraan(total:Int) {
        this.jumlahKendaraan = total
    }

    fun getJumlahKendaraan(): Int {
        return jumlahKendaraan
    }
    fun setJumlahOrang(total:Int) {
        this.jumlahOrang = total
    }

    fun getJumlahOrang(): Int {
        return jumlahOrang
    }
    fun setWisdom(total:Int) {
        this.wisdom = total
    }

    fun getWisdom(): Int {
        return wisdom
    }
    fun setWisman(total:Int) {
        this.wisman = total
    }

    fun getWisman(): Int {
        return wisman
    }
    fun setTimestamp(nilai:String) {
        this.timestamp = nilai
    }

    fun getTimestamp(): String {
        return timestamp
    }
    fun setTaxBanyuwangi(nilai:String) {
        this.taxbanyuwangi = nilai
    }

    fun getTaxBanyuwangi(): String {
        return taxbanyuwangi
    }
}