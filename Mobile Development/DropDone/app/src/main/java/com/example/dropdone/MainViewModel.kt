package com.example.dropdone

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dropdone.data.LaundryRepository
import com.example.dropdone.model.Booking
import com.example.dropdone.model.Laundry
import com.example.dropdone.model.Reviews
import com.example.dropdone.model.UserData
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainViewModel (private val repository: LaundryRepository) : ViewModel() {
    private val _laundryList = MutableStateFlow(
        repository.getLaundry()
            .sortedBy { it.laundry_name }
    )
    val laundryList: StateFlow<List<Laundry>> get() = _laundryList

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query

    fun search(newQuery: String) {
        _query.value = newQuery
        _laundryList.value = repository.searchLaundry(_query.value)
            .sortedBy { it.laundry_name }
    }

    private val db = Firebase.firestore

    suspend fun getLaundryFromRepo(): MutableList<Laundry> {
        return repository.getLaundryFireStore(db)
    }
    suspend fun getReviewsFromRepo(): MutableList<Reviews> {
        return repository.getReviewsFireStore(db)
    }
    suspend fun getBookingFromRepo(): MutableList<Booking> {
        return repository.getBookingFireStore(db)
    }
    suspend fun getUserFromRepo(): MutableList<UserData> {
        return repository.getUserFireStore(db)
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