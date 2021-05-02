package com.example.practice12.Activities

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuItemCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.myapplication.models.Product
import com.example.practice12.DataBae.DBHelper
import com.example.practice12.DataBae.DBHelprt
import com.example.practice12.MainActivity
import com.example.practice12.Models.SpecificProduct
import com.example.practice12.R
import com.example.practice12.SessionManager
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_product_detail.*
import kotlinx.android.synthetic.main.activity_sub_category.*
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.menu_cart_layout.view.*
import java.math.BigDecimal
import java.math.RoundingMode


class ProductDetailActivity : AppCompatActivity() {

   // lateinit var dbHelper:DBHelper
    lateinit var sessionManager: SessionManager

    lateinit var dbHelprt: DBHelprt
    private var textViewCartCount: TextView? = null
    private var cartIcon : ImageView? = null
    private lateinit var drawLayout: DrawerLayout

    //private var specificProduct:SpecificProduct? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)


        drawLayout = drawer_layoutProductDetailActivity
        // navView = nav_view

        var toggle = ActionBarDrawerToggle(
            this, drawLayout, toolbar, 0,0
        )
        drawLayout.addDrawerListener(toggle)

        dbHelprt = DBHelprt(this)


        setupToolbar()


        val theProduct =
            intent.getSerializableExtra("PRODUCT") as Product?

        button_add_product.setOnClickListener {
                if(theProduct!=null){
                    theProduct.amount = text_num_detail_info.text.toString().toInt()
                    dbHelprt.addProduct(theProduct)
                    updateCartCount()

                }
        }

        button_detail_minus.setOnClickListener {

            var num =text_num_detail_info.text.toString().toInt()
            --num

            if(theProduct!=null) {
                var totalPrice = theProduct.price * num

                val totalPriceD = BigDecimal(totalPrice).setScale(3, RoundingMode.HALF_EVEN)


                text_view_total_price.text = "Total price = ${totalPriceD}$"

            }

            text_num_detail_info.text = num.toString()
        }

        button_detail_plus.setOnClickListener {
            var num =text_num_detail_info.text.toString().toInt()
            ++num
            if(theProduct!=null) {
                var totalPrice = theProduct.price * num

                val totalPriceD = BigDecimal(totalPrice).setScale(3, RoundingMode.HALF_EVEN)

                text_view_total_price.text = "Total price = ${totalPriceD}$"
            }

            text_num_detail_info.text = num.toString()
        }

        if(theProduct!=null) {

            text_view_name2.text = theProduct.name
            text_view_price.text = "Price = ${theProduct.price}$"
            text_view__mrp_price.text = "mrp = ${theProduct.mrp}$"

            text_view__mrp_price.setPaintFlags(text_view__mrp_price.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)


            var num =text_num_detail_info.text.toString().toInt()
            var totalPrice = theProduct.price * num
            text_view_total_price.text = "Total price = ${totalPrice}$"


            Picasso
                .get()
                .load("http://rjtmobile.com/grocery/images/" + theProduct.image)
                .into(image_view_product2)

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

                super.onBackPressed()

            }
            R.id.menu_order_history -> {
                intent = Intent(this,OrderHistoryActivity::class.java)
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
        toolbar.title = "Product details"
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

                intent = Intent(applicationContext,MainActivity::class.java)

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

    companion object {
        @JvmStatic
        fun newInstance(specificProduct: SpecificProduct)=
            ProductDetailActivity().apply {

            }
    }
}