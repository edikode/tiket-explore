package tourtle.ticketing;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import tourtle.ticketing.model.Tiket;
import tourtle.ticketing.utils.Session;

public class Rekap extends AppCompatActivity {
    private TextView tvJumlahTiket, tvPendapatanTiket, tvJumlahKendaraan, tvPendapatanParkir, tvTotal;
    private DatabaseReference databaseReference;
    private Query query;
    private String idWisata;
    private Integer jumlahPengunjung = 0;
    private Integer pendapatanTiket = 0;
    private Integer jumlahKendaraan = 0;
    private Integer pendapatanParkir = 0;
    private Integer totalBiaya = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rekap);
        tvJumlahTiket = findViewById(R.id.tvJumlahPengunjung);
        tvPendapatanTiket = findViewById(R.id.tvPendapatanTiket);
        tvJumlahKendaraan = findViewById(R.id.tvJumlahKendaraan);
        tvPendapatanParkir = findViewById(R.id.tvPendapatanParkir);
        tvTotal = findViewById(R.id.tvTotalPendapatan);


        databaseReference = FirebaseDatabase.getInstance().getReference().child("tiket");
        Session session = new Session(Rekap.this);

        idWisata = session.getIdWisata();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy", Locale.US);
        String hari = df.format(c.getTime());

        query = databaseReference.orderByChild("hari").equalTo(hari);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Tiket tiket = ds.getValue(Tiket.class);
                    if (tiket != null && tiket.getIdWisata().equals(idWisata)) {
                        jumlahPengunjung += tiket.getJumlahOrang();
                        jumlahKendaraan += tiket.getJumlahKendaraan();
                        pendapatanTiket += tiket.getBiayaTiket();
                        pendapatanParkir += tiket.getBiayaParkir();
                    }
                }
                totalBiaya = pendapatanParkir+pendapatanTiket;

                tvJumlahTiket.setText(String.valueOf(jumlahPengunjung));
                tvJumlahKendaraan.setText(String.valueOf(jumlahKendaraan));
                tvPendapatanParkir.setText(String.valueOf(pendapatanParkir));
                tvPendapatanTiket.setText(String.valueOf(pendapatanTiket));
                tvTotal.setText(String.valueOf(totalBiaya));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
