package com.mahmoudhamdyae.smartlearning.ui.course.materials

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mahmoudhamdyae.smartlearning.databinding.MaterialItemBinding

class MaterialsAdapter(
    private val onClickListener: OnClickListener,
    private val onPlayClickListener: OnPlayClickListener,
    private val onDownloadClickListener: OnDownloadClickListener,
    private val onDelClickListener: OnDelClickListener,
    private val isTeacher: Boolean
) :
    ListAdapter<String, MaterialsAdapter.MaterialsPropertyViewHolder>(DiffCallback) {

    /**
     * The CoursePropertyViewHolder constructor takes the binding variable from the associated
     * GridViewItem, which nicely gives it access to the full [Material] information.
     */
    class MaterialsPropertyViewHolder(private var binding: MaterialItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(material: String) {
            binding.materialText.text = material
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }
    }

    /**
     * Create new [RecyclerView] item views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MaterialsPropertyViewHolder {
        val binding = MaterialItemBinding.inflate(LayoutInflater.from(parent.context))
        if (!isTeacher) {
            binding.deleteButton.visibility = View.GONE
        }

        binding.playButton.setOnClickListener {
            onPlayClickListener.onPlayClick(binding.materialText.text.toString())
        }
        binding.downloadButton.setOnClickListener {
            onDownloadClickListener.onDownloadClick(binding.materialText.text.toString())
        }
        binding.deleteButton.setOnClickListener {
            onDelClickListener.onDelClick(binding.materialText.text.toString())
        }
        return MaterialsPropertyViewHolder(binding)
    }

    /**
     * Replaces the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: MaterialsPropertyViewHolder, position: Int) {
        val material = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(material)
        }
        holder.bind(material)
    }

    /**
     * Allows the RecyclerView to determine which items have changed when the [List] of [Material]
     * has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

    /**
     * Custom listener that handles clicks on [RecyclerView] items.  Passes the [Material]
     * associated with the current item to the [onClick] function.
     * @param clickListener lambda that will be called with the current [Material]
     */
    class OnClickListener(val clickListener: (material: String) -> Unit) {
        fun onClick(material: String) = clickListener(material)
    }

    class OnPlayClickListener(val clickListener: (material: String) -> Unit) {
        fun onPlayClick(material: String) = clickListener(material)
    }

    class OnDownloadClickListener(val clickListener: (material: String) -> Unit) {
        fun onDownloadClick(material: String) = clickListener(material)
    }

    class OnDelClickListener(val clickListener: (material: String) -> Unit) {
        fun onDelClick(material: String) = clickListener(material)
    }
}