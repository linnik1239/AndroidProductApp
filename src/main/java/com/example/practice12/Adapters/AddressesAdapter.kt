package com.example.practice12.Adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.practice12.Activities.AddressActivity
import com.example.practice12.Activities.EditAddressActivity
import com.example.practice12.Activities.SuccessPayActivity
import com.example.practice12.Models.Address
import com.example.practice12.Models.EndPoints
import com.example.practice12.R
import kotlinx.android.synthetic.main.raw_address_adapter.view.*
import org.json.JSONObject


class AddressesAdapter(var mContext: Context):RecyclerView.Adapter<AddressesAdapter.ViewHolder>() {
    var mSelectedItem = -1

    var mList: ArrayList<Address> = ArrayList()
    lateinit var radioButton: RadioButton

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressesAdapter.ViewHolder {
        var view = LayoutInflater.from(mContext).inflate(
                R.layout.raw_address_adapter,
                parent,
                false
        )
        return ViewHolder(view)
    }

    fun getSelected(): Int{
        return mSelectedItem
    }

    override fun onBindViewHolder(holder: AddressesAdapter.ViewHolder, position: Int) {
        holder.mRadio?.setChecked(position == mSelectedItem)



        var address = mList[position]
        holder.bind(address)

    }

    fun setData(addresses: ArrayList<Address>){
        mList = addresses
        notifyDataSetChanged()

    }
    override fun getItemCount(): Int {
        return mList.size
    }



    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var mRadio: RadioButton? = null


        fun bind(address: Address){
            itemView.AddressesAdaptertext_view_city.text = "City: "+address.city
            itemView.AddressesAdaptertext_view_house_num.text = "House num: "+address.houseNum
            itemView.AddressesAdaptertext_view_streat.text = "Streat: "+address.streatName
            itemView.AddressesAdaptertext_view_type.text = "Type: "+address.type



            itemView.AddressesAdaptertext_view_delete_address.setOnClickListener {
               // var theNewURLPostAddress =  "http://grocery-second-app.herokuapp.com/api/address/"+address._id

                var requestQueue2 = Volley.newRequestQueue(mContext)
                var jsonObject2 = JSONObject()


                var jsonRequest2 = JsonObjectRequest(
                        Request.Method.DELETE,
                        EndPoints.getAddressAddId(address._id),
                        jsonObject2,
                        Response.Listener {
                            Log.d("abc", it.toString())

                        },
                        Response.ErrorListener {
                            Log.d("abc", it.toString())
                        }
                )
                requestQueue2.add(jsonRequest2)

                var intent = Intent(mContext, AddressActivity::class.java)

                mContext.startActivity(intent)
            }


            itemView.AddressesAdaptertext_view_edit_address.setOnClickListener {

                var intent = Intent(mContext, EditAddressActivity::class.java)

                intent.putExtra("ADDRESS",address)

                mContext.startActivity(intent)

            }


            mRadio = itemView.Radio          //findViewById(R.id.radio)
            val clickListener = View.OnClickListener {
                mSelectedItem = adapterPosition
                notifyDataSetChanged()
            }
            itemView.setOnClickListener(clickListener)
            mRadio!!.setOnClickListener(clickListener)



        }
    }




}