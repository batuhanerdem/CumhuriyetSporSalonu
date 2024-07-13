package com.example.cumhuriyetsporsalonu.ui.main.profile

import androidx.lifecycle.viewModelScope
import com.example.cumhuriyetsporsalonu.data.remote.repository.FirebaseRepository
import com.example.cumhuriyetsporsalonu.domain.model.Lesson
import com.example.cumhuriyetsporsalonu.ui.base.BaseViewModel
import com.example.cumhuriyetsporsalonu.utils.Resource
import com.example.cumhuriyetsporsalonu.utils.user.UserUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) : BaseViewModel<ProfileActionBus>() {
    var lessonList = mutableListOf<String>()
    fun getLessons() {
        val currentUser = UserUtils.getCurrentUser() ?: return
        firebaseRepository.getLessonsByStudentUid(currentUser.uid).onEach { action ->
            when (action) {
                is Resource.Error -> {
                    setLoading(false)
                    sendAction(ProfileActionBus.ShowError(action.message))
                }

                is Resource.Loading -> {
                    setLoading(true)
                }

                is Resource.Success -> {
                    setLoading(false)
                    action.data?.let {
                        generateLessonNameList(it)
                    }
                }
            }

        }.launchIn(viewModelScope)
    }

    private fun generateLessonNameList(list: List<Lesson>) {
        val nameList = mutableListOf<String>()
        list.map {
            nameList.add(it.name)
        }
        lessonList = nameList.toSet().toMutableList()
        sendAction(ProfileActionBus.LessonsLoaded)
    }

}
