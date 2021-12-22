package mx.ulai.indra.application

import android.app.Application
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import java.lang.reflect.InvocationTargetException
import javax.inject.Inject

open class IndraApplication: Application(), HasAndroidInjector {

    companion object{
        val TAG = "IndraApplication"
        val COMPONENT_OVERRIDE_KEY = "mx.ulai.indra.application.componentOverride"
    }

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        if (!overrideApplicationComponent(this))
            DaggerIndraApplicationComponent.builder().application(this).build().inject(this)
    }

    fun overrideApplicationComponent(application: IndraApplication):Boolean{
        try{
            val appInfo: ApplicationInfo  =
                packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA)
            appInfo.apply {
                var className = metaData.getString(COMPONENT_OVERRIDE_KEY)
                if (className == null){
                    Log.e(TAG, "Component override metadata not found, using default component.")
                    return false
                }
                Log.i(TAG, className)
                val builderObject = Class.forName(className).getMethod("builder").invoke(null)
                val builderClass = builderObject.javaClass
                builderClass
                    .getMethod("application", Application::class.java)
                    .invoke(builderObject, application)
                val component = builderClass.getMethod("build").invoke(builderObject)
                component.javaClass
                    .getMethod("inject", getIndraClass())
                    .invoke(component, application)
                return true
            }
        } catch (e: Exception) {
            when(e){
                is PackageManager.NameNotFoundException ,
                is ClassNotFoundException , is NoSuchMethodException,
                is InvocationTargetException , is IllegalAccessException -> {
                    Log.e(TAG, "Component override failed with exception:", e)
                }
            }
        }
        return false
    }

    protected fun getIndraClass(): Class<out IndraApplication>{
        return IndraApplication::class.java
    }


    override fun androidInjector(): AndroidInjector<Any> = androidInjector
}