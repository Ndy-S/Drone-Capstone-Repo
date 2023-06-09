package com.example.dropdone.model

data class Laundry(
    val address: String? = null,
    val email: String? = null,
    val icon: String? = null,
    val latitude: String? = null,
    val laundry_name: String? = null,
    val longitude: String? = null,
    val opening_hours: String? = null,
    val phone_number: String? = null,
    val price: String? = null,
    val rating: Int? = null
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