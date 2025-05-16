package hrcode.labs.collabquiz.data.schemas.logic.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hrcode.labs.collabquiz.data.domain.Sincro
import hrcode.labs.collabquiz.data.schemas.logic.repositories.SincroRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SincroViewModel(
    private val repository: SincroRepository
) : ViewModel() {
    private val _sincrons = MutableStateFlow<List<Sincro>>(emptyList())
    val sincrons: StateFlow<List<Sincro>> = _sincrons

    init {
        loadSincrons()
    }

    fun loadSincrons() {
        viewModelScope.launch {
            _sincrons.value = repository.getAll()
        }
    }

    fun add(sincro: Sincro) {
        viewModelScope.launch {
            repository.insert(sincro)
            loadSincrons()
        }
    }

    fun update(sincro: Sincro) {
        viewModelScope.launch {
            repository.update(sincro)
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