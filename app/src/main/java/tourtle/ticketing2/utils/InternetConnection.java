package tourtle.ticketing2.utils;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by ONYX on 6/6/2017.
 */
public class InternetConnection {
    private Context context;

    public InternetConnection(Context context) {
        this.context = context;
    }

    public static boolean checkConnection(Context context) {

            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            return cm.getActiveNetworkInfo() != null;
    }

}
