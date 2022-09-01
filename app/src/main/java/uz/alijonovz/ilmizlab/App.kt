package uz.alijonovz.ilmizlab

import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.orhanobut.hawk.Hawk

class App : MultiDexApplication() {

    companion object {
        lateinit var app: App
    }

    override fun onCreate() {
        super.onCreate()
        app = this
        MultiDex.install(this)
        Hawk.init(this).build()
    }
}