package tourtle.ticketing2

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import io.realm.Realm
import io.realm.exceptions.RealmException
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import tourtle.ticketing2.adapter.v2.TiketAdapter
import tourtle.ticketing2.model.Realm.Tiket
import tourtle.ticketing2.utils.Session
import java.util.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var dialog: AlertDialog
    private lateinit var session: Session
    private lateinit var firebaseAuth: FirebaseAuth

    private var dataJenisTiket: ArrayList<String>? = null
    private var dataJenisKendaraan: ArrayList<String>? = null

    private var idJenisTiket: String? = null
    private var idJenisKendaraan: String? = null
    private var jumlahOrang: Int? = 1
    private var idWisata: String? = null
    private var idUser: String? = null

    private var tiketDomestik = 0
    private var tiketManca = 0
    private var tiketWeekend = 0
    private var parkirRodaDua = 0
    private var parkirRodaEmpat = 0
    private var parkirBus = 0

    private var hargaTiket = 0
    private var parkir = 0

    lateinit var realm: Realm
    lateinit var tiketAdapter: TiketAdapter
    val lm = LinearLayoutManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
        action()
    }

    fun init() {
        firebaseAuth = FirebaseAuth.getInstance()
        setDrawer()
        checkUpdate()
        checkLogin()
        spinner()

        realm = Realm.getDefaultInstance()

        rv_tiket.layoutManager = lm
        tiketAdapter = TiketAdapter(this)
        rv_tiket.adapter = tiketAdapter
        getAllTiket()

    }

    fun spinner() {
        val citynames = arrayOf("1", "2", "3")
        val kendaraan = arrayOf("1", "2", "3", "4")

        dataJenisTiket = ArrayList(Arrays.asList(*citynames))
        dataJenisKendaraan = ArrayList(Arrays.asList(*kendaraan))

        val spinnerCountShoesArrayAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.country_name,
            android.R.layout.simple_spinner_item
        )
        spinnerCountShoesArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sp_jenisTiket.adapter = spinnerCountShoesArrayAdapter

        sp_jenisTiket.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View, i: Int, l: Long) {
                idJenisTiket = dataJenisTiket!!.get(i)
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {
                idJenisTiket = "1"
            }
        }

        val spinnerKendaraanAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.jenisKendaraan,
            android.R.layout.simple_spinner_item
        )
        spinnerKendaraanAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sp_jenisKendaraan.adapter = spinnerKendaraanAdapter

        sp_jenisKendaraan.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View, i: Int, l: Long) {
                idJenisKendaraan = dataJenisKendaraan!!.get(i)
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {
                idJenisKendaraan = "1"
            }
        }

        sp_jmlOrang.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                jumlahOrang = sp_jmlOrang!!.selectedItem.toString().toInt()
                Log.e("jml orang tes", jumlahOrang.toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                jumlahOrang = 1
                Log.e("jml orang", jumlahOrang.toString())
            }

        }
    }

    fun action() {
        btnSubmit.setOnClickListener {
            if (tiketDomestik == 0) {
                startActivity(Intent(this@MainActivity, MainActivity::class.java))
                finish()
            } else {

                var jumlahKendaraan = sp_jmlKendaraan!!.selectedItem.toString().toInt()

                if (jumlahOrang == 0) {
                    Toast.makeText(
                        this@MainActivity,
                        "Masukkan jumlah orang dengan benar!",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    when (idJenisTiket) {
                        "1" -> hargaTiket = tiketDomestik
                        "2" -> hargaTiket = tiketManca
                        "3" -> hargaTiket = tiketWeekend
                    }

                    when (idJenisKendaraan) {
                        "1" -> parkir = 0
                        "2" -> parkir = parkirRodaDua
                        "3" -> parkir = parkirRodaEmpat
                        "4" -> parkir = parkirBus
                    }

                    Toast.makeText(
                        this@MainActivity,
                        "daaaa",
                        Toast.LENGTH_SHORT
                    ).show()

                    val biayaTiket = jumlahOrang!! * hargaTiket
                    val biayaParkir = jumlahKendaraan * parkir
                    val totalBiaya = biayaTiket + biayaParkir

                    Log.e("idWisata", idWisata.toString())
                    Log.e("biaya tiket", biayaTiket.toString())
                    Log.e("biaya parkir", biayaParkir.toString())
                    Log.e("total biaya", totalBiaya.toString())
                    Log.e("jumlah orang", jumlahOrang.toString())
                    Log.e("jumlah kendaraan", jumlahKendaraan.toString())
                    Log.e("harga tiket", hargaTiket.toString())
                    Log.e("parkir", parkir.toString())
                    Log.e("jenis tiket", idJenisTiket.toString())
                    Log.e("jenis kendaraan", idJenisKendaraan.toString())
                    Log.e("id user", idUser.toString())
                    Log.e("tiket domestik", tiketDomestik.toString())

                    realm.beginTransaction()
                    var count = 0

                    realm.where(Tiket::class.java).findAll().let {
                        for (i in it) {
                            count++
                        }
                    }
                    try {
                        var tiket = realm.createObject(Tiket::class.java)
                        tiket.setId(count+1)
                        tiket.setIdWisata(idUser.toString().toInt())
                        tiket.setBiayaParkir(biayaParkir)
                        tiket.setBiayaTiket(biayaTiket)
                        tiket.setTotalBiaya(totalBiaya)
                        tiket.setJumlahOrang(jumlahOrang!!)
                        tiket.setJumlahKendaraan(jumlahKendaraan)
                        tiket.setTimestamp("2021-12-01")
                        tiket.setTaxBanyuwangi("key")

                        realm.commitTransaction()

                        getAllTiket()
                    } catch (e: RealmException) {
                        Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    fun getAllTiket() {
        realm.where(Tiket::class.java).findAll().let {
            tiketAdapter.setTiket(it)
            Log.e("data", it.toString())
        }
    }

    fun setDrawer() {
        setSupportActionBar(toolbar)

        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    fun checkUpdate() {
        dialog = AlertDialog.Builder(this)
            .setTitle("New version available")
            .setMessage("Please, update app to new version to continue.")
            .setCancelable(false)
            .setPositiveButton(
                "Update"
            ) { dialog, which -> redirectStore("https://play.google.com/store/apps/details?id=tourtle.ticketing2&hl=en") }
            .create()
    }

    fun checkLogin() {
        session = Session(this@MainActivity)
        if (!session.loggedin()) {
            logout()
        }

        idWisata = session.getIdWisata()
        idUser = session.getIdUser()
    }

    private fun logout() {
        firebaseAuth.signOut()
        session.setLoggedIn(false)
        val intent = Intent(this@MainActivity, Login::class.java)
        startActivity(intent)
        finish()
    }

    override fun onStart() {
        super.onStart()

        val databaseUser = FirebaseDatabase.getInstance().getReference("wisata")
        val qWisata = databaseUser.orderByChild("idWisata").equalTo(idWisata).limitToFirst(1)
        qWisata.keepSynced(true)

        qWisata.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (ds in dataSnapshot.children) {
                    tiketDomestik = ds.child("tiketDomestik").getValue(Int::class.java)!!
                    tiketManca = ds.child("tiketManca").getValue(Int::class.java)!!
                    tiketWeekend = ds.child("tiketWeekend").getValue(Int::class.java)!!
                    parkirRodaDua = ds.child("parkirRodaDua").getValue(Int::class.java)!!
                    parkirRodaEmpat = ds.child("parkirRodaEmpat").getValue(Int::class.java)!!
                    parkirBus = ds.child("parkirBus").getValue(Int::class.java)!!
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(
                    this@MainActivity,
                    "Koneksi internet tidak stabil",
                    Toast.LENGTH_SHORT
                ).show()
                Log.d("er", "eror")
            }
        })
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        // Handle navigation view item clicks here.
        val id = item.itemId

        if (id == R.id.nav_camera) {
            startActivity(Intent(this@MainActivity, MainActivity::class.java))
            finish()
        } else if (id == R.id.nav_gallery) {
            startActivity(Intent(this@MainActivity, RiwayatTiket::class.java))
        } else if (id == R.id.navPemasukan) {
            startActivity(Intent(this@MainActivity, Rekap::class.java))
        } else if (id == R.id.navLaporan) {
            startActivity(Intent(this@MainActivity, Laporan::class.java))
        } else if (id == R.id.navBluetooth) {
            startActivity(Intent(this@MainActivity, BluetoothCoba::class.java))
        } else if (id == R.id.navSetting) {
            startActivity(Intent(this@MainActivity, EditProfilWisata::class.java))
        } else if (id == R.id.nav_online) {
            startActivity(Intent(this, TicketOnline::class.java))
        } else if (id == R.id.navSignOut) {
            firebaseAuth.signOut()
            val preferences = getSharedPreferences("myapp", MODE_PRIVATE)
            val editor = preferences.edit()
            editor.clear()
            editor.commit()
            startActivity(Intent(this@MainActivity, Login::class.java))
            finish()
        }

        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    private fun redirectStore(updateUrl: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(updateUrl))
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

}