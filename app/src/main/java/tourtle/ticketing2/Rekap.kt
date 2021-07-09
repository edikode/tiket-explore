package tourtle.ticketing2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class Rekap : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rekap)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}