package hrcode.labs.collabquiz.data.schemas.logic.dao

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import hrcode.labs.collabquiz.data.domain.Sincro
import hrcode.labs.collabquiz.data.schemas.logic.contracts.SincroContract

class SincroDao(private val db: SQLiteDatabase) {

    fun insert(sincro: Sincro): Long {
        val values = ContentValues().apply {
            put(SincroContract.Columns.VERSION, sincro.version)
            put(SincroContract.Columns.LAST_UPDATE, sincro.lastUpdate)
        }
        return db.insert(SincroContract.TABLE_NAME, null, values)
    }

    fun getAll(): List<Sincro> {
        val sincrons = mutableListOf<Sincro>()
        val cursor: Cursor = db.query(
            SincroContract.TABLE_NAME,
            null, null, null, null, null, null
        )

        cursor.use {
            while (it.moveToNext()) {
                sincrons.add(
                    Sincro(
                        id = it.getInt(it.getColumnIndexOrThrow(SincroContract.Columns.ID)),
                        version = it.getInt(it.getColumnIndexOrThrow(SincroContract.Columns.VERSION)),
                        lastUpdate = it.getString(it.getColumnIndexOrThrow(SincroContract.Columns.LAST_UPDATE))
                    )
                )
            }
        }
        return sincrons
    }

    fun update(sincro: Sincro): Int {
        val values = ContentValues().apply {
            put(SincroContract.Columns.VERSION, sincro.version)
            put(SincroContract.Columns.LAST_UPDATE, sincro.lastUpdate)
        }
        return db.update(
            SincroContract.TABLE_NAME,
            values,
            "${SincroContract.Columns.ID} = ?",
            arrayOf(sincro.id.toString())
        )
    }

    fun delete(id: Int): Int {
        return db.delete(
            SincroContract.TABLE_NAME,
            "${SincroContract.Columns.ID} = ?",
            arrayOf(id.toString())
        )
    }
}