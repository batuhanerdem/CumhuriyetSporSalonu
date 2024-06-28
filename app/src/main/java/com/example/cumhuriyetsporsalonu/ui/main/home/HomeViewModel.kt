package com.example.cumhuriyetsporsalonu.ui.main.home

import com.example.cumhuriyetsporsalonu.R
import com.example.cumhuriyetsporsalonu.data.remote.repository.FirebaseRepository
import com.example.cumhuriyetsporsalonu.domain.model.Lesson
import com.example.cumhuriyetsporsalonu.domain.model.User
import com.example.cumhuriyetsporsalonu.ui.base.BaseViewModel
import com.example.cumhuriyetsporsalonu.utils.Resource
import com.example.cumhuriyetsporsalonu.utils.Stringfy.Companion.stringfy
import com.example.cumhuriyetsporsalonu.utils.user.UserUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val firebaseRepository: FirebaseRepository) :
    BaseViewModel<HomeActionBus>() {
    var lessonList = listOf<Lesson>()
    lateinit var currentUser: User

    fun getUserInfo() {
        UserUtils.getCurrentUser()?.let {
            currentUser = it
            return
        }
        val message = R.string.login_error_user.stringfy()
        sendAction(HomeActionBus.ShowError(message))
    }

    fun getLessonsByUid(studentUid: String) {
        firebaseRepository.getLessonsByStudentUid(studentUid) { action ->
            when (action) {
                is Resource.Error -> sendAction(HomeActionBus.ShowError(action.message))
                is Resource.Loading -> {}
                is Resource.Success -> {
                    val lessonList = action.data
                    lessonList?.let {
                        this.lessonList = it
                        sendAction(HomeActionBus.LessonsLoaded)
                    }
                }
            }


        }
    }
}