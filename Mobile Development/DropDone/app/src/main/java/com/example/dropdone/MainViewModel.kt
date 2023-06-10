package com.example.dropdone

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dropdone.data.LaundryRepository
import com.example.dropdone.model.Laundry
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel (private val repository: LaundryRepository) : ViewModel() {
    private val _laundryList = MutableStateFlow(
        repository.getLaundry()
            .sortedBy { it.id }
            .sortedBy { it.address }
    )
    val laundryList: StateFlow<List<Laundry>> get() = _laundryList

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    fun search(newQuery: String) {
        _query.value = newQuery
        _laundryList.value = repository.searchLaundry(_query.value)
            .sortedBy { it.id }
            .sortedBy { it.address }
    }

    val db = Firebase.firestore
    val data = mutableStateOf<List<Laundry>>(emptyList())

    suspend fun getLaundryFromRepo(): MutableList<Laundry> {
        return repository.getLaundryFireStore(db)
    }
}

class ViewModelFactory(private val repository: LaundryRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}