package com.bangkit.capstone.agreaseapp.data.model

import com.google.gson.annotations.SerializedName

data class UserModel(
    @field:SerializedName("uid")
    val uid: String,
    @field:SerializedName("email")
    val email: String,
    @field:SerializedName("name")
    val nama: String,
    @field:SerializedName("phone")
    val phone: String,
    @field:SerializedName("address")
    val address: String,
    @field:SerializedName("imageUrl")
    val imageUrl: String,
    @field:SerializedName("role")
    val role: String,
//    @field:SerializedName("createdAt")
//    val createdAt: Created,
    @field:SerializedName("isVerified")
    val isVerified: Boolean,
)

data class Created(
    @field:SerializedName("_seconds")
    val _seconds: Int,
    @field:SerializedName("_nanoseconds")
    val _nanoseconds: Int,
)

data class Updated(
    @field:SerializedName("_seconds")
    val _seconds: Int,
    @field:SerializedName("_nanoseconds")
    val _nanoseconds: Int,
)