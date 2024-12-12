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

    @field:ColumnInfo(name = "product_name")
    @field:SerializedName("product_name")
    val productName: String,

    @field:ColumnInfo(name = "product_desc")
    @field:SerializedName("product_desc")
    val productDescription: String,

    @field:ColumnInfo(name = "rating")
    @field:SerializedName("rating")
    val rating: Double,

    @field:ColumnInfo(name = "seller_id")
    @field:SerializedName("seller_id")
    val sellerId: String,

    @field:ColumnInfo(name = "price")
    @field:SerializedName("price")
    val price: Long,

    @field:ColumnInfo(name = "stock")
    @field:SerializedName("stock")
    val stock: Long,

    @field:ColumnInfo(name = "image")
    @field:SerializedName("image")
    val image: String,

    @field:ColumnInfo(name = "category")
    @field:SerializedName("category")
    val category: String,
)