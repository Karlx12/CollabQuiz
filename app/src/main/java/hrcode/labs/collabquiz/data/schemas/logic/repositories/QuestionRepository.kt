package hrcode.labs.collabquiz.data.schemas.logic.repositories

import hrcode.labs.collabquiz.data.domain.Question
import hrcode.labs.collabquiz.data.schemas.logic.dao.QuestionDao

class QuestionRepository(private val questionDao: QuestionDao) {

    fun insert(question: Question): Long {
        return questionDao.insert(question)
    }

    fun getAll(): List<Question> {
        return questionDao.getAll()
    }

    fun update(question: Question): Int {
        return questionDao.update(question)
    }

    fun delete(id: Int): Int {
        return questionDao.delete(id)
    }
}