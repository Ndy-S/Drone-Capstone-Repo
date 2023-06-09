package com.example.dropdone.ui

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.dropdone.data.LaundryRepository
import com.example.dropdone.model.DataLaundryDummy
import com.example.dropdone.model.Laundry
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(private val repository: LaundryRepository): ViewModel() {
    private val _laundryList = MutableStateFlow(
        repository.getLaundry()
            .sortedBy { it.title }
            .sortedBy { it.location }
    )
    val laundryList: StateFlow<List<DataLaundryDummy>> get() = _laundryList

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    fun search(newQuery: String) {
        _query.value = newQuery
        _laundryList.value = repository.searchLaundry(_query.value)
            .sortedBy { it.title }
            .sortedBy { it.location }
    }

    val data = mutableStateOf(Unit)
    val db = Firebase.firestore

    init {
        getLaundryFireStore()
    }

    private fun getLaundryFireStore() {
        viewModelScope.launch {
            data.value = repository.getLaundryFireStore(db)
            Log.d("testings", data.value.toString())
        }
    }
//    val data: MutableState<DataOrException<List<Laundry>, Exception>> = mutableStateOf(
//        DataOrException(
//            listOf(),
//            Exception("")
//        )
//    )
//
//    init {
//        getLaundryFireStore()
//    }
//
//    private fun getLaundryFireStore() {
//        viewModelScope.launch {
//            data.value = repository.getLaundryFireStore()
//            Log.d("testing", "${data.value} ")
//        }
//    }
}

class ViewModelFactory(private val repository: LaundryRepository) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}