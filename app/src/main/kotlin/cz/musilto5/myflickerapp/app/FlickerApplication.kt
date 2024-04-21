package cz.musilto5.myflickerapp.app

import android.app.Application
import cz.musilto5.myflickerapp.app.di.dataModule
import cz.musilto5.myflickerapp.app.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class FlickerApplication : Application() {
    // start koin
    override fun onCreate() {
        super.onCreate()
        // start Koin!
        startKoin {
            // declare used Android context
            androidContext(this@FlickerApplication)
            // declare modules
            modules(
                listOf(
                    dataModule,
                    presentationModule,
                )
            )
        }
    }
}