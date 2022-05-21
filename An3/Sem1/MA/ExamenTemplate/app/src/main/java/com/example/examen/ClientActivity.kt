package com.example.examen

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project_kotlin_ma.*
import java.io.File

class ClientActivity : AppCompatActivity() {

    lateinit var recyclerview: RecyclerView
    lateinit var sortedList: ArrayList<Produs>
    var pos: Int = -1
    lateinit var server: ServerRepository
    lateinit var repo: DBRepository
    lateinit var service: Service

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_client)

        recyclerview = findViewById(R.id.recyclerview)

        //var tipuri: ArrayList<String> = intent.getSerializableExtra("tipuri") as ArrayList<String>
        var list: ArrayList<Produs> = intent.getSerializableExtra("list") as ArrayList<Produs>
        var sortedList = list!!.sortedWith(
            compareBy({ -it.medie }, { it.etaj }
            )
        )

        var progressBar = findViewById<ProgressBar>(R.id.progressBar2)
        var customProgressBar = CustomProgressBar(progressBar)
        var db = DBHelper(this, null)
        server = ServerRepository()
        server.context = this
        repo = DBRepository(db, this)

        var path = getExternalFilesDir(null)
        var fileRepo = FileRepository(path, "backup")
        var fileOpBack = File(path, "operationsBackup")
        service = Service(repo, server, fileRepo, fileOpBack, ProdusViewModel(), customProgressBar)

        createRecyclerView(sortedList)


        var returnBtn = findViewById<Button>(R.id.buttonReturn)
        returnBtn.setOnClickListener {
            //val intent = Intent(this, MainActivity::class.java)
            //startActivity(intent)
            finish()
        }

        var btn = findViewById<Button>(R.id.buttonUpdate)
        btn.setOnClickListener {
            var i = 0
            var selected: Produs? = null
            for (p in sortedList) {
                if (i == pos) {
                    selected = p
                    break
                }
                i += 1
            }
            val intent = Intent(this, UpdateActivity::class.java)
            intent.putExtra("produs", selected)
            startActivityForResult(intent, 1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == 1) {
            if (data != null) {
                val produs: Produs = data.getSerializableExtra("pinguin") as Produs
                var pinguinResulted = service.add(produs)
                var recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
//                var adaptar: CustomAdapter = recyclerView.adapter as CustomAdapter
//                var item = ItemsViewModel(
//                    R.drawable.ic_launcher_foreground,
//                    pinguinResulted.nume + "      " + pinguinResulted.stare + "      " + pinguinResulted.pret + " RON"
//                )
                //produsViewModel.produse.value = service.getAll()
                //pinguinViewModel.pinguin.value?.add(pinguinResulted)
//                adaptar.addItem(item)
//                adaptar.append(pinguin.id)
//                recyclerView.adapter = adaptar
                //pinguinViewModel.pinguin.value = service.getPinguini()
            }
        } else if (resultCode == 2) {
//            if (data != null) {
//                val pinguin: Pinguin = data.getSerializableExtra("pinguin") as Pinguin
//                //val pos: Int = data.getIntExtra("pos", -1)
//                service.update(pinguin.id, pinguin)
//                var recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
//                var adaptar: CustomAdapter = recyclerView.adapter as CustomAdapter
//                var item = ItemsViewModel(
//                    R.drawable.ic_launcher_foreground,
//                    pinguin.nume + "      " + pinguin.stare + "      " + pinguin.pret + " RON"
//                )
//                pinguinViewModel.pinguin.value = service.getPinguini()
//                //pinguinViewModel.pinguin.value?.add(pinguin)
////                adaptar.updateItem(pos, item)
////                recyclerView.adapter = adaptar
//            }
        }

    }

    fun createRecyclerView(list: List<Produs>) {

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
                    list.get(i).id.toString() + "      " + list.get(i).nume + "      " + list.get(i).medie.toString() + "      " + list.get(
                        i
                    ).etaj.toString() + "      " + list.get(i).orientare.toString() + "      " + list.get(
                        i
                    ).camera + "    " + list.get(i).status
                )

//                model.pinguin.observe(this, Observer { pinguin ->
//                    model.text =
//                        pinguin.nume + "      " + pinguin.stare + "      " + pinguin.pret + " RON"
//                })
//
//                val binding = ActivityMainBinding.inflate(layoutInflater)
//                binding


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
                pos = position

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