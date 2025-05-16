package hrcode.labs.collabquiz.data.schemas.logic.dao

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase

import hrcode.labs.collabquiz.data.domain.Awnser;
import hrcode.labs.collabquiz.data.schemas.logic.contracts.AwnserContract;

class AwnserDao(private val db: SQLiteDatabase) {

    fun insert(awnser:Awnser): Long {
        val values = ContentValues().apply {
            put(AwnserContract.Columns.QUESTION_ID, awnser.questionId)
            put(AwnserContract.Columns.AWNSER, awnser.awnser)
            put(AwnserContract.Columns.ISTRUE, awnser.isTrue)
        }
        return db.insert(AwnserContract.TABLE_NAME, null, values)
    }

    fun getAll(): List<Awnser> {
        val awnsers = mutableListOf<Awnser>()
        val cursor: Cursor = db.query(
            AwnserContract.TABLE_NAME,
            null, null, null, null, null, null
        )

        cursor.use {
            while (it.moveToNext()) {
                awnsers.add(
                    Awnser(
                        id = it.getInt(it.getColumnIndexOrThrow(AwnserContract.Columns.ID)),
                        questionId = it.getInt(it.getColumnIndexOrThrow(AwnserContract.Columns.QUESTION_ID)),
                        awnser = it.getString(it.getColumnIndexOrThrow(AwnserContract.Columns.AWNSER)),
                        isTrue = it.getInt(it.getColumnIndexOrThrow(AwnserContract.Columns.ISTRUE)) == 1,
                    )
                )
            }
        }
        return awnsers
    }

    fun update(awnser: Awnser): Int {
        val values = ContentValues().apply {
            put(AwnserContract.Columns.QUESTION_ID, awnser.questionId)
            put(AwnserContract.Columns.AWNSER, awnser.awnser)
            put(AwnserContract.Columns.ISTRUE, awnser.isTrue)
        }
        return db.update(
            AwnserContract.TABLE_NAME,
            values,
            "${AwnserContract.Columns.ID} = ?",
            arrayOf(awnser.id.toString())
        )
    }

    fun delete(id: Int): Int {
        return db.delete(
            AwnserContract.TABLE_NAME,
            "${AwnserContract.Columns.ID} = ?",
            arrayOf(id.toString())
        )
    }
}