package com.example.practice12.Activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.practice12.Activities.EnterActivity2
import com.example.practice12.Models.EndPoints
import com.example.practice12.Models.User
import com.example.practice12.R
import com.example.practice12.SessionManager
import com.google.gson.Gson

import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject


class LoginActivity : AppCompatActivity() {
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        sessionManager = SessionManager(this)
        init()
    }
    private fun init() {
        button1_L.setOnClickListener {
            var email = edit_text_email_L.text.toString()
            var password = edit_text_password_L.text.toString()
            var jsonObject = JSONObject()
            jsonObject.put("email", email)
            jsonObject.put("password", password)

            var requestQueue = Volley.newRequestQueue(this)
            var jsonRequest = JsonObjectRequest(
                Request.Method.POST,
                EndPoints.getLogin(),
                jsonObject,
                Response.Listener {
                   // Toast.makeText(applicationContext,  it.toString(), Toast.LENGTH_SHORT)
                   //     .show()

                    Log.d("abc", it.toString())
                    sessionManager.setLogin(true)


//                    var firstname:String? = jsonObject.get("firstName") as? String
  //                  var email:String? = jsonObject.get("email") as? String
 //                   var mobile:String?  = jsonObject.get("mobile") as? String

           //         var user = User(firstname,email,
       //             "0000000", mobile)


                    intent = Intent(this,EnterActivity2::class.java)

  //                  intent.putExtra("USER",user)
                    startActivity(intent)
                },
                Response.ErrorListener {
                    //Toast.makeText(applicationContext, it.toString(), Toast.LENGTH_SHORT).show()
                    Log.d("abc", it.toString())
                }
            )
            requestQueue.add(jsonRequest)
        }


    }
}