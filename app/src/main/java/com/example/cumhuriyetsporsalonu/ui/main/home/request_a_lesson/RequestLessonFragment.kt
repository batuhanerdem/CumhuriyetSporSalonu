package com.example.cumhuriyetsporsalonu.ui.main.home.request_a_lesson

import androidx.core.view.isVisible
import com.example.cumhuriyetsporsalonu.R
import com.example.cumhuriyetsporsalonu.databinding.FragmentRequestLessonBinding
import com.example.cumhuriyetsporsalonu.domain.model.LessonViewHolderTypes
import com.example.cumhuriyetsporsalonu.ui.base.BaseFragment
import com.example.cumhuriyetsporsalonu.ui.main.home.adapter.LessonListingAdapter
import com.example.cumhuriyetsporsalonu.utils.SelectableData.Companion.toSelectable
import com.example.cumhuriyetsporsalonu.utils.Stringfy.Companion.stringfy
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RequestLessonFragment :
    BaseFragment<RequestLessonActionBus, RequestLessonViewModel, FragmentRequestLessonBinding>(
        FragmentRequestLessonBinding::inflate, RequestLessonViewModel::class.java
    ) {
    private lateinit var adapter: LessonListingAdapter
    override suspend fun onAction(action: RequestLessonActionBus) {
        when (action) {
            RequestLessonActionBus.Init -> {}
            RequestLessonActionBus.RequestSent -> {
                showSuccessMessage(R.string.request_sent.stringfy())
                submitListOrShowNoLesson()
            }

            is RequestLessonActionBus.ShowError -> {
                showErrorMessage(action.errorMessage)
            }

            RequestLessonActionBus.LessonsLoaded -> {
                submitListOrShowNoLesson()
            }
        }
    }

    override fun initPage() {
        setRV()
        viewModel.getLessons()
        setOnClickListeners()
    }

    private fun setRV() {
        adapter = LessonListingAdapter(LessonViewHolderTypes.ADDING) {
            viewModel.requestLesson(it.uid)
        }
        binding.rvLesson.adapter = adapter
    }

    private fun setOnClickListeners() {
        binding.imgBack.setOnClickListener {
            navigateBack()
        }
    }

    private fun showNoLessonTV(isListEmpty: Boolean) {
        binding.tvNoLessonFound.isVisible = isListEmpty
    }

    private fun submitListOrShowNoLesson() {
        adapter.submitList(viewModel.lessonList.toMutableList().toSelectable())
        showNoLessonTV(viewModel.lessonList.isEmpty())
    }

}