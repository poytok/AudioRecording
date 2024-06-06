package jjh.preinterview.ui.login

import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.google.android.gms.auth.api.signin.GoogleSignIn
import jjh.preinterview.R
import jjh.preinterview.util.GoogleLogin
import jjh.preinterview.util.OneButtonDialog
import jjh.preinterview.util.Spacer
import kotlinx.coroutines.launch


@Composable
fun LoginScreen(
  startMainView: () -> Unit,
) {
  val context = LocalContext.current
  val googleLogin = GoogleLogin(context)
  val coroutineScope = rememberCoroutineScope()
  var rememberGoogleLogin by remember { mutableStateOf(false) }

  Column(
    Modifier
      .fillMaxSize()
      .background(Color.White)
  ) {
    136.Spacer()
    Text(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 24.dp),
      textAlign = TextAlign.Center,
      text = "AI와 함께하는 떨림 없는 면접 준비",
      style = TextStyle(
        fontFamily = FontFamily(Font(R.font.spoqa_han_sans_neo_regular)),
        fontSize = 18.sp,
        lineHeight = 23.76.sp,
        color = Color(0xff000000),
      )
    ) // AI와 함께하는 떨림 없는 면접 준비

    16.Spacer()

    Text(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 24.dp),
      textAlign = TextAlign.Center,
      text = "미리면접",
      style = TextStyle(
        fontFamily = FontFamily(Font(R.font.spoqa_han_sans_neo_regular)),
        fontSize = 30.sp,
        lineHeight = 39.6.sp,
        color = Color(0xff000000),
      )
    ) // 미리면접

    25.Spacer()

    Image(
      modifier = Modifier
        .size(80.dp)
        .align(Alignment.CenterHorizontally),
      painter = painterResource(id = R.drawable.main_logo),
      contentDescription = ""
    ) // Logo

    val startGoogleLogin = rememberLauncherForActivityResult(
      contract = ActivityResultContracts.StartActivityForResult()
    ) {
      coroutineScope.launch {
        val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
        val token = googleLogin.handleSignInResult(task)

        if (token == null) {
          rememberGoogleLogin = true
          return@launch
        }
        startMainView()
      }
    } // startGoogleLogin


    Spacer(modifier = Modifier.weight(1f))
    Column(
      Modifier.fillMaxWidth(),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {

      val onGoogleLoginClickListener = {
        val intent = googleLogin.getStartIntent() ?: Intent()
        startGoogleLogin.launch(intent)
      }
      GoogleLogin(onGoogleLoginClickListener)

      16.Spacer()

      Tour(startMainView)

      110.Spacer()
    }
  }

  if (rememberGoogleLogin) {
    OneButtonDialog(
      text = "로그인에 실패하셨습니다.",
      onConfirmClickListener = { rememberGoogleLogin = false }
    )
  }
}

@Composable
fun GoogleLogin(
  onClick: () -> Unit,
) {
  Image(
    modifier = Modifier
      .clickable(onClick = onClick),
    painter = painterResource(id = R.drawable.android_light_sq_su),
    contentDescription = ""
  ) // google login button
}


@Composable
private fun Tour(
  onClick: () -> Unit,
) {
  Text(
    modifier = Modifier
      .padding(10.dp)
      .clickable(onClick = onClick),
    text = "둘러보기",
    style = TextStyle(
      fontFamily = FontFamily(Font(R.font.spoqa_han_sans_neo_regular)),
      fontSize = 18.sp,
      lineHeight = 23.76.sp,
      letterSpacing = (-0.05).em,
      color = Color(0xff000000),
      textDecoration = TextDecoration.Underline
    )
  ) // 둘러보기

}