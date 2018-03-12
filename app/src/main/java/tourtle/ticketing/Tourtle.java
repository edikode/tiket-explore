package tourtle.ticketing;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;


public class Tourtle extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
