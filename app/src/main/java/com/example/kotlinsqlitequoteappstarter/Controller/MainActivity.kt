package com.example.kotlinsqlitequoteappstarter.Controller

import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.example.kotlinsqlitequoteappstarter.Data.QuoteDatabaseHandler
import com.example.kotlinsqlitequoteappstarter.Model.Quote
import com.example.kotlinsqlitequoteappstarter.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var dbHandler: QuoteDatabaseHandler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHandler = QuoteDatabaseHandler(this)


        getStarterButton.setOnClickListener {
            progressBar.visibility = View.VISIBLE

            if (!TextUtils.isEmpty(quoteText.text.toString())
                && !TextUtils.isEmpty(quoteUserText.text.toString())
                ) {
                //save to database
                var quote = Quote()
                quote.quoteTitle = quoteText.text.toString()
                quote.createdBy = quoteUserText.text.toString()

                saveToDB(quote)
                progressBar.visibility = View.GONE
                startActivity(Intent(this, QuoteListActivity::class.java))


            } else {
                Toast.makeText(this, "Please enter a quote", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun saveToDB(quote: Quote) {
        dbHandler!!.createQuote(quote)
    }
}
