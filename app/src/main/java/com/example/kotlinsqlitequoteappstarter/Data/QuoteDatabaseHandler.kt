package com.example.kotlinsqlitequoteappstarter.Data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import com.example.kotlinsqlitequoteappstarter.Model.Quote
import com.example.kotlinsqlitequoteappstarter.Utils.*
import java.text.DateFormat
import java.util.*

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

    fun createQuote(quote: Quote) {
        var db: SQLiteDatabase = writableDatabase

        var values: ContentValues = ContentValues()
        values.put(KEY_QUOTE_TITLE, quote.quoteTitle)
        values.put(KEY_QUOTE_CREATED_BY, quote.createdBy)
        values.put(KEY_QUOTE_CREATED_TIME, System.currentTimeMillis())

        var insert = db.insert(TABLE_NAME, null, values)

        Log.d("DATA INSERTED", "SUCCESS $insert")
        db.close()

    }

    fun readQuote(id: Int): Quote{
        var db: SQLiteDatabase = writableDatabase

        //Cursor help to fetch data from table
        var cursor: Cursor = db.query(TABLE_NAME, arrayOf(KEY_ID, KEY_QUOTE_TITLE, KEY_QUOTE_CREATED_BY,
            KEY_QUOTE_CREATED_TIME), KEY_ID + "=?", arrayOf(id.toString()),null,null,null,null)

        if(cursor != null){
            cursor.moveToFirst()
        }
        var quote = Quote()
        quote.id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
        quote.quoteTitle = cursor.getString(cursor.getColumnIndex(KEY_QUOTE_TITLE))
        quote.createdBy = cursor.getString(cursor.getColumnIndex(KEY_QUOTE_CREATED_BY))
        quote.timeCreated = cursor.getLong(cursor.getColumnIndex(KEY_QUOTE_CREATED_TIME))

        var dateFormat: java.text.DateFormat = DateFormat.getDateInstance()
        var formattedDate: String = dateFormat.format(Date(cursor.getLong(cursor.getColumnIndex(KEY_QUOTE_CREATED_TIME))).time)
        return quote

    }

    fun readQuotes(): ArrayList<Quote> {


        var db: SQLiteDatabase = readableDatabase
        var list: ArrayList<Quote> = ArrayList()

        //Select all quotes from table
        var selectAll = "SELECT * FROM " + TABLE_NAME

        var cursor: Cursor = db.rawQuery(selectAll, null)

        //loop through our quotes
        if (cursor.moveToFirst()) {
            do {
                var quote = Quote()

                quote.id = cursor.getInt(cursor.getColumnIndex(KEY_ID))
                quote.quoteTitle = cursor.getString(cursor.getColumnIndex(KEY_QUOTE_TITLE))
                quote.createdBy = cursor.getString(cursor.getColumnIndex(KEY_QUOTE_CREATED_BY))
                quote.timeCreated = cursor.getLong(cursor.getColumnIndex(KEY_QUOTE_CREATED_TIME))
                list.add(quote)

            }while (cursor.moveToNext())
        }
        return list

    }

    fun updateQuote(quote: Quote): Int {
        var db: SQLiteDatabase = writableDatabase

        var values: ContentValues = ContentValues()
        values.put(KEY_QUOTE_TITLE, quote.quoteTitle)
        values.put(KEY_QUOTE_CREATED_BY, quote.createdBy)
        values.put(KEY_QUOTE_CREATED_TIME, System.currentTimeMillis())

        //update a row
        return db.update(TABLE_NAME, values, KEY_ID + "=?", arrayOf(quote.id.toString()))
    }


    fun deleteQuote(id: Int) {
        var db: SQLiteDatabase = writableDatabase
        db.delete(TABLE_NAME, KEY_ID + "=?", arrayOf(id.toString()))

        db.close()

    }

    fun getQuotesCount(): Int {
        var db: SQLiteDatabase = readableDatabase
        var countQuery = "SELECT * FROM " + TABLE_NAME
        var cursor: Cursor = db.rawQuery(countQuery, null)

        return cursor.count

    }




}