package com.slowerror.tobuy.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.slowerror.tobuy.R
import com.slowerror.tobuy.utils.SharedPrefUtil

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var appBarConfig: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupNavigateUp()
        SharedPrefUtil.init(this)
    }

    private fun setupNavigateUp() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navContainer) as NavHostFragment
        navController = navHostFragment.navController

        appBarConfig = AppBarConfiguration(setOf(R.id.homeFragment, R.id.CustomizationFragment))

        setupActionBarWithNavController(navController, appBarConfig)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigation)

        setupWithNavController(
            bottomNavigationView,
            navController,
        )

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (appBarConfig.isTopLevelDestination(destination)) {
                bottomNavigationView.isVisible = true
            } else {
                bottomNavigationView.isGone = true
            }
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfig)
                || super.onSupportNavigateUp()
    }
}