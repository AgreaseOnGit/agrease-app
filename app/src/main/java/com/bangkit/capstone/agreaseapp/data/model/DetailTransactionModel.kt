package com.bangkit.capstone.agreaseapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.sql.Date

@Entity(tableName = "Detail")
data class DetailTransactionModel (

//    @field:PrimaryKey
//    @field:ColumnInfo(name = "id")
//    @field:SerializedName("id")
//    val id: Int,

    @field:ColumnInfo(name = "id_transaction")
    @field:SerializedName("id_transaction")
    val id_transaction: String,

    @field:ColumnInfo(name = "id_buyer")
    @field:SerializedName("id_buyer")
    val id_buyer: String,

    @field:ColumnInfo(name = "id_product")
    @field:SerializedName("id_product")
    val id_product: String,

    @field:ColumnInfo(name = "payment_method")
    @field:SerializedName("payment_method")
    val payment_method: String,

    @field:ColumnInfo(name = "status")
    @field:SerializedName("status")
    val status: String,

    @field:ColumnInfo(name = "quantity")
    @field:SerializedName("quantity")
    val quantity: Int,

    @field:ColumnInfo(name = "total_payment")
    @field:SerializedName("total_payment")
    val total_payment: Int,

    @field:ColumnInfo(name = "date")
    @field:SerializedName("date")
    val date: Date
)