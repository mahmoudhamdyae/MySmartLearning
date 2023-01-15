package com.mahmoudhamdyae.smartlearning.ui.course.materials

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.mahmoudhamdyae.smartlearning.base.BaseFragment
import com.mahmoudhamdyae.smartlearning.databinding.FragmentMaterialsBinding

class MaterialsFragment: BaseFragment() {

    private lateinit var binding: FragmentMaterialsBinding
    override lateinit var viewModel: MaterialsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMaterialsBinding.inflate(inflater)
        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(this)[MaterialsViewModel::class.java]
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getUserType()

        binding.materialsList.layoutManager = GridLayoutManager(context, 1)
        binding.materialsList.adapter = MaterialsAdapter(MaterialsAdapter.OnClickListener {
        })
    }
}