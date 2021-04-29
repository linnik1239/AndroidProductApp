package com.example.practice12.Adapters

import android.content.Context
import android.content.Intent
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.myapplication.models.Product
import com.example.practice12.Activities.ProductCartListActivity
import com.example.practice12.DataBae.DBHelprt
import com.example.practice12.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_product_detail.*
import kotlinx.android.synthetic.main.row_my_product_list_adapter.view.*
import kotlinx.android.synthetic.main.row_product_adapter.view.*

class MyProductListAdapter(var mContext: Context, var mList: ArrayList<Product>) : BaseAdapter() {
    lateinit var dbHelprt: DBHelprt


    override fun getItem(position: Int): Any {
        return mList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
       return mList.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        dbHelprt = DBHelprt(mContext)

        val view =
            LayoutInflater.from(mContext).inflate(R.layout.row_my_product_list_adapter, parent, false)
        var product = mList[position]


        view.text_view_name_my_list.text = product.name.toString()
        //view.text_view_description_my_list.text = product.description.toString()
        view.text_view_price_my_list.text = product.price.toString()+"$"

        view.text_view___detail_mrp_price.text = "mrp = ${product.mrp}$"

        view.text_view___detail_mrp_price.setPaintFlags(view.text_view___detail_mrp_price.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)




        var totalPrice = product.price.toString().toDouble() * product.amount.toString().toInt()
        view.text_view__total_price_my_list.text = "Total: ${totalPrice.toString()}$"

       // view.text_view_amount_my_list.text = product.amount.toString()
        view.text_num_detail_info_my_list.text =  product.amount.toString()

        view.button_plus_details_my_list.setOnClickListener {
            var num = view.text_num_detail_info_my_list.text.toString().toInt()
            ++num
            view.text_num_detail_info_my_list.text = num.toString()
          //  view.text_view_amount_my_list.text = num.toString()

            var totalPrice = product.price.toString().toDouble() * num
            view.text_view__total_price_my_list.text = "Total: ${totalPrice.toString()}$"


            mList[position].amount = num
        }
        view.button_minus_details_my_list.setOnClickListener {
            var num = view.text_num_detail_info_my_list.text.toString().toInt()
            --num
            view.text_num_detail_info_my_list.text = num.toString()
        //    view.text_view_amount_my_list.text = num.toString()
            if(num<=0){
                dbHelprt.deleteProduct(product)
                var intent = Intent(mContext,ProductCartListActivity::class.java)
                mContext.startActivity(intent)
            }

            var totalPrice = product.price.toString().toDouble() * num
            view.text_view__total_price_my_list.text = "Total: ${totalPrice.toString()}$"


            mList[position].amount = num
        }


        view.button_detail_list_Delete_item.setOnClickListener {

            dbHelprt.deleteProduct(product)
            var intent = Intent(mContext,ProductCartListActivity::class.java)
            mContext.startActivity(intent)

        }



        Picasso
            .get()
            .load("http://rjtmobile.com/grocery/images/"+product.image)
            .into(view.image_view_product_my_list)
        return view

    }

}