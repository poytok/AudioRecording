package jjh.preinterview.ui._nav

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import jjh.preinterview.R
import jjh.preinterview.ui.home.HomeScreen
import jjh.preinterview.ui.login.LoginScreen
import jjh.preinterview.ui.record.RecordScreen
import jjh.preinterview.ui.select_question.SelectQuestionScreen
import jjh.preinterview.ui.signup.SignupScreen


@Composable
fun AudioRecordNavHost(
  modifier: Modifier,
  navHostController: NavHostController,
  startDestination: String,
) {

  NavHost(navController = navHostController, startDestination = startDestination) {
    composable(MainScreens.LOGIN_SCREEN.name) {
      LoginScreen(
        startMainView = {
          navHostController.navigate(MainScreens.HOME_SCREEN.name) {
            popUpTo(MainScreens.LOGIN_SCREEN.name) { inclusive = true }
          }
        },
        startSignupView = {
          navHostController.navigate(MainScreens.SIGNUP.name + "/$it")
        }
      )
    }

    composable(
      route = MainScreens.SIGNUP.name + "/{userId}",
      arguments = listOf(navArgument("userId") { type = NavType.StringType })
    ) { backStackEntry ->

      SignupScreen(
        modifier = modifier,
        backStackEntry.arguments?.getString("userId")
      )
    }

    composable(MainScreens.HOME_SCREEN.name) {
      Image(
        modifier = Modifier.fillMaxSize(),
        painter = painterResource(id = R.drawable.background),
        contentDescription = "background"
      ) // background image

      // Home
      HomeScreen(
        modifier = modifier,
        homeViewModel = hiltViewModel()
      )
    }

    composable(MainScreens.RECORD_SCREEN.name) {
      RecordScreen()
    }

    composable(MainScreens.SELECT_QUESTION_SCREEN.name) {
      SelectQuestionScreen()
    }
  }
}