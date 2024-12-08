package com.bangkit.capstone.agreaseapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Transaction")
data class TransactionModel(

//    @field:PrimaryKey
//    @field:ColumnInfo(name = "id")
//    @field:SerializedName("id")
//    val id: Int,

    @field:ColumnInfo(name = "product_name")
    @field:SerializedName("product_name")
    val product_name: String,

    @field:ColumnInfo(name = "image")
    @field:SerializedName("image")
    val image: String,

    @field:ColumnInfo(name = "total_price")
    @field:SerializedName("total_price")
    val total_price: Int,

    @field:ColumnInfo(name = "shop_name")
    @field:SerializedName("shop_name")
    val shop_name: String,

    @field:ColumnInfo(name = "status")
    @field:SerializedName("status")
    val status: String
)