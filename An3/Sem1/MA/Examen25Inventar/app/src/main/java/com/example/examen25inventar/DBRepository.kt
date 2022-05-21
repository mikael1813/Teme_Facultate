package com.example.project_kotlin_ma

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.examen25inventar.Produs
import java.io.File
import java.io.Serializable
import java.lang.Exception

class DBRepository : Repository, Serializable {
    var db: DBHelper
    var context: Context

    constructor(db: DBHelper, context: Context) {
        this.db = db
        this.context = context
    }


    override fun add(Produs: Produs) {
        try {
            db.add(Produs)
        } catch (e: Exception) {
            Toast.makeText(context, "Caught exception " + e.toString(), Toast.LENGTH_LONG).show()
        }
    }

    override fun update(id: Int, Produs: Produs) {
        try {
            db.update(Produs, id)
        } catch (e: Exception) {
            Toast.makeText(context, "Caught exception " + e.toString(), Toast.LENGTH_LONG).show()
        }
    }

    override fun delete(id: Int) {
        try {
            var x = db.delete(id)
        } catch (e: Exception) {
            Toast.makeText(context, "Caught exception " + e.toString(), Toast.LENGTH_LONG).show()
        }

    }

    override fun getAll(): ArrayList<Produs>? {
        try {
            return db.getAll()
        } catch (e: Exception) {
            Toast.makeText(context, "Caught exception " + e.toString(), Toast.LENGTH_LONG).show()
            return null
        }
    }
}