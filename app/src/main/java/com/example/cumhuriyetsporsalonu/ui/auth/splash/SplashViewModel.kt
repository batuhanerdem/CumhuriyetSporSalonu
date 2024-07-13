package com.example.cumhuriyetsporsalonu.ui.auth.splash

import android.util.Log
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.cumhuriyetsporsalonu.data.remote.repository.FirebaseRepository
import com.example.cumhuriyetsporsalonu.domain.model.VerifiedStatus
import com.example.cumhuriyetsporsalonu.ui.base.BaseViewModel
import com.example.cumhuriyetsporsalonu.utils.Resource
import com.example.cumhuriyetsporsalonu.utils.user.UserUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) : BaseViewModel<SplashActionBus>() {
    fun setCurrentUser(uid: String) {
        firebaseRepository.getUserByUid(uid).onEach { action ->
            when (action) {
                is Resource.Error -> {
                    setLoading(false)
                    sendAction(SplashActionBus.Error)
                }

                is Resource.Loading -> setLoading(true)

                is Resource.Success -> {
                    setLoading(false)
                    val user = action.data ?: return@onEach
                    UserUtils.setCurrentUser(user)
                    if (user.isVerified != VerifiedStatus.VERIFIED) {
                        sendAction(SplashActionBus.NotVerified)
                        return@onEach
                    }
                    sendAction(SplashActionBus.ReadyToGo)
                }
            }
        }.launchIn(viewModelScope)
    }

}
