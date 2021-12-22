package mx.ulai.indra.application

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import mx.ulai.indra.api.ApiMovieModule
import mx.ulai.indra.application.scope.ApplicationScope
import mx.ulai.indra.db.AppDataBaseModule
import mx.ulai.indra.ui.activity.ModuleActivity
import javax.inject.Singleton

@Singleton
@ApplicationScope
@Component(modules = [
    AndroidSupportInjectionModule::class,
    AppDataBaseModule::class,
    ApiMovieModule::class,
    ModuleActivity::class
])
interface IndraApplicationComponent {
    fun inject(app: IndraApplication)

    @Component.Builder
    interface Builder{

        @BindsInstance
        fun application(aplication: Application): IndraApplicationComponent.Builder

        fun build(): IndraApplicationComponent
    }
}