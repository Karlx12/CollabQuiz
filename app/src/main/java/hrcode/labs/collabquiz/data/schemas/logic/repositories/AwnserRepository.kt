package hrcode.labs.collabquiz.data.schemas.logic.repositories

import android.content.Context
import hrcode.labs.collabquiz.data.database.DbHelper
import hrcode.labs.collabquiz.data.domain.Awnser
import hrcode.labs.collabquiz.data.schemas.logic.dao.AwnserDao

class AwnserRepository(context: Context) {
    private val dbHelper = DbHelper(context)
    private val dao by lazy { AwnserDao(dbHelper.writableDatabase) }

    fun insert(awnser: Awnser): Long {
        return dao.insert(awnser)
    }

    fun getAll(): List<Awnser> {
        return dao.getAll()
    }

    fun update(awnser: Awnser): Int {
        return dao.update(awnser)
    }

    fun delete(id: Int): Int {
        return dao.delete(id)
    }
}