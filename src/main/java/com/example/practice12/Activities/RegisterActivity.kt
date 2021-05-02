package com.example.practice12.Activities

import android.Manifest
import android.Manifest.permission
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
//import androidx.security.crypto.EncryptedSharedPreferences
//import androidx.security.crypto.MasterKeys
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.practice12.Models.EndPoints
import com.example.practice12.Models.User
import com.example.practice12.Models.UserList
import com.example.practice12.R
import com.example.practice12.SessionManager

import kotlinx.android.synthetic.main.activity_register.*
import org.json.JSONObject


class RegisterActivity : AppCompatActivity() {
    private var RECORD_REQUEST_CODE = 101

    var theName:String = ""
    var theEmail:String = ""
    var thePassword:String = ""
    var thePhone:String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        init()

    }


    private fun init(){

      //  setupPermissionsAudio()
        var res: Boolean
        val result =
            ContextCompat.checkSelfPermission(this, permission.ACCESS_FINE_LOCATION)
        val result1 =
            ContextCompat.checkSelfPermission(this, permission.ACCESS_COARSE_LOCATION)
        if (result == PackageManager.PERMISSION_GRANTED || result1 == PackageManager.PERMISSION_GRANTED) {
            res=true
        } else {
            res=false
        }


        button1_R.setOnClickListener {


             theName = edit_text_name_R.text.toString()
             thePassword = edit_text_password_R.text.toString()
             theEmail = edit_text_email_R.text.toString()
             thePhone = edit_text_phone_R.text.toString()
            UserList.addUSer(
                theName,
                thePassword
            )

            var jsonObject = JSONObject()
            jsonObject.put("firstName", theName)
            jsonObject.put("email", theEmail)
            jsonObject.put("password", thePassword)
            jsonObject.put("mobile", thePhone)



           // var theURL_Register = "http://grocery-second-app.herokuapp.com/api/auth/register"

            var requestQueue = Volley.newRequestQueue(this)
            var jsonRequest = JsonObjectRequest(
                Request.Method.POST,
                EndPoints.getRegister(),
                jsonObject,
                Response.Listener {
                    Log.d("abc", it.toString())

                    var user: User = User(theName,theEmail,thePassword,thePhone)

                    var sessionManager = SessionManager(this)
                    sessionManager.register(user)


                    intent = Intent(this, LoginActivity::class.java)

                    startActivity(intent)
                },
                Response.ErrorListener {
                    Log.d("abcE", it.toString())

                  //  Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
                }
            )
            requestQueue.add(jsonRequest)

        }

    }

    private fun setupPermissionsAudio() {
        val permission = ContextCompat.checkSelfPermission(this,
            Manifest.permission.RECORD_AUDIO)

        if (permission != PackageManager.PERMISSION_GRANTED) {
            Log.i("TAG", "Permission to record denied")
            makeRequestAudio()
        }
    }
    private fun setupPermissionCamera(){


        val permission = ContextCompat.checkSelfPermission(this,
            Manifest.permission.CAMERA)

        if(permission != PackageManager.PERMISSION_GRANTED){
            Log.i("TAG","Permission to record denied.")
            makeRequestCamera()
        }
    }

    private fun makeRequestCamera(){
        ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
            RECORD_REQUEST_CODE)
        Log.i("TAG","After")
    }
    private fun makeRequestAudio() {
        ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.RECORD_AUDIO),
            RECORD_REQUEST_CODE)
    }

}


