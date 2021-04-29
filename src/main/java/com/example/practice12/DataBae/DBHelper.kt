package com.example.practice12.DataBae

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import com.example.myapplication.models.Product

class DBHelper(var mContext: Context): SQLiteOpenHelper(mContext,DATABASE_NAME,null,DATABASE_VERSION) {
    var db = writableDatabase
    companion object{
        const val DATABASE_NAME = "mydb12"
        const val DATABASE_VERSION =1
        const val TABLE_NAME = "product"
        const val COLUMN_IMAGE_NAME = "image"
        const val COLUMN_NAME = "name"
        const val COLUMN_DESCRIPTION = "description"
    }

    override fun onCreate(db: SQLiteDatabase?) {
      //  val createTable = "create table $TABLE_NAME ( $COLUMN_NAME char(200), $COLUMN_DESCRIPTION varchar(200), $COLUMN_IMAGE_NAME char(200))"
        val createTable = "create table $TABLE_NAME ($COLUMN_NAME char(50), $COLUMN_DESCRIPTION varchar(250), $COLUMN_IMAGE_NAME char(50))"

        db?.execSQL(createTable)
        Log.d("abc","onCreate")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        Log.d("abc","onUpgrade")
        val dropTable = "drop table product"
        db?.execSQL(dropTable)
        onCreate(db)

    }

    fun addProduct(product: Product){
        db = writableDatabase

        var contentValue = ContentValues()



     //  contentValue.put("COLUMN_IMAGE_NAME",product.image)
       // contentValue.put("COLUMN_IMAGE_NAME",52345)


        contentValue.put("COLUMN_DESCRIPTION",product.description)
        contentValue.put("COLUMN_NAME",product.name)
       // contentValue.put("COLUMN_NAME",5345243)


//
//        var someId = "543243"
//        var whereCause = "$someId = ?"
//        var WhereArgs = arrayOf(someId)

        db.insert(TABLE_NAME,null,contentValue)

       // return db.update(TABLE_NAME,contentValue,whereCause,WhereArgs)

    }

}