package com.example.practice12.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.practice12.Models.History
import com.example.practice12.R
import kotlinx.android.synthetic.main.activity_order_history.view.*
import kotlinx.android.synthetic.main.raw_order_history.view.*

class AdapterOrderHIstory(var mContext: Context):RecyclerView.Adapter<AdapterOrderHIstory.ViewHolder>() {

    var mList :ArrayList<History> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterOrderHIstory.ViewHolder {
        var view =
            LayoutInflater.from(mContext).inflate(R.layout.raw_order_history, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var history = mList[position]
        holder.bind(history)
    }

    override fun getItemCount(): Int {
         return mList.size
    }

    fun setData( inputList: ArrayList<History>){
        mList = inputList
        notifyDataSetChanged()
    }


    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(history: History){
            itemView.history_date.text = "Date: "+history.date
            itemView.history_amount.text = "Amount of diffrent prodcuts: "+history.amount
            itemView.history_total_cost.text = "Total paid = "+history.cost+"$"
        }

    }


}