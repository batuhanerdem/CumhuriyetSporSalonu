package com.example.cumhuriyetsporsalonu.ui.main.profile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.cumhuriyetsporsalonu.databinding.ItemLessonProfileBinding

class LessonNameAdapter(
) : ListAdapter<String, LessonNameViewHolder>(LessonDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonNameViewHolder {
        val binding =
            ItemLessonProfileBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LessonNameViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LessonNameViewHolder, position: Int) {
        val currentName = getItem(position)
        holder.bind(currentName)
    }

    object LessonDiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
//            return false //testing
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
//            return false //testing
        }
    }

}