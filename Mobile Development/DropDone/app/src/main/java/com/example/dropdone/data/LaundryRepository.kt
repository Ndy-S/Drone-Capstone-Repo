package com.example.dropdone.data

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.example.dropdone.model.DataLaundryDummy
import com.example.dropdone.model.Laundry
import com.example.dropdone.model.ObjectLaundryDummy
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class LaundryRepository {
    fun getLaundry(): List<DataLaundryDummy> {
        return ObjectLaundryDummy.obLaundry
    }

    fun searchLaundry(query: String): List<DataLaundryDummy> {
        return ObjectLaundryDummy.obLaundry.filter {
            it.title.contains(query, ignoreCase = true)
        }
    }

    fun getLaundryFireStore(db: FirebaseFirestore) {
        val collection = db.collection("laundry")

        collection.get().addOnSuccessListener {
            for (document in it) {
                val data = document.data
                Log.d("testing", "Document ID: ${document.id}, Data: $data")
            }
        }
    }
}