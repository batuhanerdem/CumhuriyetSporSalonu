package com.example.cumhuriyetsporsalonu.ui.main.profile

import android.content.Intent
import com.example.cumhuriyetsporsalonu.R
import com.example.cumhuriyetsporsalonu.databinding.FragmentProfileBinding
import com.example.cumhuriyetsporsalonu.databinding.FragmentSplashBinding
import com.example.cumhuriyetsporsalonu.ui.auth.AuthActivity
import com.example.cumhuriyetsporsalonu.ui.base.BaseFragment
import com.example.cumhuriyetsporsalonu.ui.main.MainActivity
import com.example.cumhuriyetsporsalonu.utils.Stringfy.Companion.stringfy
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BaseFragment<ProfileActionBus, ProfileViewModel, FragmentProfileBinding>(
    FragmentProfileBinding::inflate, ProfileViewModel::class.java,
) {

    override fun initPage() {
    }

    override suspend fun onAction(action: ProfileActionBus) {
        when (action) {
            ProfileActionBus.Error -> TODO()
            ProfileActionBus.Init -> TODO()
        }
    }

    private fun setUserProfile(){
       val user =  viewModel.currentUser ?: return
        // set user
    }


}