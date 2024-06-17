package com.example.cumhuriyetsporsalonu.ui.main

import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.cumhuriyetsporsalonu.R
import com.example.cumhuriyetsporsalonu.databinding.ActivityMainBinding
import com.example.cumhuriyetsporsalonu.domain.model.User
import com.example.cumhuriyetsporsalonu.utils.user.UserUtils
import com.example.cumhuriyetsporsalonu.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<MainActionBus, MainActivityViewModel, ActivityMainBinding>(
    ActivityMainBinding::inflate,
    MainActivityViewModel::class.java
) {

    private var user: User? = null
    override fun init() {
        println(UserUtils.getCurrentUser()?.name)
        setNavListeners()
    }

    override suspend fun onAction(action: MainActionBus) {

    }

    private fun setNavListeners() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        navController.addOnDestinationChangedListener { _, navDestination, _ ->
            //
        }
        binding.bottomNavigationView.setupWithNavController(navController)
    }
}