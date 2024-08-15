package com.example.cumhuriyetsporsalonu.ui.main.home

import androidx.lifecycle.viewModelScope
import com.example.cumhuriyetsporsalonu.data.remote.repository.FirebaseRepository
import com.example.cumhuriyetsporsalonu.domain.model.Lesson
import com.example.cumhuriyetsporsalonu.ui.base.BaseViewModel
import com.example.cumhuriyetsporsalonu.utils.Resource
import com.example.cumhuriyetsporsalonu.utils.user.UserUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val firebaseRepository: FirebaseRepository) :
    BaseViewModel<HomeActionBus>() {
    var lessonList = listOf<Lesson>()

    fun getLessonsByUid() {
        val currentUser = UserUtils.getCurrentUser() ?: return
        setLoading(true)
        firebaseRepository.getLessonsByStudentUid(currentUser.uid).onEach { action ->
            setLoading(false)
            when (action) {
                is Resource.Error -> {
                    sendAction(HomeActionBus.ShowError(action.message))
                }

                is Resource.Success -> {
                    action.data?.let {
                        lessonList = it
                        sendAction(HomeActionBus.LessonsLoaded)
                    }
                }
            }
        }.launchIn(viewModelScope)
    }
}