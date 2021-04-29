package com.example.practice12.Models

import java.io.Serializable

data class SpecificProduct(
    var name:String,
    var description:String,
    var image:String)
 :Serializable{
    companion object{
        const val KEY_SPECIFIC_PRODUCT = "specific_product"
    }
}