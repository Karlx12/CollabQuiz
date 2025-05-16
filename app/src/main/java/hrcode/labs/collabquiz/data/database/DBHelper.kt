package hrcode.labs.collabquiz.data.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import hrcode.labs.collabquiz.data.schemas.student.AwnserContract
import hrcode.labs.collabquiz.data.schemas.student.QuestionContract
import hrcode.labs.collabquiz.data.schemas.student.SincroContract


class DbHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "CollabQuiz.db"
        private const val DATABASE_VERSION = 1

//        private const val SQL_CREATE_STUDENT_TABLE = """
//            CREATE TABLE ${StudentContract.TABLE_NAME} (
//                ${StudentContract.Columns.ID} INTEGER PRIMARY KEY AUTOINCREMENT,
//                ${StudentContract.Columns.NAME} TEXT NOT NULL,
//                ${StudentContract.Columns.LASTNAME} TEXT NOT NULL,
//                ${StudentContract.Columns.STUDENTCODE} TEXT NOT NULL,
//                ${StudentContract.Columns.EMAIL} TEXT NOT NULL
//            )
//        """

        private const val SQL_CREATE_QUESTION_TABLE = """
            CREATE TABLE ${QuestionContract.TABLE_NAME} (
                ${QuestionContract.Columns.ID} INTEGER PRIMARY KEY AUTOINCREMENT,
                ${QuestionContract.Columns.QUESTION} TEXT NOT NULL,
                ${QuestionContract.Columns.CATEGORY} TEXT NOT NULL,
                ${QuestionContract.Columns.OPTIONS} TEXT NOT NULL,
                ${QuestionContract.Columns.ACTIVE} INTEGER NOT NULL,
                ${QuestionContract.Columns.LAST_UPDATE} TEXT NOT NULL,
                ${QuestionContract.Columns.VERSION} INTEGER NOT NULL
            )
        """

        private const val SQL_CREATE_AWNSER_TABLE = """
            CREATE TABLE ${AwnserContract.TABLE_NAME} (
                ${AwnserContract.Columns.ID} INTEGER PRIMARY KEY AUTOINCREMENT,
                ${AwnserContract.Columns.QUESTION_ID} INTEGER NOT NULL,
                ${AwnserContract.Columns.AWNSER} TEXT NOT NULL,
                ${AwnserContract.Columns.ISTRUE} INTEGER NOT NULL,
                ${AwnserContract.Columns.LAST_UPDATE} TEXT NOT NULL,
                FOREIGN KEY(${AwnserContract.Columns.QUESTION_ID}) REFERENCES ${QuestionContract.TABLE_NAME}(${QuestionContract.Columns.ID})
            )
        """

        private const val SQL_CREATE_SINCRO_TABLE = """
            CREATE TABLE ${SincroContract.TABLE_NAME} (
                ${SincroContract.Columns.ID} INTEGER PRIMARY KEY AUTOINCREMENT,
                ${SincroContract.Columns.VERSION} INTEGER NOT NULL,
                ${SincroContract.Columns.LAST_UPDATE} TEXT NOT NULL
            )
        """
    }

    override fun onCreate(db: SQLiteDatabase) {
//        db.execSQL(SQL_CREATE_STUDENT_TABLE)
        db.execSQL(SQL_CREATE_QUESTION_TABLE)
        db.execSQL(SQL_CREATE_AWNSER_TABLE)
        db.execSQL(SQL_CREATE_SINCRO_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
//        db.execSQL("DROP TABLE IF EXISTS ${StudentContract.TABLE_NAME}")
        db.execSQL("DROP TABLE IF EXISTS ${QuestionContract.TABLE_NAME}")
        db.execSQL("DROP TABLE IF EXISTS ${AwnserContract.TABLE_NAME}")
        db.execSQL("DROP TABLE IF EXISTS ${SincroContract.TABLE_NAME}")
        onCreate(db)
    }
}