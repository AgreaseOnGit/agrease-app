package com.bangkit.capstone.agreaseapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "products")
data class ProductModel(
    @field:PrimaryKey
    @field:ColumnInfo(name = "id")
    @field:SerializedName("id")
    val id: Int,

    @field:ColumnInfo(name = "name")
    @field:SerializedName("name")
    val name: String,

    @field:ColumnInfo(name = "price")
    @field:SerializedName("price")
    val price: String,

    @field:ColumnInfo(name = "image")
    @field:SerializedName("image")
    val image: String,

    @field:ColumnInfo(name = "rating")
    @field:SerializedName("rating")
    val rating: String,
)