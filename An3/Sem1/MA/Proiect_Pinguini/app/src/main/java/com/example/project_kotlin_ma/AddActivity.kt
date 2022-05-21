package com.example.project_kotlin_ma

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog

class AddActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    var list_of_items = arrayListOf<Stare>(Stare.ADOPTAT, Stare.DECEDAT, Stare.NEADOPTAT)

    override fun onCreate(savedInstanceState: Bundle?) {
        //val service: Service = intent.getSerializableExtra("service") as Service
        //var id: Int = intent.getIntExtra("id", 0)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        var returnBtn = findViewById<Button>(R.id.returnButton)
        returnBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        var spinner = findViewById<Spinner>(R.id.spinner)
        //spinner!!.setOnItemSelectedListener(this)

        // Create an ArrayAdapter using a simple spinner layout and languages array
        val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, list_of_items)
        // Set layout to use when the list of choices appear
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Set Adapter to Spinner
        spinner!!.setAdapter(aa)

        var nume = findViewById<EditText>(R.id.editNume)
        var inaltime = findViewById<EditText>(R.id.editInaltime)
        inaltime.setText("0")
        var greutate = findViewById<EditText>(R.id.editGreutate)
        greutate.setText("0")
        var data = findViewById<EditText>(R.id.editDate)
        data.setText("18/12/2021")
        var specie = findViewById<EditText>(R.id.editSpecie)
        var pret = findViewById<EditText>(R.id.editPret)
        pret.setText("0")
        var stare = findViewById<Spinner>(R.id.spinner)


        var addBtn = findViewById<Button>(R.id.buttonAdd)
        addBtn.setOnClickListener {
            if (nume.text.toString().isEmpty() || inaltime.text.toString()
                    .isEmpty() || greutate.text.toString().isEmpty() || pret.text.toString()
                    .isEmpty() || specie.text.toString().isEmpty() || data.text.toString()
                    .isEmpty()
            ) {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Android Alert")
                builder.setMessage("All fields must contain a value")
                builder.show()
            } else {

                var pinguin = Pinguin(
                    nume.text.toString(),
                    inaltime.text.toString().toFloat(),
                    greutate.text.toString().toFloat(),
                    Stare.valueOf(stare.selectedItem.toString()),
                    pret.text.toString().toFloat(),
                    specie.text.toString(),
                    data.text.toString()
                )
//                if (service != null) {
//                    service.add(pinguin)
//                }
                //val intent = Intent(this, MainActivity::class.java)
                //startActivity(intent)

                var resultIntent: Intent = Intent()
                resultIntent.putExtra("pinguin", pinguin)
                setResult(1, resultIntent)

                finish()
            }
        }
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        TODO("Not yet implemented")
    }


    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}