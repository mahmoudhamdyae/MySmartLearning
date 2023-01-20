package com.mahmoudhamdyae.smartlearning.ui.course.materials

import android.net.Uri
import android.util.Log
import androidx.lifecycle.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.mahmoudhamdyae.smartlearning.base.BaseViewModel
import com.mahmoudhamdyae.smartlearning.data.repository.FirebaseRepository
import com.mahmoudhamdyae.smartlearning.utils.STATUS
import kotlinx.coroutines.launch

class MaterialsViewModel(
    private val repository: FirebaseRepository,
    private val courseId: String
) : BaseViewModel() {

    private val _materials = MutableLiveData<List<String>>()
    val materials: LiveData<List<String>>
        get() = _materials

    private val _progressDialog = MutableLiveData<Double>()
    val progressDialog: LiveData<Double>
        get() = _progressDialog

    init {
        getListOfMaterials()
    }

    fun addMaterial(file: Uri, name: String?, courseId: String) {
        _uploadStatus.value = STATUS.LOADING
        repository.addMaterialStorage(file, name!!, courseId).addOnCompleteListener { taskStorage ->
            if (taskStorage.isSuccessful) {
                repository.addMaterialsToDataBase(name, courseId).addOnCompleteListener { taskDatabase  ->
                    if (taskDatabase.isSuccessful) {
                        _uploadStatus.value = STATUS.DONE
                    } else {
                        _uploadStatus.value = STATUS.ERROR
                        _error.value = taskDatabase.exception?.message
                    }
                }
            } else {
                _uploadStatus.value = STATUS.ERROR
                _error.value = taskStorage.exception?.message
            }
        }.addOnProgressListener {
            _progressDialog.value = 100.0 * it.bytesTransferred / it.totalByteCount
        }
    }

    private fun getListOfMaterials() {
        try {
            viewModelScope.launch {
                _downloadStatus.value = STATUS.LOADING
                repository.getMaterials(courseId).addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val materialsList: MutableList<String> = mutableListOf()
                        for (material in dataSnapshot.children) {
                            val materialItem = material.getValue(String::class.java)
                            materialsList.add(materialItem!!)
                        }
                        _materials.value = materialsList
                        _downloadStatus.value = STATUS.DONE
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.w("getMaterials:Cancelled", "loadMaterials:onCancelled", error.toException())
                        _downloadStatus.value = STATUS.ERROR
                    }
                })
            }
        } catch (e: Exception) {
            _error.value = e.message
            _downloadStatus.value = STATUS.ERROR
        }
    }
}

@Suppress("UNCHECKED_CAST")
class MaterialsViewModelFactory (
    private val repository: FirebaseRepository,
    private val courseId: String
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        (MaterialsViewModel(repository, courseId) as T)
}