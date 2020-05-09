package com.example.app.features.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.app.R
import com.example.app.databinding.HomeActivityBinding
import com.example.app.navigation.setupWithNavController
import com.example.app.util.viewBinding

class HomeActivity : AppCompatActivity(R.layout.home_activity) {

    private var currentNavController: LiveData<NavController>? = null
    private val binding by viewBinding(HomeActivityBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(binding.homeToolbar)

        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        } // Else, need to wait for onRestoreInstanceState
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        // Now that BottomNavigationBar has restored its instance state
        // and its selectedItemId, we can proceed with setting up the
        // BottomNavigationBar with Navigation
        setupBottomNavigationBar()
    }

    /**
     * Called on first creation and when restoring state.
     */
    private fun setupBottomNavigationBar() {
        // Setup the bottom navigation view with a list of navigation graphs
        val controller = binding.homeBottomNav.setupWithNavController(
            navGraphIds = listOf(R.navigation.list_nav, R.navigation.about_nav),
            fragmentManager = supportFragmentManager,
            containerId = R.id.home_nav_container,
            intent = intent
        )

        // Whenever the selected controller changes, setup the action bar.
        controller.observe(
            this,
            Observer { navController ->
                setupActionBarWithNavController(navController)
            }
        )

        currentNavController = controller
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }
}
