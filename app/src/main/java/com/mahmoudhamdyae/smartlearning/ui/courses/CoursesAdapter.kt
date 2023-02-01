package com.mahmoudhamdyae.smartlearning.ui.courses

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mahmoudhamdyae.smartlearning.data.models.Course
import com.mahmoudhamdyae.smartlearning.databinding.CourseItemBinding

class CoursesAdapter(
    private val onClickListener: OnClickListener,
    private val onDelClickListener: OnDelClickListener,
) : ListAdapter<Course, CoursesAdapter.CoursePropertyViewHolder>(DiffCallback) {

    private var search: Boolean = false
    private var isTeacher: Boolean = false

    /**
     * The CoursePropertyViewHolder constructor takes the binding variable from the associated
     * GridViewItem, which nicely gives it access to the full [Course] information.
     */
    class CoursePropertyViewHolder(private var binding: CourseItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(course: Course) {
            binding.property = course
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }
    }

    fun setVisibility(isTeacher2: Boolean, search2: Boolean) {
        search = search2
        isTeacher = isTeacher2
    }

    /**
     * Create new [RecyclerView] item views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): CoursePropertyViewHolder {
        val binding = CourseItemBinding.inflate(LayoutInflater.from(parent.context))
        if (search) {
            binding.deleteButton.visibility = View.GONE
        }
        if (isTeacher) {
            binding.teacherNameLabel.visibility = View.GONE
            binding.teacherName.visibility = View.GONE
        } else {
            binding.studentNoLabel.visibility = View.GONE
            binding.studentNo.visibility = View.GONE
        }
        binding.deleteButton.setOnClickListener {
            onDelClickListener.onDelClick(binding.property!!)
        }
        return CoursePropertyViewHolder(binding)
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: CoursePropertyViewHolder, position: Int) {
        val course = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(course)
        }
        holder.bind(course)
    }

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [Course]
     * has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<Course>() {
        override fun areItemsTheSame(oldItem: Course, newItem: Course): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Course, newItem: Course): Boolean {
            return oldItem.id == newItem.id
        }
    }

    /**
     * Custom listener that handles clicks on [RecyclerView] items.  Passes the [Course]
     * associated with the current item to the [onClick] function.
     * @param clickListener lambda that will be called with the current [Course]
     */
    class OnClickListener(val clickListener: (course: Course) -> Unit) {
        fun onClick(course: Course) = clickListener(course)
    }

    class OnDelClickListener(val clickListener: (course: Course) -> Unit) {
        fun onDelClick(course: Course) = clickListener(course)
    }
}