package hrcode.labs.collabquiz.data.domain

data class Awnser(
    val id: Int = 0,
    val questionId: Int,
    val awnser: String,
    val isTrue: Boolean,
    val lastUpdate: String
)