package com.example.kotlinsqlitequoteappstarter.Data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import com.example.kotlinsqlitequoteappstarter.Model.Quote
import com.example.kotlinsqlitequoteappstarter.Utils.*

class QuoteDatabaseHandler(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

    override fun onCreate(db: SQLiteDatabase?) {
        var CREATE_QUOTE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" + KEY_ID + " INTEGER PRIMARY KEY," +
                KEY_QUOTE_TITLE + " TEXT," +
                KEY_QUOTE_CREATED_BY + " TEXT," +
                KEY_QUOTE_CREATED_TIME + " LONG" + ")"

        db?.execSQL(CREATE_QUOTE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    fun createChore(quote: Quote) {
        var db: SQLiteDatabase = writableDatabase

        var values: ContentValues = ContentValues()
        values.put(KEY_QUOTE_TITLE, quote.quoteTitle)
        values.put(KEY_QUOTE_CREATED_BY, quote.createdBy)
        values.put(KEY_QUOTE_CREATED_TIME, System.currentTimeMillis())

        var insert = db.insert(TABLE_NAME, null, values)

        Log.d("DATA INSERTED", "SUCCESS $insert")
        db.close()

    }


}