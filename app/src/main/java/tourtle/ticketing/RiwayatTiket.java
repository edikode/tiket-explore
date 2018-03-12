package tourtle.ticketing;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import tourtle.ticketing.adapter.RiwayatTiketAdapter;
import tourtle.ticketing.model.Tiket;
import tourtle.ticketing.utils.Session;

public class RiwayatTiket extends AppCompatActivity {
    private RecyclerView rvRiwayat;
    private ArrayList<Tiket> list;
    private RiwayatTiketAdapter adapter;
    private Query query;
    private String idWisata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat_tiket);
        DatabaseReference databaseTiket = FirebaseDatabase.getInstance().getReference().child("tiket");
        databaseTiket.keepSynced(true);

        Session session = new Session(RiwayatTiket.this);

        idWisata = session.getIdWisata();

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy", Locale.US);
        String hari = df.format(c.getTime());

        query = databaseTiket.orderByChild("hari").equalTo(hari);
        rvRiwayat = findViewById(R.id.rvRiwayatTiket);
        list = new ArrayList<>();

    }

    @Override
    protected void onStart() {
        super.onStart();
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("sedang mengambil data");
        progressDialog.show();

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot tiketSnapshot : dataSnapshot.getChildren()){
                    Tiket tiket = tiketSnapshot.getValue(Tiket.class);
                    if (tiket != null && tiket.getIdWisata().equals(idWisata)) {
                        list.add(tiket);
                    }

                }

                adapter = new RiwayatTiketAdapter(list,RiwayatTiket.this);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                rvRiwayat.setAdapter(adapter);
                rvRiwayat.setLayoutManager(layoutManager);
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }
}
