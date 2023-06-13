package com.example.dropdone.data

import android.util.Log
import com.example.dropdone.model.Booking
import com.example.dropdone.model.Laundry
import com.example.dropdone.model.Reviews
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

class LaundryRepository {
    fun getLaundry(): MutableList<Laundry> {
        val laundryData = mutableListOf<Laundry>()
        val db = Firebase.firestore

        db.collection("laundry")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
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
                            opening_hours = data["opening_hours"].toString(),
                            phone_number = data["phone_number"].toString(),
                            price = data["price"].toString(),
                            rating = data["rating"].toString().toDoubleOrNull() ?: 0.0
                        )
                        laundryData.add(documentData)
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.w("testing", "Error getting documents.", exception)
            }
        Log.d("testings", "get $laundryData")
        return laundryData
    }

    fun searchLaundry(query: String): List<Laundry> {
        val laundryData = mutableListOf<Laundry>()
        val db = Firebase.firestore

        db.collection("laundry")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
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
                            opening_hours = data["opening_hours"].toString(),
                            phone_number = data["phone_number"].toString(),
                            price = data["price"].toString(),
                            rating = data["rating"].toString().toDoubleOrNull() ?: 0.0
                        )
                        laundryData.add(documentData)
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.w("testing", "Error getting documents.", exception)
            }
        Log.d("testings", "search $laundryData")
        return laundryData.filter {
            it.id.contains(query, ignoreCase = true)
        }
//        return laundryData.filter {
//            it.id.contains(query, ignoreCase = true)
//        }
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
                        opening_hours = data["opening_hours"].toString(),
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
        Log.d("review", "getLaundryFireStore: $laundryData")
        return laundryData
    }

    suspend fun getReviewsFireStore(db: FirebaseFirestore): MutableList<Reviews> {
        val reviewsData = mutableListOf<Reviews>()
        val collection = db.collection("reviews")

        try {
            val snapshot = collection.get().await()

            for (document in snapshot.documents) {
                val data = document.data
                if (data != null) {
                    val documentData = Reviews(
                        id = document.id,
                        laundry_id = data["laundry_id"].toString(),
                        review_author_id = data["review_author_id"].toString(),
                        review_rating = data["review_rating"].toString().toInt(),
                        review_text = data["review_text"].toString(),
                        sentiment_score = data["sentiment_score"].toString().toDoubleOrNull() ?: 0.0
                    )
                    reviewsData.add(documentData)
                }
            }
        } catch (e: Exception) {
            Log.e("Firestore Error", "Error getting laundry documents: ", e)
        }
        Log.d("review", "getReviewFireStore: $reviewsData")
        return reviewsData
    }
    suspend fun getBookingFireStore(db: FirebaseFirestore): MutableList<Booking> {
        val bookingData = mutableListOf<Booking>()
        val collection = db.collection("booking")

        try {
            val snapshot = collection.get().await()

            for (document in snapshot.documents) {
                val data = document.data
                if (data != null) {
                    val documentData = Booking(
                        id = document.id,
                        laundry_id = data["laundry_id"].toString(),
                        review_author_id = data["review_author_id"].toString(),
                        delivery = false,
                        delivery_cost = data["delivery_cost"].toString().toInt(),
                        note = data["note"].toString(),
                        pick = false,
                        type = data["type"].toString(),
                        weight = data["weight"].toString().toDoubleOrNull() ?: 0.0
                    )
                    bookingData.add(documentData)
                }
            }
        } catch (e: Exception) {
            Log.e("Firestore Error", "Error getting laundry documents: ", e)
        }
        Log.d("reviews", "getBookingFireStore: $bookingData")
        return bookingData
    }
}