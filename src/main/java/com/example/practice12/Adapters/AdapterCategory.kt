package com.example.practice12.Adapters

import android.content.Context
import android.content.Intent
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.practice12.Activities.SubCategoryActivity

import com.example.practice12.Models.Category
import com.example.practice12.R
import com.squareup.picasso.Picasso

import kotlinx.android.synthetic.main.row_category_adapter.view.*
import kotlinx.android.synthetic.main.row_product_adapter.view.*

class AdapterCategory(var mContext: Context) :
    RecyclerView.Adapter<AdapterCategory.ViewHolder>() {
    var mList: ArrayList<Category> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view =
            LayoutInflater.from(mContext).inflate(R.layout.row_category_adapter, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }
    fun setData(list: ArrayList<Category>){
        mList = list
        notifyDataSetChanged()
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var category = mList[position]
        holder.bind(category)
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(category: Category){
            itemView.text_view_category_name.text = category.catName
            Picasso
                .get()
                .load("http://rjtmobile.com/grocery/images/${category.catImage}")
                .into(itemView.image_view_category)

            itemView.setOnClickListener {

                var catID = (category.catId).toString()
                var intent = Intent(mContext, SubCategoryActivity::class.java)
                var bundle = Bundle()
                bundle.putString("CAT_ID",catID)
                intent.putExtra("DATA",bundle)
                mContext.startActivity(intent)

            }
        }

    }

}