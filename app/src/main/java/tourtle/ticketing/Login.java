package tourtle.ticketing;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import tourtle.ticketing.utils.Session;

public class Login extends AppCompatActivity {
    private EditText etEmail;
    private EditText etPassword;

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;
    private Query query;
    private Session session;

    private DatabaseReference databaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        etEmail = findViewById(R.id.email);
        etPassword = findViewById(R.id.password);

        session = new Session(Login.this);
        if (firebaseAuth.getCurrentUser()!=null){
            startActivity(new Intent(Login.this,MainActivity.class));
        }

        databaseUser = FirebaseDatabase.getInstance().getReference("user");

        progressDialog = new ProgressDialog(this);

        Button btnLogin = findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = etEmail.getText().toString().trim();
                String password = etPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    etEmail.setError("Mohon isi email anda");
                }else if (TextUtils.isEmpty(password)){
                    etPassword.setError("Mohon isi password anda");
                }else{
                    progressDialog.setMessage("verifikasi email dan password");
                    progressDialog.show();

                    firebaseAuth.signInWithEmailAndPassword(email,password)
                            .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressDialog.dismiss();

                                    if (task.isSuccessful()){
                                        query = databaseUser.orderByChild("email").equalTo(email);
                                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                String idWisata,namaWisata;
                                                for (DataSnapshot ds :dataSnapshot.getChildren()){
                                                    idWisata = ds.child("idWisata").getValue(String.class);
                                                    namaWisata = ds.child("namaWisata").getValue(String.class);
                                                    session.setIdWisata(idWisata);
                                                    session.setNamaWisata(namaWisata);

                                                }


                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {

                                            }
                                        });

                                        finish();
                                        Intent intent = new Intent(Login.this,MainActivity.class);
                                        startActivity(intent);

                                    }
                                }
                            });
                }
            }
        });
    }
}
