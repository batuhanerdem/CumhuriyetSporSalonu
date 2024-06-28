package com.example.cumhuriyetsporsalonu.ui.main.profile.edit_profile

import com.example.cumhuriyetsporsalonu.databinding.FragmentSplashBinding
import com.example.cumhuriyetsporsalonu.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProfileFragment :
    BaseFragment<EditProfileActionBus, EditProfileViewModel, FragmentSplashBinding>(
        FragmentSplashBinding::inflate, EditProfileViewModel::class.java,
    ) {

    override fun initPage() {
    }

    override suspend fun onAction(action: EditProfileActionBus) {
        when (action) {
            is EditProfileActionBus.ShowError -> TODO()
            EditProfileActionBus.Init -> TODO()
            EditProfileActionBus.UserUpdated -> TODO()
        }
    }

    private fun setUserProfile() {
        val user = viewModel.currentUser ?: return
        // set user
    }


}