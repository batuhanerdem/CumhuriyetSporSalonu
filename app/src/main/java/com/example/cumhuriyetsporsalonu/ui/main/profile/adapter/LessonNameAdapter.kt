package com.example.cumhuriyetsporsalonu.ui.main.profile.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cumhuriyetsporsalonu.databinding.ItemLessonBinding
import com.example.cumhuriyetsporsalonu.databinding.ItemLessonProfileBinding
import com.example.cumhuriyetsporsalonu.domain.model.Lesson

class LessonNameAdapter(
) : ListAdapter<String, LessonNameAdapter.LessonNameViewHolder>(LessonDiffCallback) {
    class LessonNameViewHolder(val binding: ItemLessonProfileBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(lessonName: String) {
            binding.apply {
                tvName.text = lessonName
            }
        }
    }

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