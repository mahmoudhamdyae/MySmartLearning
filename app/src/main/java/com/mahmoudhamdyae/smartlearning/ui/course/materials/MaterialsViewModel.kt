package com.mahmoudhamdyae.smartlearning.ui.course.materials

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mahmoudhamdyae.smartlearning.base.BaseViewModel

class MaterialsViewModel : BaseViewModel() {

    private val _materials = MutableLiveData<List<String>>()
    val materials: LiveData<List<String>>
        get() = _materials

    init {
        val c = mutableListOf<String>()
        c.add("1")
        c.add("2")
        c.add("3")

        _materials.value = c
    }
}