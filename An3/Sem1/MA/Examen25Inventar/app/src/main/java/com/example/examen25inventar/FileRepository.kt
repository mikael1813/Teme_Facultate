package com.example.project_kotlin_ma

import androidx.lifecycle.MutableLiveData
import com.example.examen25inventar.Produs
import com.google.gson.Gson
import org.json.JSONObject
import java.io.File
import java.io.FileInputStream

class FileRepository : Repository {

    var file: File
    //var gson = Gson()

    constructor(path: File?, filename: String) {
        file = File(path, filename)

    }

    override fun getAll(): ArrayList<Produs>? {
        return this.readFile()
    }

    fun readFile(): ArrayList<Produs>? {
        if (file.exists()) {
            val inputAsString = FileInputStream(file).bufferedReader().use { it.readText() }
            val input = inputAsString.split("\n")
            var list = ArrayList<Produs>()
            for (i in input) {
                if (i != "") {
                    var pinguinJson = JSONObject(i.toString())
//                    var x = false
//                    if (pinguinJson.getString("status").toInt() == 1) {
//                        x = true
//                    }

                    var pinguin = Produs(
                        pinguinJson.getString("nume"),
                        pinguinJson.getString("tip"),
                        pinguinJson.getString("cantitate").toInt(),
                        pinguinJson.getString("pret").toInt(),
                        pinguinJson.getString("discount").toInt(),
                        pinguinJson.getBoolean("status")
                    )
                    pinguin.id = pinguinJson.getInt("id")
                    list.add(pinguin)
                }
            }
            //var livedata = MutableLiveData<ArrayList<Pinguin>>()
            //livedata.value = list
            return list
        } else {
            return null
        }

    }

    fun writeFile(list: ArrayList<Produs>) {
        file.delete()
        for (pinguin in list) {
            var json = Gson().toJson(pinguin, Produs::class.java)
            //var json = "gson.toJson(pinguin, Pinguin::class.java)"


            file.appendText(json.toString())
            file.appendText("\n")
        }
    }

    override fun add(pinguin: Produs) {
        var list = readFile()
        if (list != null) {
            list.add(pinguin)
            writeFile(list)
        } else {
            var list2 = ArrayList<Produs>()
            list2.add(pinguin)
            writeFile(list2)
        }
    }

    override fun update(id: Int, pinguin: Produs) {
        var list = readFile()
        if (list != null) {
            for (p in list) {
                if (p.id == id) {
                    p.tip = pinguin.tip
                    p.cantitate = pinguin.cantitate
                    p.discount = pinguin.discount
                    p.id = pinguin.id
                    p.nume = pinguin.nume
                    p.pret = pinguin.pret
                    p.status = pinguin.status
                }
            }
        }
        if (list != null) {
            writeFile(list)
        }
    }

    override fun delete(id: Int) {
        var list = readFile()
        if (list != null) {
            for (pinguin in list) {
                if (pinguin.id == id) {
                    list.remove(pinguin)
                    writeFile(list)
                    break
                }
            }
        }
    }

    fun emptyFile() {
        file.delete()
    }
}