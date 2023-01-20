package com.mahmoudhamdyae.smartlearning.ui.course.materials

import android.app.Activity.RESULT_OK
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.mahmoudhamdyae.smartlearning.base.BaseFragment
import com.mahmoudhamdyae.smartlearning.data.repository.FirebaseRepository
import com.mahmoudhamdyae.smartlearning.databinding.FragmentMaterialsBinding
import com.mahmoudhamdyae.smartlearning.utils.STATUS
import com.mahmoudhamdyae.smartlearning.utils.getFileName

@Suppress("DEPRECATION")
class MaterialsFragment: BaseFragment() {

    private lateinit var binding: FragmentMaterialsBinding
    override val viewModel: MaterialsViewModel by viewModels {
        MaterialsViewModelFactory(FirebaseRepository())
    }

    private lateinit var courseId: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMaterialsBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        courseId = MaterialsFragmentArgs.fromBundle(requireArguments()).courseId!!
        viewModel.getListOfMaterials(courseId)

        getUserType()

        binding.backButton.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.materialsList.layoutManager = GridLayoutManager(context, 1)
        binding.materialsList.adapter = MaterialsAdapter(MaterialsAdapter.OnClickListener {
        })

        binding.addFab.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "application/*"
            try {
                startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), PICK_FILE)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }
        }

        viewModel.uploadStatus.observe(viewLifecycleOwner) {
            if (it == STATUS.LOADING) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
            }
        }
        viewModel.progressDialog.observe(viewLifecycleOwner) {
            binding.progressBar.progress = it.toInt()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) return
        if (requestCode == PICK_FILE && resultCode == RESULT_OK) {
            val file: Uri = data.data!!
            viewModel.addMaterial(file, context?.getFileName(file), courseId)
        }
    }

    companion object {
        private const val PICK_FILE = 1
    }
}