package tourtle.ticketing2.model

class WisataModel(
        private var idWisata: String?,
        private var alamat: String?,
        private var idKota: String?,
        private var namaWisata: String?,
        private var parkirBus: Int?,
        private var parkirRodaDua: Int?,
        private var parkirRodaEmpat: Int?,
        private var tiketDomestik: Int?,
        private var tiketManca: Int?,
        private var tiketWeekend: Int?
) {
    fun getIdWisata(): String? {
        return idWisata
    }

    fun setIdWisata(idWisata: String?) {
        this.idWisata = idWisata
    }

    fun getAlamat(): String? {
        return alamat
    }

    fun setAlamat(alamat: String?) {
        this.alamat = alamat
    }

    fun getIdKota(): String? {
        return idKota
    }

    fun setIdKota(idKota: String?) {
        this.idKota = idKota
    }

    fun getNamaWisata(): String? {
        return namaWisata
    }

    fun setNamaWisata(namaWisata: String?) {
        this.namaWisata = namaWisata
    }

    fun getParkirBus(): Int? {
        return parkirBus
    }

    fun setParkirBus(parkirBus: Int?) {
        this.parkirBus = parkirBus
    }

    fun getParkirRodaDua(): Int? {
        return parkirRodaDua
    }

    fun setParkirRodaDua(parkirRodaDua: Int?) {
        this.parkirRodaDua = parkirRodaDua
    }

    fun getParkirRodaEmpat(): Int? {
        return parkirRodaEmpat
    }

    fun setParkirRodaEmpat(parkirRodaEmpat: Int?) {
        this.parkirRodaEmpat = parkirRodaEmpat
    }

    fun getTiketDomestik(): Int? {
        return tiketDomestik
    }

    fun setTiketDomestik(tiketDomestik: Int?) {
        this.tiketDomestik = tiketDomestik
    }

    fun getTiketManca(): Int? {
        return tiketManca
    }

    fun setTiketManca(tiketManca: Int?) {
        this.tiketManca = tiketManca
    }

    fun getTiketWeekend(): Int? {
        return tiketWeekend
    }

    fun setTiketWeekend(tiketWeekend: Int?) {
        this.tiketWeekend = tiketWeekend
    }

}