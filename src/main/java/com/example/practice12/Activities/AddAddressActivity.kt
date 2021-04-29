package com.example.practice12.Activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.practice12.R
import kotlinx.android.synthetic.main.activity_add_address.*
import kotlinx.android.synthetic.main.activity_address.*
import kotlinx.android.synthetic.main.activity_address.AddressActivity_add_address
import org.json.JSONObject

class AddAddressActivity : AppCompatActivity() {

    var type = "Office"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_address)

        AddAddressActivity_add_address.setOnClickListener {
            var streatName = AddAddressActivity_streat_name.text.toString()
            var houseNum = AddAddressActivity_house_num.text.toString()
           // var type = AddAddressActivity_type.text.toString()
            var city = AddAddressActivity_city.text.toString()


//            var sharedPreferances = getSharedPreferences("my_pref2", Context.MODE_PRIVATE)
//            var editor = sharedPreferances.edit()
//            editor.putString("STREAT_NAME",streatName)
//            editor.putString("HOUSE_NUM",houseNum)
//
//            editor.putString("TYPE",type)
//
//            editor.putString("CITY",city)
//            editor.commit()




//            {
//                "error": false,
//                "message": "address added successfully",
//                "data": {
//                "_id": "608715c2420cc20017dbf01d",
//                "pincode": 3,
//                "city": "c",
//                "houseNo": "c",
//                "streetName": "c",
//                "type": "c",
//                "userId": "603c691772642f00171f30c2",
//                "__v": 0
//            }
//            }

            var jsonObject = JSONObject()
            jsonObject.put("houseNo",houseNum)
            jsonObject.put("streetName",streatName)
            jsonObject.put("city",city)

            jsonObject.put("type",type)
            jsonObject.put("userId","603c691772642f00171f30c2")
            jsonObject.put("pincode",65445)


            var theURLPostAddress = "http://grocery-second-app.herokuapp.com/api/address"

            var requestQueue = Volley.newRequestQueue(this)
            var jsonRequest = JsonObjectRequest(
                Request.Method.POST,
                theURLPostAddress,
                jsonObject,
                Response.Listener {
                    Log.d("abc", it.toString())
                    startActivity(Intent(this,AddressActivity::class.java))
                },
                Response.ErrorListener {
                    Log.d("abc", it.toString())
                }

            )
            requestQueue.add(jsonRequest)










        }

        // Enables Always-on
        //setAmbientEnabled()
    }


    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked

            // Check which radio button was clicked
            when (view.getId()) {
                R.id.radio_home ->
                    if (checked) {
                        type =  radio_home.text.toString()
                        // Pirates are the best
                    }
                R.id.radio_office ->
                    if (checked) {
                        type = radio_office.text.toString()
                    }
                else ->{
                    if (checked) {// Ninjas rule
                        type = radio_other.text.toString()
                    }
                }
            }
        }
    }
}