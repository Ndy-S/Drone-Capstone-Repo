package com.example.dropdone.model

data class Laundry(
    val id: String = "",
    val address: String = "",
    val email: String = "",
    val icon: String = "",
    val latitude: String = "",
    val laundry_name: String = "",
    val longitude: String = "",
    val opening_hours: String = "",
    val phone_number: String = "",
    val price: String = "",
    val rating: Double = 0.0
)

data class Reviews(
    val laundry_id: String? = null,
    val review_author_id: String? = null,
    val review_rating: Int? = null,
    val review_text: String? = null,
    val sentiment_score: Int? = null
)

data class User(
    val id: String? = null,
    val address: String? = null,
    val email: String? = null,
    val password: String? = null,
    val pref_loc: Boolean = false,
    val pref_rating: Boolean = false,
    val username: String? = null
)