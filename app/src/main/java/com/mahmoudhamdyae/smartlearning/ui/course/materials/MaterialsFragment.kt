package com.mahmoudhamdyae.smartlearning.ui.course.materials

import android.app.Activity.RESULT_OK
import android.content.ActivityNotFoundException
import android.content.ClipData
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
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
            playMaterial(it.name!!)
        }, MaterialsAdapter.OnPlayClickListener {
            playMaterial(it.name!!)
        }, MaterialsAdapter.OnDownloadClickListener {
            downloadMaterial(it.name!!)
        }, MaterialsAdapter.OnDelClickListener {
            delMaterial(it.name!!)
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

        viewModel.snackBar.observe(viewLifecycleOwner) {
            Snackbar.make(requireView(), getString(it), Snackbar.LENGTH_SHORT)
                .setAction(R.string.snack_bar_button) {
                    openPDF()
                }
                .show()
        }

        verifyStoragePermission()
    }

    private fun playMaterial(material: String) {
        downloadMaterial(material)
        openPDF()
    }

    private fun downloadMaterial(material: String) {
        val rootPath = File(Environment.getExternalStorageDirectory(), "Download")
        val localFile = File(rootPath, material)
        viewModel.getMaterial(courseId, material, localFile)
    }

    private fun verifyStoragePermission() {
//        val permission = ActivityCompat.checkSelfPermission(requireActivity(), WRITE_EXTERNAL_STORAGE)
//
//        // Surrounded with if statement for Android R to get access of complete file.
//        if (SDK_INT >= Build.VERSION_CODES.R) {
//            if (!Environment.isExternalStorageManager() && permission != PackageManager.PERMISSION_GRANTED) {
//                ActivityCompat.requestPermissions(
//                    requireActivity(),
//                    PERMISSION_STORAGE,
//                    REQUEST_EXTERNAL_STORAGE
//                )
//
//                // Abruptly we will ask for permission once the application is launched for sake demo.
//                val intent = Intent()
//                intent.action = ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION
//                val uri = Uri.fromParts("package", requireActivity().packageName, null)
//                intent.data = uri
//                startActivity(intent)
//            }
//        }
    }

    private fun openPDF() {

        // Get the File location and file name.
        val file = File(Environment.getExternalStorageDirectory(), "Download/My Resume.pdf")

        // Get the URI Path of file.
        val uriPdfPath: Uri = FileProvider.getUriForFile(
            requireContext(),
            requireActivity().applicationContext.packageName + ".provider",
            file
        )

        // Start Intent to View PDF from the Installed Applications.
        val pdfOpenIntent = Intent(Intent.ACTION_VIEW)
        pdfOpenIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        pdfOpenIntent.clipData = ClipData.newRawUri("", uriPdfPath)
        pdfOpenIntent.setDataAndType(uriPdfPath, "application/pdf")
        pdfOpenIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        try {
            startActivity(pdfOpenIntent)
        } catch (activityNotFoundException: ActivityNotFoundException) {
            Toast.makeText(context, "There is no app to load corresponding PDF", Toast.LENGTH_LONG).show()
        }
    }

    private fun delMaterial(material: String) {
        val materialAlertDialogBuilder = MaterialAlertDialogBuilder(requireContext())
        materialAlertDialogBuilder
            .setMessage(R.string.material_delete_dialog_msg)
            // Delete Button
            .setPositiveButton(R.string.material_delete_dialog_delete) { dialog, _ ->
                viewModel.delMaterial(courseId, material)
                dialog.dismiss()
            }
            // Cancel Button
            .setNegativeButton(R.string.material_delete_dialog_cancel) { dialog, _ ->
                dialog.dismiss()
            }
            .show()
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
//        private const val REQUEST_EXTERNAL_STORAGE = 1
//        private val PERMISSION_STORAGE = arrayOf(
//            READ_EXTERNAL_STORAGE,
//            WRITE_EXTERNAL_STORAGE
//        )
    }

}