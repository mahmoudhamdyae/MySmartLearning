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
import java.io.File

class MaterialsViewModel(
    private val repository: FirebaseRepository
) : BaseViewModel() {

    private val _materials = MutableLiveData<List<String>>()
    val materials: LiveData<List<String>>
        get() = _materials

    private val _progressDialog = MutableLiveData<Double>()
    val progressDialog: LiveData<Double>
        get() = _progressDialog

    fun addMaterial(file: Uri, name: String?, courseId: String) {
        this._status.value = STATUS.LOADING
        repository.addMaterialStorage(file, name!!, courseId).addOnCompleteListener { taskStorage ->
            if (taskStorage.isSuccessful) {
                onCompleteListener(repository.addMaterialsToDataBase(name, courseId))
            } else {
                this._status.value = STATUS.ERROR
                _error.value = taskStorage.exception?.message
            }
        }.addOnProgressListener {
            _progressDialog.value = 100.0 * it.bytesTransferred / it.totalByteCount
        }
    }

    fun getListOfMaterials(courseId: String) {
        try {
            viewModelScope.launch {
                _status.value = STATUS.LOADING
                repository.getMaterials(courseId).addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val materialsList: MutableList<String> = mutableListOf()
                        for (material in dataSnapshot.children) {
                            val materialItem = material.getValue(String::class.java)
                            materialsList.add(materialItem!!)
                        }
                        _materials.value = materialsList
                        _status.value = STATUS.DONE
                    }

                    override fun onCancelled(error: DatabaseError) {
                        Log.w("getMaterials:Cancelled", "loadMaterials:onCancelled", error.toException())
                        _status.value = STATUS.ERROR
                    }
                })
            }
        } catch (e: Exception) {
            _error.value = e.message
            _status.value = STATUS.ERROR
        }
    }

    fun delMaterial(courseId: String, material: String) {
        try {
            viewModelScope.launch {
                _status.value = STATUS.LOADING
                repository.delMaterialStorage(courseId, material).addOnCompleteListener {task ->
                    if (task.isSuccessful) {
                    repository.getMaterials(courseId).addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            for (dataSnapshot in snapshot.children) {
                                if (material == dataSnapshot.getValue(String::class.java)) {
                                    onCompleteListener(repository.delMaterialDatabase(courseId, dataSnapshot.key!!))
                                    break
                                }
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            Log.w("getMaterials:Cancelled", "loadMaterials:onCancelled", error.toException())
                            _status.value = STATUS.ERROR
                        }
                    })
                    } else {
                        _status.value = STATUS.ERROR
                        _error.value = task.exception?.message
                    }
                }
            }
        } catch (e: Exception) {
            _error.value = e.message
            _status.value = STATUS.ERROR
        }
    }

    fun getMaterial(courseId: String, name: String, localFile: File) {
        viewModelScope.launch {
            _status.value = STATUS.LOADING
            repository.getMaterial(courseId, name, localFile).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _status.value = STATUS.DONE
                } else {
                    _status.value = STATUS.ERROR
                    _error.value = task.exception!!.message
                }
            }
        }
    }
}

@Suppress("UNCHECKED_CAST")
class MaterialsViewModelFactory (
    private val repository: FirebaseRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        (MaterialsViewModel(repository) as T)
}