package tourtle.ticketing2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import org.alkaaf.btprint.BluetoothPrint;

import tourtle.ticketing2.utils.DatabaseHelper;
import tourtle.ticketing2.utils.ImageHelper;
import tourtle.ticketing2.utils.Session;


public class BluetoothCoba extends AppCompatActivity {
    private DatabaseHelper db;
    Bitmap finalBitmap;
    public static final String IMAGE_ID = "IMG_ID";
    Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_coba);

        db = new DatabaseHelper(this);
        session = new Session(this);

        if (db.checkImage() != 0) {
            new LoadImageFromDatabaseTask().execute(0);
        }

        Button btnPrint = findViewById(R.id.btnPrint);
        btnPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                BluetoothPrint.Builder builder = new BluetoothPrint.Builder(BluetoothPrint.Size.WIDTH58);
                builder.setAlignMid();
                builder.addTextln("SPONSORED BY");
                if (db.checkImage() != 0) {
                    Bitmap sBitmap = initSponBitmap();
                    builder.addBitmap(sBitmap);
                }else{
                    builder.addTextln("PEMERINTAH KABUPATEN BANYUWANGI");
                }
                builder.setAlignMid();
                if (session.getFooter()!=null){
                    builder.addTextln(session.getFooter());
                }

                BluetoothPrint.with(BluetoothCoba.this)
                        .autoCloseAfter(0)
                        .setData(builder.getByte())
                        .print();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_print, menu);
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.about){
            startActivity(new Intent(this, Setting_print.class));
        }

        return true;
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

}
