package com.bangkit.capstone.agreaseapp.data.model

import com.google.gson.annotations.SerializedName

data class VerifyModel(
    @field:SerializedName("authEmail")
    val authEmail: String,

    @field:SerializedName("authUID")
    val authUID: String,
)