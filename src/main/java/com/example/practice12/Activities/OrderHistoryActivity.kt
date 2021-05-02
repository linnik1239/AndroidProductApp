package com.example.practice12.Activities

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
import com.example.practice12.Adapters.AdapterOrderHIstory
import com.example.practice12.MainActivity
import com.example.practice12.Models.Address
import com.example.practice12.Models.EndPoints
import com.example.practice12.Models.History
import com.example.practice12.R
import com.example.practice12.SessionManager
import kotlinx.android.synthetic.main.activity_address.*
import kotlinx.android.synthetic.main.activity_address.recycler_view_address
import kotlinx.android.synthetic.main.activity_order_history.*
import kotlinx.android.synthetic.main.app_bar.*
import kotlinx.android.synthetic.main.content_main.*
import org.json.JSONObject


class OrderHistoryActivity : AppCompatActivity()  {
    lateinit var sessionManager: SessionManager

    lateinit var adapterHistory: AdapterOrderHIstory

    var mList: ArrayList<History> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_history)

        // Enables Always-on
        init()
    }

    private fun init(){

        setupToolbar()
        sessionManager = SessionManager(this)




        var jsonObject = JSONObject()


        var requestQueue = Volley.newRequestQueue(this)

     //   var theURLPostOrder = "http://grocery-second-app.herokuapp.com/api/orders/603c691772642f00171f30c2"//"http://grocery-second-app.herokuapp.com/api/orders/603c691772642f00171f30c2"

        var jsonRequest = JsonObjectRequest(
            Request.Method.GET,
            EndPoints.getOrdersUID(),
            jsonObject,
            Response.Listener {

                val array = it.getJSONArray("data")



                if(array!=null) {

                    for (i in 0 until array.length()) {

                        val position: JSONObject = array.getJSONObject(i)
                        var user =position.getJSONObject("user")


                        var email = "X"

                        if(user.has("email")){
                            email = user.getString("email")
                        }else{
                            continue
                        }




                        var shippingAddress  = position.getJSONObject("shippingAddress")

                        var products =position.getJSONArray("products")

                        var orderSummary =position.getJSONObject("orderSummary")

                        var orderAmount = 0

                        if(orderSummary.has("ourPrice")){
                            orderAmount = orderSummary.getInt("ourPrice")
                        }else{
                            continue
                        }

                        var amountProducts = products.length()

                        var date =position.getString("date")
                        if(email.equals(sessionManager.getEmail())){

                            var hist = History(date,amountProducts,orderAmount)
                            mList.add(hist)
                        }


                    }

                    adapterHistory = AdapterOrderHIstory(this)
                    adapterHistory.setData(mList)


                    recycler_view_order_history.adapter = adapterHistory

                    recycler_view_order_history.layoutManager = LinearLayoutManager(this)
                    recycler_view_order_history.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))


                }


             //   Log.d("abc",array.toString())


            },
            Response.ErrorListener {
                Log.d("abc",it.toString())
            }

        )
        requestQueue.add(jsonRequest)



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
        }
        return true
    }

    private fun setupToolbar(){
        var toolbar = toolbar
        toolbar.title = "Product details"
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