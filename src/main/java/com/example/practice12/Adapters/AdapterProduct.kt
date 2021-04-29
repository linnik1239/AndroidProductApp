package com.example.practice12.Adapters


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.myapplication.models.Category2
import com.example.myapplication.models.Product
import com.example.practice12.Activities.ProductDetailActivity
import com.example.practice12.Models.Category
import com.example.practice12.Models.SpecificProduct
import com.example.practice12.R

import com.google.gson.Gson
import com.squareup.picasso.Picasso


import kotlinx.android.synthetic.main.row_product_adapter.view.*

class AdapterProduct(var mContext: Context, var mList: ArrayList<Product>) : BaseAdapter() {
    override fun getCount(): Int {
        return mList.size
    }

    override fun getItem(position: Int): Any {
        return mList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view =
            LayoutInflater.from(mContext).inflate(R.layout.row_product_adapter, parent, false)
        var product = mList[position]

            view.setOnClickListener {
                var intent = Intent(mContext, ProductDetailActivity::class.java)
                intent.putExtra("PRODUCT",product)
                mContext.startActivity( intent)
            }

        view.text_view_name.text =   product.name
        view.text_view_price.text = "Price: "+product.price+"$"
        Picasso
            .get()
            .load("http://rjtmobile.com/grocery/images/"+product.image)
            .into(view.image_view_product)
        return view
    }
}