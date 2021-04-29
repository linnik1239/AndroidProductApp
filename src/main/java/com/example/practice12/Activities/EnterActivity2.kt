package com.example.practice12.Activities

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.GridLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import com.android.volley.Request
import com.android.volley.Response
import com.example.practice12.Activities.EnterActivity2
import com.example.practice12.Adapters.AdapterCategory
import com.example.practice12.Models.Category
import com.example.practice12.Models.CategoryResponse
import com.example.practice12.Models.Product
import com.example.practice12.Models.User
import com.example.practice12.R
import com.example.practice12.SessionManager
import com.google.android.material.navigation.NavigationView

import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_enter2.*
import kotlinx.android.synthetic.main.app_bar.*

class EnterActivity2 : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    var mList: ArrayList<Category> = ArrayList()

    lateinit var sessionManager: SessionManager

    lateinit var adapterCategory: AdapterCategory

    private lateinit var drawLayout: DrawerLayout
    private lateinit var navView: NavigationView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter2)
        init()



    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        return true;
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_cart -> {

                intent = Intent(this,ProductCartListActivity::class.java)


                sessionManager = SessionManager(this)
                var theUser = User(sessionManager.getName(),sessionManager.getEmail(),"00000",sessionManager.getMobile())



                //Toast.makeText(this,theUser.toString(),Toast.LENGTH_LONG).show()
                Log.d("abc", theUser.toString())

                //var theUser = User("Albert","Maccabi@gmail.con","6432","234234")

                intent.putExtra("USER",theUser)

                startActivity(intent)

                Toast.makeText(applicationContext, "Cart", Toast.LENGTH_SHORT).show()
            }
            R.id.menu_settings -> Toast.makeText(applicationContext, "Settings", Toast.LENGTH_SHORT).show()
            R.id.menu_logout -> Toast.makeText(applicationContext, "Logout", Toast.LENGTH_SHORT).show()
        }
        return true
    }

    private fun setupToolbar(){
        var toolbar = toolbar
        toolbar.title = "Categoty"
        setSupportActionBar(toolbar)

        //app_bar
    }

    private fun init(){
        drawLayout = drawer_layout
        navView = nav_view

        var toggle = ActionBarDrawerToggle(
                this, drawLayout, toolbar, 0,0
        )
        drawLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)

        setupToolbar()
        getData()
        adapterCategory = AdapterCategory(this)
        recycler_view.adapter = adapterCategory
        recycler_view.layoutManager = GridLayoutManager(this,2)

        Picasso
            .get()
            .load("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSUo8ynBItVwCdYRCNj9cibTm2nud8gACmfsA&usqp=CAU")
            .placeholder(R.drawable.ic_launcher_background)
            .error(R.drawable.ic_launcher_background)
            .into(image_view_banner)

    }
    private fun getData(){
        var requestQueue = Volley.newRequestQueue(this)
        var request = StringRequest(
            Request.Method.GET,
            "http://grocery-second-app.herokuapp.com/api/category",
            Response.Listener {
                var gson = Gson()
                var categoryResponse = gson.fromJson(it, CategoryResponse::class.java)
                adapterCategory.setData(categoryResponse.data)
                progress_bar.visibility = View.GONE
            },
            Response.ErrorListener {
                Log.d("abc", it.message.toString())
            }
        )
        requestQueue.add(request)

    }



    private fun logoutDialoge() {
        var builder = AlertDialog.Builder(this)
        builder.setTitle("Logout")
        builder.setMessage("Are you sure you wan tto logout")
        builder.setPositiveButton("Yes", object: DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {
                Toast.makeText(applicationContext, "Logging out...", Toast.LENGTH_SHORT).show()
            }
        })
        builder.setNegativeButton("No", object : DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {
                dialog?.dismiss()
            }

        })
        var alertDialog = builder.create()
        alertDialog.show()
    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.item_account -> Toast.makeText(applicationContext, "account", Toast.LENGTH_SHORT).show()
            R.id.item_logout -> logoutDialoge()
        }
        drawLayout.closeDrawer(GravityCompat.START)
        return true
    }
    override fun onBackPressed() {
        if(drawLayout.isDrawerOpen(GravityCompat.START)){
            drawLayout.closeDrawer(GravityCompat.START)
        }else{
            super.onBackPressed()
        }
    }

}