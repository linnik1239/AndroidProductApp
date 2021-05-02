package com.example.practice12.Activities

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuItemCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.myapplication.adapters.ViewPagerAdapter
import com.example.myapplication.fragments.ProductListFragment
import com.example.myapplication.models.Category2
import com.example.practice12.DataBae.DBHelprt
import com.example.practice12.MainActivity
import com.example.practice12.Models.*
import com.example.practice12.R
import com.example.practice12.SessionManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_enter2.*
import kotlinx.android.synthetic.main.activity_sub_category.*
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.content_sub_category.*
import kotlinx.android.synthetic.main.content_sub_category.tab_layout
import kotlinx.android.synthetic.main.content_sub_category.view_pager
import kotlinx.android.synthetic.main.menu_cart_layout.view.*

class SubCategoryActivity : AppCompatActivity(), ProductListFragment.OnFragmentIteration {
    private lateinit var drawLayout: DrawerLayout

    lateinit var sessionManager: SessionManager

    var categoryList: ArrayList<Category2> = ArrayList()
    lateinit var dbHelprt: DBHelprt
    private var textViewCartCount: TextView? = null
    private var cartIcon : ImageView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_category)
        init()
    }

    private fun init() {
        setupToolbar()
        dbHelprt = DBHelprt(this)







        drawLayout = drawer_layoutSubCategoryActivity
        // navView = nav_view

        var toggle = ActionBarDrawerToggle(
            this, drawLayout, toolbar, 0,0
        )
        drawLayout.addDrawerListener(toggle)





        var bundle = intent.getBundleExtra("DATA")
        var catID = bundle?.getString("CAT_ID")

        //var subCatURL = "http://grocery-second-app.herokuapp.com/api/subcategory/"+catID

        var subCatURL = EndPoints.getSubCategoryById(catID?.toInt())

        var requestQueue = Volley.newRequestQueue(this)
        var request = StringRequest(
            Request.Method.GET,
            subCatURL,
            Response.Listener {
                var gson = Gson()
                var SubCategoryResponse = gson.fromJson(it, SubCategoryResponse::class.java)

                getCategories2(SubCategoryResponse.data)


                var viewPagerAdapter = ViewPagerAdapter(supportFragmentManager)

                for (category2 in categoryList){
                    viewPagerAdapter.addFragment(category2)
                }

                view_pager.adapter = viewPagerAdapter
                tab_layout.setupWithViewPager(view_pager)



            },
            Response.ErrorListener {
               // Toast.makeText(applicationContext, it.message, Toast.LENGTH_SHORT).show()
                Log.d("abc", it.message.toString())
            }
        )
        requestQueue.add(request)

    }

    override fun onButtonClick(name:String, description:String){

    }

    private fun getCategories(){
        categoryList.add(Category2(1, "Mobile"))
        categoryList.add(Category2(2, "Laptop"))
        categoryList.add(Category2(3, "Desktop"))
    }

    private fun getCategories2(categoryList2: List<ResponseData>){


        for(item in categoryList2){
            categoryList.add(Category2(item.subId, item.subName))
        }

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
            R.id.menu_cart ->{


                intent = Intent(this, ProductCartListActivity::class.java)
                startActivity(intent)

            }
            R.id.menu_logout ->{
                logoutDialoge()



            }
            R.id.menu_home ->{
                intent = Intent(this, EnterActivity2::class.java)
                startActivity(intent)
            }
            R.id.menu_back ->{
                intent = Intent(this, EnterActivity2::class.java)
                startActivity(intent)
            }
        }
        return true
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
    private fun setupToolbar(){
        var toolbar = toolbar
        toolbar.title = "Categories"
        setSupportActionBar(toolbar)
    }
    private fun logoutDialoge() {
        var builder = AlertDialog.Builder(this)
        builder.setTitle("Logout")
        builder.setMessage("Are you sure you wan to logout")
        builder.setPositiveButton("Yes", object: DialogInterface.OnClickListener{
            override fun onClick(dialog: DialogInterface?, which: Int) {
                Toast.makeText(applicationContext, "Logging out...", Toast.LENGTH_SHORT).show()


                sessionManager = SessionManager(applicationContext)
                sessionManager.setLogin(false)
                sessionManager.logout()

                intent = Intent(applicationContext, MainActivity::class.java)

                startActivity(intent)
                Toast.makeText(applicationContext, "Logout", Toast.LENGTH_SHORT).show()



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
}