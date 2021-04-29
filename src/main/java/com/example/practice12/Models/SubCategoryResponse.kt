package com.example.practice12.Models

data class SubCategoryResponse(
    val count: Int,
    val `data`: List<ResponseData>,
    val error: Boolean
)

data class ResponseData(
    val __v: Int,
    val _id: String,
    val catId: Int,
    val position: Int,
    val status: Boolean,
    val subDescription: String,
    val subId: Int,
    val subImage: String,
    val subName: String
)