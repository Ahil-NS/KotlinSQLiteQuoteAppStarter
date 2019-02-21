package com.example.kotlinsqlitequoteappstarter.Model

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
}