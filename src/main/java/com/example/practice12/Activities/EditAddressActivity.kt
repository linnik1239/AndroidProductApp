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
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.practice12.MainActivity
import com.example.practice12.Models.Address
import com.example.practice12.Models.EndPoints
import com.example.practice12.R
import com.example.practice12.SessionManager
import kotlinx.android.synthetic.main.activity_edit_address.*
import kotlinx.android.synthetic.main.app_bar.*
import org.json.JSONObject

class EditAddressActivity : AppCompatActivity() {
    var address_id:String? = null

    lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_address)
        setupToolbar()
        init()

    }


    fun init(){
        var address = intent.getSerializableExtra("ADDRESS") as? Address
        home_num.setText(address?.houseNum)
        streat_name.setText(address?.streatName)
        city.setText(address?.city)
        type.setText(address?.type)
        address_id = address?._id

        update.setOnClickListener {
            var streat = streat_name.text.toString()
            var house_num = home_num.text.toString()
            var city = city.text.toString()
            var type = type.text.toString()


            var newAddress:Address? = Address(streat,house_num,type,city,address_id.toString())


           // var theNewURLPostAddress =  "http://grocery-second-app.herokuapp.com/api/address/"+address_id

            var requestQueue2 = Volley.newRequestQueue(this)
            var jsonObject2 = JSONObject()




            jsonObject2.put("houseNo",house_num)
            jsonObject2.put("streetName",streat)
            jsonObject2.put("city",city)

            jsonObject2.put("type",type)
           // jsonObject2.put("userId","603c691772642f00171f30c2")
            jsonObject2.put("userId",EndPoints.getUserID())

            jsonObject2.put("pincode",65445)




            var jsonRequest2 = JsonObjectRequest(
                Request.Method.PUT,
                EndPoints.getAddressAddId(address_id),
                jsonObject2,
                Response.Listener {


                    startActivity(Intent(this,AddressActivity::class.java))

                },
                Response.ErrorListener {
                    Log.d("abc", it.toString())
                }
            )
            requestQueue2.add(jsonRequest2)




        }
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