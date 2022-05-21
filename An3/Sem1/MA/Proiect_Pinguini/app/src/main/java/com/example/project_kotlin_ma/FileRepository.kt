package com.example.project_kotlin_ma

import androidx.lifecycle.MutableLiveData
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

    override fun getAll(): ArrayList<Pinguin>? {
        return this.readFile()
    }

    fun readFile(): ArrayList<Pinguin>? {
        if (file.exists()) {
            val inputAsString = FileInputStream(file).bufferedReader().use { it.readText() }
            val input = inputAsString.split("\n")
            var list = ArrayList<Pinguin>()
            for (i in input) {
                if (i != "") {
                    var pinguinJson = JSONObject(i.toString())
                    var pinguin = Pinguin(
                        pinguinJson.getString("nume"),
                        pinguinJson.getString("inaltime").toFloat(),
                        pinguinJson.getString("greutate").toFloat(),
                        Stare.valueOf(pinguinJson.getString("stare")),
                        pinguinJson.getString("pret").toFloat(),
                        pinguinJson.getString("specie"),
                        pinguinJson.getString("dataNasterii")
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

    fun writeFile(list: ArrayList<Pinguin>) {
        file.delete()
        for (pinguin in list) {
            var json = Gson().toJson(pinguin, Pinguin::class.java)
            //var json = "gson.toJson(pinguin, Pinguin::class.java)"


            file.appendText(json.toString())
            file.appendText("\n")
        }
    }

    override fun add(pinguin: Pinguin) {
        var list = readFile()
        if (list != null) {
            list.add(pinguin)
            writeFile(list)
        } else {
            var list2 = ArrayList<Pinguin>()
            list2.add(pinguin)
            writeFile(list2)
        }
    }

    override fun update(id: Int, pinguin: Pinguin) {
        var list = readFile()
        if (list != null) {
            for (p in list) {
                if (p.id == id) {
                    p.inaltime = pinguin.inaltime
                    p.dataNasterii = pinguin.dataNasterii
                    p.greutate = pinguin.greutate
                    p.id = pinguin.id
                    p.nume = pinguin.nume
                    p.pret = pinguin.pret
                    p.stare = pinguin.stare
                    p.specie = pinguin.specie
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