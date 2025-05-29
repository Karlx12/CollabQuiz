package hrcode.labs.collabquiz.data.schemas.logic.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hrcode.labs.collabquiz.data.domain.Awnser
import hrcode.labs.collabquiz.data.schemas.logic.repositories.AwnserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.text.insert

class AwnserViewModel(
    private val repository: AwnserRepository
) : ViewModel() {
    private val _awnsers = MutableStateFlow<List<Awnser>>(emptyList())
    val awnsers: StateFlow<List<Awnser>> = _awnsers

    init {
        loadAwnsers()
    }

    fun loadAwnsers() {
        viewModelScope.launch {
            _awnsers.value = repository.getAll()
        }
    }

    fun add(awnser: Awnser) {
        viewModelScope.launch {
            repository.insert(awnser)
            loadAwnsers()
        }
    }
    fun insert(awnser: Awnser): Long {
        return repository.insert(awnser)
    }

    fun update(awnser: Awnser) {
        viewModelScope.launch {
            repository.update(awnser)
            loadAwnsers()
        }
    }

    fun delete(id: Int) {
        viewModelScope.launch {
            repository.delete(id)
            loadAwnsers()
        }
    }
}