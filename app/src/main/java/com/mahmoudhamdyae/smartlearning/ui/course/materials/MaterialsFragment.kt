package com.mahmoudhamdyae.smartlearning.ui.course.materials

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mahmoudhamdyae.smartlearning.databinding.FragmentMaterialsBinding

class MaterialsFragment: Fragment() {

    private lateinit var binding: FragmentMaterialsBinding
    private lateinit var viewModel: MaterialsViewModel

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
}