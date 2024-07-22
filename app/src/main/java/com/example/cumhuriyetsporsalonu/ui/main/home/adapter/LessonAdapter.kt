package com.example.cumhuriyetsporsalonu.ui.main.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.cumhuriyetsporsalonu.databinding.ItemLessonBinding
import com.example.cumhuriyetsporsalonu.databinding.ItemLessonSelectingBinding
import com.example.cumhuriyetsporsalonu.domain.model.Lesson
import com.example.cumhuriyetsporsalonu.domain.model.LessonViewHolderTypes
import com.example.cumhuriyetsporsalonu.domain.model.LessonViewHolderTypes.ADDING
import com.example.cumhuriyetsporsalonu.domain.model.LessonViewHolderTypes.LISTING
import com.example.cumhuriyetsporsalonu.utils.SelectableData

class LessonListingAdapter(
    private val type: LessonViewHolderTypes = LISTING,
    private val lessonOnClick: (lesson: Lesson) -> Unit
) : ListAdapter<SelectableData<Lesson>, LessonViewHolder>(LessonDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonViewHolder {
        when (type) {
            LISTING -> {
                val binding =
                    ItemLessonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return (LessonListingViewHolder(binding))

            }

            ADDING -> {
                val binding = ItemLessonSelectingBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
                return LessonSelectingViewHolder(binding)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (type) {
            LISTING -> LISTING.ordinal
            ADDING -> ADDING.ordinal
        }
    }

    override fun onBindViewHolder(holder: LessonViewHolder, position: Int) {
        val currentLesson = getItem(position)
        holder.setOnClickListener(currentLesson, lessonOnClick)
        holder.bind(currentLesson)
    }

    object LessonDiffCallback : DiffUtil.ItemCallback<SelectableData<Lesson>>() {
        override fun areItemsTheSame(
            oldItem: SelectableData<Lesson>, newItem: SelectableData<Lesson>
        ): Boolean {
            return oldItem.data.uid == newItem.data.uid
        }

        override fun areContentsTheSame(
            oldItem: SelectableData<Lesson>, newItem: SelectableData<Lesson>
        ): Boolean {
            return oldItem.isSelected == newItem.isSelected
        }
    }

}