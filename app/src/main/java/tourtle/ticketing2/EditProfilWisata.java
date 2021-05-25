package tourtle.ticketing2;

import android.app.ProgressDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import tourtle.ticketing2.model.WisataModel;
import tourtle.ticketing2.utils.Session;

public class EditProfilWisata extends AppCompatActivity {
    Session session;
    String idWisata,alamat,idKota,key;
    private Query qWisata;
    EditText etNamaWisata,etDomestik,etManca,etWeekend,etRodaDua,etRodaEmpat,etBus;
    Button btnRefresh;
    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profil_wisata);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Edit wisata");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        session = new Session(this);
        idWisata = session.getIdWisata();

        initView();

        getData();

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void initView(){
        etNamaWisata = findViewById(R.id.etNamaWisata);
        etDomestik = findViewById(R.id.etTiketDomestik);
        etManca = findViewById(R.id.etTiketManca);
        etWeekend = findViewById(R.id.etTiketWeekend);
        etRodaDua = findViewById(R.id.etParkirRodaDua);
        etRodaEmpat = findViewById(R.id.etParkirRodaEmpat);
        etBus = findViewById(R.id.etParkirBus);

        btnRefresh = findViewById(R.id.btnRefresh);
        linearLayout = findViewById(R.id.linear);

        Button btnSubmit = findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etNamaWisata.getText().toString().trim().equals("")){
                    etNamaWisata.setError("Mohon isi nama wisata");
                }else if (etDomestik.getText().toString().trim().equals("")){
                    etDomestik.setError("Mohon isi tiket domestik");
                }else if (etManca.getText().toString().trim().equals("")){
                    etManca.setError("Mohon isi tiket manca");
                }else if (etWeekend.getText().toString().trim().equals("")){
                    etWeekend.setError("Mohon isi tiket weekend");
                }else if (etRodaDua.getText().toString().trim().equals("")){
                    etRodaDua.setError("Mohon isi roda dua");
                }else if (etRodaEmpat.getText().toString().trim().equals("")){
                    etRodaEmpat.setError("Mohon isi roda empat");
                }else if (etBus.getText().toString().trim().equals("")){
                    etBus.setError("Mohon isi bus");
                }else{
                    final FirebaseDatabase database = FirebaseDatabase.getInstance();
                    final DatabaseReference wisata = database.getReference("/wisata/" + key);

                    String namaWisata = etNamaWisata.getText().toString();
                    Integer domestik = Integer.parseInt(etDomestik.getText().toString());
                    Integer manca = Integer.parseInt(etManca.getText().toString());
                    Integer weekend = Integer.parseInt(etWeekend.getText().toString());
                    Integer rodaDua = Integer.parseInt(etRodaDua.getText().toString());
                    Integer rodaEmpat = Integer.parseInt(etRodaEmpat.getText().toString());
                    Integer bus = Integer.parseInt(etBus.getText().toString());

                    WisataModel wisataModel = new WisataModel(idWisata,alamat,idKota,namaWisata,bus,rodaDua,rodaEmpat,domestik,manca,weekend);
                    wisata.setValue(wisataModel);
                    finish();

                }
            }
        });
    }

    public void getData(){
        DatabaseReference databaseUser = FirebaseDatabase.getInstance().getReference("wisata");
        qWisata = databaseUser.orderByChild("idWisata").equalTo(idWisata).limitToFirst(1);
        qWisata.keepSynced(true);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("sedang mengambil data");
        progressDialog.show();

        qWisata.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    key = ds.getKey();
                    idKota = ds.child("idKota").getValue(String.class);
                    alamat = ds.child("alamat").getValue(String.class);
                    etDomestik.setText(String.valueOf(ds.child("tiketDomestik").getValue(Integer.class)));
                    etManca.setText(String.valueOf(ds.child("tiketManca").getValue(Integer.class)));
                    etWeekend.setText(String.valueOf(ds.child("tiketWeekend").getValue(Integer.class)));
                    etRodaDua.setText(String.valueOf(ds.child("parkirRodaDua").getValue(Integer.class)));
                    etRodaEmpat.setText(String.valueOf(ds.child("parkirRodaEmpat").getValue(Integer.class)));
                    etBus.setText(String.valueOf(ds.child("parkirBus").getValue(Integer.class)));
                    etNamaWisata.setText(ds.child("namaWisata").getValue(String.class));
//                    Toast.makeText(MainActivity.this, ""+tiketDomestik, Toast.LENGTH_SHORT).show();
                }
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
                linearLayout.setVisibility(View.GONE);
                btnRefresh.setVisibility(View.VISIBLE);
                Toast.makeText(EditProfilWisata.this, "Terjadi gangguan, harap coba lagi", Toast.LENGTH_SHORT).show();
                Log.d("er", "eror");
            }
        });
    }
}
