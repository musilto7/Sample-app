package cz.musilto5.myflickerapp.app

import androidx.compose.ui.window.ComposeUIViewController
import cz.musilto5.myflickerapp.app.di.iosDataModule
import cz.musilto5.myflickerapp.app.di.iosPresentationModule
import cz.musilto5.myflickerapp.presentation.App
import cz.musilto5.myflickerapp.presentation.feature.image.list.viewModel.ImagesScreenStateHolder
import org.koin.compose.koinInject
import org.koin.core.context.startKoin
import platform.UIKit.UIViewController

fun MainViewController(): UIViewController {
    startKoinIfNeeded()
    return ComposeUIViewController {
        val stateHolder: ImagesScreenStateHolder = koinInject()
        App(stateHolder = stateHolder)
    }
}

private var koinStarted = false

private fun startKoinIfNeeded() {
    if (koinStarted) return
    koinStarted = true
    startKoin {
        modules(iosDataModule, iosPresentationModule)
    }
}
