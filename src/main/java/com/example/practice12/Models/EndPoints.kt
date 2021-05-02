package com.example.practice12.Models

class EndPoints {

    companion object{
        private const val URL_CATEGORY = "category"
        private const val URL_SUB_CATEGORY = "subcategory/"
        private const val UEL_PRODUCT_BY_SUB_ID = "product/"
        private const val URL_REGISTER = "auth/register"
        private const val URL_LOGIN = "auth/login"
        private const val URL_ADDRESS = "address"
        private const val URL_ORDERS = "orders"
        private const val URL_IMAGE ="http://rjtmobile.com/grocery/images/"

                               //"603c691772642f00171f30c2"
        private var ID = "603c691772642f00171f30c2"

        fun setUserID(theID:String){
            ID = theID
        }

        private const val BASE_URL = "http://grocery-second-app.herokuapp.com/api/"

        fun getUserID():String{
            return ID
        }
        fun getOrders():String{
            return BASE_URL+URL_ORDERS
        }
        fun getOrdersUID():String{
            return BASE_URL+URL_ORDERS+"/"+ID
        }


        fun getImageURL():String{
            return URL_IMAGE
        }

        fun getAddressAddId(addrID:String?):String{
            return BASE_URL+URL_ADDRESS+"/"+addrID
        }

        fun getOnlyAddress():String{
            return BASE_URL+URL_ADDRESS
        }

        fun getAddress():String{
            return BASE_URL+URL_ADDRESS+"/"+ID
        }

        fun getCategory():String{
            return BASE_URL+URL_CATEGORY
        }
// "http://grocery-second-app.herokuapp.com/api/auth/register"
        fun getSubCategoryById(catId :Int?):String
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