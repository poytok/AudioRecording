package jjh.preinterview.ui._nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import jjh.preinterview.ui.signup.job_selection.JobSelectionScreen
import jjh.preinterview.ui.signup.nickname.InputNicknameScreen

@Composable
fun SignupNavHost(
  modifier: Modifier = Modifier,
  navHostController: NavHostController,
  startDestination: String,
  userId: String?,
) {
  NavHost(navController = navHostController, startDestination = startDestination) {
    composable(SignupScreens.INPUT_NICKNAME.name) {
      InputNicknameScreen(
        modifier = modifier,
        onNextButtonClick = { navHostController.navigate(SignupScreens.JOB_SELECTION.name) },
        userId = userId
      )
    }

    composable(SignupScreens.JOB_SELECTION.name) {
      JobSelectionScreen()
    }
  }
}