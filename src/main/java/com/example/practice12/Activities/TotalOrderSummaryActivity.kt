package com.example.practice12.Activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.practice12.Adapters.AddressesAdapter
import com.example.practice12.DataBae.DBHelprt
import com.example.practice12.Models.Address
import com.example.practice12.Models.EndPoints
import com.example.practice12.R
import com.example.practice12.SessionManager
import kotlinx.android.synthetic.main.activity_total_order_summary.*
import org.json.JSONArray
import org.json.JSONObject
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class TotalOrderSummaryActivity : AppCompatActivity() {

    lateinit var dbHelprt: DBHelprt

    lateinit var adapterAddress : AddressesAdapter
    lateinit var sessionManager: SessionManager



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_total_order_summary)


        init()
    }


    private fun init(){
        sessionManager = SessionManager(this)
        dbHelprt = DBHelprt(this)
        adapterAddress = AddressesAdapter(this)


        val now = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MMM-dd HH:mm:ss.SSS")
        val formatted = now.format(formatter)

        final_order_name.text = "Name: "+sessionManager.getName()
        final_order_phone.text = "Phone: "+sessionManager.getMobile()
        final_order_date.text ="Date: "+formatted




        var addr=  intent.getSerializableExtra("ADDRESS") as? Address //adapterAddress.mList.get(adapterAddress.getSelected())

        final_order_address.text = "Address:\nCity: ${addr?.city}\nStreat: ${addr?.streatName}\nHouse num: ${addr?.houseNum}\nType: ${addr?.type}"


        var sharedPreferences = getSharedPreferences("my_pref4", Context.MODE_PRIVATE)

        var deliveryCharges = sharedPreferences.getString("deliveryCharges",null)
        var discount = sharedPreferences.getString("discount",null)

        var orderAmount = sharedPreferences.getString("orderAmount",null)

        var ourPrice = sharedPreferences.getString("ourPrice",null)

        var totalAmount = sharedPreferences.getString("totalAmount",null)


        final_order_total_sum.text = "Total amount: ${orderAmount}$"
        final_order_discount.text = "Discount: ${discount}$"
        final_order_deliverly_charges.text = "Deliverly charges: ${deliveryCharges}$"
        final_order_amount_to_pay.text = "Amount to pay: ${ourPrice}$"



        final_order_button_accept.setOnClickListener {
            makeUploading(addr)


        }


        final_order_button_coupon.setOnClickListener {
            startActivity(Intent(this,CouponActivity::class.java))

        }

    }



    private fun makeUploading(addr:Address?){

            var jsonObjectUser = JSONObject()
            jsonObjectUser.put("email", sessionManager.getEmail())
            jsonObjectUser.put("mobile",sessionManager.getMobile())


//            var jsonObjectUser = JSONObject()
//            jsonObjectUser.put("email", theUser?.email)
//            jsonObjectUser.put("mobile",theUser?.phone)

            //  jsonObjectOderSummary.put("_id", "000001111")



            //  var sumOrder = intent.getSerializableExtra("ORDER_SUMMARY") as? orderSummary


            var jsonObjectOderSummary = JSONObject()


            var sharedPreferences = getSharedPreferences("my_pref4", Context.MODE_PRIVATE)

            var deliveryCharges = sharedPreferences.getString("deliveryCharges",null)
            var discount = sharedPreferences.getString("discount",null)

            var orderAmount = sharedPreferences.getString("orderAmount",null)

            var ourPrice = sharedPreferences.getString("ourPrice",null)

            var totalAmount = sharedPreferences.getString("totalAmount",null)



            jsonObjectOderSummary.put("totalAmount", totalAmount)
            jsonObjectOderSummary.put("deliveryCharges", deliveryCharges)

            jsonObjectOderSummary.put("orderAmount", orderAmount)
            jsonObjectOderSummary.put("ourPrice", ourPrice)
            jsonObjectOderSummary.put("discount", discount)


            var jsonObjectShippingAddress = JSONObject()
            jsonObjectShippingAddress.put("pincode","43")
            jsonObjectShippingAddress.put("houseNo", addr?.houseNum)
            jsonObjectShippingAddress.put("streetName", addr?.streatName)
            jsonObjectShippingAddress.put("type", addr?.type)
            jsonObjectShippingAddress.put("city", addr?.city)



//            var jsonObjectPrpduct = JSONObject()
//            jsonObjectPrpduct.put("mrp", 55)
//            jsonObjectPrpduct.put("image", "asdfads.jpg")
//
//            jsonObjectPrpduct.put("quantity", 11)
//
//            jsonObjectPrpduct.put("price", 155)
//            jsonObjectPrpduct.put("productName", "THE product")



            dbHelprt = DBHelprt(this)
            var mList = dbHelprt.getAllProduccts()

            var jsonArrayProducts = JSONArray()



            for(item in mList){

                var jsonObjectPrpduct = JSONObject()
                jsonObjectPrpduct.put("mrp", item.mrp)
                jsonObjectPrpduct.put("image", item.image)

                jsonObjectPrpduct.put("quantity", item.amount)

                jsonObjectPrpduct.put("price", item.price)
                jsonObjectPrpduct.put("productName", item.name)

                jsonArrayProducts.put(jsonObjectPrpduct )


            }



            var jsonObject = JSONObject()
            //  jsonObject.put("_id","534534")

            jsonObject.put("orderSummary",jsonObjectOderSummary)

            jsonObject.put("user",jsonObjectUser)
            jsonObject.put("shippingAddress",jsonObjectShippingAddress)
            jsonObject.put("products",jsonArrayProducts)




            jsonObject.put("userId",EndPoints.getUserID())










            var requestQueue = Volley.newRequestQueue(this)

           // var theURLPostOrder = "http://grocery-second-app.herokuapp.com/api/orders"//"http://grocery-second-app.herokuapp.com/api/orders/603c691772642f00171f30c2"

            var jsonRequest = JsonObjectRequest(
                Request.Method.POST,
                EndPoints.getOrders(),
                jsonObject,
                Response.Listener {
                    Log.d("abc",it.toString())

                    intent = Intent(this,SuccessPayActivity::class.java)
                    startActivity(intent)

                },
                Response.ErrorListener {
                    Log.d("abc",it.toString())
                }

            )
            requestQueue.add(jsonRequest)













    }

}