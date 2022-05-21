package com.example.examen25inventar

import android.content.Intent
import android.content.IntentFilter
import android.graphics.ColorSpace.connect
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.system.Os.connect
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project_kotlin_ma.*
import org.json.JSONObject
import java.io.File
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity(), () -> Lifecycle, MessageListener {

    var produsViewModel = ProdusViewModel()
    lateinit var service: Service
    lateinit var server: ServerRepository
    lateinit var repo: DBRepository
    var first = true
    lateinit var recyclerview: RecyclerView
    val serverUrl = "ws://10.0.2.2:2025"
    lateinit var progressBar: CustomProgressBar


    fun getRequest() {
        progressBar.progressBar.visibility = View.VISIBLE
        progressBar.add()
        if (first) {
            server.getAll {
                if (it != null) {
                    for (p in repo.getAll()!!) {
                        repo.delete(p.id)
                    }
                    for (p in it) {
                        repo.add(p)
                    }
                    produsViewModel.produse.value = service.getAll()
                    produsViewModel.produse.observe(this@MainActivity, Observer {
                        createRecyclerView(it)
                    })
                    first = false
                    //progressBar.visibility = View.INVISIBLE
                    progressBar.substract()
                } else {
                    if (service.getAll() == null || service.getAll()!!.size == 0) {
                        val builder1 = AlertDialog.Builder(this)
                        builder1.setMessage("Get request failed")
                            .setCancelable(true)
                            .setPositiveButton("Reload") { dialog, id ->
                                getRequest()
                            }
                            .setNegativeButton("Cancel") { dialog, id ->
                                // Dismiss the dialog
                                //progressBar.visibility = View.INVISIBLE
                                progressBar.substract()
                            }
                        val alert = builder1.create()
                        alert.show()
                    } else {
                        produsViewModel.produse.value = service.getAll()
                        produsViewModel.produse.observe(this@MainActivity, Observer {
                            createRecyclerView(service.getAll())
                        })
                        //progressBar.visibility = View.INVISIBLE
                        progressBar.substract()
                    }
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var progressBar1 = findViewById<ProgressBar>(R.id.progressBar)
        progressBar1.visibility = View.INVISIBLE
        progressBar = CustomProgressBar(progressBar1)

        var socketManager = WebSocketManager()
        socketManager.init(serverUrl, this)
        thread {
            kotlin.run {
                socketManager.connect()
            }
        }


        recyclerview = findViewById<RecyclerView>(R.id.recyclerview)
        var db = DBHelper(this, null)
//        db.add(Produs("nume", "tip", 2, 2, 2, true))
//        var x = db.getAll()
        //db.deleteAll()
        repo = DBRepository(db, this)

        server = ServerRepository()
        server.context = this


        var path = getExternalFilesDir(null)
        var fileRepo = FileRepository(path, "backup")
        var fileOpBack = File(path, "operationsBackup")

        val receiver = ServerStatusReceiver()
        receiver.set(server, fileRepo, fileOpBack, repo, produsViewModel)
        IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION).also {
            registerReceiver(receiver, it)
        }
        service = Service(repo, server, fileRepo, fileOpBack, produsViewModel, progressBar)


        //var livedata = MutableLiveData<ArrayList<Produs>>()
        //produsViewModel.produse.value = service.getPinguini()


        var buttonAdd = findViewById<Button>(R.id.buttonAdd)
        buttonAdd.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            //intent.putExtra("service", service)
            startActivityForResult(intent, 1)
        }

        var buttonGet = findViewById<Button>(R.id.buttonGet)
        buttonGet.setOnClickListener {
            getRequest()
        }

        var buttonClient = findViewById<Button>(R.id.buttonClient)
        buttonClient.setOnClickListener {
            progressBar.progressBar.visibility = View.VISIBLE
            progressBar.add()
            server.getAll {
                if (it != null) {
                    val intent = Intent(this, ClientActivity::class.java)
                    var tipuri = service.getTipuri()
                    var list = service.getListOfCheapest(tipuri)
                    intent.putExtra("tipuri", tipuri)
                    intent.putExtra("list", list)
                    startActivity(intent)
                } else {
                    Toast.makeText(this@MainActivity, "Fara conexiune la server", Toast.LENGTH_LONG)
                        .show()
                }

                //progressBar.visibility = View.INVISIBLE
                progressBar.substract()
            }
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
                produsViewModel.produse.value = service.getAll()
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


    fun createRecyclerView(list: ArrayList<Produs>?) {

        // getting the recyclerview by its id


        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)

        // ArrayList of class ItemsViewModel
        val data = ArrayList<ItemsViewModel>()
        var IDList = ArrayList<Int>()
        // This loop will create 20 Views containing
        // the image with the count of view

        if (list != null) {
            for (i in list) {
                IDList.add(i.id)
                var model = ItemsViewModel(
                    R.drawable.ic_launcher_foreground,
                    i.id.toString() + "      " + i.nume + "      " + i.tip + "      " + i.pret.toString() + " RON      " + i.cantitate.toString()
                )

//                model.pinguin.observe(this, Observer { pinguin ->
//                    model.text =
//                        pinguin.nume + "      " + pinguin.stare + "      " + pinguin.pret + " RON"
//                })
//
//                val binding = ActivityMainBinding.inflate(layoutInflater)
//                binding

                data.add(
                    model
                )

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

    override fun invoke(): Lifecycle {
        TODO("Not yet implemented1")
    }

    override fun onConnectSuccess() {
        //TODO("Not yet implemented2")
        println("haha")
    }

    override fun onConnectFailed() {
        //TODO("Not yet implemented3")
        println("haha")
    }

    override fun onClose() {
        //TODO("Not yet implemented4")
        println("haha")
    }

    override fun onMessage(text: String?) {
        //TODO("Not yet implemented5")
        var pinguinJson = JSONObject(text)
        var pinguin = Produs(
            pinguinJson.getString("nume"),
            pinguinJson.getString("tip"),
            pinguinJson.getString("cantitate").toInt(),
            pinguinJson.getString("pret").toInt(),
            pinguinJson.getString("discount").toInt(),
            pinguinJson.getBoolean("status")
        )
        pinguin.id = pinguinJson.getInt("id")
        runOnUiThread(Runnable {
            Toast.makeText(
                this,
                "A fost adaugat un produs nou cu " + pinguin.toString(),
                Toast.LENGTH_LONG
            ).show()
        })

    }
}