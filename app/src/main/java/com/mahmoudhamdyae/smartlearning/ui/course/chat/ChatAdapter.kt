package com.mahmoudhamdyae.smartlearning.ui.course.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mahmoudhamdyae.smartlearning.data.models.Message
import com.mahmoudhamdyae.smartlearning.databinding.ChatItemBinding

class ChatAdapter(
    private val onClickListener: OnClickListener,
    private val userUid: String
) :
    ListAdapter<Message, ChatAdapter.MessagePropertyViewHolder>(DiffCallback) {

    /**
     * The [MessagePropertyViewHolder] constructor takes the binding variable from the associated
     * GridViewItem, which nicely gives it access to the full [Message] information.
     */
    class MessagePropertyViewHolder(private var binding: ChatItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(message: Message) {
            binding.property = message
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }
    }

    /**
     * Create new [RecyclerView] item views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MessagePropertyViewHolder {
        val binding = ChatItemBinding.inflate(LayoutInflater.from(parent.context))
//        if (binding.property!!.fromUid == userUid) {
//            binding.messageReceived.visibility = View.GONE
//            binding.myMessage.visibility = View.VISIBLE
//        } else {
//            binding.messageReceived.visibility = View.VISIBLE
//            binding.myMessage.visibility = View.GONE
//        }
        return MessagePropertyViewHolder(binding)
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: ChatAdapter.MessagePropertyViewHolder, position: Int) {
        val user = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(user)
        }
        holder.bind(user)
    }

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [Message]
     * has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<Message>() {
        override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem == newItem
        }
    }

    /**
     * Custom listener that handles clicks on [RecyclerView] items.  Passes the [Message]
     * associated with the current item to the [onClick] function.
     * @param clickListener lambda that will be called with the current [Message]
     */
    class OnClickListener(val clickListener: (message: Message) -> Unit) {
        fun onClick(message: Message) = clickListener(message)
    }
}