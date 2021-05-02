package com.example.practice12.Activities

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.core.view.MenuItemCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import com.android.volley.Request
import com.android.volley.Response
import com.example.practice12.Activities.EnterActivity2
import com.example.practice12.Adapters.AdapterCategory
import com.example.practice12.DataBae.DBHelprt
import com.example.practice12.Models.*
import com.example.practice12.R
import com.example.practice12.SessionManager
import com.google.android.material.navigation.NavigationView

import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_enter2.*
import kotlinx.android.synthetic.main.activity_product_cart_list.view.*
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.menu_cart_layout.view.*

class EnterActivity2 : AppCompatActivity() {
    var mList: ArrayList<Category> = ArrayList()
    private var textViewCartCount: TextView? = null

    private var cartIcon : ImageView? = null

    lateinit var sessionManager: SessionManager

    lateinit var adapterCategory: AdapterCategory

    private lateinit var drawLayout: DrawerLayout
    private lateinit var navView: NavigationView

    lateinit var dbHelprt: DBHelprt

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter2)
        init()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)



        var item =  menu?.findItem(R.id.menu_cart)
        MenuItemCompat.setActionView(item, R.layout.menu_cart_layout)
        var view = MenuItemCompat.getActionView(item)

        textViewCartCount = view.text_view_cart_count

        updateCartCount()
        textViewCartCount!!.setOnClickListener {
            Toast.makeText(applicationContext, "Cart", Toast.LENGTH_SHORT).show()
        }
        cartIcon  =view.cart_with_num

      //  view.cart

        cartIcon!!.setOnClickListener {

            intent = Intent(this, ProductCartListActivity::class.java)
            startActivity(intent)
        }
        return true;
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_cart -> {

                intent = Intent(this, ProductCartListActivity::class.java)
                startActivity(intent)
            }
           // R.id.menu_settings -> Toast.makeText(applicationContext, "Settings", Toast.LENGTH_SHORT).show()
            R.id.menu_logout -> Toast.makeText(applicationContext, "Logout", Toast.LENGTH_SHORT).show()
            R.id.menu_order_history -> {
                 intent = Intent(this,OrderHistoryActivity::class.java)
                 startActivity(intent)
            }
            R.id.menu_back ->{

                super.onBackPressed()
            }
        }
        return true
    }

    private fun setupToolbar(){
        var toolbar = toolbar
        toolbar.title = "Home"
        setSupportActionBar(toolbar)
    }
    private fun updateCartCount(){
        var count = dbHelprt.getAllProduccts().size;
        if(count == 0){
            textViewCartCount?.visibility = View.GONE
        }else{
            textViewCartCount?.visibility = View.VISIBLE
            textViewCartCount?.text = count.toString()
        }
    }
    private fun init(){
        dbHelprt = DBHelprt(this)

        drawLayout = drawer_layout
       // navView = nav_view

        var toggle = ActionBarDrawerToggle(
                this, drawLayout, toolbar, 0,0
        )
        drawLayout.addDrawerListener(toggle)
        toggle.syncState()
       // navView.setNavigationItemSelectedListener(this)

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

    //"http://grocery-second-app.herokuapp.com/api/category"

    private fun getData(){
        var requestQueue = Volley.newRequestQueue(this)
        var request = StringRequest(
            Request.Method.GET,
            EndPoints.getCategory(),
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
        builder.setMessage("Are you sure you wan to logout")
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
//    override fun onNavigationItemSelected(item: MenuItem): Boolean {
//        when(item.itemId){
//            R.id.item_account -> Toast.makeText(applicationContext, "account", Toast.LENGTH_SHORT).show()
//            R.id.item_logout -> logoutDialoge()
//        }
//        drawLayout.closeDrawer(GravityCompat.START)
//        return true
//    }
    override fun onBackPressed() {
        if(drawLayout.isDrawerOpen(GravityCompat.START)){
            drawLayout.closeDrawer(GravityCompat.START)
        }else{
            super.onBackPressed()
        }
    }

}