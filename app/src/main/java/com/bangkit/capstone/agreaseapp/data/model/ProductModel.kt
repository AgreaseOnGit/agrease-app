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
    val id: String,

    @field:ColumnInfo(name = "image")
    @field:SerializedName("image")
    val image: String,

    @field:ColumnInfo(name = "rating")
    @field:SerializedName("rating")
    val rating: String,

    @field:ColumnInfo(name = "stock")
    @field:SerializedName("stock")
    val stock: String,

    @field:ColumnInfo(name = "category")
    @field:SerializedName("category")
    val category: String,

    @field:ColumnInfo(name = "price")
    @field:SerializedName("price")
    val price: String,
)