package tourtle.ticketing2.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class Session {

    private final SharedPreferences prefs;
    private final SharedPreferences.Editor editor;
    private String idWisata,namaWisata,idUser,footer,email;

    public Session(Context context) {
        prefs = context.getSharedPreferences("myapp", Context.MODE_PRIVATE);
        editor = prefs.edit();
        editor.apply();
    }

    public void setLoggedIn(boolean loggedin) {
        editor.putBoolean("loggedInmode", loggedin);
        editor.commit();
    }

    public void setIdWisata(String idWisata) {
        this.idWisata = idWisata;
        editor.putString("idWisata", this.idWisata);
        editor.commit();
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
        editor.putString("idUser", this.idUser);
        editor.commit();
    }

    public void setEmail(String email) {
        this.email = email;
        editor.putString("email",this.email);
        editor.commit();
    }

    public void setNamaWisata(String namaWisata) {
        this.namaWisata = namaWisata;
        editor.putString("namaWisata", this.namaWisata);
        editor.commit();
    }


    public void setFooter(String footer) {
        this.footer = footer;
        editor.putString("footer",this.footer);
        editor.commit();
    }

    public String getIdWisata(){
        return prefs.getString("idWisata", this.idWisata);
    }


    public String getEmail() {
        return prefs.getString("email",this.email);
    }

    public boolean loggedin() {
        return prefs.getBoolean("loggedInmode", false);
    }

    public String getNamaWisata() {
        return prefs.getString("namaWisata", this.namaWisata);
    }

    public String getIdUser() {
        return prefs.getString("idUser",this.idUser);
    }

    public String getFooter() {
        return prefs.getString("footer",this.footer);
    }


}
