package com.example.practice12.Models

data class TheData(
    val count: Int,
    val `data`: List<Data2>,
    val error: Boolean
)

data class Data2(
    val __v: Int,
    val _id: String,
    val date: String,
    val orderSummary: OrderSummary,
    val products: List<Product>,
    val shippingAddress: ShippingAddress,
    val user: User2,
    val userId: String
)

data class OrderSummary(
    val _id: String,
    val deliveryCharges: Int,
    val discount: Int,
    val orderAmount: Int,
    val ourPrice: Int,
    val totalAmount: Int
)

data class Product(
    val _id: String,
    val image: String,
    val mrp: Int,
    val price: Int,
    val productName: String,
    val quantity: Int
)

data class ShippingAddress(
    val _id: String,
    val city: String,
    val houseNo: String,
    val pincode: Int,
    val streetName: String,
    val type: String
)

data class User2(
    val _id: String,
    val email: String,
    val mobile: String
)