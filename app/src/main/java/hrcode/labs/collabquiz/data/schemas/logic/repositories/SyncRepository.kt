package hrcode.labs.collabquiz.data.schemas.logic.repositories

import android.content.Context
import hrcode.labs.collabquiz.data.database.DbHelper
import hrcode.labs.collabquiz.data.domain.Sync
import hrcode.labs.collabquiz.data.schemas.logic.dao.SyncDao


class SyncRepository(context: Context) {
    private val dbHelper = DbHelper(context)
    private val dao by lazy { SyncDao(dbHelper.writableDatabase) }


    fun insert(sync: Sync): Long {
        return dao.insert(sync)
    }

    fun getAll(): List<Sync> {
        return dao.getAll()
    }

    fun update(sync: Sync): Int {
        return dao.update(sync)
    }

    fun delete(id: Int): Int {
        return dao.delete(id)
    }
}