package jjh.preinterview.ui._nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import jjh.preinterview.ui.home.HomeScreen
import jjh.preinterview.ui.login.LoginScreen
import jjh.preinterview.ui.record.RecordScreen
import jjh.preinterview.ui.select_question.SelectQuestionScreen


@Composable
fun AudioRecordNavHost(
  modifier: Modifier,
  navHostController: NavHostController,
  startDestination: String,
) {

  NavHost(navController = navHostController, startDestination = startDestination) {
    composable(Screens.LOGIN_SCREEN.name) {
      LoginScreen(
        startMainView = {
          navHostController.navigate(Screens.HOME_SCREEN.name)
        }
      )
    }

    composable(Screens.HOME_SCREEN.name) {
      // Home
      HomeScreen(
        modifier = modifier,
        homeViewModel = hiltViewModel()
      )
    }

    composable(Screens.RECORD_SCREEN.name) {
      RecordScreen()
    }

    composable(Screens.SELECT_QUESTION_SCREEN.name) {
      SelectQuestionScreen()
    }
  }
}