package com.bangkit.capstone.agreaseapp.data.model

import com.google.gson.annotations.SerializedName

data class UserModel(
    @field:SerializedName("uid")
    val uid: String,
    @field:SerializedName("email")
    val email: String,
    @field:SerializedName("nama")
    val nama: String,
    @field:SerializedName("phone")
    val phone: String,
    @field:SerializedName("address")
    val address: String,
    @field:SerializedName("role")
    val role: String,
    @field:SerializedName("photo")
    val photo: String,
    @field:SerializedName("isVerified")
    val isVerified: Boolean,
)