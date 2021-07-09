package tourtle.ticketing2.utils

import android.content.Context
import android.net.ConnectivityManager

/**
 * Created by ONYX on 6/6/2017.
 */
class InternetConnection(private val context: Context?) {

    companion object {
        fun checkConnection(context: Context?): Boolean {
            val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            return cm.activeNetworkInfo != null
        }
    }

}