package mx.ulai.indra.ui.activity

import dagger.Module
import dagger.android.ContributesAndroidInjector
import mx.ulai.indra.application.scope.ActivityScope
import mx.ulai.indra.ui.activity.navigation.NavigationActivity
import mx.ulai.indra.ui.fragment.ModuleFragment
import mx.ulai.indra.viewmodel.ModuleViewModel

@Module(includes = [
    ModuleFragment::class,
    ModuleViewModel::class
])
abstract class ModuleActivity {
    @ActivityScope
    @ContributesAndroidInjector
    abstract fun contributeNavigationActivity(): NavigationActivity
}