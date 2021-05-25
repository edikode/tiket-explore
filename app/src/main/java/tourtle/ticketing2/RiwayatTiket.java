package tourtle.ticketing2;

import android.app.ProgressDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import tourtle.ticketing2.adapter.RiwayatTiketAdapter;
import tourtle.ticketing2.model.Tiket;
import tourtle.ticketing2.utils.Session;

public class RiwayatTiket extends AppCompatActivity {
    private RecyclerView rvRiwayat;
    private ArrayList<Tiket> list;
    private RiwayatTiketAdapter adapter;
    private Query query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat_tiket);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Riwayat Transaksi");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault());
        String hari = df.format(c.getTime());

        DatabaseReference databaseTiket = FirebaseDatabase.getInstance().getReference().child("tiket");
        Session session = new Session(RiwayatTiket.this);

        String idWisata = session.getIdWisata();
        String idUser = session.getIdUser();

        String iHari = hari + "_" + idWisata + "_" + idUser;

        query = databaseTiket.orderByChild("iHari").equalTo(iHari);
        query.keepSynced(true);

        rvRiwayat = findViewById(R.id.rvRiwayatTiket);
        list = new ArrayList<>();

    }

    @Override
    protected void onStart() {
        super.onStart();

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("sedang mengambil data");
        progressDialog.show();

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot tiketSnapshot : dataSnapshot.getChildren()) {
                    Tiket tiket = tiketSnapshot.getValue(Tiket.class);
                    list.add(tiket);

                }

                adapter = new RiwayatTiketAdapter(list, RiwayatTiket.this);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                layoutManager.setReverseLayout(true);
                layoutManager.setStackFromEnd(true);

                rvRiwayat.setAdapter(adapter);
                rvRiwayat.setLayoutManager(layoutManager);
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
                progressDialog.dismiss();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
