package com.example.practice12.Models

class EndPoints {

    companion object{
        private const val URL_CATEGORY = "category"
        private const val URL_SUB_CATEGORY = "subcategory/"
        private const val UEL_PRODUCT_BY_SUB_ID = "product/"
        private const val URL_REGISTER = "auth/register"
        private const val URL_LOGIN = "auth/login"

        private const val BASE_URL = "http://grocery-second-app.herokuapp.com/api/"
        fun getCategort():String{
            return BASE_URL+URL_CATEGORY
        }

        fun getSubCategoryById(catId :Int):String
        {
            return BASE_URL+URL_SUB_CATEGORY+catId.toString()
        }
        fun getProductBySubId(subId :Int):String
        {
            return BASE_URL+UEL_PRODUCT_BY_SUB_ID+subId.toString()
        }


        fun getRegister():String{
            return BASE_URL+URL_REGISTER
        }
        fun getLogin():String{
            return BASE_URL+URL_LOGIN
        }



    }

}