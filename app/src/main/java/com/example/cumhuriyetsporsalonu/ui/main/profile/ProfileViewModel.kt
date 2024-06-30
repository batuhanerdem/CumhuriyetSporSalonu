package com.example.cumhuriyetsporsalonu.ui.main.profile

import com.example.cumhuriyetsporsalonu.data.remote.repository.FirebaseRepository
import com.example.cumhuriyetsporsalonu.domain.model.Lesson
import com.example.cumhuriyetsporsalonu.domain.model.User
import com.example.cumhuriyetsporsalonu.ui.base.BaseViewModel
import com.example.cumhuriyetsporsalonu.utils.Resource
import com.example.cumhuriyetsporsalonu.utils.user.UserUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository
) : BaseViewModel<ProfileActionBus>() {
    val currentUser: User? get() = UserUtils.getCurrentUser()

    var lessonList = mutableListOf<String>()

    private fun generateLessonNameList(list: List<Lesson>) {
        val nameList = mutableListOf<String>()
        list.map {
            nameList.add(it.name)
        }
        lessonList = nameList.toSet().toMutableList()
        sendAction(ProfileActionBus.LessonsLoaded)
    }

    fun getLessons() {
        val user = currentUser ?: return
        firebaseRepository.getLessonsByStudentUid(user.uid) { action ->
            when (action) {
                is Resource.Error -> {}
                is Resource.Loading -> {}
                is Resource.Success -> {
                    action.data?.let {
                        generateLessonNameList(it)
                    }
                }
            }

        }
    }
}
