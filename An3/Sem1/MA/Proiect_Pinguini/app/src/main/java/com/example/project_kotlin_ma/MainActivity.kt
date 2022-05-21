package com.example.project_kotlin_ma

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.project_kotlin_ma.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.*
import java.time.LocalDate

var pinguinViewModel = PinguinViewModel()

@RequiresApi(Build.VERSION_CODES.M)
fun isOnline(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    if (connectivityManager != null) {
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        if (capabilities != null) {
            if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                return true
            } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                return true
            }
        }
    }
    return false
}

class MainActivity : AppCompatActivity(), () -> Lifecycle {
    private val lifecycleOwner = LifecycleOwner(this)
    var pos = -1

    lateinit var service: Service


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var db = DBHelper(this, null)
        var p123 = Pinguin("a", 13.0F, 13F, Stare.ADOPTAT, 120F, "Ooof", "12/12/2002")
        p123.id = 3


        //server test

        var ok = isOnline(this)

        val server = ServerRepository()


//        server.add(p123){
//            var x = 3
//        }


        //var onGetPenguinsListener : OnGetPenguinsListener = OnGetPenguinsListener()
        var txt = findViewById<TextView>(R.id.textView)
//
//        Handler(Looper.getMainLooper()).postDelayed(
//            {
//                // This method will be executed once the timer is over
//            },
//            1000 // value in milliseconds
//        )
//        print("aaaa")
        //
        //var p124 = Pinguin("ababa", 12.0F,12F,Stare.DECEDAT,120F,"Ooof","12/12/2002")
//        db.add(p123)
//        p123.id = 4
//        db.add(p123)
//        p123.id = 5
//        db.add(p123)

        //db.delete(0)
        //db.update(p123,0)
        db.getAll()
//        var x = 2
//        server.getAll {
//             x= 3
//        }

        var filename = "pinguini.json"
        var path = getExternalFilesDir(null)   //get file directory for this package


        var repo = DBRepository(db)
        //var repo = FileRepository(path, filename)


        var progressBar = findViewById<ProgressBar>(R.id.progressBar)

        var fileRepo = FileRepository(path, "backup")
        var fileOpBack = File(path, "operationsBackup")


//        val thread = CheckConnection(fileRepo, fileOpBack, server, repo)
//        thread.start()

        val receiver = ServerStatusReceiver()
        receiver.set(server, fileRepo, fileOpBack, repo, pinguinViewModel)
        IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION).also {
            registerReceiver(receiver, it)
        }
        service = Service(repo, server, fileRepo, fileOpBack, pinguinViewModel, progressBar)


        var livedata = MutableLiveData<ArrayList<Pinguin>>()
        pinguinViewModel.pinguin.value = service.getPinguini()
        pinguinViewModel.pinguin.observe(this, Observer {
            createRecyclerView(service.getPinguini())
        })

        createRecyclerView(service.getPinguini())

        service.getPinguiniFromServer()

        var recycler = findViewById<RecyclerView>(R.id.recyclerview)

        var buttonAdd = findViewById<Button>(R.id.buttonAdd)
        buttonAdd.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            //intent.putExtra("service", service)
            startActivityForResult(intent, 1)

        }


        var buttonUpdate = findViewById<Button>(R.id.buttonUpdate)
        buttonUpdate.setOnClickListener {
            if (pos >= 0) {

                val intent = Intent(this, UpdateActivity::class.java)
                //intent.putExtra("service", service)
                var adaptar: CustomAdapter = recycler.adapter as CustomAdapter
                var p = service.findByID(adaptar.getIDByPoz(pos))
                intent.putExtra("pinguin", p)
                startActivityForResult(intent, 1)
                //pinguinViewModel.pinguin.value = service.getPinguini()
            } else {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Android Alert")
                builder.setMessage("No item selected")
                builder.show()
            }

        }

        var buttonDelete = findViewById<Button>(R.id.buttonDelete)
        buttonDelete.setOnClickListener {
            if (pos >= 0) {
                val builder1 = AlertDialog.Builder(this)
                builder1.setMessage("Are you sure you want to delete this penguin?")
                    .setCancelable(false)
                    .setPositiveButton("Yes") { dialog, id ->
                        // Delete selected pinguin from file
//                        var pinguin = service.findByPosition(pos)
//                        if (pinguin != null) {
//                            service.delete(pinguin.id)
//                            //createRecyclerView(service.getPinguini())
//                        }
                        var adaptar: CustomAdapter = recycler.adapter as CustomAdapter
                        //var pinguin = service.findByID(adaptar.getIDByPoz(pos))
                        var id = adaptar.getIDByPoz(pos)
                        service.delete(id)

                        pinguinViewModel.pinguin.value = service.getPinguini()
//                        adaptar.deleteItem(pos)
//                        adaptar.remove(id)
//                        recycler.adapter = adaptar
                        pos = -1
                        val builder = AlertDialog.Builder(this)
                        builder.setTitle("Android Update")
                        builder.setMessage("Pinguin successfully deleted")
                        builder.show()
                    }
                    .setNegativeButton("No") { dialog, id ->
                        // Dismiss the dialog
                        pos = -1
                    }
                val alert = builder1.create()
                alert.show()
            } else {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Android Alert")
                builder.setMessage("No item selected")
                builder.show()
            }

        }

        //service.getPinguini()?.observe(lifecycleOwner, Observer {

        //})


    }

//    private fun observeCurrentName() {
//        vm.getCurrentName().observe(this, Observer{
//            //Toast here
//        })
//    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == 1) {
            if (data != null) {
                val pinguin: Pinguin = data.getSerializableExtra("pinguin") as Pinguin
                var pinguinResulted = service.add(pinguin)
                var recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
                var adaptar: CustomAdapter = recyclerView.adapter as CustomAdapter
                var item = ItemsViewModel(
                    R.drawable.ic_launcher_foreground,
                    pinguinResulted.nume + "      " + pinguinResulted.stare + "      " + pinguinResulted.pret + " RON"
                )
                pinguinViewModel.pinguin.value = service.getPinguini()
                //pinguinViewModel.pinguin.value?.add(pinguinResulted)
//                adaptar.addItem(item)
//                adaptar.append(pinguin.id)
//                recyclerView.adapter = adaptar
                //pinguinViewModel.pinguin.value = service.getPinguini()
            }
        } else if (resultCode == 2) {
            if (data != null) {
                val pinguin: Pinguin = data.getSerializableExtra("pinguin") as Pinguin
                //val pos: Int = data.getIntExtra("pos", -1)
                service.update(pinguin.id, pinguin)
                var recyclerView = findViewById<RecyclerView>(R.id.recyclerview)
                var adaptar: CustomAdapter = recyclerView.adapter as CustomAdapter
                var item = ItemsViewModel(
                    R.drawable.ic_launcher_foreground,
                    pinguin.nume + "      " + pinguin.stare + "      " + pinguin.pret + " RON"
                )
                pinguinViewModel.pinguin.value = service.getPinguini()
                //pinguinViewModel.pinguin.value?.add(pinguin)
//                adaptar.updateItem(pos, item)
//                recyclerView.adapter = adaptar
            }
        }

    }

    fun createRecyclerView(list: ArrayList<Pinguin>?) {

        // getting the recyclerview by its id
        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)


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
                    i.nume + "      " + i.stare + "      " + i.pret + " RON"
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

                print(position)
                pos = position

            }


        })

        adapter.setOnItemLongClickListener(object : CustomAdapter.onItemLongClickListener {
            override fun onItemLongClick(position: Int) {
                print(position)
                var id = adapter.getIDByPoz(position)
                var pinguin = service.findByID(id)
                val intent = Intent(this@MainActivity, DescribeActivity::class.java)
                intent.putExtra("pinguin", pinguin)
                startActivity(intent)
            }
        })


    }

    override fun invoke(): Lifecycle {
        TODO("Not yet implemented")
    }
}


