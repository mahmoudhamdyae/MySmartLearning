package com.mahmoudhamdyae.smartlearning.ui.notification

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mahmoudhamdyae.smartlearning.data.models.Notification
import com.mahmoudhamdyae.smartlearning.databinding.NotificationItemBinding

class NotificationAdapter(
    private val onClickListener: OnClickListener
) : ListAdapter<Notification, NotificationAdapter.NotificationPropertyViewHolder>(DiffCallback) {

    /**
     * The CoursePropertyViewHolder constructor takes the binding variable from the associated
     * GridViewItem, which nicely gives it access to the full [Notification] information.
     */
    class NotificationPropertyViewHolder(private var binding: NotificationItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(course: Notification) {
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
                                    viewType: Int): NotificationPropertyViewHolder {
        val binding = NotificationItemBinding.inflate(LayoutInflater.from(parent.context))
        return NotificationPropertyViewHolder(binding)
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: NotificationPropertyViewHolder, position: Int) {
        val notification = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(notification)
        }
        holder.bind(notification)
    }

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [Notification]
     * has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<Notification>() {
        override fun areItemsTheSame(oldItem: Notification, newItem: Notification): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Notification, newItem: Notification): Boolean {
            return oldItem == newItem
        }
    }

    /**
     * Custom listener that handles clicks on [RecyclerView] items.  Passes the [Notification]
     * associated with the current item to the [onClick] function.
     * @param clickListener lambda that will be called with the current [Notification]
     */
    class OnClickListener(val clickListener: (notification: Notification) -> Unit) {
        fun onClick(notification: Notification) = clickListener(notification)
    }
}