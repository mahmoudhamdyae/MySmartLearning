package com.mahmoudhamdyae.smartlearning.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.mahmoudhamdyae.smartlearning.base.BaseFragment
import com.mahmoudhamdyae.smartlearning.databinding.FragmentSearchBinding
import com.mahmoudhamdyae.smartlearning.ui.courses.CoursesAdapter

class SearchFragment: BaseFragment() {

    private lateinit var binding: FragmentSearchBinding
    override val viewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val user = SearchFragmentArgs.fromBundle(requireArguments()).user

        binding.coursesList.layoutManager = GridLayoutManager(requireContext(), 1)
        val adapter = CoursesAdapter(CoursesAdapter.OnClickListener {
            viewModel.addCourse(it, user)
            findNavController().navigateUp()
        }, CoursesAdapter.OnDelClickListener {
        })
        adapter.setVisibility(isTeacher2 = false, search2 = true)
        binding.coursesList.adapter = adapter

        binding.toolbar.setOnClickListener {
            findNavController().navigateUp()
        }

        viewModel.courses.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }
}