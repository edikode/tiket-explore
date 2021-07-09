package tourtle.ticketing2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class EditProfilWisata : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profil_wisata)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}