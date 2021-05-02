package com.example.practice12.Activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import com.example.practice12.R
import kotlinx.android.synthetic.main.activity_add_address.*
import kotlinx.android.synthetic.main.activity_coupon.*
import java.util.*

class CouponActivity : AppCompatActivity() {

    private var choise = -1


    private val AMOUNT_FOR_FIRST_DISCOUNT = 5000.0

    private val AMOUNT_FOR_SECOND_DISCOUNT = 2000.0

    private val AMOUNT_FOR_THIRD_DISCOUNT = 1000.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coupon)


        coupon_back.setOnClickListener {
            startActivity(Intent(this,TotalOrderSummaryActivity::class.java))
        }
        coupon_choose.setOnClickListener {

            var sharedPreferences = getSharedPreferences("my_pref4", Context.MODE_PRIVATE)

            var deliveryCharges = sharedPreferences.getString("deliveryCharges",null)
            var discount = sharedPreferences.getString("discount",null)

            var orderAmount = sharedPreferences.getString("orderAmount",null)

            var ourPrice = sharedPreferences.getString("ourPrice",null)

            var totalAmount = sharedPreferences.getString("totalAmount",null)

            if(orderAmount==null || discount==null || totalAmount==null){
                super.onBackPressed()

            }
            else{
                var mtotalAmount = totalAmount?.toDouble()
                var mdiscount = discount?.toDouble()

                var morderAmount = orderAmount?.toDouble()

                var mourPrice = ourPrice?.toDouble()
                var mdeliveryCharges = deliveryCharges?.toDouble()

                when(choise){
                    1 -> {
                        if(morderAmount>AMOUNT_FOR_FIRST_DISCOUNT){
                            mdiscount += 500.0
                            mourPrice = morderAmount - mdiscount!!
                            var editor = sharedPreferences.edit()
                            editor.putString("deliveryCharges",mdeliveryCharges.toString())
                            editor.putString("discount",mdiscount.toString())
                            editor.putString("orderAmount",morderAmount.toString())
                            editor.putString("ourPrice",mourPrice.toString())
                            editor.putString("totalAmount",mtotalAmount.toString())
                            editor.commit()
                            startActivity(Intent(this,TotalOrderSummaryActivity::class.java))
                        }else{
                            Toast.makeText(this,"Total payment amount must be more than 5000$.",Toast.LENGTH_LONG).show()

                        }

                    }
                    2 ->{

                        if(morderAmount>AMOUNT_FOR_SECOND_DISCOUNT){
                            mdiscount += 200.0
                            mourPrice = morderAmount - mdiscount!!
                            var editor = sharedPreferences.edit()
                            editor.putString("deliveryCharges",mdeliveryCharges.toString())
                            editor.putString("discount",mdiscount.toString())
                            editor.putString("orderAmount",morderAmount.toString())
                            editor.putString("ourPrice",mourPrice.toString())
                            editor.putString("totalAmount",mtotalAmount.toString())
                            editor.commit()
                            startActivity(Intent(this,TotalOrderSummaryActivity::class.java))
                        }else{
                            Toast.makeText(this,"Total payment amount must be more than 2000$.",Toast.LENGTH_LONG).show()

                        }

                    }

                    3->{

                        val day = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
                        if(morderAmount>AMOUNT_FOR_THIRD_DISCOUNT && (day>=2 && day <=6)){

                            mourPrice = morderAmount - mdiscount!!

                            mdiscount += mourPrice*.05
                            mourPrice*=.95



                            var editor = sharedPreferences.edit()
                            editor.putString("deliveryCharges",mdeliveryCharges.toString())
                            editor.putString("discount",mdiscount.toString())

                            editor.putString("orderAmount",morderAmount.toString())
                            editor.putString("ourPrice",mourPrice.toString())
                            editor.putString("totalAmount",mtotalAmount.toString())
                            editor.commit()

                            startActivity(Intent(this,TotalOrderSummaryActivity::class.java))




                        }else{
                            if(!(day>=2 && day <=6)){
                                Toast.makeText(this,"Only from Monday to friday",Toast.LENGTH_LONG).show()

                            }else{
                                Toast.makeText(this,"Total payment amount must be more than 1000$.",Toast.LENGTH_LONG).show()

                            }
                        }



                    }
                    else ->{
                        Toast.makeText(this,"No choice for coupon as chosen.",Toast.LENGTH_LONG).show()

                    }



                }

            }



        }
    }


//    fun onRadioButtonClicked(view: View) {
//        if (view is RadioButton) {
//            // Is the button now checked?
//            val checked = view.isChecked
//
//            when (view.getId()) {
//                R.id.radio_first ->
//                    if (checked) {
//                        choise = 1
//                        Toast.makeText(this,"First",Toast.LENGTH_LONG).show()
//
//                    }
//                R.id.radio_second ->
//                    if (checked) {
//                        choise = 2
//                        Toast.makeText(this,"Second",Toast.LENGTH_LONG).show()
//
//                    }
//                R.id.radio_third ->
//                    if (checked) {
//                        choise = 3
//                        Toast.makeText(this,"Third",Toast.LENGTH_LONG).show()
//
//                    }
//                else ->{
//                    if (checked) {
//                        choise = -1
//                        Toast.makeText(this,"Nothind",Toast.LENGTH_LONG).show()
//
//                    }
//                }
//            }
//        }
//    }

}