package com.example.practice12.Activities

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.practice12.MainActivity
import com.example.practice12.Models.EndPoints
import com.example.practice12.R
import com.example.practice12.SessionManager
import kotlinx.android.synthetic.main.activity_add_address.*
import kotlinx.android.synthetic.main.activity_address.*
import kotlinx.android.synthetic.main.activity_address.AddressActivity_add_address
import kotlinx.android.synthetic.main.app_bar.*
import org.json.JSONObject

class AddAddressActivity : AppCompatActivity() {
    lateinit var sessionManager: SessionManager

    var type = "Office"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_address)
        setupToolbar()

        AddAddressActivity_add_address.setOnClickListener {
            var streatName = AddAddressActivity_streat_name.text.toString()
            var houseNum = AddAddressActivity_house_num.text.toString()
           // var type = AddAddressActivity_type.text.toString()
            var city = AddAddressActivity_city.text.toString()


            var jsonObject = JSONObject()
            jsonObject.put("houseNo",houseNum)
            jsonObject.put("streetName",streatName)
            jsonObject.put("city",city)

            jsonObject.put("type",type)
            jsonObject.put("userId",EndPoints.getUserID())
            jsonObject.put("pincode",65445)


            //var theURLPostAddress = "http://grocery-second-app.herokuapp.com/api/address"

            var requestQueue = Volley.newRequestQueue(this)
            var jsonRequest = JsonObjectRequest(
                Request.Method.POST,
                EndPoints.getOnlyAddress(),
                jsonObject,
                Response.Listener {
                    startActivity(Intent(this,AddressActivity::class.java))
                },
                Response.ErrorListener {
                    Log.d("abc", it.toString())
                }

            )
            requestQueue.add(jsonRequest)

        }

    }


    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked

            // Check which radio button was clicked
            when (view.getId()) {
                R.id.radio_home ->
                    if (checked) {
                        type =  radio_home.text.toString()
                        // Pirates are the best
                    }
                R.id.radio_office ->
                    if (checked) {
                        type = radio_office.text.toString()
                    }
                else ->{
                    if (checked) {// Ninjas rule
                        type = radio_other.text.toString()
                    }
                }
            }
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
            R.id.menu_order_history -> {
                intent = Intent(this,OrderHistoryActivity::class.java)
                startActivity(intent)
            }

        }
        return true
    }

    private fun setupToolbar(){
        var toolbar = toolbar
        toolbar.title = "New addresses"
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