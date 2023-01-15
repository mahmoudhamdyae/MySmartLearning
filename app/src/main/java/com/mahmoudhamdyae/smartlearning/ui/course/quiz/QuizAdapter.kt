package com.mahmoudhamdyae.smartlearning.ui.course.quiz

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mahmoudhamdyae.smartlearning.data.models.Quiz
import com.mahmoudhamdyae.smartlearning.databinding.QuizItemBinding

class QuizAdapter(private val onClickListener: OnClickListener) :
    ListAdapter<Quiz, QuizAdapter.QuizzesPropertyViewHolder>(DiffCallback) {

    /**
     * The CoursePropertyViewHolder constructor takes the binding variable from the associated
     * GridViewItem, which nicely gives it access to the full [Quiz] information.
     */
    class QuizzesPropertyViewHolder(private var binding: QuizItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(quiz: Quiz) {
            binding.property = quiz
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }
    }

    /**
     * Create new [RecyclerView] item views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizzesPropertyViewHolder {
        return QuizzesPropertyViewHolder(
            QuizItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: QuizzesPropertyViewHolder, position: Int) {
        val course = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(course)
        }
        holder.bind(course)
    }

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [Quiz]
     * has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<Quiz>() {
        override fun areItemsTheSame(oldItem: Quiz, newItem: Quiz): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Quiz, newItem: Quiz): Boolean {
            return oldItem.id == newItem.id
        }
    }

    /**
     * Custom listener that handles clicks on [RecyclerView] items.  Passes the [Quiz]
     * associated with the current item to the [onClick] function.
     * @param clickListener lambda that will be called with the current [Quiz]
     */
    class OnClickListener(val clickListener: (quiz: Quiz) -> Unit) {
        fun onClick(quiz: Quiz) = clickListener(quiz)
    }
}