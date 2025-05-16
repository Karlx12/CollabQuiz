package hrcode.labs.collabquiz.data.schemas.logic.repositories

import hrcode.labs.collabquiz.data.domain.Awnser
import hrcode.labs.collabquiz.data.schemas.logic.dao.AwnserDao

class AwnserRepository(private val awnserDao: AwnserDao) {

    fun insert(awnser: Awnser): Long {
        return awnserDao.insert(awnser)
    }

    fun getAll(): List<Awnser> {
        return awnserDao.getAll()
    }

    fun update(awnser: Awnser): Int {
        return awnserDao.update(awnser)
    }

    fun delete(id: Int): Int {
        return awnserDao.delete(id)
    }
}