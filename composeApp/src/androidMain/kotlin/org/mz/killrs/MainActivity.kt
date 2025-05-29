package org.mz.killrs

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen


class MainActivity : ComponentActivity() {
    // ✅ Installs the splash screen
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()

        enableEdgeToEdge()
        installSplashScreen().apply {
            splashScreen.setOnExitAnimationListener { splashViewProvider ->
                splashViewProvider.view.animate()
                    .translationY(-splashViewProvider.view.height.toFloat())
                    .setDuration(500L)
                    .withEndAction {
                        splashViewProvider.remove()
                    }.start()
            }
            super.onCreate(savedInstanceState)
            setContent {
                App() // ✅ Composable function (assumed defined elsewhere)
            }
        }
    }
}