package hrcode.labs.collabquiz

import android.app.Application
import hrcode.labs.collabquiz.data.schemas.logic.repositories.*
class App: Application() {
    val awnserRepository by lazy {
        AwnserRepository(applicationContext)
    }

    val questionRepository by lazy {
        QuestionRepository(applicationContext)
    }

    val syncRepository by lazy {
        SyncRepository(applicationContext)
    }


}