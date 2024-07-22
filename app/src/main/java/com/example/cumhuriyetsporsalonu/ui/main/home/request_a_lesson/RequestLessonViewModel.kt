package com.example.cumhuriyetsporsalonu.ui.main.home.request_a_lesson

import androidx.lifecycle.viewModelScope
import com.example.cumhuriyetsporsalonu.data.remote.repository.FirebaseRepository
import com.example.cumhuriyetsporsalonu.domain.model.Lesson
import com.example.cumhuriyetsporsalonu.domain.use_case.GetRequestableLessonsUseCase
import com.example.cumhuriyetsporsalonu.ui.base.BaseViewModel
import com.example.cumhuriyetsporsalonu.utils.Resource
import com.example.cumhuriyetsporsalonu.utils.user.UserUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RequestLessonViewModel @Inject constructor(
    private val firebaseRepository: FirebaseRepository,
    private val getRequestableLessonsUseCase: GetRequestableLessonsUseCase
) : BaseViewModel<RequestLessonActionBus>() {
    private var _lessonList = mutableListOf<Lesson>()
    val lessonList: List<Lesson> get() = _lessonList

    fun getLessons() {
        val user = UserUtils.getCurrentUser() ?: return
        viewModelScope.launch {
            getRequestableLessonsUseCase.execute(user.uid).collect { result ->
                when (result) {
                    is Resource.Error -> {
                        setLoading(false)
                        sendAction(RequestLessonActionBus.ShowError(result.message))
                    }

                    is Resource.Loading -> setLoading(true)
                    is Resource.Success -> {
                        setLoading(false)
                        result.data?.let {
                            _lessonList = it.toMutableList()
                            sendAction(RequestLessonActionBus.LessonsLoaded)
                        }
                    }
                }

            }
        }
    }

    fun requestLesson(lessonUid: String) {
        val user = UserUtils.getCurrentUser() ?: return
        viewModelScope.launch {

            firebaseRepository.requestLesson(user.uid, lessonUid).collect { result ->
                when (result) {
                    is Resource.Error -> {
                        setLoading(false)
                        sendAction(RequestLessonActionBus.ShowError(result.message))
                    }

                    is Resource.Loading -> setLoading(true)
                    is Resource.Success -> {
                        setLoading(false)
                        _lessonList.removeIf { it.uid == lessonUid }
                        sendAction(RequestLessonActionBus.RequestSent)
                    }
                }
            }
        }
    }
}