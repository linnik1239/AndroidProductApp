package com.example.practice12

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.GridLayout
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import com.android.volley.Request
import com.android.volley.Response
import com.example.practice12.Activities.EnterActivity2
import com.example.practice12.Activities.LoginActivity
import com.example.practice12.Activities.RegisterActivity
import com.example.practice12.Adapters.AdapterCategory
import com.example.practice12.Models.Category
import com.example.practice12.Models.CategoryResponse
import com.example.practice12.R

import com.google.gson.Gson
import com.squareup.picasso.Picasso
import org.json.JSONArray

class MainActivity : AppCompatActivity() {
    var mList: ArrayList<Category> = ArrayList()
    lateinit var adapterCategory: AdapterCategory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


       // intent = Intent(this,EnterActivity2::class.java)
      //  startActivity(intent)

        init()
    }
    private fun init() {
        button1.setOnClickListener{
            intent = Intent(this,
                RegisterActivity::class.java)
            startActivity(intent)
        }
        button2.setOnClickListener{
            intent = Intent(this,
                LoginActivity::class.java)
            startActivity(intent)
        }
    }
}