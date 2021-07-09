package tourtle.ticketing2.utils

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import java.io.ByteArrayOutputStream

class DatabaseHelper(context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_IMAGE_TABLE = ("CREATE TABLE " + TABLE_IMAGE + "("
                + COL_ID + " INTEGER PRIMARY KEY ,"
                + IMAGE_ID + " TEXT,"
                + IMAGE_BITMAP + " TEXT )")
        db?.execSQL(CREATE_IMAGE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, i: Int, i1: Int) {
        val image = "DROP TABLE IF EXISTS $TABLE_IMAGE"
        db?.execSQL(image)
        onCreate(db)
    }

    fun insertImage(dbDrawable: Drawable?, imageId: String?) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(IMAGE_ID, imageId)
        val bitmap = (dbDrawable as BitmapDrawable?)?.getBitmap()
        val stream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.PNG, 100, stream)
        values.put(IMAGE_BITMAP, stream.toByteArray())
        db.insert(TABLE_IMAGE, null, values)
        db.close()
    }

    fun getImage(imageId: String?): ImageHelper? {
        val db = this.writableDatabase
        val cursor2 = db.query(TABLE_IMAGE, arrayOf(COL_ID, IMAGE_ID, IMAGE_BITMAP), IMAGE_ID
                + " LIKE '" + imageId + "%'", null, null, null, null)
        val imageHelper = ImageHelper()
        if (cursor2.moveToFirst()) {
            do {
//                imageHelper.imageId = cursor2.getString(1)
//                imageHelper.imageByteArray = cursor2.getBlob(2)
            } while (cursor2.moveToNext())
        }
        cursor2.close()
        db.close()
        return imageHelper
    }

    fun checkImage(): Int? {
        val selectQuery = "SELECT * from $TABLE_IMAGE"
        val db = this.writableDatabase
        var jumlah = 0
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                jumlah++
            } while (cursor.moveToNext())
        }
        db.close()
        return jumlah
    }

    companion object {
        private val DATABASE_NAME: String? = "wisata"
        private const val DATABASE_VERSION = 1
        private val TABLE_IMAGE: String? = "ImageTable"

        // Image Table Columns names
        private val COL_ID: String? = "col_id"
        private val IMAGE_ID: String? = "image_id"
        private val IMAGE_BITMAP: String? = "image_bitmap"
    }
}