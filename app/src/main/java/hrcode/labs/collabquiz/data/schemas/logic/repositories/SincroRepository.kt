package hrcode.labs.collabquiz.data.schemas.logic.repositories

import hrcode.labs.collabquiz.data.domain.Sincro
import hrcode.labs.collabquiz.data.schemas.logic.dao.SincroDao


class SincroRepository(private val sincroDao: SincroDao) {

    fun insert(sincro: Sincro): Long {
        return sincroDao.insert(sincro)
    }

    fun getAll(): List<Sincro> {
        return sincroDao.getAll()
    }

    fun update(sincro: Sincro): Int {
        return sincroDao.update(sincro)
    }

    fun delete(id: Int): Int {
        return sincroDao.delete(id)
    }
}