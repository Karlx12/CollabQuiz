package hrcode.labs.collabquiz.data.domain

data class Sync(
    val id: Int = 0,
    val version: Int,
    val lastUpdate: String
)