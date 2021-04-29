package com.example.practice12.Models
import java.io.Serializable

data class User(
        var name :String? = null,
        var email: String?=null,
        var password: String? = null,
        var phone: String?=null
): Serializable{
        companion object{
                const val KEY_PRODUCT_ID = "uderId"
        }
}