package com.mahmoudhamdyae.smartlearning.ui.course.materials

import android.app.Activity.RESULT_OK
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.mahmoudhamdyae.smartlearning.R
import com.mahmoudhamdyae.smartlearning.base.BaseFragment
import com.mahmoudhamdyae.smartlearning.data.repository.FirebaseRepository
import com.mahmoudhamdyae.smartlearning.databinding.FragmentMaterialsBinding
import com.mahmoudhamdyae.smartlearning.utils.IsTeacher
import com.mahmoudhamdyae.smartlearning.utils.getFileName
import java.io.File

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

        binding.toolbar.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.materialsList.layoutManager = GridLayoutManager(context, 1)
        binding.materialsList.adapter = MaterialsAdapter(MaterialsAdapter.OnClickListener {
            playMaterial(it)
        }, MaterialsAdapter.OnPlayClickListener {
            playMaterial(it)
        }, MaterialsAdapter.OnDownloadClickListener {
            downloadMaterial(it)
        }, MaterialsAdapter.OnDelClickListener {
            delMaterial(it)
        }, viewModel.isTeacher.value == IsTeacher.TEACHER)

        binding.addFab.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "application/pdf"
            try {
                startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), PICK_FILE)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun playMaterial(material: String) {
        downloadMaterial(material)
//        val file = File(requireContext().externalCacheDir, material)
//        viewModel.getMaterial(courseId, material, file)
//
//        try {
//            val intent = Intent(Intent.ACTION_VIEW)
//            intent.setDataAndType(Uri.fromFile(file), "application/pdf")
//            intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
//            requireContext().startActivity(intent)
//        } catch (e: ActivityNotFoundException) {
//            // No program to open pdf is installed
//            Toast.makeText(context, R.string.there_is_no_app_to_read_pdf, Toast.LENGTH_SHORT).show()
//        } catch (e: Exception) {
//            binding.emptyView.visibility = View.VISIBLE
//            binding.emptyView.text = e.message.toString()
//            Toast.makeText(context, e.message.toString(), Toast.LENGTH_SHORT).show()
//        }
    }

    private fun downloadMaterial(material: String) {
        val rootPath = File(Environment.getExternalStorageDirectory(), "Download")
        val localFile = File(rootPath, material)
        viewModel.getMaterial(courseId, material, localFile)
        viewModel.notification.observe(viewLifecycleOwner) {
            if (it) {
                createNotification()
                Toast.makeText(context, "Downloaded to Downloads", Toast.LENGTH_SHORT).show()
                viewModel.makeNotificationFalse()
            }
        }
    }

    private fun delMaterial(material: String) {
        // Create Dialog
        val builder = AlertDialog.Builder(
            requireContext()
        )
        builder.setMessage(getString(R.string.material_delete_dialog_msg))
        builder.setPositiveButton(R.string.material_delete_dialog_delete) { _, _ ->
            // User clicked the "Delete" button, so delete the Course.
            viewModel.delMaterial(courseId, material)
        }
        builder.setNegativeButton(R.string.material_delete_dialog_cancel) { dialog, _ ->
            // User clicked the "Cancel" button, so dismiss the dialog
            dialog?.dismiss()
        }
        val alertDialog = builder.create()
        alertDialog.show()
    }

    private fun createNotification() {
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