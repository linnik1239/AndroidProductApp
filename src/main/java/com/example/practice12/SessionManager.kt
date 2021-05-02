package com.example.practice12

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.practice12.Models.User

class SessionManager(val mContext: Context) {
    private val FILE_NAME = "my_pref5"
    private val KEY_NAME = "name"
    private val KEY_EMAIL = "email"
    private val KEY_PASSWORD = "password"
    private val KEY_MOBILE = "mobile"

    private val KEY_IS_LOGGED_IN = "isLoggedIn"

    val masterKey = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)



    var sharedPreferences = EncryptedSharedPreferences.create(
            "my_pref5",
    masterKey,
        mContext,
    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM

    )




    //getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE)

    var editor = sharedPreferences.edit()

    fun register(user: User){
        editor.putString(KEY_NAME,user.name)
        editor.putString(KEY_EMAIL,user.email)
        editor.putString(KEY_PASSWORD,user.password)
        editor.putString(KEY_MOBILE,user.phone)

        editor.putBoolean(KEY_IS_LOGGED_IN,false)
        editor.commit()
    }

    fun setLogin(status:Boolean){
        editor.putBoolean(KEY_IS_LOGGED_IN,status)

    }

    fun isLoggedIN(): Boolean{
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN,false)
    }
    fun login(user:User):Boolean{
        var saveEmail = sharedPreferences.getString(KEY_EMAIL,null)
        var saveName = sharedPreferences.getString(KEY_NAME,null)
        var savePassword = sharedPreferences.getString(KEY_PASSWORD,null)

        return saveName.equals(user.name) && savePassword.equals(user.password)
    }


    fun getName():String?{
        return sharedPreferences.getString(KEY_NAME,null)
    }

    fun getEmail():String?{
        return sharedPreferences.getString(KEY_EMAIL,null)
    }
    fun getMobile():String?{
        return sharedPreferences.getString(KEY_MOBILE,null)
    }


    fun logout(){
        editor.clear()
        editor.commit()
    }







}