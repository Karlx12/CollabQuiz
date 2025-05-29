package hrcode.labs.collabquiz.ui.screens.singleplayer

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import hrcode.labs.collabquiz.Screen
import hrcode.labs.collabquiz.data.viewmodels.SinglePlayerViewModel
import hrcode.labs.collabquiz.ui.theme.CollabQuizTheme

@Composable
fun SinglePlayerUsernameScreen(navController: NavController) {
    var username by remember { mutableStateOf("") }
    var usernameError by remember { mutableStateOf("") }
    val viewModel = viewModel<SinglePlayerViewModel>()

    UsernameScreen(
        username = username,
        usernameError = usernameError,
        onUsernameChange = {
            username = it
            usernameError = ""
        },
        onStartGame = {
            if (username.isBlank()) {
                usernameError = "Ingresa un nombre para continuar"
            } else {
                viewModel.setUsername(username)
                navController.navigate(Screen.SinglePlayerRoute.QuestionRoute.createRoute(0)) {
                    popUpTo(Screen.SinglePlayerRoute.route)
                }
            }
        }
    )
}

@Preview
@Composable
fun SinglePlayerUsernameScreenPreview() {
    CollabQuizTheme {
        SinglePlayerUsernameScreen(rememberNavController())
    }
}