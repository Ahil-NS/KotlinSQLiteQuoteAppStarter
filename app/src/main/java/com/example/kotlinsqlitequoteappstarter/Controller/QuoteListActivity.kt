package com.example.kotlinsqlitequoteappstarter.Controller

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
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


        // setup list = recyclerview
        quoteRecyclerView.layoutManager = layoutManager
        quoteRecyclerView.adapter = adapter

        //Load our chores
        quoteList = dbHandler!!.readQuotes()
        //quoteList!!.reverse()

        for (c in quoteList!!.iterator()) {

            val quote = Quote()
            quote.quoteTitle = "Quote: ${c.quoteTitle}"
            quote.createdBy = "Created By: ${c.createdBy}"
            quote.id = c.id
           // quote.showHumanDate(c.timeAssigned!!)


//            Log.d("====ID=====", c.id.toString())
//            Log.d("====Name=====", c.choreName)
//            Log.d("====Date=====", chore.showHumanDate(c.timeAssigned!!))
//            Log.d("====aTo=====", c.assignedTo)
//            Log.d("====aBy=====", c.assignedTo)
            quoteListItems!!.add(quote)


        }
        adapter!!.notifyDataSetChanged()


    }
}
