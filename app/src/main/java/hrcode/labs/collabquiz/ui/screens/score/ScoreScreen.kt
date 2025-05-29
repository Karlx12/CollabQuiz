package hrcode.labs.collabquiz.ui.screens.score

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import hrcode.labs.collabquiz.Screen
import hrcode.labs.collabquiz.data.viewmodels.SinglePlayerViewModel
import hrcode.labs.collabquiz.ui.theme.CollabQuizTheme

@Composable
fun ScoreScreen(navController: NavController,singlePlayerViewModel: SinglePlayerViewModel) {
    val score by singlePlayerViewModel.score.collectAsState()
    val username by singlePlayerViewModel.username.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "¡Juego terminado!",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = "Jugador: $username",
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        Text(
            text = "Tu puntuación: $score",
            style = MaterialTheme.typography.headlineSmall,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Button(
            onClick = {
                singlePlayerViewModel.resetGame()
                navController.navigate(Screen.HomeRoute.route) {
                    popUpTo(Screen.HomeRoute.route) {
                        inclusive = true
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text("Volver al inicio")
        }

        Button(
            onClick = {
                singlePlayerViewModel.resetGame()
                navController.navigate(Screen.SinglePlayerRoute.UsernameRoute.route) {
                    popUpTo(Screen.SinglePlayerRoute.route)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(top = 8.dp)
        ) {
            Text("Jugar de nuevo")
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun ScoreScreenPreview() {
//    CollabQuizTheme {
//        ScoreScreen(rememberNavController())
//    }
//}