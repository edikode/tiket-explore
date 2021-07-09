package tourtle.ticketing2

import android.app.Application

class Tourtle : Application() {
    override fun onCreate() {
        super.onCreate()
    }

    companion object {
        private val TAG: String? = Tourtle::class.java.simpleName
    }
}