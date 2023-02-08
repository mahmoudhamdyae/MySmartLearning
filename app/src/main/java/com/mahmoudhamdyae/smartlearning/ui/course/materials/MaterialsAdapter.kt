package com.mahmoudhamdyae.smartlearning.ui.course.materials

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mahmoudhamdyae.smartlearning.data.models.Material
import com.mahmoudhamdyae.smartlearning.databinding.MaterialItemBinding

class MaterialsAdapter(
    private val onClickListener: OnClickListener,
    private val onPlayClickListener: OnPlayClickListener,
    private val onDownloadClickListener: OnDownloadClickListener,
    private val onDelClickListener: OnDelClickListener,
    private val isTeacher: Boolean
) :
    ListAdapter<Material, MaterialsAdapter.MaterialsPropertyViewHolder>(DiffCallback) {

    /**
     * The CoursePropertyViewHolder constructor takes the binding variable from the associated
     * GridViewItem, which nicely gives it access to the full [Material] information.
     */
    class MaterialsPropertyViewHolder(private var binding: MaterialItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(material: Material) {
            binding.property = material
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
            onPlayClickListener.onPlayClick(binding.property!!)
        }
        binding.downloadButton.setOnClickListener {
            onDownloadClickListener.onDownloadClick(binding.property!!)
        }
        binding.deleteButton.setOnClickListener {
            onDelClickListener.onDelClick(binding.property!!)
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
    companion object DiffCallback : DiffUtil.ItemCallback<Material>() {
        override fun areItemsTheSame(oldItem: Material, newItem: Material): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Material, newItem: Material): Boolean {
            return oldItem == newItem
        }
    }

    /**
     * Custom listener that handles clicks on [RecyclerView] items.  Passes the [Material]
     * associated with the current item to the [onClick] function.
     * @param clickListener lambda that will be called with the current [Material]
     */
    class OnClickListener(val clickListener: (material: Material) -> Unit) {
        fun onClick(material: Material) = clickListener(material)
    }

    class OnPlayClickListener(val clickListener: (material: Material) -> Unit) {
        fun onPlayClick(material: Material) = clickListener(material)
    }

    class OnDownloadClickListener(val clickListener: (material: Material) -> Unit) {
        fun onDownloadClick(material: Material) = clickListener(material)
    }

    class OnDelClickListener(val clickListener: (material: Material) -> Unit) {
        fun onDelClick(material: Material) = clickListener(material)
    }
}