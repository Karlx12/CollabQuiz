package hrcode.labs.collabquiz.data.schemas.logic.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import hrcode.labs.collabquiz.data.schemas.logic.repositories.AwnserRepository
import hrcode.labs.collabquiz.data.schemas.logic.viewmodel.AwnserViewModel

class AwnserViewModelFactory(
    private val repository: AwnserRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AwnserViewModel::class.java)) {
            return AwnserViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}