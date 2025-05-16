package hrcode.labs.collabquiz.data.schemas.logic.repositories

import hrcode.labs.collabquiz.data.domain.Sync
import hrcode.labs.collabquiz.data.schemas.logic.dao.SyncDao


class SyncRepository(private val syncDao: SyncDao) {

    fun insert(sync: Sync): Long {
        return syncDao.insert(sync)
    }

    fun getAll(): List<Sync> {
        return syncDao.getAll()
    }

    fun update(sync: Sync): Int {
        return syncDao.update(sync)
    }

    fun delete(id: Int): Int {
        return syncDao.delete(id)
    }
}