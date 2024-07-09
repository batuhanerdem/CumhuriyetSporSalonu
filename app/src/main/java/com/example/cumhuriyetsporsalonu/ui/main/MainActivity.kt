package com.example.cumhuriyetsporsalonu.ui.main

import androidx.core.view.isGone
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.cumhuriyetsporsalonu.R
import com.example.cumhuriyetsporsalonu.databinding.ActivityMainBinding
import com.example.cumhuriyetsporsalonu.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<MainActionBus, MainActivityViewModel, ActivityMainBinding>(
    ActivityMainBinding::inflate, MainActivityViewModel::class.java
) {

    override fun init() {
        setNavListeners()
    }

    override suspend fun onAction(action: MainActionBus) {

    }

    private fun setNavListeners() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        navController.addOnDestinationChangedListener { _, navDestination, _ ->
            binding.bottomNavigationView.isGone = when (navDestination.id) {
                R.id.editProfileFragment -> true
                else -> false
            }
        }
        binding.bottomNavigationView.setupWithNavController(navController)

    }
}