package com.example.practice12.Models


import java.io.Serializable

data class orderSummary(
    val _id: String,
    val deliveryCharges: Double,
    var discount: Double,
    val orderAmount: Double,
    val ourPrice: Double,
    val totalAmount: Double
): Serializable{
    companion object{
        const val KEY_PRODUCT_ID = "orderSummaryId"
    }
}