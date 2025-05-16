package hrcode.labs.collabquiz.data.schemas.logic.contracts

object  QuestionContract {

        const val TABLE_NAME = "questions"
        object Columns {
            const val ID = "id"
            const val LAST_UPDATE = "last_update"
            const val VERSION = "version"
            const val CATEGORY = "category"
            const val QUESTION = "question"
            const val ACTIVE = "active"

        }


}