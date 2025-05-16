package hrcode.labs.collabquiz.data.schemas.logic.contracts

object  AwnserContract {

        const val TABLE_NAME = "awnsers"
        object Columns {
            const val ID = "id"
            const val LAST_UPDATE = "last_update"
            const val QUESTION_ID = "question_id"
            const val AWNSER = "awnser"
            const val ISTRUE = "istrue"
        }


}