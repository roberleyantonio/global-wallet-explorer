package br.com.dev360.globalwalletexplorer

import android.app.Application
import br.com.dev360.globalwalletexplorer.di.initializeKoin
import org.koin.android.ext.koin.androidContext

class RootApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initializeKoin(
            config = {
                androidContext(this@RootApplication)
            }
        )
    }
}