package com.example.practice12.Models

data class SubSubCategoryResponse(
    val count: Int,
    val `data`: List<SubSubCategory>,
    val error: Boolean
)

data class SubSubCategory(
    val __v: Int,
    val _id: String,
    val catId: Int,
    val created: String,
    val description: String,
    val image: String,
    val mrp: Double,
    val position: Int,
    val price: Double,
    val productName: String,
    val quantity: Int,
    val status: Boolean,
    val subId: Int,
    val unit: String
)