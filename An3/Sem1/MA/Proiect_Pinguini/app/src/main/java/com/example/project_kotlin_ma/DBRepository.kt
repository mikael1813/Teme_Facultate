package com.example.project_kotlin_ma

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.io.File
import java.io.Serializable

class DBRepository : Repository,Serializable {
    lateinit var db: DBHelper

    constructor(db: DBHelper) {
        this.db = db

    }



    override fun add(pinguin: Pinguin) {
        db.add(pinguin)
    }

    override fun update(id: Int, pinguin: Pinguin) {
        db.update(pinguin, id)
    }

    override fun delete(id: Int) {
        var x = db.delete(id)
        var y = x
    }

    override fun getAll(): ArrayList<Pinguin>? {
        return db.getAll()
    }
}