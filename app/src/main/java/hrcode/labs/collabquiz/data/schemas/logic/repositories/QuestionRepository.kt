package hrcode.labs.collabquiz.data.schemas.logic.repositories

import android.content.Context
import hrcode.labs.collabquiz.data.database.DbHelper
import hrcode.labs.collabquiz.data.domain.Question

import hrcode.labs.collabquiz.data.schemas.logic.dao.QuestionDao

class QuestionRepository(context: Context) {
    private val dbHelper = DbHelper(context)
    private val dao by lazy { QuestionDao(dbHelper.writableDatabase) }


    fun insert(question: Question): Long {
        return if (!questionExists(question)) {
            dao.insert(question)
        } else {
            // Return -1 to indicate question already exists
            -1
        }
    }

    fun getAll(): List<Question> {
        return dao.getAll()
    }

    fun update(question: Question): Int {
        return dao.update(question)
    }

    fun delete(id: Int): Int {
        return dao.delete(id)
    }
    fun questionExists(question: Question): Boolean {
        return dao.questionExists(question.question)
    }

}