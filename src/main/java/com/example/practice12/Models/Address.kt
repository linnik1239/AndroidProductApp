package com.example.practice12.Models


import java.io.Serializable


data class Address(var streatName:String,
var houseNum:String,
var type:String,
var city:String,
var _id:String): Serializable{
    companion object{
        const val KEY_PRODUCT_ID = "AddressId"
    }
}
