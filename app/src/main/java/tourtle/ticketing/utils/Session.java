package tourtle.ticketing.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class Session {

    private final SharedPreferences prefs;
    private final SharedPreferences.Editor editor;
    private String idWisata,namaWisata;

    public Session(Context context) {
        prefs = context.getSharedPreferences("myapp", Context.MODE_PRIVATE);
        editor = prefs.edit();
        editor.apply();
    }

    public void setIdWisata(String idWisata) {
        this.idWisata = idWisata;
        editor.putString("idWisata", this.idWisata);
        editor.commit();
    }

    public void setNamaWisata(String namaWisata) {
        this.namaWisata = namaWisata;
        editor.putString("namaWisata", this.namaWisata);
        editor.commit();
    }


    public String getIdWisata(){
        return prefs.getString("idWisata", this.idWisata);
    }

    public String getNamaWisata() {
        return prefs.getString("namaWisata", this.namaWisata);
    }
}
