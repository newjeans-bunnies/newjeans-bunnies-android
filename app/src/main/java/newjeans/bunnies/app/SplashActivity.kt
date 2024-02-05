package newjeans.bunnies.app

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.compose.setContent

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

import newjeans.bunnies.auth.AuthActivity
import newjeans.bunnies.main.MainActivity


class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            finish()
        } else {
            val authIntent = Intent(this, AuthActivity::class.java)
            val mainIntent = Intent(this, MainActivity::class.java)

            authIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            //clear
            authIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            mainIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            mainIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK


            setContent {
                Box(
                    Modifier
                        .fillMaxSize(),
                    Alignment.Center
                ) {
                    Image(
                        modifier = Modifier
                            .width(150.dp),
                        painter = painterResource(id = newjeans.bunnies.designsystem.R.drawable.ic_main),
                        contentDescription = ""
                    )
                }

                Handler(Looper.getMainLooper()).postDelayed({
                    if (NewJeansBunniesApplication.prefs.autoLogin) {
                        Log.d("autoLogin", "true")
                        startActivity(mainIntent)
                    } else {
                        Log.d("autoLogin", "false")
                        startActivity(authIntent)
                    }
                }, 1000)
            }
        }
    }
}
