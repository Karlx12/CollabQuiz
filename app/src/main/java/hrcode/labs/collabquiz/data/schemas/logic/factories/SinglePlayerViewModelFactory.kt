package hrcode.labs.collabquiz.data.schemas.logic.factories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import hrcode.labs.collabquiz.data.viewmodels.SinglePlayerViewModel

class SinglePlayerViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SinglePlayerViewModel() as T
    }
}