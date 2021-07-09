package tourtle.ticketing2

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_login.*
import tourtle.ticketing2.model.User
import tourtle.ticketing2.utils.Session


class Login : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth

    //    private lateinit var progressDialog: ProgressDialog
    private var query: Query? = null
    private var session: Session? = null

    private lateinit var databaseUser: DatabaseReference

    private lateinit var dbref: DatabaseReference
    private lateinit var userDetail: User
    private lateinit var userArrayList: ArrayList<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        firebaseAuth = FirebaseAuth.getInstance()
        session = Session(this@Login)
        if (firebaseAuth!!.getCurrentUser() != null) {
            Log.e("err", "redirect main")
            startActivity(Intent(this@Login, MainActivity::class.java))
        }

        btnLogin.setOnClickListener {
            val email = et_email.getText().toString().trim { it <= ' ' }
            val password = et_password.getText().toString().trim { it <= ' ' }

            if (TextUtils.isEmpty(email)) {
                et_email.setError("Mohon isi email anda")
            } else if (TextUtils.isEmpty(password)) {
                et_password.setError("Mohon isi password anda")
            } else {

//                progressBar = ProgressBar(this@youractivity, null, android.R.attr.progressBarStyleLarge)
//                val params = RelativeLayout.LayoutParams(100, 100)
//                params.addRule(RelativeLayout.CENTER_IN_PARENT)
//                layout.addView(progressBar, params)
//
//                progressDialog!!.setMessage("verifikasi email dan password")
//                progressDialog!!.show()
                firebaseAuth!!.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this@Login) { task ->
//                    progressDialog!!.dismiss()
                        if (task.isSuccessful) {
                            databaseUser = FirebaseDatabase.getInstance().getReference("user")
                            query = databaseUser!!.orderByChild("email").equalTo(email)
                            query!!.addListenerForSingleValueEvent(object : ValueEventListener {

                                override fun onCancelled(error: DatabaseError) {
                                    Toast.makeText(
                                        this@Login,
                                        "Terjadi kesalahan, mohon coba lagi",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                                override fun onDataChange(snapshot: DataSnapshot) {
                                    for (ds in snapshot.getChildren()) {
                                        val idWisata = ds.child("idWisata").getValue(String::class.java)
                                        val namaWisata = ds.child("namaWisata").getValue(String::class.java)
                                        val email = ds.child("email").getValue(String::class.java)

                                        session?.setIdWisata(idWisata)
                                        session?.setNamaWisata(namaWisata)
                                        session?.setIdUser(ds.key)
                                        session?.setEmail(email)
                                        session?.setLoggedIn(true)
                                        finish()
                                        val intent = Intent(this@Login, MainActivity::class.java)
                                        startActivity(intent)
                                    }
                                }
                            })
                        } else {
                            Toast.makeText(
                                this@Login,
                                "Maaf salah email atau password",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
            }
        }
    }

    private fun getUserData() {
        dbref = FirebaseDatabase.getInstance().getReference("user")

        dbref.addValueEventListener(object : ValueEventListener {

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {
                    for (userSnapshot in snapshot.children) {
                        val user = userSnapshot.getValue(User::class.java)
                        userArrayList.add(user!!)
                    }
                }
            }

        })

//        progressDialog = ProgressDialog(this)
    }
}