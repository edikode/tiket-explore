package tourtle.ticketing2.utils

import android.content.Context
import android.content.SharedPreferences

class Session(context: Context?) {
    private val prefs: SharedPreferences?
    private val editor: SharedPreferences.Editor?
    private var idWisata: String? = null
    private var namaWisata: String? = null
    private var idUser: String? = null
    private var footer: String? = null
    private var email: String? = null

    fun setLoggedIn(loggedin: Boolean) {
        editor?.putBoolean("loggedInmode", loggedin)
        editor?.commit()
    }

    fun setIdWisata(idWisata: String?) {
        this.idWisata = idWisata
        editor?.putString("idWisata", this.idWisata)
        editor?.commit()
    }

    fun setIdUser(idUser: String?) {
        this.idUser = idUser
        editor?.putString("idUser", this.idUser)
        editor?.commit()
    }

    fun setEmail(email: String?) {
        this.email = email
        editor?.putString("email", this.email)
        editor?.commit()
    }

    fun setNamaWisata(namaWisata: String?) {
        this.namaWisata = namaWisata
        editor?.putString("namaWisata", this.namaWisata)
        editor?.commit()
    }

    fun setFooter(footer: String?) {
        this.footer = footer
        editor?.putString("footer", this.footer)
        editor?.commit()
    }

    fun getIdWisata(): String? {
        return prefs?.getString("idWisata", idWisata)
    }

    fun getEmail(): String? {
        return prefs?.getString("email", email)
    }

    fun loggedin(): Boolean {
        return prefs!!.getBoolean("loggedInmode", false)
    }

    fun getNamaWisata(): String? {
        return prefs?.getString("namaWisata", namaWisata)
    }

    fun getIdUser(): String? {
        return prefs?.getString("idUser", idUser)
    }

    fun getFooter(): String? {
        return prefs?.getString("footer", footer)
    }

    init {
        prefs = context?.getSharedPreferences("myapp", Context.MODE_PRIVATE)
        editor = prefs?.edit()
        editor?.apply()
    }
}