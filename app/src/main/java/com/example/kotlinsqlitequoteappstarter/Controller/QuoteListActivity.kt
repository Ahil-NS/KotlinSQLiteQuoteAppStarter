package com.example.kotlinsqlitequoteappstarter.Controller

import android.app.AlertDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.example.kotlinsqlitequoteappstarter.Data.Adapter.QuoteListAdapter
import com.example.kotlinsqlitequoteappstarter.Data.QuoteDatabaseHandler
import com.example.kotlinsqlitequoteappstarter.Model.Quote
import com.example.kotlinsqlitequoteappstarter.R
import kotlinx.android.synthetic.main.activity_quote_list.*
import kotlinx.android.synthetic.main.popup.view.*

class QuoteListActivity : AppCompatActivity() {

    private var adapter: QuoteListAdapter? = null
    private var quoteList: ArrayList<Quote>? = null
    private var quoteListItems: ArrayList<Quote>? = null


    private var layoutManager: RecyclerView.LayoutManager? = null
    var dbHandler: QuoteDatabaseHandler? = null

    private var dialogBuilder: android.app.AlertDialog.Builder? = null
    private var dialog: android.app.AlertDialog? = null

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
        quoteList!!.reverse()

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
        createPopupDialog()
        return super.onOptionsItemSelected(item)
    }

    fun createPopupDialog() {

        var view = layoutInflater.inflate(R.layout.popup, null)
        var quoteTitle = view.popQuoteText
        var quoteUser = view.popQuoteUserText
        var saveButton = view.popQuoteStarterButton

        dialogBuilder = AlertDialog.Builder(this).setView(view)
        dialog = dialogBuilder!!.create()
        dialog?.show()

        saveButton.setOnClickListener {
            var title = quoteTitle.text.toString().trim()
            var createdBy =  quoteUser.text.toString().trim()

            if (!TextUtils.isEmpty(title)
                && !TextUtils.isEmpty(createdBy)
                ) {
                var quote = Quote()

                quote.quoteTitle = title
                quote.createdBy = createdBy

                dbHandler!!.createQuote(quote)

                dialog!!.dismiss()

                startActivity(Intent(this, QuoteListActivity::class.java))
                finish()




            } else {

            }
        }


    }

}
