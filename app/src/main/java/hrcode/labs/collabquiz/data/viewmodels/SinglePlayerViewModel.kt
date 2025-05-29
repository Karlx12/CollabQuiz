    package hrcode.labs.collabquiz.data.viewmodels

    import androidx.lifecycle.ViewModel
    import kotlinx.coroutines.flow.MutableStateFlow
    import kotlinx.coroutines.flow.StateFlow

    class SinglePlayerViewModel : ViewModel() {

        private val _username = MutableStateFlow("")
        val username: StateFlow<String> = _username

        private val _score = MutableStateFlow(0)
        val score: StateFlow<Int> = _score

        private val _currentQuestionIndex = MutableStateFlow(0)
        val currentQuestionIndex: StateFlow<Int> = _currentQuestionIndex

        fun setUsername(name: String) {
            _username.value = name
        }

        fun incrementScore() {
            _score.value += 1
        }

        fun nextQuestion() {
            _currentQuestionIndex.value += 1
        }

        fun resetGame() {
            _score.value = 0
            _currentQuestionIndex.value = 0
        }

    }