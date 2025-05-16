package hrcode.labs.collabquiz.data.schemas.logic.dao

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import hrcode.labs.collabquiz.data.domain.Question
import hrcode.labs.collabquiz.data.schemas.logic.contracts.QuestionContract

class QuestionDao(private val db: SQLiteDatabase) {

    fun insert(question: Question): Long {
        val values = ContentValues().apply {
            put(QuestionContract.Columns.QUESTION, question.question)
            put(QuestionContract.Columns.CATEGORY, question.category)
            put(QuestionContract.Columns.ACTIVE, question.active)
            put(QuestionContract.Columns.LAST_UPDATE, question.lastUpdate)
            put(QuestionContract.Columns.VERSION, question.version)
        }
        return db.insert(QuestionContract.TABLE_NAME, null, values)
    }

    fun getAll(): List<Question> {
        val questions = mutableListOf<Question>()
        val cursor: Cursor = db.query(
            QuestionContract.TABLE_NAME,
            null, null,  null,null, null, null
        )

        cursor.use {
            while (it.moveToNext()) {
                questions.add(
                    Question(
                        id = it.getInt(it.getColumnIndexOrThrow(QuestionContract.Columns.ID)),
                        question = it.getString(it.getColumnIndexOrThrow(QuestionContract.Columns.QUESTION)),
                        category = it.getString(it.getColumnIndexOrThrow(QuestionContract.Columns.CATEGORY)),
                        active = it.getInt(it.getColumnIndexOrThrow(QuestionContract.Columns.ACTIVE)) == 1,
                        lastUpdate = it.getString(it.getColumnIndexOrThrow(QuestionContract.Columns.LAST_UPDATE)),
                        version = it.getInt(it.getColumnIndexOrThrow(QuestionContract.Columns.VERSION))
                    )
                )
            }
        }
        return questions
    }

    fun update(question: Question): Int {
        val values = ContentValues().apply {
            put(QuestionContract.Columns.QUESTION, question.question)
            put(QuestionContract.Columns.CATEGORY, question.category)
            put(QuestionContract.Columns.ACTIVE, question.active)
            put(QuestionContract.Columns.LAST_UPDATE, question.lastUpdate)
            put(QuestionContract.Columns.VERSION, question.version)
        }
        return db.update(
            QuestionContract.TABLE_NAME,
            values,
            "${QuestionContract.Columns.ID} = ?",
            arrayOf(question.id.toString())
        )
    }

    fun delete(id: Int): Int {
        return db.delete(
            QuestionContract.TABLE_NAME,
            "${QuestionContract.Columns.ID} = ?",
            arrayOf(id.toString())
        )
    }
}