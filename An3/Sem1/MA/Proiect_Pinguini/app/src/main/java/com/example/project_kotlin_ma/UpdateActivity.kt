package com.example.project_kotlin_ma

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog

class UpdateActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    var list_of_items = arrayListOf<Stare>(Stare.ADOPTAT, Stare.DECEDAT, Stare.NEADOPTAT)

    override fun onCreate(savedInstanceState: Bundle?) {
        //val service: Service = intent.getSerializableExtra("service") as Service
        //val pos: Int = intent.getStringExtra("position").toString().toInt()
        val pinguin: Pinguin = intent.getSerializableExtra("pinguin") as Pinguin

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

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

        //var pinguin = service.findByPosition(pos)
        var id_initial = pinguin!!.id

        var nume = findViewById<EditText>(R.id.editNume)
        var inaltime = findViewById<EditText>(R.id.editInaltime)
        var greutate = findViewById<EditText>(R.id.editGreutate)
        var data = findViewById<EditText>(R.id.editDate)
        var specie = findViewById<EditText>(R.id.editSpecie)
        var pret = findViewById<EditText>(R.id.editPret)
        var stare = findViewById<Spinner>(R.id.spinner)
        var id = findViewById<EditText>(R.id.editID)

        if (pinguin != null) {
            nume.setText(pinguin.nume)
            inaltime.setText(pinguin.inaltime.toString())
            greutate.setText(pinguin.greutate.toString())
            data.setText(pinguin.dataNasterii)
            specie.setText(pinguin.specie)
            pret.setText(pinguin.pret.toString())
            var position = 0
            if (pinguin.stare == Stare.DECEDAT) position = 1
            if (pinguin.stare == Stare.NEADOPTAT) position = 2
            stare.setSelection(position)
            id.setText(pinguin.id.toString())
        }

        var updateBtn = findViewById<Button>(R.id.buttonUpdate)
        updateBtn.setOnClickListener {

//            if (id.text.toString().toInt() != id_initial && service.ifExistsID(
//                    id.text.toString().toInt()
//                )
//            ) {
//                val builder = AlertDialog.Builder(this)
//                builder.setTitle("Android Alert")
//                builder.setMessage("This ID is aleady used")
//                builder.show()
//            } else {
            if (nume.text.toString().isEmpty() || inaltime.text.toString()
                    .isEmpty() || greutate.text.toString().isEmpty() || pret.text.toString()
                    .isEmpty() || specie.text.toString().isEmpty() || data.text.toString()
                    .isEmpty() || id.text.toString().isEmpty()
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
                pinguin.id = id_initial
                //pinguin.id = id.text.toString().toInt()
//                if (service != null) {
//                    service.update(id_initial, pinguin)
//                }
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)

                var resultIntent: Intent = Intent()
                resultIntent.putExtra("pinguin", pinguin)
                //resultIntent.putExtra("pos", pos)
                setResult(2, resultIntent)

                finish()
            }
            //}
        }
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        TODO("Not yet implemented")
    }


    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }
}