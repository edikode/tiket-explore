package tourtle.ticketing2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.alkaaf.btprint.BluetoothPrint;

import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import tourtle.ticketing2.model.Tiket;
import tourtle.ticketing2.model.TiketBulan;
import tourtle.ticketing2.utils.DatabaseHelper;
import tourtle.ticketing2.utils.ImageHelper;
import tourtle.ticketing2.utils.Session;

public class ReviewTiket extends AppCompatActivity {
    private DatabaseReference databaseReference, databaseHari, databaseBulan;
    private String idWisata, idUser, jenisKendaraan, bulan, jam, tahun;
    private String hari, id;
    private String idNegara;
    private Integer jumlahOrang = 0;
    private Integer jumlahKendaraan = 0;
    private Integer biayaTiket = 0;
    private Integer biayaParkir = 0;
    private Integer totalBiaya = 0;
    private Integer hargaTiket = 0;
    private Integer parkir = 0;
    Integer wisdom = 0;
    Integer wisman = 0;
    Integer rodaDua = 0;
    Integer rodaEmpat = 0;
    Integer bus = 0;
    Integer jalan = 0;

    private DecimalFormat decimalFormat;
    private Session session;

    private DatabaseHelper db;
    Bitmap finalBitmap;

    public static final String IMAGE_ID = "IMG_ID";

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_tiket);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Review Tiket");
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

        db = new DatabaseHelper(this);

        Bundle bundle = getIntent().getExtras();
        session = new Session(ReviewTiket.this);
        idWisata = session.getIdWisata();

        if (bundle != null) {
            jumlahOrang = bundle.getInt("jumlahOrang");
            jumlahKendaraan = bundle.getInt("jumlahKendaraan");
            totalBiaya = bundle.getInt("totalBiaya");
            biayaTiket = bundle.getInt("biayaTiket");
            biayaParkir = bundle.getInt("biayaParkir");
            idNegara = bundle.getString("idNegara");
            parkir = bundle.getInt("parkir");
            hargaTiket = bundle.getInt("hargaTiket");
            idUser = bundle.getString("idUser");
            jenisKendaraan = bundle.getString("jenisKendaraan");
        }


        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() == null) {
            finish();
            startActivity(new Intent(ReviewTiket.this, Login.class));
        }

        decimalFormat = new DecimalFormat("#,##0" +
                "");
        initView();

        if (db.checkImage() != 0) {
            new LoadImageFromDatabaseTask().execute(0);
        }

        databaseReference = FirebaseDatabase.getInstance().getReference("tiket");
        databaseBulan = FirebaseDatabase.getInstance().getReference("bulan");
        databaseHari = FirebaseDatabase.getInstance().getReference("hari");

        Button btnPrint = findViewById(R.id.btnPrint);
        btnPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault());
                DateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
                Calendar c = Calendar.getInstance();

                hari = df.format(c.getTime());
                bulan = String.valueOf(c.get(Calendar.MONTH) + 1);
                tahun = String.valueOf(c.get(Calendar.YEAR));

                jam = simpleDateFormat.format(c.getTime());

                id = databaseReference.push().getKey();
                if (idWisata.equals("")) {
                    firebaseAuth.signOut();
                } else {

                    String iHari = hari + "_" + idWisata + "_" + idUser;
                    String iBulan = bulan + "_" + tahun + "_" + idWisata + "_" + idUser;
                    final String index = hari + "_" + idWisata;

                    final Tiket tiket = new Tiket(id, idWisata, jumlahOrang, jumlahKendaraan, totalBiaya,
                            biayaParkir, biayaTiket, hari, idNegara, "0", idUser, iHari, iBulan,
                            jenisKendaraan, jam, index);

                    if (idNegara.equals("1")) {
                        wisdom = jumlahOrang;
                    } else if (idNegara.equals("2")) {
                        wisman = jumlahOrang;
                    } else {
                        wisdom = jumlahOrang;
                    }

                    if (jenisKendaraan.equals("1")) {
                        jalan = jumlahKendaraan;
                    } else if (jenisKendaraan.equals("2")) {
                        rodaDua = jumlahKendaraan;
                    } else if (jenisKendaraan.equals("3")) {
                        rodaEmpat = jumlahKendaraan;
                    } else if (jenisKendaraan.equals("4")) {
                        bus = jumlahKendaraan;
                    }

                    databaseReference.child(id).setValue(tiket);


                    addDataHari(hari + "_" + idWisata, jumlahOrang, jumlahKendaraan, idWisata, totalBiaya, biayaTiket, biayaParkir, wisdom, wisman, rodaDua, rodaEmpat, bus, jalan);
                    addDataBulan(bulan + "_" + idWisata, jumlahOrang, jumlahKendaraan, idWisata, totalBiaya, biayaTiket, biayaParkir, wisdom, wisman, rodaDua, rodaEmpat, bus, jalan);

                    BluetoothPrint.Builder builder = new BluetoothPrint.Builder(BluetoothPrint.Size.WIDTH58);
                    builder.addLine();
                    builder.setAlignMid();

                    if (db.checkImage() != 0) {
                        Bitmap sBitmap = initSponBitmap();
                        builder.addBitmap(sBitmap);
                    } else {
                        builder.addTextln(session.getNamaWisata());
                    }
                    builder.addLine();
                    builder.setAlignLeft();
                    builder.addFrontEnd("No Transaksi: ", id);
                    builder.addFrontEnd("Tanggal: ", ubahTanggal(hari));
                    builder.addFrontEnd("Jam", jam);
                    builder.addLine();
                    builder.addFrontEnd("Tiket " + jumlahOrang + " x " + hargaTiket + ":", "" + decimalFormat.format(biayaTiket));
                    builder.addFrontEnd("Kendaraan " + jumlahKendaraan + " x " + " " + parkir + ":", "" + biayaParkir);
                    builder.addLine();
                    builder.setAlignRight();
                    builder.addTextln("Total: " + decimalFormat.format(totalBiaya));
                    builder.addLine();
                    builder.setAlignMid();
                    if (session.getFooter() != null) {
                        builder.addTextln(session.getFooter());
                    } else {
                        builder.addTextln("Ayo ke Banyuwangi, anda pasti ingin kembali");
                    }
                    builder.addTextln("");
                    builder.addTextln("");
                    BluetoothPrint.with(ReviewTiket.this)
                            .autoCloseAfter(0)
                            .setData(builder.getByte())
                            .print();


                }
            }
        });
    }

    public void initView() {
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
    }


    private Bitmap initSponBitmap() {
        int offset = 50;

        Bitmap sponBitmap = finalBitmap;

        Bitmap sBitmap = Bitmap.createBitmap(
                sponBitmap.getWidth() + offset * 2, // Width
                sponBitmap.getHeight() + 25 * 2, // Height
                Bitmap.Config.ARGB_8888 // Config
        );

        Canvas canvasSpon = new Canvas(sBitmap);
        canvasSpon.drawColor(Color.WHITE);

        Paint paint = new Paint();
        paint.setColor(Color.BLACK);

        canvasSpon.drawBitmap(
                sponBitmap, // Bitmap
                offset, // Left
                25, // Top
                paint // Paint
        );
        return sBitmap;
    }

    private Bitmap sponsor(){
        int offset = 50;
        Bitmap icon = BitmapFactory.decodeResource(getResources(),
                R.drawable.jatim);

        Bitmap sBitmap = Bitmap.createBitmap(
                icon.getWidth() + offset * 2, // Width
                icon.getHeight() + 25 * 2, // Height
                Bitmap.Config.ARGB_8888 // Config
        );

        Canvas canvasSpon = new Canvas(sBitmap);
        canvasSpon.drawColor(Color.WHITE);

        Paint paint = new Paint();
        paint.setColor(Color.BLACK);

        canvasSpon.drawBitmap(
                icon, // Bitmap
                offset, // Left
                25, // Top
                paint // Paint
        );
        return sBitmap;
    }

    public void addDataBulan(final String idTiket, final Integer jumlahOrang, final Integer jumlahKendaraan, final String idWisata, final Integer totalBiaya,
                             final Integer biayaTiket, final Integer biayaParkir, final Integer wisdom, final Integer wisman, final Integer rodaDua, final Integer rodaEmpat,
                             final Integer bus, final Integer jalan) {
        Query query = databaseBulan.child(bulan + "_" + tahun).orderByChild("idTiket").equalTo(bulan + "_" + idWisata);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                if (dataSnapshot.exists()) {

                    Integer jumlahOrang1 = 0;
                    Integer jumlahKendaraan1 = 0;
                    Integer biayaTiket1 = 0;
                    Integer biayaParkir1 = 0;
                    Integer totalBiaya1 = 0;
                    Integer wisdom1 = 0;
                    Integer wisman1 = 0;
                    Integer rodaDua1 = 0;
                    Integer rodaEmpat1 = 0;
                    Integer bus1 = 0;
                    Integer jalan1 = 0;

                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        jumlahOrang1 += ds.child("jumlahOrang").getValue(Integer.class);
                        jumlahKendaraan1 += ds.child("jumlahKendaraan").getValue(Integer.class);
                        totalBiaya1 += ds.child("totalBiaya").getValue(Integer.class);
                        biayaTiket1 += ds.child("biayaTiket").getValue(Integer.class);
                        biayaParkir1 += ds.child("biayaParkir").getValue(Integer.class);
                        wisdom1 += ds.child("wisdom").getValue(Integer.class);
                        wisman1 += ds.child("wisman").getValue(Integer.class);
                        rodaDua1 += ds.child("rodaDua").getValue(Integer.class);
                        rodaEmpat1 += ds.child("rodaEmpat").getValue(Integer.class);
                        bus1 += ds.child("bus").getValue(Integer.class);
                        jalan1 += ds.child("jalan").getValue(Integer.class);
                    }

                    TiketBulan tiketBulan = new TiketBulan(idTiket, jumlahOrang + jumlahOrang1,
                            jumlahKendaraan + jumlahKendaraan1, idWisata, totalBiaya + totalBiaya1,
                            biayaTiket + biayaTiket1, biayaParkir + biayaParkir1, wisdom + wisdom1,
                            wisman + wisman1, rodaDua + rodaDua1, rodaEmpat + rodaEmpat1,
                            bus + bus1, jalan + jalan1);

                    Map<String, Object> postValues = tiketBulan.toMap();
                    Map<String, Object> childUpdates = new HashMap<>();

                    childUpdates.put("/" + bulan + "_" + tahun + "/" + idTiket, postValues);
                    databaseBulan.updateChildren(childUpdates);

                } else {

                    TiketBulan tiketBulan = new TiketBulan(idTiket, jumlahOrang, jumlahKendaraan, idWisata, totalBiaya, biayaTiket,
                            biayaParkir, wisdom, wisman, rodaDua, rodaEmpat, bus, jalan);

                    databaseBulan.child(bulan + "_" + tahun).child(idTiket).setValue(tiketBulan);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    public void addDataHari(final String idTiket, final Integer jumlahOrang, final Integer jumlahKendaraan, final String idWisata, final Integer totalBiaya,
                            final Integer biayaTiket, final Integer biayaParkir, final Integer wisdom, final Integer wisman, final Integer rodaDua, final Integer rodaEmpat,
                            final Integer bus, final Integer jalan) {
        Query queryHari = databaseHari.child(hari).orderByChild("idTiket").equalTo(idTiket);
        queryHari.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    Integer jumlahOrang1 = 0;
                    Integer jumlahKendaraan1 = 0;
                    Integer biayaTiket1 = 0;
                    Integer biayaParkir1 = 0;
                    Integer totalBiaya1 = 0;
                    Integer wisdom1 = 0;
                    Integer wisman1 = 0;
                    Integer rodaDua1 = 0;
                    Integer rodaEmpat1 = 0;
                    Integer bus1 = 0;
                    Integer jalan1 = 0;

                    for (DataSnapshot ds : dataSnapshot.getChildren()) {

                        jumlahOrang1 += ds.child("jumlahOrang").getValue(Integer.class);
                        jumlahKendaraan1 += ds.child("jumlahKendaraan").getValue(Integer.class);
                        totalBiaya1 += ds.child("totalBiaya").getValue(Integer.class);
                        biayaTiket1 += ds.child("biayaTiket").getValue(Integer.class);
                        biayaParkir1 += ds.child("biayaParkir").getValue(Integer.class);
                        wisdom1 += ds.child("wisdom").getValue(Integer.class);
                        wisman1 += ds.child("wisman").getValue(Integer.class);
                        rodaDua1 += ds.child("rodaDua").getValue(Integer.class);
                        rodaEmpat1 += ds.child("rodaEmpat").getValue(Integer.class);
                        bus1 += ds.child("bus").getValue(Integer.class);
                        jalan1 += ds.child("jalan").getValue(Integer.class);
                    }


                    TiketBulan tiketBulan = new TiketBulan(idTiket, jumlahOrang + jumlahOrang1, jumlahKendaraan + jumlahKendaraan1,
                            idWisata, totalBiaya + totalBiaya1, biayaTiket + biayaTiket1,
                            biayaParkir + biayaParkir1, wisdom + wisdom1, wisman + wisman1,
                            rodaDua + rodaDua1, rodaEmpat + rodaEmpat1, bus + bus1, jalan + jalan1);

                    Map<String, Object> postValues = tiketBulan.toMap();
                    Map<String, Object> childUpdates = new HashMap<>();

                    childUpdates.put("/" + hari + "/" + idTiket, postValues);
                    databaseHari.updateChildren(childUpdates);

                } else {
                    TiketBulan tiketBulan = new TiketBulan(idTiket, jumlahOrang, jumlahKendaraan, idWisata, totalBiaya, biayaTiket,
                            biayaParkir, wisdom, wisman, rodaDua, rodaEmpat, bus, jalan);

                    databaseHari.child(hari).child(idTiket).setValue(tiketBulan);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private String ubahTanggal(String tanggal) {
        SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault());

        Date newDate = null;
        try {
            newDate = format.parse(tanggal);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        format = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        return format.format(newDate);
    }


    private class LoadImageFromDatabaseTask extends AsyncTask<Integer, Integer, ImageHelper> {


        protected void onPreExecute() {
        }

        @Override
        protected ImageHelper doInBackground(Integer... integers) {
            return db.getImage(IMAGE_ID);
        }

        protected void onProgressUpdate(Integer... progress) {
        }

        protected void onPostExecute(ImageHelper imageHelper) {
            setUpImage(imageHelper.getImageByteArray());
        }

    }


    private void setUpImage(byte[] bytes) {
        finalBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


}
