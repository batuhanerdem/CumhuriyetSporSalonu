package com.example.cumhuriyetsporsalonu.ui.main.profile.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.cumhuriyetsporsalonu.databinding.ItemLessonProfileBinding

class LessonNameViewHolder(val binding: ItemLessonProfileBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(lessonName: String) {
        binding.apply {
            tvName.text = lessonName
        }
    }
}