package com.example.examen25inventar

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class AddActivity : AppCompatActivity() {

    fun isInt(str: String): Boolean {
        if (str.toIntOrNull() != null) {
            return true
        }
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        var returnBtn = findViewById<Button>(R.id.returnButton)
        returnBtn.setOnClickListener {
            //val intent = Intent(this, MainActivity::class.java)
            //startActivity(intent)
            finish()
        }

        var spinner = findViewById<Spinner>(R.id.spinner)
        //spinner!!.setOnItemSelectedListener(this)

        var list_of_items = arrayListOf<Boolean>(true, false)
        // Create an ArrayAdapter using a simple spinner layout and languages array
        val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, list_of_items)
        // Set layout to use when the list of choices appear
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Set Adapter to Spinner
        spinner!!.setAdapter(aa)

        var nume = findViewById<EditText>(R.id.editNume)
        var tip = findViewById<EditText>(R.id.editTip)
        tip.setText("Tip")
        var cantitate = findViewById<EditText>(R.id.editGreutate)
        cantitate.setText("0")
        var discount = findViewById<EditText>(R.id.editDiscount)
        discount.setText("0")
        var pret = findViewById<EditText>(R.id.editPret)
        pret.setText("0")
        var status = findViewById<Spinner>(R.id.spinner)


        var addBtn = findViewById<Button>(R.id.buttonAdd)
        addBtn.setOnClickListener {
            var error = ""
            if (nume.text.toString().isEmpty() || tip.text.toString()
                    .isEmpty() || cantitate.text.toString().isEmpty() || pret.text.toString()
                    .isEmpty() || discount.text.toString().isEmpty()
            ) {
                error += "All fields must contain a value\n"
            }
            if (!isInt(cantitate.text.toString())) {
                error += "Cantitatea trebuie sa fie un numar intreg\n"
            }
            if (!isInt(pret.text.toString())) {
                error += "Pretul trebuie sa fie un numar intreg\n"
            }
            if (!isInt(discount.text.toString())) {
                error += "Discountul trebuie sa fie un numar intreg\n"
            }
            if (error != "") {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Valori incorecte")
                builder.setMessage(error)
                builder.show()
            } else {

                var pinguin = Produs(
                    nume.text.toString(),
                    tip.text.toString(),
                    cantitate.text.toString().toInt(),
                    pret.text.toString().toInt(),
                    discount.text.toString().toInt(),
                    status.selectedItem.toString().toBoolean()
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
}