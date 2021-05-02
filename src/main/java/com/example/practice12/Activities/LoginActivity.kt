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

                    var ob = it.getJSONObject("user")

                    var firstName = ob.getString("firstName").toString()
                    var _id = ob.getString("_id").toString()

                    var email = ob.getString("email").toString()
                    var mobile = ob.getString("mobile").toString()



                    Log.d("abc",_id)
                    var user: User = User(firstName,email,password,mobile)

                    var sessionManager = SessionManager(this)
                    sessionManager.register(user)





                    EndPoints.setUserID(_id)

                    sessionManager.setLogin(true)

                    intent = Intent(this,EnterActivity2::class.java)

                    startActivity(intent)
                },
                Response.ErrorListener {
                    Log.d("abc", it.toString())
                }
            )
            requestQueue.add(jsonRequest)
        }


    }
}