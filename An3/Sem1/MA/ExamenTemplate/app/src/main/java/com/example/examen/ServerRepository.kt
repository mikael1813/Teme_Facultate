package com.example.project_kotlin_ma

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.examen.Produs

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.Serializable
import java.lang.Exception


class ServerRepository : Serializable {

    var retrofitBuilder: ApiInterface
    var tag = "SERVER"
    lateinit var context: Context

    var BASE_URL = "http://10.0.2.2:2028/"
    //val BASE_URL = "https://jsonplaceholder.typicode.com/"

    constructor() {
        retrofitBuilder = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
    }

    fun add(pinguin: Produs, onResult: (Int?) -> Unit) {
        Log.i(tag, "add " + pinguin.toString())
        try {
            val retrofitData = retrofitBuilder.addPengunin(pinguin)

            retrofitData.enqueue(object : Callback<Int?> {
                override fun onResponse(call: Call<Int?>, response: Response<Int?>) {
                    //Toast.makeText(context, "Add succes ", Toast.LENGTH_LONG).show()
                    val addedPenguin = response.body()
                    onResult(addedPenguin)
                }

                override fun onFailure(call: Call<Int?>, t: Throwable) {
                    onResult(null)
                }
            })
        } catch (e: Exception) {
            Toast.makeText(context, "Caught exception " + e.toString(), Toast.LENGTH_LONG).show()
            onResult(null)
        }
    }

    fun update(id: Int, pinguin: Produs, onResult: (Produs?) -> Unit) {
        Log.i(tag, "update " + pinguin.toString())
        try {
            val retrofitData = retrofitBuilder.updatePengunin(id, pinguin)

            retrofitData.enqueue(object : Callback<Produs?> {
                override fun onResponse(call: Call<Produs?>, response: Response<Produs?>) {
                    val updatedPenguin = response.body()
                    onResult(updatedPenguin)
                }

                override fun onFailure(call: Call<Produs?>, t: Throwable) {
                    onResult(null)
                }
            })
        } catch (e: Exception) {
            Toast.makeText(context, "Caught exception " + e.toString(), Toast.LENGTH_LONG).show()
            onResult(null)
        }
    }

    fun delete(id: Int, onResult: (Produs?) -> Unit) {
        Log.i(tag, "delete " + id.toString())
        try {
            val retrofitData = retrofitBuilder.deletePenguin(id)

            retrofitData.enqueue(object : Callback<Produs?> {
                override fun onResponse(call: Call<Produs?>, response: Response<Produs?>) {
                    val deletedPenguin = response.body()
                    onResult(deletedPenguin)
                }

                override fun onFailure(call: Call<Produs?>, t: Throwable) {
                    onResult(null)
                }
            })
        } catch (e: Exception) {
            Toast.makeText(context, "Caught exception " + e.toString(), Toast.LENGTH_LONG).show()
            onResult(null)
        }
    }

    fun getAll(onResult: (ArrayList<Produs>?) -> Unit) {
        Log.i(tag, "getAll")
        try {
            val retrofitData = retrofitBuilder.getData()
            //var ok = retrofitData.execute().isSuccessful


            //txt.text = ""

            retrofitData.enqueue(object : Callback<List<Produs>?> {
                override fun onResponse(
                    call: Call<List<Produs>?>?,
                    response: Response<List<Produs>?>?
                ) {
                    val responseBody = response!!.body()!!
                    var list: ArrayList<Produs> = ArrayList()
                    //txt.text = "AAAAAAAAAAAAAA"

                    for (data in responseBody) {
                        var p = Produs(
                            data.nume,
                            data.medie,
                            data.etaj,
                            data.orientare,
                            data.camera,
                            data.status
                        )
                        p.id = data.id
                        list.add(p)
                        //txt.text = txt.text.toString() + data.nume
                    }

                    onResult(list)
                }

                override fun onFailure(call: Call<List<Produs>?>?, t: Throwable?) {

                    onResult(null)
                    //txt.text = t!!.stackTraceToString()
                }
            })
        } catch (e: Exception) {
            Toast.makeText(context, "Caught exception " + e.toString(), Toast.LENGTH_LONG).show()
            onResult(null)
        }
        //return list
    }

}

//class OnGetPenguinsListener {
//    var list : List<Pinguin> = ArrayList<Pinguin>()
//    var error = ""
//
//    fun onGetPenguinsSuccess(pinguinList: List<Pinguin>) {
//        list = pinguinList
//    }
//
//    fun onGetPenguinsFailure(errorMessage: String) {
//        error = errorMessage
//    }
//}