package com.example.practice12.Activities

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.practice12.Adapters.AddressesAdapter
import com.example.practice12.DataBae.DBHelprt
import com.example.practice12.MainActivity
import com.example.practice12.Models.*
import com.example.practice12.R
import com.example.practice12.SessionManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_address.*
import kotlinx.android.synthetic.main.activity_enter2.*
import kotlinx.android.synthetic.main.activity_product_cart_list.*
import kotlinx.android.synthetic.main.activity_sub_category.*
import kotlinx.android.synthetic.main.app_bar.*
import org.json.JSONArray
import org.json.JSONObject


class AddressActivity : AppCompatActivity() {

    lateinit var dbHelprt: DBHelprt

    lateinit var adapterAddress : AddressesAdapter
    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address)
        sessionManager = SessionManager(this)


        setupToolbar()




        AddressActivity_add_address.setOnClickListener {
            //Toast.makeText(this,"Num = "+adapterAddress.getSelected(),Toast.LENGTH_LONG).show()


            intent = Intent(this,AddAddressActivity::class.java)
            startActivity(intent)



//            var addr=  adapterAddress.mList.get(adapterAddress.getSelected())
//
//            intent = Intent(this,TotalOrderSummaryActivity::class.java)
//
//            intent.putExtra("ADDRESS",addr)
//
//
//
//            startActivity(intent)

        }


        AddressActivity_get_info.setOnClickListener {

            if(adapterAddress.mList.size<=0){
                Toast.makeText(this,"Please add address to send.",Toast.LENGTH_LONG).show()

            }else {



                var addr :Address = adapterAddress.mList[adapterAddress.getSelected()]

                var sharedPrederence = getSharedPreferences("my_pref_final_address",Context.MODE_PRIVATE)

                var editor = sharedPrederence.edit()
                editor.putString("city",addr.city)
                editor.putString("houseNum",addr.houseNum)

                editor.putString("streatName",addr.streatName)

                editor.putString("type",addr.type)
                editor.commit()

                // var addr = adapterAddress.mList.get(adapterAddress.getSelected())
                intent = Intent(this, TotalOrderSummaryActivity::class.java)

                //intent.putExtra("ADDRESS", addr)


                startActivity(intent)
            }



        }


     //   Log.d("abc","ttt")
    //    AddressActivity_get_info.setOnClickListener {
          //  Log.d("abc","mmm")


            var jsonObject = JSONObject()

            var requestQueue = Volley.newRequestQueue(this)

          //  var theURLPostAddress = "http://grocery-second-app.herokuapp.com/api/address/603c691772642f00171f30c2"

            var addressList = ArrayList<Address>()
            var jsonRequest = JsonObjectRequest(
                Request.Method.GET,
                EndPoints.getAddress(),
                jsonObject,
                Response.Listener {
                    val array = it.getJSONArray("data") //as ArrayList<Address>()
                    if(array!=null) {
                        for (i in 0 until array.length()) {
                            val address: JSONObject = array.getJSONObject(i)
                            var city = address.getString("city").toString()
                            var streetName = address.getString("streetName").toString()
                            var houseNo = address.getString("houseNo").toString()
                            var type = address.getString("type").toString()
                            var pincode = address.getInt("pincode").toInt()

                            var _id = address.getString("_id").toString()

                            if(pincode==65445){
                                addressList.add(Address(streetName,houseNo,type,city,_id))
                            }else{
                                var theNewURLPostAddress =  "http://grocery-second-app.herokuapp.com/api/address/"+_id
                                var requestQueue2 = Volley.newRequestQueue(this)
                                var jsonObject2 = JSONObject()



                                var jsonRequest2 = JsonObjectRequest(
                                        Request.Method.DELETE,
                                        theNewURLPostAddress,
                                        jsonObject2,
                                        Response.Listener {

                                        },
                                        Response.ErrorListener {
                                            Log.d("abc",it.toString())
                                        }
                                )
                                requestQueue2.add(jsonRequest2)
                            }
                        }
                    }
                    adapterAddress = AddressesAdapter(this)

                    adapterAddress.setData(addressList)

                    if(adapterAddress.mList.size<=0){
                        text_view_empty_address.text = "The address list is empty"
                        text_view_empty_address.textSize = 40F
                    }else{
                        text_view_empty_address.text = ""
                        text_view_empty_address.textSize = 0F
                    }



                    recycler_view_address.adapter = adapterAddress

                    recycler_view_address.layoutManager = LinearLayoutManager(this)
                    recycler_view_address.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

                },
                Response.ErrorListener {
                    Log.d("abc",it.toString())
                }

            )
            requestQueue.add(jsonRequest)
       // }

        // Enables Always-on
        //setAmbientEnabled()
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
            R.id.menu_home ->{
                intent = Intent(this, EnterActivity2::class.java)
                startActivity(intent)
            }

            R.id.menu_back ->{
                super.onBackPressed()

            }
            R.id.menu_order_history -> {
                intent = Intent(this,OrderHistoryActivity::class.java)
                startActivity(intent)
            }

        }
        return true
    }

    private fun setupToolbar(){
        var toolbar = toolbar
        toolbar.title = "Addresses"
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

                intent = Intent(applicationContext, MainActivity::class.java)

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




}