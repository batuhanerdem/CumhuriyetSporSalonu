package com.example.cumhuriyetsporsalonu.ui.auth.splash

import com.example.cumhuriyetsporsalonu.data.remote.repository.FirebaseRepository
import com.example.cumhuriyetsporsalonu.utils.user.UserUtils
import com.example.newsapp.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) : BaseViewModel<SplashActionBus>() {
    fun setCurrentUser(uid: String?) {
        uid ?: return
        firebaseRepository.getUserByUid(uid) { user ->
            user?.let {
                UserUtils.setCurrentUser(user)
                sendAction(SplashActionBus.ReadyToGo)
                return@getUserByUid
            }
            sendAction(SplashActionBus.Error)
        }
    }

}
