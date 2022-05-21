package com.example.examen25inventar

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.annotation.ArrayRes
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project_kotlin_ma.CustomAdapter
import com.example.project_kotlin_ma.ItemsViewModel

class ClientActivity : AppCompatActivity() {

    lateinit var recyclerview: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client)

        recyclerview = findViewById(R.id.recyclerview)

        var tipuri: ArrayList<String> = intent.getSerializableExtra("tipuri") as ArrayList<String>
        var list: ArrayList<Produs> = intent.getSerializableExtra("list") as ArrayList<Produs>

        createRecyclerView(list, tipuri)


        var returnBtn = findViewById<Button>(R.id.buttonReturn)
        returnBtn.setOnClickListener {
            //val intent = Intent(this, MainActivity::class.java)
            //startActivity(intent)
            finish()
        }
    }

    fun createRecyclerView(list: ArrayList<Produs>, tipuri: ArrayList<String>) {

        // getting the recyclerview by its id


        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)

        // ArrayList of class ItemsViewModel
        val data = ArrayList<ItemsViewModel>()
        var IDList = ArrayList<Int>()
        // This loop will create 20 Views containing
        // the image with the count of view

        if (list != null) {
            for (i in 0..list.size - 1) {
                IDList.add(list.get(i).id)
                var model1 = ItemsViewModel(
                    R.drawable.ic_launcher_foreground,
                    list.get(i).id.toString() + "      " + list.get(i).nume + "      " + list.get(i).tip + "      " + list.get(
                        i
                    ).pret.toString() + " RON      " + list.get(i).cantitate.toString()
                )
                var model2 = ItemsViewModel(
                    R.drawable.ic_launcher_foreground,
                    "Tip:      " + tipuri.get(i)
                )
//                model.pinguin.observe(this, Observer { pinguin ->
//                    model.text =
//                        pinguin.nume + "      " + pinguin.stare + "      " + pinguin.pret + " RON"
//                })
//
//                val binding = ActivityMainBinding.inflate(layoutInflater)
//                binding

                data.add(model2)
                data.add(model1)

            }
        }

        // This will pass the ArrayList to our Adapter
        val adapter = CustomAdapter(data)
        adapter.setListID(IDList)

        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter
        adapter.setOnItemClickListener(object : CustomAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {

//                print(position)
//                pos = position

            }


        })

        adapter.setOnItemLongClickListener(object : CustomAdapter.onItemLongClickListener {
            override fun onItemLongClick(position: Int) {
//                print(position)
//                var id = adapter.getIDByPoz(position)
//                var pinguin = service.findByID(id)
//                val intent = Intent(this@MainActivity, DescribeActivity::class.java)
//                intent.putExtra("pinguin", pinguin)
//                startActivity(intent)
            }
        })


    }
}