package tourtle.ticketing2;

import android.app.ProgressDialog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.alkaaf.btprint.BluetoothPrint;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import tourtle.ticketing2.model.Tiket;
import tourtle.ticketing2.utils.Session;

public class Rekap extends AppCompatActivity {
    private TextView tvJumlahTiket, tvPendapatanTiket, tvJumlahKendaraan, tvPendapatanParkir, tvTotal, tvDomestik, tvManca, tvMotor, tvMobil, tvBus;
    private DatabaseReference databaseReference;
    private String idWisata, hari, namaWisata, idUser;
    private Integer jumlahPengunjung = 0;
    private Integer pendapatanTiket = 0;
    private Integer jumlahKendaraan = 0;
    private Integer pendapatanParkir = 0;
    private Integer totalBiaya = 0;
    private Integer domestik = 0;
    private Integer manca = 0;
    private Integer motor = 0;
    private Integer mobil = 0;
    private Integer bus = 0;

    DecimalFormat decimalFormat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rekap);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Pemasukan");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        tvJumlahTiket = findViewById(R.id.tvJumlahPengunjung);
        tvPendapatanTiket = findViewById(R.id.tvPendapatanTiket);
        tvJumlahKendaraan = findViewById(R.id.tvJumlahKendaraan);
        tvPendapatanParkir = findViewById(R.id.tvPendapatanParkir);
        tvTotal = findViewById(R.id.tvTotalPendapatan);
        tvDomestik = findViewById(R.id.tvJumlahDomestik);
        tvManca = findViewById(R.id.tvJumlahManca);
        tvMotor = findViewById(R.id.tvJumlahMotor);
        tvMobil = findViewById(R.id.tvJumlahMobil);
        tvBus = findViewById(R.id.tvJumlahBus);

        Session session = new Session(this);
        namaWisata = session.getNamaWisata();

        decimalFormat = new DecimalFormat("#,##0" +
                "");

        databaseReference = FirebaseDatabase.getInstance().getReference().child("tiket");

        idWisata = session.getIdWisata();
        idUser = session.getIdUser();

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy", Locale.US);
        hari = df.format(c.getTime());


        getDaata();

        Button btnPrint = findViewById(R.id.btnPrint);
        btnPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BluetoothPrint.Builder builder = new BluetoothPrint.Builder(BluetoothPrint.Size.WIDTH58);
                builder.addLine();
                builder.setAlignMid();
                builder.addTextln(namaWisata);
                builder.addLine();
                builder.setAlignLeft();
                builder.addFrontEnd("Pendapatan Tanggal: ", hari);
                builder.addLine();
                builder.addFrontEnd("Jumlah Tiket: ", decimalFormat.format(jumlahPengunjung));
                builder.addFrontEnd("Jumlah Kendaraan: ", decimalFormat.format(jumlahKendaraan));
                builder.addFrontEnd("Pendapatan Tiket:", decimalFormat.format(pendapatanTiket));
                builder.addFrontEnd("Pendapatan Parkir:", decimalFormat.format(pendapatanParkir));
                builder.addLine();
                builder.addFrontEnd("Total Pendapatan", decimalFormat.format(totalBiaya));
                builder.addLine();
                BluetoothPrint.with(Rekap.this)
                        .autoCloseAfter(0)
                        .setData(builder.getByte())
                        .print();
            }
        });


    }

    public void getDaata() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("sedang mengambil data");
        progressDialog.show();


        String iHari = hari + "_" + idWisata + "_" + idUser;
        Query query = databaseReference.orderByChild("iHari").equalTo(iHari);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Tiket tiket = ds.getValue(Tiket.class);
                    if (tiket.getIdNegara().equals("1")) {
                        domestik += tiket.getJumlahOrang();
                    } else {
                        manca += tiket.getJumlahOrang();
                    }

                    if (tiket.getJenisKendaraan().equals("2")) {
                        motor += tiket.getJumlahKendaraan();
                    } else if (tiket.getJenisKendaraan().equals("3")) {
                        mobil += tiket.getJumlahKendaraan();
                    } else if (tiket.getJenisKendaraan().equals("4")) {
                        bus += tiket.getJumlahKendaraan();
                    }

                    jumlahPengunjung += tiket.getJumlahOrang();
                    jumlahKendaraan += tiket.getJumlahKendaraan();
                    pendapatanTiket += tiket.getBiayaTiket();
                    pendapatanParkir += tiket.getBiayaParkir();

                }
                totalBiaya = pendapatanParkir + pendapatanTiket;

                tvJumlahTiket.setText(String.valueOf(jumlahPengunjung));
                tvJumlahKendaraan.setText(String.valueOf(jumlahKendaraan));

                tvPendapatanParkir.setText(decimalFormat.format(pendapatanParkir));
                tvPendapatanTiket.setText(decimalFormat.format(pendapatanTiket));
                tvTotal.setText(decimalFormat.format(totalBiaya));

                tvDomestik.setText(String.valueOf(domestik));
                tvManca.setText(String.valueOf(manca));
                tvMotor.setText(String.valueOf(motor));
                tvMobil.setText(String.valueOf(mobil));
                tvBus.setText(String.valueOf(bus));

                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
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
