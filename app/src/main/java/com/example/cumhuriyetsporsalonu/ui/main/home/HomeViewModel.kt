package com.example.cumhuriyetsporsalonu.ui.main.home

import com.example.cumhuriyetsporsalonu.data.remote.repository.FirebaseRepository
import com.example.cumhuriyetsporsalonu.domain.model.Lesson
import com.example.cumhuriyetsporsalonu.ui.base.BaseViewModel
import com.example.cumhuriyetsporsalonu.utils.Resource
import com.example.cumhuriyetsporsalonu.utils.user.UserUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val firebaseRepository: FirebaseRepository) :
    BaseViewModel<HomeActionBus>() {
    var lessonList = listOf<Lesson>()

    fun getLessonsByUid() {
        val currentUser = UserUtils.getCurrentUser() ?: return
        firebaseRepository.getLessonsByStudentUid(currentUser.uid) { action ->
            when (action) {
                is Resource.Error -> {
                    setLoading(false)
                    sendAction(HomeActionBus.ShowError(action.message))
                }

                is Resource.Loading -> {
                    setLoading(true)
                }

                is Resource.Success -> {
                    setLoading(false)
                    action.data?.let {
                        lessonList = it
                        sendAction(HomeActionBus.LessonsLoaded)
                    }
                }
            }
        }
    }
}