package com.example.cumhuriyetsporsalonu.ui.main.home.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.cumhuriyetsporsalonu.databinding.ItemLessonBinding
import com.example.cumhuriyetsporsalonu.domain.model.Lesson

class LessonViewHolder(val binding: ItemLessonBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(lesson: Lesson) {
        val context = binding.tvName.context
        binding.apply {
            val dayText = lesson.lessonDate.day.stringIdAsStringfy.getString(context)
            tvDay.text = dayText
            tvName.text = lesson.name
            val hoursText = "${lesson.lessonDate.startHour} - ${lesson.lessonDate.endHour}"
            tvDate.text = hoursText
        }
    }
}