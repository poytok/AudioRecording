package jjh.study.audiorecording.ui._nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.orhanobut.logger.Logger
import jjh.study.audiorecording.ui.home.HomeScreen
import jjh.study.audiorecording.ui.login.LoginScreen
import jjh.study.audiorecording.ui.main.MainViewModel
import jjh.study.audiorecording.ui.record.RecordScreen
import jjh.study.audiorecording.ui.select_question.SelectQuestionScreen


@Composable
fun AudioRecordNavHost(
  modifier: Modifier,
  navHostController: NavHostController,
  startDestination: String,
  mainViewModel: MainViewModel
) {

  NavHost(navController = navHostController, startDestination = startDestination) {
    composable(Screens.Login.name) {
      LoginScreen(modifier)
    }

    composable(Screens.Home.name) {
      // Home
      HomeScreen(
        modifier,
        onClick = {
          Logger.e("클릭")
          mainViewModel.stopRecording()
        }
      )
    }

    composable(Screens.Record.name) {
      RecordScreen()
    }

    composable(Screens.SelectQuestion.name) {
      SelectQuestionScreen()
    }
  }
}