package com.example.practice12.Activities

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.myapplication.adapters.ViewPagerAdapter
import com.example.myapplication.fragments.ProductListFragment
import com.example.myapplication.models.Category2
import com.example.practice12.MainActivity
import com.example.practice12.Models.Category
import com.example.practice12.Models.CategoryResponse
import com.example.practice12.Models.ResponseData
import com.example.practice12.Models.SubCategoryResponse
import com.example.practice12.R
import com.example.practice12.SessionManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_sub_category.*
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.content_sub_category.*
import kotlinx.android.synthetic.main.content_sub_category.tab_layout
import kotlinx.android.synthetic.main.content_sub_category.view_pager

class SubCategoryActivity : AppCompatActivity(), ProductListFragment.OnFragmentIteration {

    lateinit var sessionManager: SessionManager

    var categoryList: ArrayList<Category2> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub_category)
        init()
    }

    private fun init() {
        setupToolbar()
        var bundle = intent.getBundleExtra("DATA")
        var catID = bundle?.getString("CAT_ID")

        var subCatURL = "http://grocery-second-app.herokuapp.com/api/subcategory/"+catID



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

        }
        return true
    }

    private fun setupToolbar(){
        var toolbar = toolbar
        toolbar.title = "Categoty"
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