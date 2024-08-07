package com.example.cumhuriyetsporsalonu.ui.auth.splash.not_verified

import androidx.lifecycle.viewModelScope
import com.example.cumhuriyetsporsalonu.data.remote.repository.FirebaseRepository
import com.example.cumhuriyetsporsalonu.domain.model.VerifiedStatus
import com.example.cumhuriyetsporsalonu.ui.base.BaseViewModel
import com.example.cumhuriyetsporsalonu.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class NotVerifiedViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) : BaseViewModel<NotVerifiedActionBus>() {

    private var _status = VerifiedStatus.NOTANSWERED
    val status get() = _status

    fun getUserVerifiedStatus(id: String) {
        setLoading(true)
        firebaseRepository.listenVerifiedStatus(id).onEach { result ->
            setLoading(false)
            when (result) {
                is Resource.Error -> {
                    sendAction(NotVerifiedActionBus.ShowError(result.message))
                }

                is Resource.Success -> {
                    result.data?.let {
                        _status = it
                        sendAction(NotVerifiedActionBus.StatusLoaded)
                    }
                }
            }
        }.launchIn(viewModelScope)
    }
}