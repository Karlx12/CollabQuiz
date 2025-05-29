package hrcode.labs.collabquiz.ui.screens.singleplayer

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import hrcode.labs.collabquiz.ui.theme.CollabQuizTheme

@Composable
fun UsernameScreen(
    username: String,
    usernameError: String,
    onUsernameChange: (String) -> Unit,
    onStartGame: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Ingresa tu nombre",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        OutlinedTextField(
            value = username,
            onValueChange = onUsernameChange,
            label = { Text("Nombre del jugador") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            isError = usernameError.isNotEmpty(),
            supportingText = {
                if (usernameError.isNotEmpty()) {
                    Text(text = usernameError)
                }
            }
        )

        Button(
            onClick = onStartGame,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text("Comenzar juego")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UsernameScreenPreview() {
    CollabQuizTheme {
        UsernameScreen(
            username = "Jugador1",
            usernameError = "",
            onUsernameChange = {},
            onStartGame = {}
        )
    }
}
