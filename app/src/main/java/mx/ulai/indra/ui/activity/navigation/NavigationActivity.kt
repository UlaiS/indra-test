package mx.ulai.indra.ui.activity.navigation

import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import mx.ulai.indra.MainActivity
import mx.ulai.indra.R
import mx.ulai.indra.databinding.ActivityNavigationBinding

class NavigationActivity: MainActivity<ActivityNavigationBinding, NavigationViewModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding.lifecycleOwner = this
        setSupportActionBar(dataBinding.toolbar)
        supportActionBar?.let { it.title = "Películas Populares" }
        if (savedInstanceState == null) {
            val appBarConfiguration = AppBarConfiguration.Builder(
                R.id.navigation_most_popular,
                R.id.navigation_now_playing
            ).build()
            val navController = Navigation.findNavController(this, R.id.container)
            NavigationUI.setupActionBarWithNavController(this,navController,appBarConfiguration)
            NavigationUI.setupWithNavController(dataBinding.navView, navController)
        }
    }

    override val layoutRes: Int
        get() = R.layout.activity_navigation

    override fun getViewModel(): Class<NavigationViewModel> {
        return NavigationViewModel::class.java
    }


}