package tourtle.ticketing2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class RiwayatTiket : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_riwayat_tiket)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}