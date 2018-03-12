package tourtle.ticketing;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

import tourtle.ticketing.utils.Session;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DatabaseReference databaseTiket;
    private FirebaseAuth firebaseAuth;
    private Query qWisata;
    private Integer tiketDomestik =0;
    private Integer tiketManca = 0;
    private Integer tiketWeekend = 0;
    private Integer hargaTiket = 0;
    private Integer parkirRodaDua = 0;
    private Integer parkirRodaEmpat = 0;
    private Integer parkirBus = 0;
    private Integer parkir =0;
    private String idNegara,idKendaraan,idWisata;

    private ArrayList<String> country_code;
    private ArrayList<String> jenisKendaraan;

    private Spinner spinJumlahOrang;
    private Spinner spinJumlahKendaraan;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String citynames[]={"1", "2","3"};
        String kendaraan[] ={"2","3","4","1"};

        country_code = new ArrayList<>(Arrays.asList(citynames));
        jenisKendaraan = new ArrayList<>(Arrays.asList(kendaraan));

        Session session = new Session(getApplicationContext());

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser()== null){
            finish();
            startActivity(new Intent(MainActivity.this,Login.class));
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();

        session = new Session(MainActivity.this);
        idWisata = session.getIdWisata();

        DatabaseReference databaseUser = FirebaseDatabase.getInstance().getReference();
        databaseUser.keepSynced(true);

        qWisata = databaseUser.child("wisata").orderByChild("idWisata").equalTo(idWisata);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView =  findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Spinner spinNegara = findViewById(R.id.negara);
        Spinner spinJenisKendaraan = findViewById(R.id.jenisKendaraan);

        ArrayAdapter<CharSequence> spinnerCountShoesArrayAdapter = ArrayAdapter.createFromResource(this,R.array.country_name,android.R.layout.simple_spinner_item);
        spinnerCountShoesArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinNegara.setAdapter(spinnerCountShoesArrayAdapter);

        ArrayAdapter<CharSequence> spinnerKendaraanAdapter = ArrayAdapter.createFromResource(this,R.array.jenisKendaraan,android.R.layout.simple_spinner_item);
        spinnerKendaraanAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinJenisKendaraan.setAdapter(spinnerKendaraanAdapter);

        spinNegara.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                idNegara = country_code.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                idNegara = "1";
            }
        });

        spinJenisKendaraan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                idKendaraan = jenisKendaraan.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                idKendaraan = "2";
            }
        });

         spinJumlahKendaraan = findViewById(R.id.spinKendaraan);
         spinJumlahOrang = findViewById(R.id.spinJumlahOrang);


        Button btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String sJumlahOrang = String.valueOf(spinJumlahOrang.getSelectedItem());
                String sJumlahKendaraan = String.valueOf(spinJumlahKendaraan.getSelectedItem());

                Integer jumlahOrang = Integer.parseInt(sJumlahOrang);
                Integer jumlahKendaraan = Integer.parseInt(sJumlahKendaraan);


                switch (idNegara){
                    case "1":
                        hargaTiket = tiketDomestik;
                        break;
                    case "2":
                        hargaTiket = tiketManca;
                        break;
                    case "3":
                        hargaTiket = tiketWeekend;
                        break;
                }

                switch (idKendaraan){
                    case "2":
                        parkir = parkirRodaDua;
                        break;
                    case "3":
                        parkir = parkirRodaEmpat;
                        break;
                    case "4":
                        parkir = parkirBus;
                        break;
                    case "1":
                        parkir = 0;
                        break;
                }


                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy", Locale.US);
                String hari = df.format(c.getTime());
                Integer biayaTiket = jumlahOrang*hargaTiket;
                Integer biayaParkir = jumlahKendaraan*parkir;
                Integer totalBiaya = biayaTiket+biayaParkir;
//                Toast.makeText(MainActivity.this, ""+hari, Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity.this,ReviewTiket.class);
                Bundle bundle = new Bundle();

                bundle.putString("idWisata",idWisata);
                bundle.putInt("biayaTiket",biayaTiket);
                bundle.putInt("biayaParkir",biayaParkir);
                bundle.putInt("totalBiaya",totalBiaya);
                bundle.putString("hari",hari);
                bundle.putInt("jumlahOrang",jumlahOrang);
                bundle.putInt("jumlahKendaraan",jumlahKendaraan);
                bundle.putInt("hargaTiket",hargaTiket);
                bundle.putInt("parkir",parkir);
                bundle.putString("idNegara",idNegara);
                intent.putExtras(bundle);
                startActivity(intent);
//                Toast.makeText(MainActivity.this, ""+idWisata, Toast.LENGTH_SHORT).show();
//                Tiket tiket = new Tiket(id,idWisata,jumlahOrang,totalBiaya,biayaTiket,biayaParkir,hari);
//                databaseTiket.child(id).setValue(tiket);
//                Toast.makeText(MainActivity.this, ""+session.getIdWisata(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();


        qWisata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    tiketDomestik = ds.child("tiketDomestik").getValue(Integer.class);
                    tiketManca = ds.child("tiketManca").getValue(Integer.class);
                    tiketWeekend = ds.child("tiketWeekend").getValue(Integer.class);
                    parkirRodaDua = ds.child("parkirRodaDua").getValue(Integer.class);
                    parkirRodaEmpat = ds.child("parkirRodaEmpat").getValue(Integer.class);
                    parkirBus = ds.child("parkirBus").getValue(Integer.class);
//                    Toast.makeText(MainActivity.this, ""+tiketDomestik, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("er","eror");
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            firebaseAuth.signOut();
            startActivity(new Intent(MainActivity.this,Login.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Intent intent = new Intent(MainActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_gallery) {
            Intent intent = new Intent(MainActivity.this,RiwayatTiket.class);
            startActivity(intent);
        }else if (id == R.id.navPemasukan){
            startActivity(new Intent(MainActivity.this,Rekap.class));
        }else if (id == R.id.nav_send) {
            firebaseAuth.signOut();
            startActivity(new Intent(MainActivity.this,Login.class));
            finish();
        }else if (id == R.id.navBluetooth){
            startActivity(new Intent(MainActivity.this,BluetoothCoba.class));
        }else if (id == R.id.navLaporan){
            startActivity(new Intent(MainActivity.this,Laporan.class));
        }

        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
