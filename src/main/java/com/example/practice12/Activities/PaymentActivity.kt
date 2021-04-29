package com.example.practice12.Activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.practice12.Models.User
import com.example.practice12.Models.orderSummary
import com.example.practice12.R
import kotlinx.android.synthetic.main.activity_payment.*
import kotlinx.android.synthetic.main.activity_product_cart_list.*

class PaymentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        var bundle = intent.getBundleExtra("DATA")


        var totalMRP = bundle?.getString("TOTAL_MRP").toString()
        payment_summary_detail_1.text = "Total Brfore discount: "+(bundle?.getString("TOTAL_MRP")).toString()+"$"
        payment_summary_detail_2.text = "Total discount: "+(bundle?.getString("TOTAL_DISCOUNT")).toString()+"$"
        payment_summary_detail_3.text = "Total To pay: "+(bundle?.getString("TOTAL_PRICE")).toString()+"$"

        payment_button_detail_back2.setOnClickListener {
            startActivity(Intent(this,ProductCartListActivity::class.java))
        }

        payment_button_detail_order.setOnClickListener {

            intent = Intent(this,AddressActivity::class.java)

            startActivity(intent)


        }


    }
}