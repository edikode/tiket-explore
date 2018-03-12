package tourtle.ticketing;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.alkaaf.btprint.BluetoothPrint;

import java.text.DecimalFormat;

import tourtle.ticketing.model.Tiket;
import tourtle.ticketing.utils.Session;

public class ReviewTiket extends AppCompatActivity {
    private DatabaseReference databaseReference;
    private String idWisata;
    private String hari;
    private String idNegara;
    private Integer jumlahOrang =0;
    private Integer jumlahKendaraan = 0;
    private Integer biayaTiket = 0;
    private Integer biayaParkir =0;
    private Integer totalBiaya = 0;
    private Integer hargaTiket =0;
    private Integer parkir =0;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_tiket);

        Bundle bundle = getIntent().getExtras();
        final Session session = new Session(getApplicationContext());
        idWisata = session.getIdWisata();
        assert bundle != null;
        jumlahOrang = bundle.getInt("jumlahOrang");
        jumlahKendaraan = bundle.getInt("jumlahKendaraan");
        biayaTiket = bundle.getInt("biayaTiket");
        biayaParkir = bundle.getInt("biayaParkir");
        totalBiaya = bundle.getInt("totalBiaya");
        hargaTiket = bundle.getInt("hargaTiket");
        parkir = bundle.getInt("parkir");
        hari = bundle.getString("hari");
        idNegara = bundle.getString("idNegara");

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser()== null){
            finish();
            startActivity(new Intent(ReviewTiket.this,Login.class));
        }

        TextView tvJumlahOrang = findViewById(R.id.tvJumlahOrang);
        TextView tvJumlahKendaraan = findViewById(R.id.tvJumlahKendaraan);
        TextView tvBiayaTiket = findViewById(R.id.tvBiayaTiket);
        TextView tvBiayaParkir = findViewById(R.id.tvBiayaParkir);
        TextView tvTotalBiaya = findViewById(R.id.tvTotalBiaya);

        tvJumlahKendaraan.setText(String.valueOf(jumlahKendaraan));
        tvJumlahOrang.setText(String.valueOf(jumlahOrang));
        tvBiayaParkir.setText(String.valueOf(biayaParkir));
        tvBiayaTiket.setText(String.valueOf(biayaTiket));
        tvTotalBiaya.setText(String.valueOf(totalBiaya));

        databaseReference = FirebaseDatabase.getInstance().getReference("tiket");

        final ProgressDialog progressDialog = new ProgressDialog(this);

        Button btnPrint = findViewById(R.id.btnPrint);
        btnPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.setMessage("sedang mengambil data");
                progressDialog.show();

                String id = databaseReference.push().getKey();
                if (idWisata.equals("")){
                    firebaseAuth.signOut();
                    progressDialog.dismiss();
                }else{
                    Tiket tiket = new Tiket(id,idWisata,jumlahOrang,jumlahKendaraan,totalBiaya,biayaParkir,biayaTiket,hari,idNegara,"0");
                    databaseReference.child(id).setValue(tiket);

                    DecimalFormat decimalFormat = new DecimalFormat("#,##0" +
                            "");
                    progressDialog.dismiss();
                    Intent intent = new Intent(ReviewTiket.this,MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(intent);
                    finish();

                    Bitmap sBitmap = initSponBitmap();
                    BluetoothPrint print = new BluetoothPrint(ReviewTiket.this);
                    BluetoothPrint.Builder builder = new BluetoothPrint.Builder(BluetoothPrint.Size.WIDTH58);
                    builder.addLine();
                    builder.setAlignMid();
                    builder.addTextln(session.getNamaWisata());
                    builder.addLine();
                    builder.setAlignLeft();
                    builder.addFrontEnd("No Transaksi: ", id);
                    builder.addFrontEnd("Tanggal: ", hari);
                    builder.addLine();
                    builder.addFrontEnd("Tiket " + jumlahOrang + " x " + hargaTiket + ":", "" + decimalFormat.format(biayaTiket));
                    builder.addFrontEnd("Kendaraan " + jumlahKendaraan + " x " + " " + parkir + ":", "" + biayaParkir);
                    builder.addLine();
                    builder.setAlignRight();
                    builder.addTextln("Total: " + decimalFormat.format(totalBiaya));
                    builder.addTextln("*sudah termasuk asuransi");
                    builder.addLine();
                    builder.setAlignMid();
                    builder.addTextln("SPONSORED BY");
                    builder.addBitmap(sBitmap, 400);
                    print.print(builder.getByte());

                }
            }
        });
    }

    private Bitmap initSponBitmap() {
        int offset = 50;

        Bitmap sponBitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.majestic);

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
}
