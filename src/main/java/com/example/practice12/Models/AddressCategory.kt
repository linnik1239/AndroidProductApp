package com.example.practice12.Models

data class AddressCategpry(
    val count: Int,
    val `data`: List<Data>,
    val error: Boolean
)

data class Data(
    val __v: Int,
    val _id: String,
    val city: String,
    val houseNo: String,
    val pincode: Int,
    val streetName: String,
    val type: String,
    val userId: String
)