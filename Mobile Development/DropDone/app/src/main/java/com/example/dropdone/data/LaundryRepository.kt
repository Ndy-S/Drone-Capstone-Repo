package com.example.dropdone.data

import android.util.Log
import com.example.dropdone.model.Laundry
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class LaundryRepository {
    fun getLaundry(): List<Laundry> {
        return mutableListOf()
    }

    fun searchLaundry(query: String): List<Laundry> {
        val laundryList = mutableListOf<Laundry>()
        return laundryList.filter {
            it.id.contains(query, ignoreCase = true)
        }
    }
     suspend fun getLaundryFireStore(db: FirebaseFirestore): MutableList<Laundry> {
        val laundryData = mutableListOf<Laundry>()
        val collection = db.collection("laundry")

        try {
            val snapshot = collection.get().await()

            for (document in snapshot.documents) {
                val data = document.data
                if (data != null) {
                    val documentData = Laundry(
                        id = document.id,
                        address = data["address"].toString(),
                        email = data["email"].toString(),
                        icon = data["icon"].toString(),
                        latitude = data["latitude"].toString(),
                        laundry_name = data["laundry_name"].toString(),
                        longitude = data["longitude"].toString(),
                        opening_hours = data["opening_hour"].toString(),
                        phone_number = data["phone_number"].toString(),
                        price = data["price"].toString(),
                        rating = data["rating"].toString().toDoubleOrNull() ?: 0.0
                    )
                    laundryData.add(documentData)
                }
            }
        } catch (e: Exception) {
            Log.e("Firestore Error", "Error getting laundry documents: ", e)
        }

        Log.d("testing", "querySnapshot data: $laundryData")
        return laundryData
    }
}