package tourtle.ticketing2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class Setting_print : AppCompatActivity() {

    companion object {
        //storage permission code
        private const val STORAGE_PERMISSION_CODE = 123
        val IMAGE_ID: String? = "IMG_ID"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting_print)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}