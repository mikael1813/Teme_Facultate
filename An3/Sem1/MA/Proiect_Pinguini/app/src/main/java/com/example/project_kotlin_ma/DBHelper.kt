package com.example.project_kotlin_ma

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.io.Serializable
import java.util.ArrayList

class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) :
    SQLiteOpenHelper(context, "Pinguin", factory, 1), Serializable {


    override fun onCreate(db: SQLiteDatabase?) {
        // below is a sqlite query, where column names
        // along with their data types is given
//        val query = ("CREATE TABLE " + "Pinguini" + " ("
//                + ID_COL + " INTEGER PRIMARY KEY, " +
//                NAME_COl + " TEXT," +
//                AGE_COL + " TEXT" + ")")

        val query =
            ("CREATE TABLE Pinguini (id INTEGER PRIMARY KEY, nume TEXT, inaltime INTEGER,greutate INTEGER,stare TEXT,pret INTEGER,specie TEXT,dataNasterii TEXT)")

        // we are calling sqlite
        // method for executing our query
        if (db != null) {
            db.execSQL(query)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        // this method is to check if table already exists
        if (db != null) {
            db.execSQL("DROP TABLE IF EXISTS " + "Pinguini")
        }
        onCreate(db)
    }

    // This method is for adding data in our database
    fun add(pinguin: Pinguin) {

        // below we are creating
        // a content values variable
        print("\n\n\nadaugat:   " + pinguin.toString() + "\n\n\n")
        val values = ContentValues()

        // we are inserting our values
        // in the form of key-value pair
        values.put("id", pinguin.id)
        values.put("nume", pinguin.nume)
        values.put("inaltime", pinguin.inaltime.toInt())
        values.put("greutate", pinguin.greutate.toInt())
        values.put("dataNasterii", pinguin.dataNasterii)
        values.put("pret", pinguin.pret.toInt())
        values.put("specie", pinguin.specie)
        values.put("stare", pinguin.stare.toString())

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
        db.insert("Pinguini", null, values)

        // at last we are
        // closing our database
        db.close()
    }

    fun update(pinguin: Pinguin, id: Int) {
        // below we are creating
        // a content values variable
        val values = ContentValues()

        // we are inserting our values
        // in the form of key-value pair
        values.put("nume", pinguin.nume)
        values.put("inaltime", pinguin.inaltime)
        values.put("greutate", pinguin.greutate)
        values.put("dataNasterii", pinguin.dataNasterii)
        values.put("pret", pinguin.pret)
        values.put("specie", pinguin.specie)
        values.put("stare", pinguin.stare.toString())

        val db = this.writableDatabase

        db.update("Pinguini", values, "id=?", arrayOf(id.toString()))

        db.close()
    }

    fun delete(id: Int) {
        val db = this.writableDatabase

//        val query =
//            ("delete from Pinguini;")
//
//        // we are calling sqlite
//        // method for executing our query
//        if (db != null) {
//            db.execSQL(query)
//        }

        db.delete("Pinguini", "id=?", arrayOf(id.toString()))
        db.close()
    }

    // below method is to get
    // all data from our database
    fun getAll(): ArrayList<Pinguin>? {


        // here we are creating a readable
        // variable of our database
        // as we want to read value from it
        val db = this.readableDatabase

        // below code returns a cursor to
        // read data from the database
        var cursor = db.rawQuery("SELECT * FROM " + "Pinguini", null)

        //var livedata = MutableLiveData<ArrayList<Pinguin>>()
        var list = ArrayList<Pinguin>()
        if (cursor.count != 0) {
            cursor!!.moveToFirst()
            var p = Pinguin(
                cursor.getString(cursor.getColumnIndex("nume")),
                cursor.getFloat(cursor.getColumnIndex("inaltime")),
                cursor.getFloat(cursor.getColumnIndex("greutate")),
                Stare.valueOf(cursor.getString(cursor.getColumnIndex("stare"))),
                cursor.getFloat(cursor.getColumnIndex("pret")),
                cursor.getString(cursor.getColumnIndex("specie")),
                cursor.getString(cursor.getColumnIndex("dataNasterii"))
            )
            p.id = cursor.getInt(cursor.getColumnIndex("id"))

            list.add(p)

            while (cursor.moveToNext()) {
                var p3 = Pinguin(
                    cursor.getString(cursor.getColumnIndex("nume")),
                    cursor.getFloat(cursor.getColumnIndex("inaltime")),
                    cursor.getFloat(cursor.getColumnIndex("greutate")),
                    Stare.valueOf(cursor.getString(cursor.getColumnIndex("stare"))),
                    cursor.getFloat(cursor.getColumnIndex("pret")),
                    cursor.getString(cursor.getColumnIndex("specie")),
                    cursor.getString(cursor.getColumnIndex("dataNasterii"))
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