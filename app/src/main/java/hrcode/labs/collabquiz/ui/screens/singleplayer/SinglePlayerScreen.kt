package hrcode.labs.collabquiz.ui.screens.singleplayer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import hrcode.labs.collabquiz.Screen
import hrcode.labs.collabquiz.data.schemas.logic.viewmodel.AwnserViewModel
import hrcode.labs.collabquiz.data.schemas.logic.viewmodel.QuestionViewModel
import hrcode.labs.collabquiz.data.viewmodels.SinglePlayerViewModel

@Composable
fun SinglePlayerScreen(
    navController: NavController,
    questionViewModel: QuestionViewModel,
    awnserViewModel: AwnserViewModel
) {
    val singlePlayerViewModel = viewModel<SinglePlayerViewModel>()

    // Load questions and answers if they're not already loaded
    LaunchedEffect(Unit) {
        // Reset the game when entering this screen
        singlePlayerViewModel.resetGame()

        // Navigate to username screen to start the game flow
        navController.navigate(Screen.SinglePlayerRoute.UsernameRoute.route) {
            popUpTo(Screen.SinglePlayerRoute.route) {
                inclusive = true
            }
        }
    }
}