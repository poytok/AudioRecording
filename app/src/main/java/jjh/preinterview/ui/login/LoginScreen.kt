package jjh.preinterview.ui.login

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.orhanobut.logger.Logger
import jjh.preinterview.R
import jjh.preinterview.util.GoogleLogin
import jjh.preinterview.util.Spacer
import kotlinx.coroutines.launch


@Preview(showBackground = true)
@Composable
fun LoginScreen(modifier: Modifier = Modifier) {
  val context = LocalContext.current
  val googleLogin = GoogleLogin(context)
  val coroutineScope = rememberCoroutineScope()

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
        googleLogin.handleSignInResult(task)
        Logger.e(googleLogin.getLoginData())
      }
    } // startGoogleLogin

    Button(onClick = { startGoogleLogin.launch(googleLogin.getStartIntent() ?: return@Button) }) {
      Text(text = "구글로그인이다 해")
    }

  }
}