package jjh.preinterview.audiorecording.ui._nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.orhanobut.logger.Logger
import jjh.preinterview.audiorecording.ui.home.HomeScreen
import jjh.preinterview.audiorecording.ui.login.LoginScreen
import jjh.preinterview.audiorecording.ui.record.RecordScreen
import jjh.preinterview.ui._nav.Screens
import jjh.preinterview.ui.main.MainViewModel
import jjh.preinterview.ui.select_question.SelectQuestionScreen


@Composable
fun AudioRecordNavHost(
  modifier: Modifier,
  navHostController: NavHostController,
  startDestination: String,
  mainViewModel: MainViewModel
) {

  NavHost(navController = navHostController, startDestination = startDestination) {
    composable(Screens.LOGIN_SCREEN.name) {
      LoginScreen(modifier)
    }

    composable(Screens.HOME_SCREEN.name) {
      // Home
      HomeScreen(
        modifier,
        onClick = {
          Logger.e("클릭")
          mainViewModel.stopRecording()
        }
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