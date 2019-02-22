package com.example.kotlinsqlitequoteappstarter.Data.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.kotlinsqlitequoteappstarter.Data.QuoteDatabaseHandler
import com.example.kotlinsqlitequoteappstarter.Model.Quote
import com.example.kotlinsqlitequoteappstarter.R

class QuoteListAdapter(
    private val list: ArrayList<Quote>,
    private val context: Context
) : RecyclerView.Adapter<QuoteListAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder?.bindViews(list[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        //create our view from our xml file
        val view = LayoutInflater.from(context)
            .inflate(R.layout.quote_list_item, parent, false)

        return ViewHolder(view,context,list)

    }

    override fun getItemCount(): Int {
        return list.size
    }

    //make class inner to invoke certain functions
    inner class ViewHolder(itemView: View, context: Context, list: ArrayList<Quote>) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        var mContext =context
        var mList = list

        var quoteTitle = itemView.findViewById<TextView>(R.id.quoteTitleText)
        var quoteUser = itemView.findViewById(R.id.popQuoteUserText) as TextView
        var quoteCreatedDate = itemView.findViewById(R.id.quoteDateText) as TextView

        var editBtn = itemView.findViewById<Button>(R.id.editButton)
        var deleteBtn = itemView.findViewById<Button>(R.id.deleteButton)

        fun bindViews(quote: Quote) {

            quoteTitle.text = quote.quoteTitle
            quoteUser.text = quote.createdBy
            quoteCreatedDate.text = quote.showHumanDate(System.currentTimeMillis())

            editBtn.setOnClickListener(this)
            deleteBtn.setOnClickListener(this)

        }

        override fun onClick(v: View?) {

            var mPosition: Int = adapterPosition
            var quote = mList[mPosition]

           when(v!!.id){

               deleteBtn.id ->{

                   deleteQuote(quote.id!!)
                   mList.removeAt(adapterPosition)
                   notifyItemRemoved(adapterPosition)
                   Toast.makeText(mContext,"edit",Toast.LENGTH_SHORT).show()
               }


           }
        }

        fun deleteQuote(id: Int) {

            var db: QuoteDatabaseHandler = QuoteDatabaseHandler(mContext)
            db.deleteQuote(id)

        }

    }

}