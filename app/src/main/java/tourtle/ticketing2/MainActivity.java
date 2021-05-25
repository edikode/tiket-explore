package tourtle.ticketing2;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;

import android.util.Log;
import android.view.View;

import com.google.android.material.navigation.NavigationView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
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
import java.util.Date;
import java.util.Locale;

import tourtle.ticketing2.kotlin.LaporanTiket;
import tourtle.ticketing2.model.UserModel;
import tourtle.ticketing2.utils.InternetConnection;
import tourtle.ticketing2.utils.Session;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Session session;
    private FirebaseAuth firebaseAuth;
    private Integer tiketDomestik = 0;
    private Integer tiketManca = 0;
    private Integer tiketWeekend = 0;
    private Integer hargaTiket = 0;
    private Integer parkirRodaDua = 0;
    private Integer parkirRodaEmpat = 0;
    private Integer parkirBus = 0;
    private Integer parkir = 0;
    private String idNegara, idKendaraan, idWisata, idUser;

    private ArrayList<String> country_code;
    private ArrayList<String> jenisKendaraan;

    private Spinner spinJumlahOrang;
    private Spinner spinJumlahKendaraan;
    private AlertDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String citynames[] = {"1", "2", "3"};
        String kendaraan[] = {"2", "3", "4", "1"};

        country_code = new ArrayList<>(Arrays.asList(citynames));
        jenisKendaraan = new ArrayList<>(Arrays.asList(kendaraan));

        firebaseAuth = FirebaseAuth.getInstance();
        session = new Session(MainActivity.this);
        idWisata = session.getIdWisata();
        idUser = session.getIdUser();

        if (!session.loggedin()) {
            logout();
        }

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("MM-dd-yyyy", Locale.getDefault());


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        dialog = new AlertDialog.Builder(this)
                .setTitle("New version available")
                .setMessage("Please, update app to new version to continue.")
                .setCancelable(false)
                .setPositiveButton("Update",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                redirectStore("https://play.google.com/store/apps/details?id=tourtle.ticketing2&hl=en");
                            }
                        }).create();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Spinner spinNegara = findViewById(R.id.negara);
        Spinner spinJenisKendaraan = findViewById(R.id.jenisKendaraan);

        ArrayAdapter<CharSequence> spinnerCountShoesArrayAdapter = ArrayAdapter.createFromResource(this, R.array.country_name, android.R.layout.simple_spinner_item);
        spinnerCountShoesArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinNegara.setAdapter(spinnerCountShoesArrayAdapter);

        ArrayAdapter<CharSequence> spinnerKendaraanAdapter = ArrayAdapter.createFromResource(this, R.array.jenisKendaraan, android.R.layout.simple_spinner_item);
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
                if (!InternetConnection.checkConnection(getApplicationContext())) {
                    Toast.makeText(MainActivity.this, "Tidak ada koneksi internet", Toast.LENGTH_SHORT).show();
                } else if (tiketDomestik == 0) {
                    startActivity(new Intent(MainActivity.this, MainActivity.class));
                    finish();
                } else {
                    String sJumlahOrang = String.valueOf(spinJumlahOrang.getSelectedItem());
                    String sJumlahKendaraan = String.valueOf(spinJumlahKendaraan.getSelectedItem());

                    Integer jumlahOrang = Integer.parseInt(sJumlahOrang);
                    Integer jumlahKendaraan = Integer.parseInt(sJumlahKendaraan);


                    switch (idNegara) {
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

                    switch (idKendaraan) {
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


                    Integer biayaTiket = jumlahOrang * hargaTiket;
                    Integer biayaParkir = jumlahKendaraan * parkir;
                    Integer totalBiaya = biayaTiket + biayaParkir;
                    Intent intent = new Intent(MainActivity.this, ReviewTiket.class);
                    Bundle bundle = new Bundle();

                    bundle.putString("idWisata", idWisata);
                    bundle.putInt("biayaTiket", biayaTiket);
                    bundle.putInt("biayaParkir", biayaParkir);
                    bundle.putInt("totalBiaya", totalBiaya);
                    bundle.putInt("jumlahOrang", jumlahOrang);
                    bundle.putInt("jumlahKendaraan", jumlahKendaraan);
                    bundle.putInt("hargaTiket", hargaTiket);
                    bundle.putInt("parkir", parkir);
                    bundle.putString("idNegara", idNegara);
                    bundle.putString("jenisKendaraan", idKendaraan);
                    bundle.putString("idUser", idUser);
                    intent.putExtras(bundle);
                    startActivity(intent);

                }

            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();

        lastOnline();

        DatabaseReference databaseUser = FirebaseDatabase.getInstance().getReference("wisata");
        Query qWisata = databaseUser.orderByChild("idWisata").equalTo(idWisata).limitToFirst(1);
        qWisata.keepSynced(true);
//        if (tiketDomestik == 0) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("sedang mengambil data");
        progressDialog.show();

        qWisata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    tiketDomestik = ds.child("tiketDomestik").getValue(Integer.class);
                    tiketManca = ds.child("tiketManca").getValue(Integer.class);
                    tiketWeekend = ds.child("tiketWeekend").getValue(Integer.class);
                    parkirRodaDua = ds.child("parkirRodaDua").getValue(Integer.class);
                    parkirRodaEmpat = ds.child("parkirRodaEmpat").getValue(Integer.class);
                    parkirBus = ds.child("parkirBus").getValue(Integer.class);
//                    Toast.makeText(MainActivity.this, ""+tiketDomestik, Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "Koneksi internet tidak stabil", Toast.LENGTH_SHORT).show();
                Log.d("er", "eror");
            }
        });

//        }

        final int versionCode = BuildConfig.VERSION_CODE;

        DatabaseReference databaseVersi = FirebaseDatabase.getInstance().getReference("versi");
        databaseVersi.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    int id_versi = ds.child("versi").getValue(Integer.class);
                    if (versionCode < id_versi) {
                        onUpdateNeeded();
                    } else {
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
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
            SharedPreferences preferences = getSharedPreferences("myapp", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.commit();

            startActivity(new Intent(MainActivity.this, Login.class));
            finish();
        } else if (id == R.id.action_refresh) {
            startActivity(new Intent(MainActivity.this, MainActivity.class));
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
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.nav_gallery) {
            Intent intent = new Intent(MainActivity.this, RiwayatTiket.class);
            startActivity(intent);
        } else if (id == R.id.navPemasukan) {
            startActivity(new Intent(MainActivity.this, Rekap.class));
        } else if (id == R.id.nav_send) {
            firebaseAuth.signOut();
            SharedPreferences preferences = getSharedPreferences("myapp", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            editor.commit();
            startActivity(new Intent(MainActivity.this, Login.class));
            finish();
        } else if (id == R.id.navLaporan) {
            startActivity(new Intent(MainActivity.this, Laporan.class));
        } else if (id == R.id.navBluetooth) {
            startActivity(new Intent(MainActivity.this, BluetoothCoba.class));
        } else if (id == R.id.navSetting) {
            startActivity(new Intent(MainActivity.this, EditProfilWisata.class));
        } else if (id == R.id.nav_online){
            startActivity(new Intent(this,TicketOnline.class));
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void onUpdateNeeded() {
        if (!isFinishing()) {

            dialog.show();
        } else {
            Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_LONG).show();
        }

    }

    private void redirectStore(String updateUrl) {
        final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(updateUrl));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public void lastOnline() {
        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        SimpleDateFormat fJam = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());

        final String tanggal = df.format(c);
        final String jam = fJam.format(c);
        final String idWisata = session.getIdWisata();
        final String idUser = session.getIdUser();
        final String namaWisata = session.getNamaWisata();
        final String email = session.getEmail();
        final int versionCode = BuildConfig.VERSION_CODE;

        final FirebaseDatabase database = FirebaseDatabase.getInstance();

// Stores the timestamp of my last disconnect (the last time I was seen online)
        final DatabaseReference lastOnlineRef = database.getReference("/user/" + idUser);

        UserModel userModel = new UserModel(idWisata, namaWisata, tanggal, jam, "Online", email, versionCode);
        lastOnlineRef.setValue(userModel);

        final DatabaseReference connectedRef = database.getReference(".info/connected");
        connectedRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                boolean connected = snapshot.getValue(Boolean.class);
                if (connected) {


                    UserModel userModel = new UserModel(idWisata, namaWisata, tanggal, jam, "Online", email, versionCode);

                    lastOnlineRef.onDisconnect().setValue(userModel);

                    UserModel userModelof = new UserModel(idWisata, namaWisata, tanggal, jam, "ofline", email, versionCode);

                    lastOnlineRef.onDisconnect().setValue(userModelof);

                    // Add this device to my connections list
                    // this value could contain info about the device or a timestamp too
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("Presence", "Listener was cancelled at .info/connected");
            }
        });
    }

    private void logout() {
        firebaseAuth.signOut();
        session.setLoggedIn(false);
        Intent intent = new Intent(MainActivity.this, Login.class);
        startActivity(intent);
        finish();
    }


}
