package com.mahmoudhamdyae.smartlearning.ui.course.materials

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mahmoudhamdyae.smartlearning.R
import com.mahmoudhamdyae.smartlearning.base.BaseViewModel
import com.mahmoudhamdyae.smartlearning.data.models.Material
import com.mahmoudhamdyae.smartlearning.data.repository.FirebaseRepository
import com.mahmoudhamdyae.smartlearning.utils.STATUS
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class MaterialsViewModel @Inject constructor(
    private val repository: FirebaseRepository
): BaseViewModel() {

    private val _materials = MutableLiveData<List<Material>>()
    val materials: LiveData<List<Material>>
        get() = _materials

    private var _snackBar = MutableLiveData<Int>()
    val snackBar: LiveData<Int>
        get() = _snackBar

    fun addMaterial(file: Uri, material: Material, courseId: String) {
        _status.value = STATUS.LOADING
        repository.addMaterialStorage(file, material.name!!, courseId) { error1 ->
            if (error1 == null) {
                repository.addMaterialsToDataBase(material, courseId) { error2 ->
                    if (error2 == null) {
                        _status.value = STATUS.DONE
                    } else {
                        _status.value = STATUS.ERROR
                        _error.value = error2.message.toString()
                    }
                }
            } else {
                _status.value = STATUS.ERROR
                _error.value = error1.message.toString()
            }
        }
    }

    fun getListOfMaterials(courseId: String) {
        try {
            viewModelScope.launch {
                _status.value = STATUS.LOADING
                repository.getMaterials(courseId, { materials ->
                    _materials.value = materials
                    _status.value = STATUS.DONE
                }, { error ->
                    if (error != null) {
                        _status.value = STATUS.ERROR
                        _error.value = error.message
                    }
                })
            }
        } catch (e: Exception) {
            _error.value = e.message
            _status.value = STATUS.ERROR
        }
    }

    fun delMaterial(courseId: String, material: Material) {
        try {
            viewModelScope.launch {
                _status.value = STATUS.LOADING
                repository.delMaterialStorage(courseId, material.name!!) { error1 ->
                    if (error1 == null) {
                        repository.delMaterialDatabase(courseId, material) { error2 ->
                            if (error2 == null) {
                                _status.value = STATUS.DONE
                            } else {
                                _status.value = STATUS.ERROR
                                _error.value = error2.message
                            }
                        }
                    } else {
                        _status.value = STATUS.ERROR
                        _error.value = error1.message
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
                    _snackBar.value = R.string.downloaded_toast
                } else {
                    _status.value = STATUS.ERROR
                    _error.value = task.exception!!.message
                }
            }
        }
    }
}