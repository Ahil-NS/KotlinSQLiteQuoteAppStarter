package com.example.kotlinsqlitequoteappstarter.Model

import java.text.DateFormat
import java.util.*

class Quote() {
    var quoteTitle: String? = null
    var createdBy: String? = null
    var timeCreated: Long? = null
    var id: Int? = null

    constructor(quoteTitle: String,createdBy: String,timeCreated: Long, id:Int) : this(){
        this.quoteTitle = quoteTitle
        this.createdBy = createdBy
        this.timeCreated = timeCreated
        this.id = id
    }

    fun showHumanDate(timeAssigned: Long): String {

        var dateFormat: java.text.DateFormat = DateFormat.getDateInstance()
        var formattedDate: String = dateFormat.format(Date(timeAssigned).time)

        return "Created: ${formattedDate}"

    }
}