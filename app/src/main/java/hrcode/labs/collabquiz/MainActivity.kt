package hrcode.labs.collabquiz

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import hrcode.labs.collabquiz.data.schemas.logic.viewmodel.AwnserViewModel
import hrcode.labs.collabquiz.data.schemas.logic.viewmodel.QuestionViewModel
import hrcode.labs.collabquiz.data.schemas.logic.viewmodel.SyncViewModel
import hrcode.labs.collabquiz.ui.theme.CollabQuizTheme
import hrcode.labs.collabquiz.data.domain.Awnser
import hrcode.labs.collabquiz.data.domain.Question
import hrcode.labs.collabquiz.data.schemas.logic.factories.AwnserViewModelFactory
import hrcode.labs.collabquiz.data.schemas.logic.factories.QuestionViewModelFactory
import hrcode.labs.collabquiz.data.schemas.logic.factories.SinglePlayerViewModelFactory
import hrcode.labs.collabquiz.data.schemas.logic.factories.SyncViewModelFactory
import hrcode.labs.collabquiz.data.viewmodels.SinglePlayerViewModel
import hrcode.labs.collabquiz.ui.composables.BottomNavigationBar
import hrcode.labs.collabquiz.ui.screens.home.HomeScreen
import hrcode.labs.collabquiz.ui.screens.multiplayer.MultiplayerScreen
import hrcode.labs.collabquiz.ui.screens.question.QuestionScreen
import hrcode.labs.collabquiz.ui.screens.score.ScoreScreen
import hrcode.labs.collabquiz.ui.screens.singleplayer.SinglePlayerScreen
import hrcode.labs.collabquiz.ui.screens.singleplayer.SinglePlayerUsernameScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            CollabQuizTheme {
                CollabQuizApp()
            }
        }
    }
}
sealed class Screen(
    val route: String,
    val label: String? = null,
    val icon: ImageVector? = null,
    val iconResourceId: Int? = null
) {
    object HomeRoute : Screen("home", "Inicio", Icons.Default.Home)

    object SinglePlayerRoute : Screen("singleplayer", "Jugador único", Icons.Default.Person) {
        object UsernameRoute : Screen("singleplayer/username")
        object QuestionRoute : Screen("singleplayer/question/{index}") {
            fun createRoute(index: Int) = "singleplayer/question/$index"
        }
        object ScoreRoute : Screen("singleplayer/score")
    }

    object MultiplayerRoute : Screen("multiplayer", "Multijugador", icon = Icons.Default.Person)//iconResourceId = R.drawable.groups_24px)
}

@Composable
fun CollabQuizApp() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val app = context.applicationContext as App

    // Get ViewModels from the App class
    val questionViewModel: QuestionViewModel = viewModel(
        factory = QuestionViewModelFactory(app.questionRepository)
    )
    val awnserViewModel: AwnserViewModel = viewModel(
        factory = AwnserViewModelFactory(app.awnserRepository)
    )
//    val syncViewModel: SyncViewModel = viewModel(
//        factory = SyncViewModelFactory(app.syncRepository)
//    )
    val singlePlayerViewModel: SinglePlayerViewModel = viewModel(
        factory = SinglePlayerViewModelFactory()
    )

    // Initialize data before showing screens
    val questionsState = questionViewModel.questions.collectAsState(initial = emptyList()).value
    val (isLoading, setIsLoading) = remember { mutableStateOf(true) }

    // Load data when app starts
    LaunchedEffect(Unit) {
        try {
            // Load existing data first
            questionViewModel.loadQuestions()
            awnserViewModel.loadAwnsers()

            // Check if we need to add sample data
            if (questionsState.isEmpty()) {
                Log.d("CollabQuizApp", "Database is empty, adding sample questions")
                insertMobileDevQuestions(questionViewModel, awnserViewModel)

                // Reload data after insertion
                questionViewModel.loadQuestions()
                awnserViewModel.loadAwnsers()
            }
        } catch (e: Exception) {
            Log.e("CollabQuizApp", "Error loading data: ${e.message}", e)
        } finally {
            setIsLoading(false)
        }
    }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController)
        }
    ) { innerPadding ->

        if (isLoading) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {

            NavHost(
                navController = navController,
                startDestination = Screen.HomeRoute.route,
                modifier = Modifier.padding(innerPadding)
            ) {


                composable(Screen.HomeRoute.route) {
                    HomeScreen(navController)
                }

                composable(Screen.SinglePlayerRoute.route) {
                    SinglePlayerScreen(navController, questionViewModel, awnserViewModel)
                }

                composable(Screen.SinglePlayerRoute.UsernameRoute.route) {
                    SinglePlayerUsernameScreen(navController)
                }

                composable(
                    Screen.SinglePlayerRoute.QuestionRoute.route,
                    arguments = listOf(navArgument("index") { type = NavType.IntType })
                ) { backStackEntry ->
                    val questionIndex = backStackEntry.arguments?.getInt("index") ?: 0
                    QuestionScreen(
                        navController = navController,
                        questionViewModel = questionViewModel,
                        awnserViewModel = awnserViewModel,
                        questionIndex = questionIndex,
                        singlePlayerViewModel = singlePlayerViewModel
                    )
                }

                composable(Screen.SinglePlayerRoute.ScoreRoute.route) {
                    ScoreScreen(
                        navController = navController,
                        singlePlayerViewModel=singlePlayerViewModel,
                    )
                }

                composable(Screen.MultiplayerRoute.route) {
                    MultiplayerScreen(navController)
                }
            }
        }
    }
}

private fun insertMobileDevQuestions(questionViewModel: QuestionViewModel, awnserViewModel: AwnserViewModel) {
    try {
        // Question 1
        val question1 = Question(
            question = "¿Qué lenguaje es oficialmente recomendado por Google para el desarrollo de apps Android?",
            category = "Aplicaciones móviles",
            active = true
        )
        val questionId1 = questionViewModel.insert(question1).toInt()

        awnserViewModel.insert(Awnser(questionId = questionId1, awnser = "JavaScript", isTrue = false))
        awnserViewModel.insert(Awnser(questionId = questionId1, awnser = "Kotlin", isTrue = true))
        awnserViewModel.insert(Awnser(questionId = questionId1, awnser = "Swift", isTrue = false))
        awnserViewModel.insert(Awnser(questionId = questionId1, awnser = "Python", isTrue = false))

        // Question 2
        val question2 = Question(
            question = "¿Qué archivo define los permisos y componentes principales de una app Android?",
            category = "Aplicaciones móviles",
            active = true
        )
        val questionId2 = questionViewModel.insert(question2).toInt()

        awnserViewModel.insert(Awnser(questionId = questionId2, awnser = "strings.xml", isTrue = false))
        awnserViewModel.insert(Awnser(questionId = questionId2, awnser = "MainActivity.kt", isTrue = false))
        awnserViewModel.insert(Awnser(questionId = questionId2, awnser = "AndroidManifest.xml", isTrue = true))
        awnserViewModel.insert(Awnser(questionId = questionId2, awnser = "build.gradle", isTrue = false))

        // Question 3
        val question3 = Question(
            question = "¿Qué componente se utiliza para mostrar una interfaz gráfica en Android?",
            category = "Aplicaciones móviles",
            active = true
        )
        val questionId3 = questionViewModel.insert(question3).toInt()

        awnserViewModel.insert(Awnser(questionId = questionId3, awnser = "Service", isTrue = false))
        awnserViewModel.insert(Awnser(questionId = questionId3, awnser = "Activity", isTrue = true))
        awnserViewModel.insert(Awnser(questionId = questionId3, awnser = "BroadcastReceiver", isTrue = false))
        awnserViewModel.insert(Awnser(questionId = questionId3, awnser = "ContentProvider", isTrue = false))

        // Question 4
        val question4 = Question(
            question = "¿Cuál es la herramienta oficial para desarrollar aplicaciones Android?",
            category = "Aplicaciones móviles",
            active = true
        )
        val questionId4 = questionViewModel.insert(question4).toInt()

        awnserViewModel.insert(Awnser(questionId = questionId4, awnser = "Eclipse", isTrue = false))
        awnserViewModel.insert(Awnser(questionId = questionId4, awnser = "Xcode", isTrue = false))
        awnserViewModel.insert(Awnser(questionId = questionId4, awnser = "Visual Studio", isTrue = false))
        awnserViewModel.insert(Awnser(questionId = questionId4, awnser = "Android Studio", isTrue = true))

        // Question 5
        val question5 = Question(
            question = "¿Qué es Jetpack Compose en Android?",
            category = "Aplicaciones móviles",
            active = true
        )
        val questionId5 = questionViewModel.insert(question5).toInt()

        awnserViewModel.insert(Awnser(questionId = questionId5, awnser = "Una base de datos local", isTrue = false))
        awnserViewModel.insert(Awnser(questionId = questionId5, awnser = "Un lenguaje de programación", isTrue = false))
        awnserViewModel.insert(Awnser(questionId = questionId5, awnser = "Un sistema de navegación GPS", isTrue = false))
        awnserViewModel.insert(Awnser(questionId = questionId5, awnser = "Un toolkit de UI declarativa", isTrue = true))

        // Question 6
        val question6 = Question(
            question = "¿Qué archivo se utiliza para declarar dependencias en un proyecto Android?",
            category = "Aplicaciones móviles",
            active = true
        )
        val questionId6 = questionViewModel.insert(question6).toInt()

        awnserViewModel.insert(Awnser(questionId = questionId6, awnser = "manifest.xml", isTrue = false))
        awnserViewModel.insert(Awnser(questionId = questionId6, awnser = "config.gradle", isTrue = false))
        awnserViewModel.insert(Awnser(questionId = questionId6, awnser = "dependencies.kt", isTrue = false))
        awnserViewModel.insert(Awnser(questionId = questionId6, awnser = "build.gradle", isTrue = true))

        // Question 7
        val question7 = Question(
            question = "¿Qué método se ejecuta al iniciar una Activity?",
            category = "Aplicaciones móviles",
            active = true
        )
        val questionId7 = questionViewModel.insert(question7).toInt()

        awnserViewModel.insert(Awnser(questionId = questionId7, awnser = "onCreate()", isTrue = true))
        awnserViewModel.insert(Awnser(questionId = questionId7, awnser = "onStop()", isTrue = false))
        awnserViewModel.insert(Awnser(questionId = questionId7, awnser = "onPause()", isTrue = false))
        awnserViewModel.insert(Awnser(questionId = questionId7, awnser = "onDestroy()", isTrue = false))

        // Question 8
        val question8 = Question(
            question = "¿Qué es un Intent en Android?",
            category = "Aplicaciones móviles",
            active = true
        )
        val questionId8 = questionViewModel.insert(question8).toInt()

        awnserViewModel.insert(Awnser(questionId = questionId8, awnser = "Un tipo de archivo multimedia", isTrue = false))
        awnserViewModel.insert(Awnser(questionId = questionId8, awnser = "Una base de datos", isTrue = false))
        awnserViewModel.insert(Awnser(questionId = questionId8, awnser = "Un mensaje para comunicar componentes", isTrue = true))
        awnserViewModel.insert(Awnser(questionId = questionId8, awnser = "Un sistema de permisos", isTrue = false))

        // Question 9
        val question9 = Question(
            question = "¿Qué significa APK?",
            category = "Aplicaciones móviles",
            active = true
        )
        val questionId9 = questionViewModel.insert(question9).toInt()

        awnserViewModel.insert(Awnser(questionId = questionId9, awnser = "Android Programming Kit", isTrue = false))
        awnserViewModel.insert(Awnser(questionId = questionId9, awnser = "Application Public Key", isTrue = false))
        awnserViewModel.insert(Awnser(questionId = questionId9, awnser = "Android Package", isTrue = true))
        awnserViewModel.insert(Awnser(questionId = questionId9, awnser = "App Plugin Kit", isTrue = false))

        // Question 10
        val question10 = Question(
            question = "¿Qué componente sirve para ejecutar tareas en segundo plano sin interfaz?",
            category = "Aplicaciones móviles",
            active = true
        )
        val questionId10 = questionViewModel.insert(question10).toInt()

        awnserViewModel.insert(Awnser(questionId = questionId10, awnser = "Activity", isTrue = false))
        awnserViewModel.insert(Awnser(questionId = questionId10, awnser = "Fragment", isTrue = false))
        awnserViewModel.insert(Awnser(questionId = questionId10, awnser = "ViewModel", isTrue = false))
        awnserViewModel.insert(Awnser(questionId = questionId10, awnser = "Service", isTrue = true))

        Log.d("CollabQuizApp", "Successfully inserted all mobile development questions")
    } catch (e: Exception) {
        Log.e("CollabQuizApp", "Error inserting mobile dev questions: ${e.message}", e)
    }
}