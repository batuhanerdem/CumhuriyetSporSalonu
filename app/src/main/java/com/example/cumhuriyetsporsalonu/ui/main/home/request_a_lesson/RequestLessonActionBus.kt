package com.example.cumhuriyetsporsalonu.ui.main.home.request_a_lesson

import com.example.cumhuriyetsporsalonu.ui.base.BaseActionBus
import com.example.cumhuriyetsporsalonu.utils.Stringfy

sealed interface RequestLessonActionBus : BaseActionBus {

    data object Init : RequestLessonActionBus

    data class ShowError(val errorMessage: Stringfy? = null) : RequestLessonActionBus

    data object RequestSent : RequestLessonActionBus

    data object LessonsLoaded : RequestLessonActionBus

}