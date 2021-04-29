package com.example.practice12.Activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.models.Product
import com.example.practice12.Adapters.AdapterProduct
import com.example.practice12.Adapters.MyProductListAdapter
import com.example.practice12.DataBae.DBHelprt
import com.example.practice12.Models.User
import com.example.practice12.Models.orderSummary
import com.example.practice12.R
import kotlinx.android.synthetic.main.activity_product_cart_list.*
import kotlinx.android.synthetic.main.fragment_list_view.view.*

class ProductCartListActivity : AppCompatActivity() {


    lateinit var dbHelprt: DBHelprt

    lateinit var totalOrder: orderSummary

    lateinit var list: List<Product>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_cart_list)


        button_detail_back2.setOnClickListener {
            intent = Intent(this,EnterActivity2::class.java)
            startActivity(intent)
            Log.d("abc",list.toString())

        }

        button_detail_order.setOnClickListener{

            var totalMRP = 0.0
            var totalPrice = 0.0
            for(item in list){
                totalMRP+= item.amount*item.mrp
                totalPrice+= item.amount*item.price
            }
            var bundle = Bundle()
            bundle.putString("TOTAL_MRP",totalMRP.toString())
            bundle.putString("TOTAL_DISCOUNT",(totalMRP-totalPrice).toString() )

            bundle.putString("TOTAL_PRICE",totalPrice.toString() )

            intent = Intent(this,PaymentActivity::class.java)


            var sharedPreferences = getSharedPreferences("my_pref4", Context.MODE_PRIVATE)
            var editor = sharedPreferences.edit()
            editor.putString("deliveryCharges",totalOrder.deliveryCharges.toString())
            editor.putString("discount",totalOrder.discount.toString())

            editor.putString("orderAmount",totalOrder.orderAmount.toString())
            editor.putString("ourPrice",totalOrder.ourPrice.toString())
            editor.putString("totalAmount",totalOrder.totalAmount.toString())
            editor.commit()



            intent.putExtra("DATA",bundle)

            startActivity(intent)


        }


        dbHelprt = DBHelprt(this)
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

        totalOrder = orderSummary("TheID",deliveryCharhes,
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
}