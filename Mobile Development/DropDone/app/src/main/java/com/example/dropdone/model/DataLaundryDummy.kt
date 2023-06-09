package com.example.dropdone.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataLaundryDummy(
    val id: String,
    val title: String,
    val location: String,
    val phone: String
): Parcelable
