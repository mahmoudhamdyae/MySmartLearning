package com.mahmoudhamdyae.smartlearning.ui.course.quiz

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mahmoudhamdyae.smartlearning.data.models.Quiz
import com.mahmoudhamdyae.smartlearning.databinding.QuizItemBinding

class QuizAdapter(
    private val onClickListener: OnClickListener,
    private val onDelClickListener: OnDelClickListener
) :
    ListAdapter<Quiz, QuizAdapter.QuizzesPropertyViewHolder>(DiffCallback) {

    private var isTeacher = true
    fun setIsTeacher(isTeacher1: Boolean) {
        isTeacher = isTeacher1
    }

    private var hashMap: HashMap<String, Int?>? = HashMap()
    fun setHashMap(hashMap2: HashMap<String, Int?>?) {
        hashMap = hashMap2
    }

    /**
     * The CoursePropertyViewHolder constructor takes the binding variable from the associated
     * GridViewItem, which nicely gives it access to the full [Quiz] information.
     */
    class QuizzesPropertyViewHolder(private var binding: QuizItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(quiz: Quiz, isTeacher: Boolean, hashMap: HashMap<String, Int?>?) {
            binding.property = quiz
            if (isTeacher) {
                binding.checkedImage.visibility = View.GONE
                binding.solvedOrNot.visibility = View.GONE
            } else {
                binding.deleteButton.visibility = View.GONE
                if (hashMap?.get(quiz.id) == null) {
                    // Not Solved
                    binding.checkedImage.visibility = View.GONE
                } else {
                    val degree = hashMap[quiz.id]
                    binding.solvedOrNot.text = "Degree: ${degree.toString()}"
                    binding.solvedOrNot.setTextColor(Color.GREEN)
                }
            }
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }
    }

    /**
     * Create new [RecyclerView] item views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuizzesPropertyViewHolder {
        val binding = QuizItemBinding.inflate(LayoutInflater.from(parent.context))
        binding.deleteButton.setOnClickListener {
            onDelClickListener.onDelClick(binding.property!!)
        }
        return QuizzesPropertyViewHolder(binding)
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: QuizzesPropertyViewHolder, position: Int) {
        val quiz = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(quiz)
        }
        holder.bind(quiz, isTeacher, hashMap)
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

    class OnDelClickListener(val clickListener: (quiz: Quiz) -> Unit) {
        fun onDelClick(quiz: Quiz) = clickListener(quiz)
    }
}