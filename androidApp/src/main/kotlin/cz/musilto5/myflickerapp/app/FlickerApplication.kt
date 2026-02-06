package cz.musilto5.myflickerapp.app

import android.app.Application
import cz.musilto5.myflickerapp.data.di.dataModule
import cz.musilto5.myflickerapp.presentation.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class FlickerApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@FlickerApplication)
            modules(dataModule, presentationModule)
        }
    }
}
