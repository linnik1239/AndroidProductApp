package com.example.practice12.Activities

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.models.Product
import com.example.practice12.DataBae.DBHelper
import com.example.practice12.DataBae.DBHelprt
import com.example.practice12.MainActivity
import com.example.practice12.Models.SpecificProduct
import com.example.practice12.R
import com.example.practice12.SessionManager
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_product_detail.*
import kotlinx.android.synthetic.main.app_bar.*


class ProductDetailActivity : AppCompatActivity() {

   // lateinit var dbHelper:DBHelper
    lateinit var sessionManager: SessionManager

    lateinit var dbHelprt: DBHelprt


    //private var specificProduct:SpecificProduct? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)


        setupToolbar()


        val theProduct =
            intent.getSerializableExtra("PRODUCT") as Product?

        button_add_product.setOnClickListener {
            dbHelprt = DBHelprt(this)

                if(theProduct!=null){
                    theProduct.amount = text_num_detail_info.text.toString().toInt()
                    dbHelprt.addProduct(theProduct)
                }
        }

        button_detail_minus.setOnClickListener {

            var num =text_num_detail_info.text.toString().toInt()
            --num

            if(theProduct!=null) {
                var totalPrice = theProduct.price * num
                text_view_total_price.text = "Total price = ${totalPrice}$"
            }

            text_num_detail_info.text = num.toString()
        }

        button_detail_plus.setOnClickListener {
            var num =text_num_detail_info.text.toString().toInt()
            ++num
            if(theProduct!=null) {
                var totalPrice = theProduct.price * num
                text_view_total_price.text = "Total price = ${totalPrice}$"
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
            button_detail_back.setOnClickListener {
                intent = Intent(this, EnterActivity2::class.java)
                startActivity(intent)
            }
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