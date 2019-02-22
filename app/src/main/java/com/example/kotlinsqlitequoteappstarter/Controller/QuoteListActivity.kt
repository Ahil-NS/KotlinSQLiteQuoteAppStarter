package com.example.kotlinsqlitequoteappstarter.Controller

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.example.kotlinsqlitequoteappstarter.Data.Adapter.QuoteListAdapter
import com.example.kotlinsqlitequoteappstarter.Data.QuoteDatabaseHandler
import com.example.kotlinsqlitequoteappstarter.Model.Quote
import com.example.kotlinsqlitequoteappstarter.R
import kotlinx.android.synthetic.main.activity_quote_list.*

class QuoteListActivity : AppCompatActivity() {

    private var adapter: QuoteListAdapter? = null
    private var quoteList: ArrayList<Quote>? = null
    private var quoteListItems: ArrayList<Quote>? = null


    private var layoutManager: RecyclerView.LayoutManager? = null
    var dbHandler: QuoteDatabaseHandler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quote_list)

        dbHandler = QuoteDatabaseHandler(this)

        quoteList = ArrayList<Quote>()
        quoteListItems = ArrayList()
        layoutManager = LinearLayoutManager(this)
        adapter = QuoteListAdapter(quoteListItems!!, this)

        quoteRecyclerView.layoutManager = layoutManager
        quoteRecyclerView.adapter = adapter

        quoteList = dbHandler!!.readQuotes()
        //quoteList!!.reverse()

        for (c in quoteList!!.iterator()) {

            val quote = Quote()
            quote.quoteTitle = "Quote: ${c.quoteTitle}"
            quote.createdBy = "Created By: ${c.createdBy}"
            quote.id = c.id
            quote.showHumanDate(c.timeCreated!!)

            quoteListItems!!.add(quote)
        }
        adapter!!.notifyDataSetChanged()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if (item!!.itemId == R.id.addMenuButton) {
            Log.d("Item Clicked", "Item Clicked")
        }
        return super.onOptionsItemSelected(item)
    }

}
