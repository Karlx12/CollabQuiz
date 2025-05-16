package hrcode.labs.collabquiz.data.schemas.logic.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hrcode.labs.collabquiz.data.domain.Sync
import hrcode.labs.collabquiz.data.schemas.logic.repositories.SyncRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SyncViewModel(
    private val repository: SyncRepository
) : ViewModel() {
    private val _sincrons = MutableStateFlow<List<Sync>>(emptyList())
    val sincrons: StateFlow<List<Sync>> = _sincrons

    init {
        loadSincrons()
    }

    fun loadSincrons() {
        viewModelScope.launch {
            _sincrons.value = repository.getAll()
        }
    }

    fun add(sync: Sync) {
        viewModelScope.launch {
            repository.insert(sync)
            loadSincrons()
        }
    }

    fun update(sync: Sync) {
        viewModelScope.launch {
            repository.update(sync)
            loadSincrons()
        }
    }

    fun delete(id: Int) {
        viewModelScope.launch {
            repository.delete(id)
            loadSincrons()
        }
    }
}