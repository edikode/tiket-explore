package tourtle.ticketing2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class ReviewTiket : AppCompatActivity() {

    companion object {
        val IMAGE_ID: String? = "IMG_ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review_tiket)

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}