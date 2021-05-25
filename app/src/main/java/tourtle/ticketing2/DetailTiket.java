package tourtle.ticketing2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.alkaaf.btprint.BluetoothPrint;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import tourtle.ticketing2.utils.Session;

public class DetailTiket extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private String print;
    private String hari,jam;
    private String idTiket;
    private Integer jumlahOrang = 0;
    private Integer jumlahKendaraan = 0;
    private Integer biayaTiket = 0;
    private Integer biayaParkir = 0;
    private Integer totalBiaya = 0;
    private Integer hargaTiket = 0;
    private Integer parkir = 0;
    private Integer z = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_tiket);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Detail Transaksi");
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

        final Session session = new Session(getApplicationContext());
        DecimalFormat decimalFormat = new DecimalFormat("#,##0" +
                "");

        Bundle bundle = getIntent().getExtras();
        mDatabase = FirebaseDatabase.getInstance().getReference("tiket");
        if (bundle != null) {
            idTiket = bundle.getString("idTiket");
            jumlahOrang = bundle.getInt("jumlahOrang");
            jumlahKendaraan = bundle.getInt("jumlahKendaraan");
            biayaTiket = bundle.getInt("biayaTiket");
            biayaParkir = bundle.getInt("biayaParkir");
            totalBiaya = bundle.getInt("totalBiaya");
            hargaTiket = bundle.getInt("hargaTiket");
            parkir = bundle.getInt("parkir");
            hari = bundle.getString("hari");
            jam = bundle.getString("jam");
            print = bundle.getString("print");
        }

        TextView tvJumlahOrang = findViewById(R.id.tvJumlahOrang);
        TextView tvJumlahKendaraan = findViewById(R.id.tvJumlahKendaraan);
        TextView tvBiayaTiket = findViewById(R.id.tvBiayaTiket);
        TextView tvBiayaParkir = findViewById(R.id.tvBiayaParkir);
        TextView tvTotalBiaya = findViewById(R.id.tvTotalBiaya);

        tvJumlahKendaraan.setText(String.valueOf(jumlahKendaraan));
        tvJumlahOrang.setText(String.valueOf(jumlahOrang));
        tvBiayaParkir.setText(decimalFormat.format(biayaParkir));
        tvBiayaTiket.setText(decimalFormat.format(biayaTiket));
        tvTotalBiaya.setText(decimalFormat.format(totalBiaya));

        Button btnPrint = findViewById(R.id.btnPrint);

        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        Calendar c = Calendar.getInstance();
        String date2 = dateFormat.format(c.getTime());

        Date date1 = null;
        try {
            date1 = dateFormat.parse(jam);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date date3 = null;
        try {
            date3 = dateFormat.parse(date2);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long difference = date3.getTime() - date1.getTime();

        int days = (int) (difference / (1000*60*60*24));
        int hours = (int) ((difference - (1000*60*60*24*days)) / (1000*60*60));
        final int min = (int) (difference - (1000*60*60*24*days) - (1000*60*60*hours)) / (1000*60);

        btnPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if (min>5){
                Toast.makeText(DetailTiket.this, "Maaf, data sudah lebih dari 5 menit.", Toast.LENGTH_SHORT).show();
            }else{
                if (print.equals("1")) {
                    Toast.makeText(DetailTiket.this, "Maaf tiket sudah di print", Toast.LENGTH_SHORT).show();
                } else {
                    DecimalFormat decimalFormat = new DecimalFormat("#,##0" +
                            "");
                    mDatabase.child(idTiket).child("print").setValue("1");
                    z++;
                    if (z>=2){
                        Toast.makeText(DetailTiket.this, "Maaf sudah diprint", Toast.LENGTH_SHORT).show();
                    }else{
                        BluetoothPrint.Builder builder = new BluetoothPrint.Builder(BluetoothPrint.Size.WIDTH58);
                        builder.addLine();
                        builder.setAlignMid();
                        builder.addTextln(session.getNamaWisata());
                        builder.addLine();
                        builder.setAlignLeft();
                        builder.addFrontEnd("No Transaksi: ", idTiket);
                        builder.addFrontEnd("Tanggal: ", ubahTanggal(hari));
                        builder.addFrontEnd("Jam",jam);
                        builder.addLine();
                        builder.addFrontEnd("Tiket " + jumlahOrang + " x " + hargaTiket + ":", "" + decimalFormat.format(biayaTiket));
                        builder.addFrontEnd("Kendaraan " + jumlahKendaraan + " x " + " " + parkir + ":", "" + biayaParkir);
                        builder.addLine();
                        builder.setAlignRight();
                        builder.addTextln("Total: " + decimalFormat.format(totalBiaya));
                        builder.setAlignMid();
                        builder.addTextln("");
                        builder.addTextln("-----COPY-----");
                        builder.addLine();

                        BluetoothPrint.with(DetailTiket.this)
                                .autoCloseAfter(0)
                                .setData(builder.getByte())
                                .print();

                    }
                }

            }

            }
        });


    }


    private String ubahTanggal(String tanggal){
        SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy",Locale.getDefault());

        Date newDate = null;
        try {
            newDate = format.parse(tanggal);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        format = new SimpleDateFormat("dd MMM yyyy",Locale.getDefault());
        String date = format.format(newDate);
        return date;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}
