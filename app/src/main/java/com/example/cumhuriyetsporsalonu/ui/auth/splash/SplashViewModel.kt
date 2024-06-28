package com.example.cumhuriyetsporsalonu.ui.auth.splash

import com.example.cumhuriyetsporsalonu.data.remote.repository.FirebaseRepository
import com.example.cumhuriyetsporsalonu.domain.model.User
import com.example.cumhuriyetsporsalonu.domain.model.VerifiedStatus
import com.example.cumhuriyetsporsalonu.ui.base.BaseViewModel
import com.example.cumhuriyetsporsalonu.utils.Resource
import com.example.cumhuriyetsporsalonu.utils.user.UserUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) : BaseViewModel<SplashActionBus>() {
    fun setCurrentUser(uid: String) {
        firebaseRepository.getUserByUid(uid) { action ->
            when (action) {
                is Resource.Error -> sendAction(SplashActionBus.Error)
                is Resource.Loading -> {}
                is Resource.Success -> {
                    val user = action.data ?: return@getUserByUid
                    UserUtils.setCurrentUser(user)
                    if (user.isVerified != VerifiedStatus.VERIFIED) {
                        sendAction(SplashActionBus.NotVerified)
                        return@getUserByUid
                    }
                    sendAction(SplashActionBus.ReadyToGo)
                }
            }
        }
    }

}
