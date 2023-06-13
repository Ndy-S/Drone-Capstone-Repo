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
    val id: String = "",
    val laundry_id: String = "",
    val review_author_id: String = "",
    val review_rating: Int = 0,
    val review_text: String = "",
    val sentiment_score: Double = 0.0
)

data class User(
    val id: String = "",
    val address: String = "",
    val email: String = "",
    val password: String = "",
    val pref_loc: Boolean = false,
    val pref_rating: Boolean = false,
    val username: String = ""
)

data class Booking(
    val id: String = "",
    val laundry_id: String = "",
    val review_author_id: String = "",
    var delivery: Boolean = false,
    val delivery_cost: Int = 0,
    val note: String = "",
    val pick: Boolean = false,
    val type: String = "",
    val weight: Double = 0.0
)