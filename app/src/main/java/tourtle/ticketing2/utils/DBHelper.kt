package tourtle.ticketing2.utils

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import tourtle.ticketing2.model.TiketWisata
import tourtle.ticketing2.model.Transaksi

class DBHelper(context: Context,
               factory: SQLiteDatabase.CursorFactory?) :
        SQLiteOpenHelper(context, DATABASE_NAME,
                factory, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_TRANSAKSI_TABLE = ("CREATE TABLE " +
                TABLE_TRANSAKSI + "("
                + TRANSAKSI_ID + " TEXT PRIMARY KEY,"
                + TRANSAKSI_ID_WISATA + " TEXT,"
                + TRANSAKSI_ID_USER + " TEXT,"
                + TRANSAKSI_ID_JENIS_TIKET + " TEXT,"
                + TRANSAKSI_DATETIME + " TEXT,"
                + TRANSAKSI_JUMLAH + " INTEGER,"
                + TRANSAKSI_BIAYA + " INTEGER)")

        val CREATE_TIKET_TABLE = ("CREATE TABLE "
                + TABLE_TIKET + "("
                + TIKET_ID + " TEXT PRIMARY KEY,"
                + TIKET_NAMA + " TEXT,"
                + TIKET_ID_WISATA + " TEXT,"
                + TIKET_HARGA + " INTEGER,"
                + TIKET_KUOTA + " INTEGER)")
        db.execSQL(CREATE_TRANSAKSI_TABLE)
        db.execSQL(CREATE_TIKET_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_TRANSAKSI")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_TIKET")
        onCreate(db)
    }

    fun addTransaksi(tiket: Transaksi) {
        val values = ContentValues()
        values.put(TRANSAKSI_ID, tiket.id)
        values.put(TRANSAKSI_ID_WISATA, tiket.idWisata)
        values.put(TRANSAKSI_ID_USER, tiket.idUser)
        values.put(TRANSAKSI_ID_JENIS_TIKET, tiket.idJenisTiket)
        values.put(TRANSAKSI_DATETIME, tiket.datetime)
        values.put(TRANSAKSI_JUMLAH, tiket.jumlah)
        values.put(TRANSAKSI_BIAYA, tiket.biaya)
        val db = this.writableDatabase
        db.insert(TABLE_TRANSAKSI, null, values)
        db.close()
    }

    fun getAllTransaksi(): ArrayList<Transaksi> {
        val db = this.readableDatabase
        val transaksi: ArrayList<Transaksi> = arrayListOf()
        val cursor = db.rawQuery("SELECT * FROM $TABLE_TRANSAKSI", null)
        if (cursor.moveToFirst()) {
            do {
                val id: String = cursor.getString(cursor.getColumnIndex(TRANSAKSI_ID))
                val idWisata: String = cursor.getString(cursor.getColumnIndex(TRANSAKSI_ID_WISATA))
                val idUser: String = cursor.getString(cursor.getColumnIndex(TRANSAKSI_ID_USER))
                val idJenisTiket: String = cursor.getString(cursor.getColumnIndex(TRANSAKSI_ID_JENIS_TIKET))
                val datetime: String = cursor.getString(cursor.getColumnIndex(TRANSAKSI_DATETIME))
                val jumlah: Int = cursor.getInt(cursor.getColumnIndex(TRANSAKSI_JUMLAH))
                val biaya: Int = cursor.getInt(cursor.getColumnIndex(TRANSAKSI_BIAYA))

                val model = Transaksi(id, idWisata, idUser, idJenisTiket, datetime, jumlah, biaya)
                transaksi.add(model)
            } while (cursor.moveToNext())
        }

        return transaksi
    }

    fun getAllTiket():ArrayList<TiketWisata> {
        val db = this.readableDatabase
        val tiket:ArrayList<TiketWisata> = arrayListOf()
        val cursor = db.rawQuery("SELECT * FROM $TABLE_TIKET",null)
        if (cursor.moveToFirst()){
            do{
                val id:String = cursor.getString(cursor.getColumnIndex(TIKET_ID))
                val idWisata:String = cursor.getString(cursor.getColumnIndex(TIKET_ID_WISATA))
                val nama:String = cursor.getString(cursor.getColumnIndex(TIKET_NAMA))
                val harga:Int = cursor.getInt(cursor.getColumnIndex(TIKET_HARGA))
                val kuota:Int = cursor.getInt(cursor.getColumnIndex(TIKET_KUOTA))

                val model = TiketWisata(id,idWisata,nama, harga, kuota)
                tiket.add(model)
            }while (cursor.moveToNext())
        }

        return tiket

    }

    companion object {
        private val DATABASE_VERSION = 2
        private val DATABASE_NAME = "tiketing"

        const val TABLE_TRANSAKSI = "transaksi"
        const val TRANSAKSI_ID = "id"
        const val TRANSAKSI_ID_WISATA = "idWisata"
        const val TRANSAKSI_ID_USER = "idUser"
        const val TRANSAKSI_ID_JENIS_TIKET = "idJenisTiket"
        const val TRANSAKSI_DATETIME = "datetime"
        const val TRANSAKSI_JUMLAH = "jumlah"
        const val TRANSAKSI_BIAYA = "biaya"

        const val TABLE_TIKET = "tiket"
        const val TIKET_ID = "id"
        const val TIKET_ID_WISATA = "idWisata"
        const val TIKET_NAMA = "nama"
        const val TIKET_HARGA = "harga"
        const val TIKET_KUOTA = "kuota"
    }
}