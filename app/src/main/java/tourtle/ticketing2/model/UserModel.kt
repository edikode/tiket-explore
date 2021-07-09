package tourtle.ticketing2.model

class UserModel(private val idWisata: String?, private val namaWisata: String?, private val tanggal: String?, private val jam: String?, private val status: String?, private val email: String?, private val kodeVersi: Int?) {
    fun getIdWisata(): String? {
        return idWisata
    }

    fun getNamaWisata(): String? {
        return namaWisata
    }

    fun getTanggal(): String? {
        return tanggal
    }

    fun getJam(): String? {
        return jam
    }

    fun getStatus(): String? {
        return status
    }

    fun getEmail(): String? {
        return email
    }

    fun getKodeVersi(): Int? {
        return kodeVersi
    }

}