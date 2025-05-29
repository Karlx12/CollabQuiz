package hrcode.labs.collabquiz.ui.screens.question

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import hrcode.labs.collabquiz.Screen
import hrcode.labs.collabquiz.data.domain.Awnser
import hrcode.labs.collabquiz.data.domain.Question
import hrcode.labs.collabquiz.data.schemas.logic.viewmodel.AwnserViewModel
import hrcode.labs.collabquiz.data.schemas.logic.viewmodel.QuestionViewModel
import hrcode.labs.collabquiz.data.viewmodels.SinglePlayerViewModel
import hrcode.labs.collabquiz.ui.theme.CollabQuizTheme

@Composable
fun QuestionScreen(
    navController: NavController,
    questionViewModel: QuestionViewModel,
    awnserViewModel: AwnserViewModel,
    questionIndex: Int,
    singlePlayerViewModel: SinglePlayerViewModel
) {
    val questions by questionViewModel.questions.collectAsState()
    val answers by awnserViewModel.awnsers.collectAsState()
    val score by singlePlayerViewModel.score.collectAsState()
    val currentQuestionIndex by singlePlayerViewModel.currentQuestionIndex.collectAsState()


    if (questionIndex >= questions.size) {
        LaunchedEffect(Unit) {
            navController.navigate(Screen.SinglePlayerRoute.ScoreRoute.route) {
                popUpTo(Screen.SinglePlayerRoute.route) {
                    inclusive = false
                }
            }
        }
        return
    }

    val currentQuestion = questions[questionIndex]
    val questionAnswers = answers.filter { it.questionId == currentQuestion.id }

    QuestionContent(
        question = currentQuestion,
        answers = questionAnswers,
        onAnswerSelected = { selectedAnswer ->
            // Award 1 point for correct answer
            if (selectedAnswer.isTrue) {
                singlePlayerViewModel.incrementScore()
            }

            // Move to next question
            singlePlayerViewModel.nextQuestion()
            navController.navigate(Screen.SinglePlayerRoute.QuestionRoute.createRoute(questionIndex + 1))
        },
        questionNumber = questionIndex + 1,
        totalQuestions = questions.size
    )
}
@Composable
fun QuestionContent(
    question: Question,
    answers: List<Awnser>,
    onAnswerSelected: (Awnser) -> Unit,
    questionNumber: Int,
    totalQuestions: Int
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Question progress indicator
        Text(
            text = "Pregunta $questionNumber de $totalQuestions",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.fillMaxWidth()
        )

        // Question card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = question.question,
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                )

                Text(
                    text = "Categoría: ${question.category}",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
        }

        // Answers
        var selectedAnswerId by remember { mutableStateOf<Int?>(null) }

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            answers.forEach { answer ->
                AnswerOption(
                    answer = answer,
                    isSelected = selectedAnswerId == answer.id,
                    onSelect = { selectedAnswerId = answer.id }
                )
            }
        }

        // Next button
        Button(
            onClick = {
                selectedAnswerId?.let { id ->
                    val selectedAnswer = answers.find { it.id == id }
                    selectedAnswer?.let { onAnswerSelected(it) }
                }
            },
            enabled = selectedAnswerId != null,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(top = 16.dp)
        ) {
            Text("Siguiente pregunta")
        }
    }
}

@Composable
fun AnswerOption(
    answer: Awnser,
    isSelected: Boolean,
    onSelect: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onSelect),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected)
                MaterialTheme.colorScheme.primaryContainer
            else
                MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(
                selected = isSelected,
                onClick = onSelect
            )
            Text(
                text = answer.awnser,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .weight(1f)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun QuestionContentPreview() {
    CollabQuizTheme {
        QuestionContent(
            question = Question(
                id = 1,
                question = "¿Cuál es el lenguaje de programación principal para el desarrollo de aplicaciones Android?",
                category = "Desarrollo móvil",
                active = true
            ),
            answers = listOf(
                Awnser(id = 1, questionId = 1, awnser = "Java", isTrue = false),
                Awnser(id = 2, questionId = 1, awnser = "Kotlin", isTrue = true),
                Awnser(id = 3, questionId = 1, awnser = "Swift", isTrue = false),
                Awnser(id = 4, questionId = 1, awnser = "JavaScript", isTrue = false)
            ),
            onAnswerSelected = {},
            questionNumber = 10,
            totalQuestions = 10
        )
    }
}