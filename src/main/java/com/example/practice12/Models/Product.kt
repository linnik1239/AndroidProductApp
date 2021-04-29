package com.example.myapplication.models

import java.io.Serializable
data class Product(
    var _id: String,
    var name: String,
    var description: String,
    var image: String,
    var price :Double,
    var mrp: Double,
    var amount: Int
): Serializable{
    companion object{
        const val KEY_PRODUCT_ID = "productId"
    }
}

