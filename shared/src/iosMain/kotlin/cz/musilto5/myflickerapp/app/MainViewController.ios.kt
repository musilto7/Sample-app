package cz.musilto5.myflickerapp.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeUIViewController
import cz.musilto5.myflickerapp.app.di.dataModule
import cz.musilto5.myflickerapp.app.di.presentationModule
import cz.musilto5.myflickerapp.presentation.App
import org.koin.core.context.startKoin
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController {
    startKoinIfNeeded()
    return ComposeUIViewController {
        // Respect iOS safe areas (notch, home indicator).
        Box(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.safeDrawing)
        ) {
            App()
        }
    }
}

private var koinStarted = false

private fun startKoinIfNeeded() {
    if (koinStarted) return
    koinStarted = true
    startKoin {
        modules(dataModule, presentationModule)
    }
}
