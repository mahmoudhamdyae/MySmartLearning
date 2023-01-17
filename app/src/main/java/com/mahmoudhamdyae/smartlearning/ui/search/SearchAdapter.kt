package com.mahmoudhamdyae.smartlearning.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mahmoudhamdyae.smartlearning.data.models.Course
import com.mahmoudhamdyae.smartlearning.databinding.CourseItemBinding

class SearchAdapter (private val onClickListener: OnClickListener) :
    ListAdapter<Course, SearchAdapter.CoursePropertyViewHolder>(DiffCallback) {

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

    /**
     * Create new [RecyclerView] item views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): CoursePropertyViewHolder {
        return CoursePropertyViewHolder(CourseItemBinding.inflate(LayoutInflater.from(parent.context)))
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
}