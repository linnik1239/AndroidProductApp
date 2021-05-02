package com.example.practice12.Activities

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.models.Product
import com.example.practice12.Adapters.AdapterProduct
import com.example.practice12.Adapters.MyProductListAdapter
import com.example.practice12.DataBae.DBHelprt
import com.example.practice12.MainActivity
import com.example.practice12.Models.User
import com.example.practice12.Models.TheOrderSummary
import com.example.practice12.R
import com.example.practice12.SessionManager
import kotlinx.android.synthetic.main.activity_product_cart_list.*
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.fragment_list_view.view.*

class ProductCartListActivity : AppCompatActivity() {
    lateinit var sessionManager: SessionManager


    lateinit var dbHelprt: DBHelprt

    lateinit var totalOrder: TheOrderSummary

    lateinit var list: List<Product>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_cart_list)
        setupToolbar()

        dbHelprt = DBHelprt(this)

        if(dbHelprt.getAllProduccts().size<=0){
            text_view_empty_cart.text = "The cart is empty"
            text_view_empty_cart.textSize = 40F
        }else{
            text_view_empty_cart.text = ""
            text_view_empty_cart.textSize = 0F
        }

        button_detail_order.setOnClickListener{



            if(dbHelprt.getAllProduccts().size<=0){
                Toast.makeText(this,"Zero amount of products",Toast.LENGTH_LONG).show()

            }else {


                findTotal()

                intent = Intent(this, AddressActivity::class.java)


                var sharedPreferences = getSharedPreferences("my_pref4", Context.MODE_PRIVATE)
                var editor = sharedPreferences.edit()
                editor.putString("deliveryCharges", totalOrder.deliveryCharges.toString())
                editor.putString("discount", totalOrder.discount.toString())

                editor.putString("orderAmount", totalOrder.orderAmount.toString())
                editor.putString("ourPrice", totalOrder.ourPrice.toString())
                editor.putString("totalAmount", totalOrder.totalAmount.toString())
                editor.commit()


                // intent.putExtra("DATA",bundle)

                startActivity(intent)
            }


        }






        list =dbHelprt.getAllProduccts()
        var productAdapter = MyProductListAdapter(this!!, list as ArrayList<Product>)
        list_view_product_cart.adapter = productAdapter
        Log.d("abc",list.toString())

        findTotal()


    }




    override fun onPause() {
        super.onPause()
        updateList()
    }

    private fun findTotal(){
        var totalMRP = 0.0
        var totalPrice = 0.0
        var deliveryCharhes = 0.0
        for(item in list){
            totalMRP+= item.amount*item.mrp
            totalPrice+= item.amount*item.price
        }

        totalOrder = TheOrderSummary("TheID",deliveryCharhes,
            totalMRP-totalPrice,
            totalMRP,
            totalPrice,
            totalPrice+deliveryCharhes
        )



    }

    private fun updateList(){
        for(item in list){
            dbHelprt.updateProduct(item)
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
            R.id.menu_home ->{
                intent = Intent(this, EnterActivity2::class.java)
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
        toolbar.title = "Products list"
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