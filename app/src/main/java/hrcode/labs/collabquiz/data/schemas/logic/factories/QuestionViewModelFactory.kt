package hrcode.labs.collabquiz.data.schemas.logic.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import hrcode.labs.collabquiz.data.schemas.logic.repositories.QuestionRepository
import hrcode.labs.collabquiz.data.schemas.logic.viewmodel.QuestionViewModel

class QuestionViewModelFactory(
    private val repository: QuestionRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QuestionViewModel::class.java)) {
            return QuestionViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}