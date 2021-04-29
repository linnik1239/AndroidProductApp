package com.example.myapplication.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.myapplication.adapters.ViewPagerAdapter
import com.example.myapplication.models.Category2
import com.example.myapplication.models.Category2.Companion.KEY_CAT_ID
import com.example.myapplication.models.Product
import com.example.practice12.MainActivity
import com.example.practice12.Activities.SubCategoryActivity
import com.example.practice12.Adapters.AdapterProduct
import com.example.practice12.Models.Category
import com.example.practice12.Models.CategoryResponse
import com.example.practice12.Models.SubCategoryResponse
import com.example.practice12.Models.SubSubCategoryResponse
import com.example.practice12.R


import com.google.gson.Gson
import kotlinx.android.synthetic.main.content_sub_category.*
import kotlinx.android.synthetic.main.fragment_list_view.view.*
import kotlinx.android.synthetic.main.row_product_adapter.view.*


class ProductListFragment : Fragment() {

    var onFragmentIteration : OnFragmentIteration? = null

    var productList: ArrayList<Product> = ArrayList()
    var mContext: Context? = null

    private var catId: Int? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if(context!=null){

            onFragmentIteration  = context as SubCategoryActivity
        }

        mContext = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            catId = it.getInt(Category2.KEY_CAT_ID)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_list_view, container, false)
        init(view)
        return view
    }

    private fun init(view: View) {
        generate()

        var subSubURL = "http://grocery-second-app.herokuapp.com/api/products/sub/"+catId


        var requestQueue = Volley.newRequestQueue(mContext)

        var request = StringRequest(
            Request.Method.GET,
            subSubURL,
            Response.Listener {
                var gson = Gson()
                var SubCategoryResponse = gson.fromJson(it, SubSubCategoryResponse::class.java)

                for(item in SubCategoryResponse.data){

                    productList.add(Product(item._id,item.productName, item.description, item.image,item.price,item.mrp,0))

                }
                var productAdapter = AdapterProduct(mContext!!, productList)
                view.list_view.adapter = productAdapter
            },
            Response.ErrorListener {
                Toast.makeText(mContext, it.message, Toast.LENGTH_SHORT).show()
                Log.d("abc", it.message.toString())
            }
        )
        requestQueue.add(request)

    }
    interface OnFragmentIteration {
        fun onButtonClick(
            name: String,
            description:String
        )
    }
    private fun generate(){
        productList.clear()
    }

    companion object {

        @JvmStatic
        fun newInstance(catId: Int) =
            ProductListFragment().apply {
                arguments = Bundle().apply {
                    putInt(Category2.KEY_CAT_ID, catId)

                }
            }
    }
}