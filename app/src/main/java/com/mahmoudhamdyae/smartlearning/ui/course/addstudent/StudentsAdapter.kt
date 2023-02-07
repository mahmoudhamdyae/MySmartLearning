package com.mahmoudhamdyae.smartlearning.ui.course.addstudent

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mahmoudhamdyae.smartlearning.R
import com.mahmoudhamdyae.smartlearning.data.models.User
import com.mahmoudhamdyae.smartlearning.data.repository.FirebaseRepository
import com.mahmoudhamdyae.smartlearning.databinding.StudentItemBinding

class StudentsAdapter(
    private val onClickListener: OnClickListener
) :
    ListAdapter<User, StudentsAdapter.StudentPropertyViewHolder>(DiffCallback) {

    private var hashMap: HashMap<User, Double>? = null

    fun setDegree(hashMap2: HashMap<User, Double>) {
        hashMap = hashMap2
    }

    /**
     * The [StudentPropertyViewHolder] constructor takes the binding variable from the associated
     * GridViewItem, which nicely gives it access to the full [User] information.
     */
    class StudentPropertyViewHolder(private var binding: StudentItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(user: User, hashMap: HashMap<User, Double>?) {
            binding.property = user
            val repository = FirebaseRepository()
            repository.getProfilePicture(user.userId!!).addOnSuccessListener {
                Glide.with(binding.profileImage.context)
                    .load(it)
                    .apply(
                        RequestOptions()
                            .placeholder(R.drawable.loading_animation)
                            .error(R.drawable.ic_broken_image))
                    .into(binding.profileImage)
            }
            if (user.teacher) {
                binding.teacherLabel.visibility = View.VISIBLE
                binding.teacherLabel.text = "The Teacher"
            } else {
                val degree = hashMap?.get(user)
                if (hashMap != null) {
                    binding.teacherLabel.text = "Degree: ${degree?.toInt()}"
                } else {
                    binding.teacherLabel.visibility = View.GONE
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
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): StudentPropertyViewHolder {
        return StudentPropertyViewHolder(StudentItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: StudentPropertyViewHolder, position: Int) {
        val user = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(user)
        }
        holder.bind(user, hashMap)
    }

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [User]
     * has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.userId == newItem.userId
        }
    }

    /**
     * Custom listener that handles clicks on [RecyclerView] items.  Passes the [User]
     * associated with the current item to the [onClick] function.
     * @param clickListener lambda that will be called with the current [User]
     */
    class OnClickListener(val clickListener: (user: User) -> Unit) {
        fun onClick(user: User) = clickListener(user)
    }
}