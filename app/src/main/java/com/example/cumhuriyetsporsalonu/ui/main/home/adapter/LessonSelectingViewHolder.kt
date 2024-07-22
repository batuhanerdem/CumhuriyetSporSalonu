package com.example.cumhuriyetsporsalonu.ui.main.home.adapter

import android.util.Log
import com.example.cumhuriyetsporsalonu.databinding.ItemLessonSelectingBinding
import com.example.cumhuriyetsporsalonu.domain.model.Lesson
import com.example.cumhuriyetsporsalonu.utils.SelectableData

class    LessonSelectingViewHolder(val binding: ItemLessonSelectingBinding) :
    LessonViewHolder(binding) {
    override fun bind(lessonSelectable: SelectableData<Lesson>) {
        val context = binding.tvName.context
        binding.apply {
            val dayText = lessonSelectable.data.lessonDate.day.stringIdAsStringfy.getString(context)
            tvDay.text = dayText
            tvName.text = lessonSelectable.data.name
            val hoursText =
                "${lessonSelectable.data.lessonDate.startHour} - ${lessonSelectable.data.lessonDate.endHour}"
            tvDate.text = hoursText
        }
    }

    override fun setOnClickListener(lessonSelectable: SelectableData<Lesson>, callback: (Lesson) -> Unit) {
        binding.imgAddLessson.setOnClickListener {
            callback(lessonSelectable.data)
        }
    }
}