package org.mz.killrs

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        // Install splash screen only ONCE
        val splashScreen = installSplashScreen()

        // Exit animation
        splashScreen.setOnExitAnimationListener { splashProvider ->
            splashProvider.view.animate()
                .translationY(-splashProvider.view.height.toFloat())
                .setDuration(500L)
                .withEndAction {
                    splashProvider.remove()
                }.start()
        }

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                android.graphics.Color.TRANSPARENT,
                android.graphics.Color.TRANSPARENT
            ),
            navigationBarStyle = SystemBarStyle.light(
                android.graphics.Color.TRANSPARENT,
                android.graphics.Color.TRANSPARENT
            )
        )

        super.onCreate(savedInstanceState)
        setContent {
            App() // ✅ Your composable app entry
        }
    }
}
