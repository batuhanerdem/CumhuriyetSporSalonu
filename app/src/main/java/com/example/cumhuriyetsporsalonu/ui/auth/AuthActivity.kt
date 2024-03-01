package com.example.cumhuriyetsporsalonu.ui.auth

import com.example.cumhuriyetsporsalonu.databinding.ActivityAuthBinding
import com.example.newsapp.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : BaseActivity<AuthActionBus, AuthViewModel, ActivityAuthBinding>(
    ActivityAuthBinding::inflate,
    AuthViewModel::class.java
) {


    override fun init() {

    }

    override suspend fun onAction(action: AuthActionBus) {
    }


    fun setUid(uid: String) {
        viewModel.uid = uid
    }

    fun getUid(): String? {
        return viewModel.uid
    }


}