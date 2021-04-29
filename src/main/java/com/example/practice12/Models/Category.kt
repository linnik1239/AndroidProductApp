package com.example.myapplication.models

data class Category2(
    var catId: Int,
    var catName: String
){
    companion object{
        const val KEY_CAT_ID = "catId"
    }
}