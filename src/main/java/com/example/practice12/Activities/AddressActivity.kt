package com.example.practice12.Activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.practice12.Adapters.AddressesAdapter
import com.example.practice12.DataBae.DBHelprt
import com.example.practice12.Models.*
import com.example.practice12.R
import com.example.practice12.SessionManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_address.*
import kotlinx.android.synthetic.main.activity_enter2.*
import kotlinx.android.synthetic.main.activity_sub_category.*
import org.json.JSONArray
import org.json.JSONObject


class AddressActivity : AppCompatActivity() {

    lateinit var dbHelprt: DBHelprt

    lateinit var adapterAddress : AddressesAdapter
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address)

        AddressActivity_add_address.setOnClickListener {
            //Toast.makeText(this,"Num = "+adapterAddress.getSelected(),Toast.LENGTH_LONG).show()


            intent = Intent(this,AddAddressActivity::class.java)
            startActivity(intent)
        }


        AddressActivity_get_info.setOnClickListener {

            sessionManager = SessionManager(this)

            var theUser = intent.getSerializableExtra("USER") as? User

            var jsonObjectUser = JSONObject()
            jsonObjectUser.put("email", sessionManager.getEmail())
            jsonObjectUser.put("mobile",sessionManager.getMobile())


//            var jsonObjectUser = JSONObject()
//            jsonObjectUser.put("email", theUser?.email)
//            jsonObjectUser.put("mobile",theUser?.phone)

          //  jsonObjectOderSummary.put("_id", "000001111")



          //  var sumOrder = intent.getSerializableExtra("ORDER_SUMMARY") as? orderSummary


            var jsonObjectOderSummary = JSONObject()

//            jsonObjectOderSummary.put("totalAmount", sumOrder?.totalAmount)
//            jsonObjectOderSummary.put("deliveryCharges", sumOrder?.deliveryCharges)
//
//            jsonObjectOderSummary.put("orderAmount", sumOrder?.orderAmount)
//            jsonObjectOderSummary.put("ourPrice", sumOrder?.ourPrice)
//            jsonObjectOderSummary.put("discount", sumOrder?.discount)


            var addr=  adapterAddress.mList.get(adapterAddress.getSelected())
            var jsonObjectShippingAddress = JSONObject()
            jsonObjectShippingAddress.put("pincode","43")
            jsonObjectShippingAddress.put("houseNo", addr.houseNum)
            jsonObjectShippingAddress.put("streetName", addr.streatName)
            jsonObjectShippingAddress.put("type", addr.type)
            jsonObjectShippingAddress.put("city", addr.city)



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


            jsonObject.put("userId","603c691772642f00171f30c2")






            var requestQueue = Volley.newRequestQueue(this)

            var theURLPostOrder = "http://grocery-second-app.herokuapp.com/api/orders"//"http://grocery-second-app.herokuapp.com/api/orders/603c691772642f00171f30c2"

            var jsonRequest = JsonObjectRequest(
                Request.Method.POST,
                theURLPostOrder,
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


        Log.d("abc","ttt")
    //    AddressActivity_get_info.setOnClickListener {
            Log.d("abc","mmm")


            var jsonObject = JSONObject()

            var requestQueue = Volley.newRequestQueue(this)

            var theURLPostAddress = "http://grocery-second-app.herokuapp.com/api/address/603c691772642f00171f30c2"

            var addressList = ArrayList<Address>()
            var jsonRequest = JsonObjectRequest(
                Request.Method.GET,
                theURLPostAddress,
                jsonObject,
                Response.Listener {
                    val array = it.getJSONArray("data") //as ArrayList<Address>()
                    if(array!=null) {
                        for (i in 0 until array.length()) {
                            val address: JSONObject = array.getJSONObject(i)
                            var city = address.getString("city").toString()
                            var streetName = address.getString("streetName").toString()
                            var houseNo = address.getString("houseNo").toString()
                            var type = address.getString("type").toString()
                            var pincode = address.getInt("pincode").toInt()

                            var _id = address.getString("_id").toString()

                            if(pincode==65445){
                                addressList.add(Address(streetName,houseNo,type,city,_id))
                            }else{
                                var theNewURLPostAddress =  "http://grocery-second-app.herokuapp.com/api/address/"+_id
                                var requestQueue2 = Volley.newRequestQueue(this)
                                var jsonObject2 = JSONObject()



                                var jsonRequest2 = JsonObjectRequest(
                                        Request.Method.DELETE,
                                        theNewURLPostAddress,
                                        jsonObject2,
                                        Response.Listener {
                                            Log.d("abc",it.toString())

                                        },
                                        Response.ErrorListener {
                                            Log.d("abc",it.toString())
                                        }
                                )
                                requestQueue2.add(jsonRequest2)
                            }
                        }
                    }
                    adapterAddress = AddressesAdapter(this)
                    adapterAddress.setData(addressList)
                    recycler_view_address.adapter = adapterAddress

                    recycler_view_address.layoutManager = LinearLayoutManager(this)
                    recycler_view_address.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
                    Log.d("abc",it.toString())

                },
                Response.ErrorListener {
                    Log.d("abc",it.toString())
                }

            )
            requestQueue.add(jsonRequest)
       // }

        // Enables Always-on
        //setAmbientEnabled()
    }
}