package com.example.cumhuriyetsporsalonu.ui.main.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cumhuriyetsporsalonu.databinding.ItemLessonBinding
import com.example.cumhuriyetsporsalonu.domain.model.Lesson

class LessonAdapter(
    private val lessonOnClick: (lesson: Lesson) -> Unit
) : ListAdapter<Lesson, LessonAdapter.LessonViewHolder>(LessonDiffCallback) {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LessonViewHolder {
        val binding = ItemLessonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LessonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LessonViewHolder, position: Int) {
        val currentLesson = getItem(position)
        holder.binding.layoutInnerConstraint.setOnClickListener {
            lessonOnClick(currentLesson)
        }
        holder.bind(currentLesson)
    }

    object LessonDiffCallback : DiffUtil.ItemCallback<Lesson>() {
        override fun areItemsTheSame(oldItem: Lesson, newItem: Lesson): Boolean {
            return oldItem == newItem
//            return false //testing
        }

        override fun areContentsTheSame(oldItem: Lesson, newItem: Lesson): Boolean {
            return oldItem.uid == newItem.uid
//            return false //testing
        }
    }

}