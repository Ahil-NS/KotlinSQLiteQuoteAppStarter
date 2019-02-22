package com.example.kotlinsqlitequoteappstarter.Data.Adapter

import android.app.AlertDialog
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.kotlinsqlitequoteappstarter.Data.QuoteDatabaseHandler
import com.example.kotlinsqlitequoteappstarter.Model.Quote
import com.example.kotlinsqlitequoteappstarter.R
import kotlinx.android.synthetic.main.popup.view.*

class QuoteListAdapter(
    private val list: ArrayList<Quote>,
    private val context: Context
) : RecyclerView.Adapter<QuoteListAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindViews(list[position])
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

               editBtn.id -> {
                   editQuote(quote)
               }


           }
        }

        fun deleteQuote(id: Int) {

            var db = QuoteDatabaseHandler(mContext)
            db.deleteQuote(id)

        }

        fun editQuote( quote: Quote) {

            var dialogBuilder: AlertDialog.Builder?
            var dialog: AlertDialog?
            var dbHandler = QuoteDatabaseHandler(context)

            var view = LayoutInflater.from(context).inflate(R.layout.popup, null)
            var quoteTitle = view.popQuoteText
            var quoteUser = view.popQuoteUserText
            var saveButton = view.popQuoteStarterButton

            dialogBuilder = AlertDialog.Builder(context).setView(view)
            dialog = dialogBuilder!!.create()
            dialog?.show()

            saveButton.setOnClickListener {
                var title = quoteTitle.text.toString().trim()
                var quoteUser =  quoteUser.text.toString().trim()


                if (!TextUtils.isEmpty(title)
                    && !TextUtils.isEmpty(quoteUser)
                   ) {

                    quote.quoteTitle = title
                    quote.createdBy = quoteUser

                    dbHandler!!.updateQuote(quote)
                    notifyItemChanged(adapterPosition, quote)
                    dialog!!.dismiss()

                } else {

                }
            }
        }
    }

}