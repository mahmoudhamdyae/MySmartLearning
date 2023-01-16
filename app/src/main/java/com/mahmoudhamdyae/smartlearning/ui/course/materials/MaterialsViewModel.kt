package com.mahmoudhamdyae.smartlearning.ui.course.materials

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mahmoudhamdyae.smartlearning.base.BaseViewModel
import com.mahmoudhamdyae.smartlearning.data.repository.FirebaseRepository
import com.mahmoudhamdyae.smartlearning.utils.STATUS

class MaterialsViewModel(private val repository: FirebaseRepository) : BaseViewModel() {

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
        _status.value = STATUS.LOADING
        repository.addMaterialStorage(file, name!!, courseId).addOnCompleteListener { taskStorage ->
            if (taskStorage.isSuccessful) {
                repository.addMaterialsToDataBase(name, courseId).addOnCompleteListener { taskDatabase  ->
                    if (taskDatabase.isSuccessful) {
                        _status.value = STATUS.DONE
                    } else {
                        _status.value = STATUS.ERROR
                        _error.value = taskDatabase.exception?.message
                    }
                }
            } else {
                _status.value = STATUS.ERROR
                _error.value = taskStorage.exception?.message
            }
        }.addOnProgressListener {
            _progressDialog.value = 100.0 * it.bytesTransferred / it.totalByteCount
        }
    }

    private fun getListOfMaterials() {
        _materials.value = repository.getMaterial()
    }
}

@Suppress("UNCHECKED_CAST")
class MaterialsViewModelFactory (
    private val repository: FirebaseRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        (MaterialsViewModel(repository) as T)
}