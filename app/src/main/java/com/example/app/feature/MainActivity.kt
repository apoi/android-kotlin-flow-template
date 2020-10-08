package com.example.app.feature

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.app.R
import com.example.app.databinding.MainActivityBinding
import com.example.app.navigation.setupWithNavController
import com.example.app.util.viewBinding

class MainActivity : AppCompatActivity(R.layout.main_activity) {

    private var currentNavController: LiveData<NavController>? = null

    private val binding by viewBinding(MainActivityBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(binding.mainToolbar)

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
        val controller = binding.mainBottomNav.setupWithNavController(
            navGraphIds = listOf(R.navigation.list_nav, R.navigation.about_nav),
            fragmentManager = supportFragmentManager,
            containerId = R.id.main_nav_container,
            intent = intent
        )

        // Whenever the selected controller changes, setup action bar and other changes
        controller.observe(
            this,
            Observer { navController ->
                setupFullscreenHandler(navController)
                setupActionBarWithNavController(navController)
            }
        )

        currentNavController = controller
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }

    private fun setupFullscreenHandler(navController: NavController) {
        navController.removeOnDestinationChangedListener(fullscreenListener)
        navController.addOnDestinationChangedListener(fullscreenListener)
    }

    private val fullscreenListener = NavController.OnDestinationChangedListener { _, _, arguments ->
        setFullscreen(arguments?.getBoolean(FULLSCREEN) == true)
    }

    private fun setFullscreen(fullscreen: Boolean) {
        binding.mainBottomNav.isVisible = !fullscreen
        binding.mainToolbar.isVisible = !fullscreen

        if (fullscreen) {
            window.apply {
                decorView.systemUiVisibility = decorView.systemUiVisibility or
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

                statusBarColor = resources.getColor(android.R.color.transparent, null)
            }
        } else {
            window.apply {
                decorView.systemUiVisibility = decorView.systemUiVisibility and
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE and
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

                statusBarColor = resources.getColor(R.color.colorPrimaryDark, null)
            }
        }
    }

    companion object {
        const val FULLSCREEN = "fullscreen"
    }
}
