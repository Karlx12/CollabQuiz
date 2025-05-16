package hrcode.labs.collabquiz.data.domain

data class Question(
    val id: Int = 0,
    val question: String,
    val category: String,
    val active: Boolean,
    val lastUpdate: String,
    val version: Int
)