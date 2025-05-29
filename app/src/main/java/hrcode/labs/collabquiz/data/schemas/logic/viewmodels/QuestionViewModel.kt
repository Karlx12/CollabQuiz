package hrcode.labs.collabquiz.data.schemas.logic.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hrcode.labs.collabquiz.data.domain.Question
import hrcode.labs.collabquiz.data.schemas.logic.repositories.QuestionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlin.text.insert

class QuestionViewModel(
    private val repository: QuestionRepository
) : ViewModel() {
    private val _questions = MutableStateFlow<List<Question>>(emptyList())
    val questions: StateFlow<List<Question>> = _questions

    init {
        loadQuestions()
    }

    fun loadQuestions() {
        viewModelScope.launch {
            _questions.value = repository.getAll()
        }
    }

    fun add(question: Question) {
        viewModelScope.launch {
            val result = repository.insert(question)
            loadQuestions()
        }
    }
    fun insert(question: Question): Long {
        return repository.insert(question)
    }

    fun update(question: Question) {
        viewModelScope.launch {
            repository.update(question)
            loadQuestions()
        }
    }

    fun delete(id: Int) {
        viewModelScope.launch {
            repository.delete(id)
            loadQuestions()
        }
    }
}