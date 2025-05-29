package hrcode.labs.collabquiz.data.schemas.logic.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import hrcode.labs.collabquiz.data.schemas.logic.repositories.SyncRepository
import hrcode.labs.collabquiz.data.schemas.logic.viewmodel.SyncViewModel

class SyncViewModelFactory(
    private val repository: SyncRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SyncViewModel::class.java)) {
            return SyncViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}