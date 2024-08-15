package com.example.cumhuriyetsporsalonu.ui.main.home.adapter

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.cumhuriyetsporsalonu.domain.model.Lesson
import com.example.cumhuriyetsporsalonu.utils.SelectableData

abstract class LessonViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {
    abstract fun bind(lessonSelectable: SelectableData<Lesson>)

    abstract fun setOnClickListener(
        lessonSelectable: SelectableData<Lesson>, callback: (Lesson) -> Unit
    )
}