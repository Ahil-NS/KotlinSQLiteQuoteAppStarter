package com.example.kotlinsqlitequoteappstarter.Controller

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.kotlinsqlitequoteappstarter.Data.QuoteDatabaseHandler
import com.example.kotlinsqlitequoteappstarter.Model.Quote
import com.example.kotlinsqlitequoteappstarter.R
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    var dbHandler: QuoteDatabaseHandler? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHandler = QuoteDatabaseHandler(this)

        var quote = Quote()
        quote.quoteTitle = "Develop your skills"
        quote.createdBy = "Ahil"
        dbHandler!!.createQuote(quote)

        var readQ: Quote = dbHandler!!.readQuote(1)
        Log.d("DATA INSERTED", "SUCCESS ${readQ.quoteTitle}")
    }
}
