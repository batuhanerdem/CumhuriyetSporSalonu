package com.example.cumhuriyetsporsalonu.ui.main.profile.edit_profile

import androidx.lifecycle.viewModelScope
import com.example.cumhuriyetsporsalonu.data.remote.repository.FirebaseRepository
import com.example.cumhuriyetsporsalonu.ui.base.BaseViewModel
import com.example.cumhuriyetsporsalonu.utils.Resource
import com.example.cumhuriyetsporsalonu.utils.user.UserUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) : BaseViewModel<EditProfileActionBus>() {

    fun saveUser(
        name: String?, surname: String?, age: String?
    ) {
        val currentUser = UserUtils.getCurrentUser() ?: return
        val newUser = currentUser.copy(name = name, surname = surname, age = age)
        firebaseRepository.setUser(newUser).onEach { action ->
            when (action) {
                is Resource.Error -> sendAction(EditProfileActionBus.ShowError(action.message))
                is Resource.Loading -> {}
                is Resource.Success -> {
                    UserUtils.setCurrentUser(newUser)
                    sendAction(EditProfileActionBus.UserUpdated)
                }
            }
        }.launchIn(viewModelScope)
    }
}
