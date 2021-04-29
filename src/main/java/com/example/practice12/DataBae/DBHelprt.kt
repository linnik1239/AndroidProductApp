package com.example.practice12.DataBae

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.myapplication.models.Product
import com.example.practice12.Models.Employee

class DBHelprt(var mContext: Context):SQLiteOpenHelper(mContext,DATABASE_NAME,null,DATABASE_VERSION) {
     var db=writableDatabase

    companion object{
        const val DATABASE_NAME = "mydb31"
        const val DATABASE_VERSION = 2
        const val TABLE_NAME = "product"
        const val COLUMN_IMAGE_NAME = "image"
        const val COLUMN_NAME = "name"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_ID = "_id"

        const val COLUMN_PRICE = "price"
        const val COLUMN_MRP = "mrp"
        const val COLUMN_AMOUNT = "amount"


    }


    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "create table $TABLE_NAME ($COLUMN_ID char(30), $COLUMN_IMAGE_NAME char(50), $COLUMN_NAME char(50), $COLUMN_DESCRIPTION varchar(200), $COLUMN_PRICE integer, $COLUMN_MRP integer, $COLUMN_AMOUNT integer )"
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
        var contentValues = ContentValues()
        contentValues.put(COLUMN_ID,product._id)
        contentValues.put(COLUMN_IMAGE_NAME,product.image)
        contentValues.put(COLUMN_NAME,product.name)

        contentValues.put(COLUMN_DESCRIPTION,product.description)

        contentValues.put(COLUMN_PRICE,product.price)
        contentValues.put(COLUMN_MRP,product.mrp)

        contentValues.put(COLUMN_AMOUNT,product.amount)




        db.insert(TABLE_NAME,null,contentValues)
        Log.d("abc","add product")

    }

    fun updateProduct(product:Product):Int{
        var contentValue = ContentValues()






        contentValue.put(COLUMN_ID,product._id)
        contentValue.put(COLUMN_IMAGE_NAME,product.image)
        contentValue.put(COLUMN_NAME,product.name)

        contentValue.put(COLUMN_DESCRIPTION,product.description)

        contentValue.put(COLUMN_PRICE,product.price)
        contentValue.put(COLUMN_MRP,product.mrp)
        contentValue.put(COLUMN_AMOUNT,product.amount)

//
//
//        contentValue.put(COLUMN_NAME,employee.name)
//        contentValue.put(COLUMN_EMAIL,employee.email)

        var whereCause = "$COLUMN_ID = ?"
        var whereArgs = arrayOf(product._id.toString())
        return db.update(TABLE_NAME,
        contentValue,whereCause,whereArgs)

    }

//
    fun deleteProduct(product:Product):Int{
        Log.d("abc","Delete0 product")

        var whereClause = "$COLUMN_ID = ?"
        var whereArgs = arrayOf(product._id)
        Log.d("abc","Delete product")

        return db.delete(TABLE_NAME,whereClause,whereArgs)
    }
//

    fun getAllProduccts():List<Product>{
        var list : ArrayList<Product> = ArrayList()
        var columns = arrayOf(
            COLUMN_ID,
            COLUMN_IMAGE_NAME,
            COLUMN_NAME,
            COLUMN_DESCRIPTION,
            COLUMN_PRICE,
            COLUMN_MRP,
            COLUMN_AMOUNT
        )
        var cursor = db.query(TABLE_NAME,columns,null,null,null,null,null)
        if(cursor != null && cursor.moveToFirst()){
            do{
                var image = cursor.getString(cursor.getColumnIndex(COLUMN_IMAGE_NAME))
                var name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
                var description = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPTION))
                var price = cursor.getDouble(cursor.getColumnIndex(COLUMN_PRICE))

                var amount = cursor.getInt(cursor.getColumnIndex(COLUMN_AMOUNT))
                var _id = cursor.getString(cursor.getColumnIndex(COLUMN_ID))
                var mrp = cursor.getDouble(cursor.getColumnIndex(COLUMN_MRP))

                var product = Product(_id,name,name,image,price,mrp,amount)
                list.add(product)
            }while(cursor.moveToNext())
        }



        return list
    }

//
//    fun getEmployeeId(id:Int):Employee?{
//        var employee:Employee? = null
//        var query = "select * from $TABLE_NAME where $COLUMN_ID = ?"
//        var cursor = db.rawQuery(query,arrayOf(id.toString()))
//
//        if (cursor!=null){
//            var id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
//            var name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
//
//            var email = cursor.getString(cursor.getColumnIndex(COLUMN_EMAIL))
//
//            employee = Employee(id,name,email)
//        }
//        return employee
//
//
//
//    }




}