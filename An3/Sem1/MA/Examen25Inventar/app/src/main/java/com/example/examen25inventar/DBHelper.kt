package com.example.project_kotlin_ma

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.examen25inventar.Produs
import java.io.Serializable
import java.util.ArrayList

class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, "Produs", factory, 1), Serializable {

    var tableName = "Produs"
    var tag = "DB"

    override fun onCreate(db: SQLiteDatabase?) {
        // below is a sqlite query, where column names
        // along with their data types is given
//        val query = ("CREATE TABLE " + "Pinguini" + " ("
//                + ID_COL + " INTEGER PRIMARY KEY, " +
//                NAME_COl + " TEXT," +
//                AGE_COL + " TEXT" + ")")

        val query =
            ("CREATE TABLE " + tableName + " (id INTEGER PRIMARY KEY, nume TEXT, tip TEXT,cantitate INTEGER,pret INTEGER,discount INTEGER,status INTEGER)")

        // we are calling sqlite
        // method for executing our query
        if (db != null) {
            db.execSQL(query)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        // this method is to check if table already exists
        if (db != null) {
            db.execSQL("DROP TABLE IF EXISTS " + tableName)
        }
        onCreate(db)
    }

    // This method is for adding data in our database
    fun add(pinguin: Produs) {
        Log.i(tag, "add " + pinguin.toString())
        // below we are creating
        // a content values variable
        print("\n\n\nadaugat:   " + pinguin.toString() + "\n\n\n")
        val values = ContentValues()

        // we are inserting our values
        // in the form of key-value pair
        values.put("id", pinguin.id)
        values.put("nume", pinguin.nume)
        values.put("tip", pinguin.tip)
        values.put("cantitate", pinguin.cantitate)
        values.put("pret", pinguin.pret)
        values.put("discount", pinguin.discount)
        var x = 0
        if (pinguin.status) {
            x = 1
        }
        values.put("status", x)


        // here we are creating a
        // writable variable of
        // our database as we want to
        // insert value in our database
        val db = this.writableDatabase

        //db.execSQL("DROP TABLE pinguini")

        //db.execSQL("CREATE TABLE Pinguini (id INTEGER, nume TEXT, inaltime INTEGER,greutate INTEGER,stare TEXT,pret INTEGER,specie TEXT,dataNasterii TEXT)")

//        val query = "INSERT INTO Pinguini VALUES (?, ?, ?, ?, ?, ?, ?, ?)"
//
//        val strings = arrayOf(
//            pinguin.id,
//            pinguin.nume,
//            pinguin.inaltime,
//            pinguin.greutate,
//            pinguin.stare,
//            pinguin.pret,
//            pinguin.specie,
//            pinguin.dataNasterii
//        )
//
//        db.execSQL(query, strings)


        // all values are inserted into database
        db.insert(tableName, null, values)

        // at last we are
        // closing our database
        db.close()
    }

    fun update(pinguin: Produs, id: Int) {
        Log.i(tag, "update " + "id= " + id.toString() + " " + pinguin.toString())
        // below we are creating
        // a content values variable
        val values = ContentValues()

        // we are inserting our values
        // in the form of key-value pair
        values.put("nume", pinguin.nume)
        values.put("tip", pinguin.tip)
        values.put("cantitate", pinguin.cantitate)
        values.put("pret", pinguin.pret)
        values.put("discount", pinguin.discount)
        var x = 0
        if (pinguin.status) {
            x = 1
        }
        values.put("status", x)

        val db = this.writableDatabase

        db.update(tableName, values, "id=?", arrayOf(id.toString()))

        db.close()
    }

    fun deleteAll() {
        Log.i(tag, "delete all")
        val db = this.writableDatabase
        val query =
            ("delete from " + tableName + ";")

        // we are calling sqlite
        // method for executing our query
        if (db != null) {
            db.execSQL(query)
        }
        db.close()
    }

    fun delete(id: Int) {
        Log.i(tag, "delete " + id.toString())
        val db = this.writableDatabase

//        val query =
//            ("delete from Pinguini;")
//
//        // we are calling sqlite
//        // method for executing our query
//        if (db != null) {
//            db.execSQL(query)
//        }

        db.delete(tableName, "id=?", arrayOf(id.toString()))
        db.close()
    }

    // below method is to get
    // all data from our database
    fun getAll(): ArrayList<Produs>? {
        Log.i(tag, "getAll")

        // here we are creating a readable
        // variable of our database
        // as we want to read value from it
        val db = this.readableDatabase

        // below code returns a cursor to
        // read data from the database
        var cursor = db.rawQuery("SELECT * FROM " + tableName, null)

        //var livedata = MutableLiveData<ArrayList<Pinguin>>()
        var list = ArrayList<Produs>()
        if (cursor.count != 0) {
            cursor!!.moveToFirst()
            var x = false
            if (cursor.getInt(cursor.getColumnIndex("status")) == 1) {
                x = true
            }
            var p = Produs(
                cursor.getString(cursor.getColumnIndex("nume")),
                cursor.getString(cursor.getColumnIndex("tip")),
                cursor.getInt(cursor.getColumnIndex("cantitate")),
                cursor.getInt(cursor.getColumnIndex("pret")),
                cursor.getInt(cursor.getColumnIndex("discount")),
                x
            )
            p.id = cursor.getInt(cursor.getColumnIndex("id"))

            list.add(p)

            while (cursor.moveToNext()) {
                var x = false
                if (cursor.getInt(cursor.getColumnIndex("status")) == 1) {
                    x = true
                }
                var p3 = Produs(
                    cursor.getString(cursor.getColumnIndex("nume")),
                    cursor.getString(cursor.getColumnIndex("tip")),
                    cursor.getInt(cursor.getColumnIndex("cantitate")),
                    cursor.getInt(cursor.getColumnIndex("pret")),
                    cursor.getInt(cursor.getColumnIndex("discount")),
                    x
                )
                p3.id = cursor.getInt(cursor.getColumnIndex("id"))
                list.add(p3)
            }
        }
        cursor.close()

        //livedata.value = list
        print(list)
        return list;

    }
}