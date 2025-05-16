package hrcode.labs.collabquiz.data.schemas.logic.dao

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import hrcode.labs.collabquiz.data.domain.Sync
import hrcode.labs.collabquiz.data.schemas.logic.contracts.SyncContract

class SyncDao(private val db: SQLiteDatabase) {

    fun insert(sync: Sync): Long {
        val values = ContentValues().apply {
            put(SyncContract.Columns.VERSION, sync.version)
            put(SyncContract.Columns.LAST_UPDATE, sync.lastUpdate)
        }
        return db.insert(SyncContract.TABLE_NAME, null, values)
    }

    fun getAll(): List<Sync> {
        val sincrons = mutableListOf<Sync>()
        val cursor: Cursor = db.query(
            SyncContract.TABLE_NAME,
            null, null, null, null, null, null
        )

        cursor.use {
            while (it.moveToNext()) {
                sincrons.add(
                    Sync(
                        id = it.getInt(it.getColumnIndexOrThrow(SyncContract.Columns.ID)),
                        version = it.getInt(it.getColumnIndexOrThrow(SyncContract.Columns.VERSION)),
                        lastUpdate = it.getString(it.getColumnIndexOrThrow(SyncContract.Columns.LAST_UPDATE))
                    )
                )
            }
        }
        return sincrons
    }

    fun update(sync: Sync): Int {
        val values = ContentValues().apply {
            put(SyncContract.Columns.VERSION, sync.version)
            put(SyncContract.Columns.LAST_UPDATE, sync.lastUpdate)
        }
        return db.update(
            SyncContract.TABLE_NAME,
            values,
            "${SyncContract.Columns.ID} = ?",
            arrayOf(sync.id.toString())
        )
    }

    fun delete(id: Int): Int {
        return db.delete(
            SyncContract.TABLE_NAME,
            "${SyncContract.Columns.ID} = ?",
            arrayOf(id.toString())
        )
    }
}