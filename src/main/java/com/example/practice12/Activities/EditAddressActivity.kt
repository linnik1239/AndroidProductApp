package com.example.practice12.Activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.practice12.Models.Address
import com.example.practice12.R
import kotlinx.android.synthetic.main.activity_edit_address.*
import org.json.JSONObject

class EditAddressActivity : AppCompatActivity() {
    var address_id:String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_address)

        init()

    }


    fun init(){
        var address = intent.getSerializableExtra("ADDRESS") as? Address
        home_num.setText(address?.houseNum)
        streat_name.setText(address?.streatName)
        city.setText(address?.city)
        type.setText(address?.type)
        address_id = address?._id

        update.setOnClickListener {
            var streat = streat_name.text.toString()
            var house_num = home_num.text.toString()
            var city = city.text.toString()
            var type = type.text.toString()


            var newAddress:Address? = Address(streat,house_num,type,city,address_id.toString())


            var theNewURLPostAddress =  "http://grocery-second-app.herokuapp.com/api/address/"+address_id

            var requestQueue2 = Volley.newRequestQueue(this)
            var jsonObject2 = JSONObject()




            jsonObject2.put("houseNo",house_num)
            jsonObject2.put("streetName",streat)
            jsonObject2.put("city",city)

            jsonObject2.put("type",type)
            jsonObject2.put("userId","603c691772642f00171f30c2")
            jsonObject2.put("pincode",65445)




            var jsonRequest2 = JsonObjectRequest(
                Request.Method.PUT,
                theNewURLPostAddress,
                jsonObject2,
                Response.Listener {
                    Log.d("abc", it.toString())


                    startActivity(Intent(this,AddressActivity::class.java))

                },
                Response.ErrorListener {
                    Log.d("abc", it.toString())
                }
            )
            requestQueue2.add(jsonRequest2)




        }
    }

}