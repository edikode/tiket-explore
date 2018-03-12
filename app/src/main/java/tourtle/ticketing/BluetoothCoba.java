package tourtle.ticketing;

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

import org.alkaaf.btprint.BluetoothPrint;

public class BluetoothCoba extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_coba);

        Button btnPrint = findViewById(R.id.btnPrint);
        btnPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap sBitmap = initSponBitmap();
                BluetoothPrint print = new BluetoothPrint(BluetoothCoba.this);
                BluetoothPrint.Builder builder = new BluetoothPrint.Builder(BluetoothPrint.Size.WIDTH58);
                builder.setAlignMid();
                builder.addTextln("SPONSORED BY");
                builder.addBitmap(sBitmap, 400);
                print.print(builder.getByte());
                startActivity(new Intent(BluetoothCoba.this,BluetoothCoba.class));
                finish();
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
