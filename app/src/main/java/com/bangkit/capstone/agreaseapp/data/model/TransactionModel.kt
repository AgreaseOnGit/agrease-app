package com.bangkit.capstone.agreaseapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.sql.Date

@Entity(tableName = "transaction")
data class TransactionModel(

    @field:PrimaryKey
    @field:ColumnInfo(name = "id")
    @field:SerializedName("id")
    val id: String,

    @field:ColumnInfo(name = "idBuyer")
    @field:SerializedName("idBuyer")
    val idBuyer: String,

    @field:ColumnInfo(name = "product")
    @field:SerializedName("product")
    val product: ProductModel,

    @field:ColumnInfo(name = "method")
    @field:SerializedName("method")
    val method: String,

    @field:ColumnInfo(name = "quantity")
    @field:SerializedName("quantity")
    val quantity: Int,

    @field:ColumnInfo(name = "total")
    @field:SerializedName("total")
    val total: Int,

    @field:ColumnInfo(name = "date")
    @field:SerializedName("date")
    val date: Date,

    @field:ColumnInfo(name = "status")
    @field:SerializedName("status")
    val status: String,
)