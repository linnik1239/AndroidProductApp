package com.example.practice12.Models

import android.app.Application

class UserList: Application()
{
    companion object{
        var names: ArrayList<String>  =  ArrayList<String>()
        var passwords: ArrayList<String>  =  ArrayList<String>()


        fun checkUser(name:String,password:String):Boolean{
            var index = names.indexOf(name)
            if(index == -1){
                return false

            }

            if(passwords[index]==password){
                return true
            }
            else
            {
                return false
            }


        }


        fun addUSer(name:String,password:String){

            names.add(name)
            passwords.add(password)

        }




    }

}