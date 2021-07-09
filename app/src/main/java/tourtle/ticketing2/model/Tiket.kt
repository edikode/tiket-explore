package tourtle.ticketing2.model

class Tiket {
    private var idTiket: String? = null
    private var jumlahOrang: Int? = null
    private var jumlahKendaraan: Int? = null
    private var hari: String? = null
    private var jam: String? = null
    private var idWisata: String? = null
    private var idNegara: String? = null
    private var print: String? = null
    private var totalBiaya: Int? = null
    private var biayaTiket: Int? = null
    private var biayaParkir: Int? = null
    private var idUser: String? = null
    private var iHari: String? = null
    private var iBulan: String? = null
    private var jenisKendaraan: String? = null
    private var index: String? = null

    constructor() {}
    constructor(idTiket: String?, idWisata: String?, jumlahOrang: Int?, jumlahKendaraan: Int?, totalBiaya: Int?, biayaParkir: Int?, biayaTiket: Int?,
                hari: String?, idNegara: String?, print: String?, idUser: String?, iHari: String?, iBulan: String?, jenisKendaraan: String?, jam: String?, index: String?) {
        this.idTiket = idTiket
        this.idWisata = idWisata
        this.jumlahOrang = jumlahOrang
        this.jumlahKendaraan = jumlahKendaraan
        this.totalBiaya = totalBiaya
        this.biayaParkir = biayaParkir
        this.biayaTiket = biayaTiket
        this.hari = hari
        this.idNegara = idNegara
        this.print = print
        this.idUser = idUser
        this.iHari = iHari
        this.iBulan = iBulan
        this.jenisKendaraan = jenisKendaraan
        this.jam = jam
        this.index = index
    }

    fun getPrint(): String? {
        return print
    }

    fun getHari(): String? {
        return hari
    }

    fun getIdNegara(): String? {
        return idNegara
    }

    fun getBiayaTiket(): Int? {
        return biayaTiket
    }

    fun getIdWisata(): String? {
        return idWisata
    }

    fun getJumlahKendaraan(): Int? {
        return jumlahKendaraan
    }

    fun getBiayaParkir(): Int? {
        return biayaParkir
    }

    fun getIdTiket(): String? {
        return idTiket
    }

    fun getJumlahOrang(): Int? {
        return jumlahOrang
    }

    fun getTotalBiaya(): Int? {
        return totalBiaya
    }

    fun getJenisKendaraan(): String? {
        return jenisKendaraan
    }

    fun getIdUser(): String? {
        return idUser
    }

    fun getiHari(): String? {
        return iHari
    }

    fun getiBulan(): String? {
        return iBulan
    }

    fun getJam(): String? {
        return jam
    }

    fun getIndex(): String? {
        return index
    }
}