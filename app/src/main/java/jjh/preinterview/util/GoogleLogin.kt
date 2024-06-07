package jjh.preinterview.util

import android.content.Context
import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.orhanobut.logger.Logger
import jjh.preinterview.R
import kotlinx.coroutines.tasks.await

@Suppress("deprecation")
class GoogleLogin(private val context: Context) {

  private var mGoogleSignInClient: GoogleSignInClient
  private val mAuth = FirebaseAuth.getInstance()

  init {
    // Google 로그인을 앱에 통합
    // GoogleSignInOptions 개체를 구성할 때 requestIdToken을 호출
    val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
      .requestIdToken(context.getString(R.string.default_web_client_id))
      .requestEmail()
      .build()

    mGoogleSignInClient = GoogleSignIn.getClient(context, googleSignInOptions)
  }


  fun getStartIntent(): Intent = mGoogleSignInClient.signInIntent

  fun getLoginData(): String {
    val account = GoogleSignIn.getLastSignedInAccount(context)?.account
    val id = GoogleSignIn.getLastSignedInAccount(context)?.id
    val idToken = GoogleSignIn.getLastSignedInAccount(context)?.idToken
    val email = GoogleSignIn.getLastSignedInAccount(context)?.email
    val displayName = GoogleSignIn.getLastSignedInAccount(context)?.displayName
    val familyName = GoogleSignIn.getLastSignedInAccount(context)?.familyName
    val serverAuthCode = GoogleSignIn.getLastSignedInAccount(context)?.serverAuthCode

    return """
      account           >> $account
      id                >> $id
      idToken           >> $idToken
      email             >> $email
      displayName       >> $displayName
      familyName        >> $familyName
      serverAuthCode    >> $serverAuthCode
    """.trimIndent()
  }


  /* 사용자 정보 가져오기 */
  suspend fun handleSignInResult(completedTask: Task<GoogleSignInAccount>): String? {
    return runCatching {
      val acct = completedTask.await() // r.getResult(ApiException::class.java)
      val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
      val authResult = mAuth.signInWithCredential(credential).await()
      authResult.user?.getIdToken(false)?.await()?.token
    }.onFailure {
      // The ApiException status code indicates the detailed failure reason.
      // Please refer to the GoogleSignInStatusCodes class reference for more information.
      if (it is ApiException) {
        Logger.e("signInResult:failed code=" + it.statusCode)
      }
      it.printStackTrace()
    }.getOrNull()
  }

//  private suspend fun firebaseAuthWithGoogle(idToken: String?): String? {
//    val credential = GoogleAuthProvider.getCredential(idToken, null)
//    val authResult = mAuth.signInWithCredential(credential).await()
//    val token = authResult.user?.getIdToken(false)?.await()
//    return token?.token
//  }

  fun setLogout() {
    mGoogleSignInClient.signOut()
  }
  companion object {

    fun isLogin(context: Context): Boolean {
      return GoogleSignIn.getLastSignedInAccount(context) != null
    }
  }
}