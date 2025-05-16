package hrcode.labs.collabquiz.data.schemas.logic.contracts

object  SincroContract {

        const val TABLE_NAME = "sincronizations"
        object Columns {
            const val ID = "id"
            const val LAST_UPDATE = "last_update"
            const val VERSION = "version"
        }
}