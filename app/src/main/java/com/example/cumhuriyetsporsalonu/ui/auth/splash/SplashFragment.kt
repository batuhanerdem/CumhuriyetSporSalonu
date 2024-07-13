package com.example.cumhuriyetsporsalonu.ui.auth.splash

import android.content.Intent
import com.example.cumhuriyetsporsalonu.R
import com.example.cumhuriyetsporsalonu.databinding.FragmentSplashBinding
import com.example.cumhuriyetsporsalonu.ui.auth.AuthActivity
import com.example.cumhuriyetsporsalonu.ui.base.BaseFragment
import com.example.cumhuriyetsporsalonu.ui.main.MainActivity
import com.example.cumhuriyetsporsalonu.utils.Stringfy.Companion.stringfy
import com.example.cumhuriyetsporsalonu.utils.user.UserUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : BaseFragment<SplashActionBus, SplashViewModel, FragmentSplashBinding>(
    FragmentSplashBinding::inflate, SplashViewModel::class.java,
) {

    override fun initPage() {
        setUid()
    }

    override suspend fun onAction(action: SplashActionBus) {
        when (action) {
            SplashActionBus.Init -> {}
            SplashActionBus.Error -> showErrorMessage(R.string.login_error_default.stringfy())
            is SplashActionBus.ReadyToGo -> {
                navigateHome()
            }

            SplashActionBus.NotVerified -> {
                navigateNotVerifiedFragment()
            }
        }
    }

    private fun navigateHome() {
        Intent(requireContext(), MainActivity::class.java).apply {
            startActivity(this)
            requireActivity().finish()
        }
    }

    private fun navigateNotVerifiedFragment() {
        progressBar.hide()
        val user = UserUtils.getCurrentUser() ?: return
        val action = SplashFragmentDirections.actionSplashFragmentToNotVerifiedFragment(user.uid)
        navigateTo(action)
    }

    private fun setUid() {
        val activity = requireActivity() as AuthActivity
        val uid = activity.getUid() ?: return
        viewModel.setCurrentUser(uid)
    }
}