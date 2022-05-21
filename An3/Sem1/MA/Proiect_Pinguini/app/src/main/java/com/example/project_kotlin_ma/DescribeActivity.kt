package com.example.project_kotlin_ma

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView

class DescribeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_describe)

        val pinguin: Pinguin = intent.getSerializableExtra("pinguin") as Pinguin

        var returnBtn = findViewById<Button>(R.id.returnButton)
        returnBtn.setOnClickListener {
            //val intent = Intent(this, MainActivity::class.java)
            //startActivity(intent)
            finish()
        }

        var nume = findViewById<TextView>(R.id.editNume)
        var inaltime = findViewById<TextView>(R.id.editInaltime)
        var greutate = findViewById<TextView>(R.id.editGreutate)
        var data = findViewById<TextView>(R.id.editDate)
        var specie = findViewById<TextView>(R.id.editSpecie)
        var pret = findViewById<TextView>(R.id.editPret)
        var stare = findViewById<TextView>(R.id.spinner)
        var id = findViewById<TextView>(R.id.editID)

        nume.setText(pinguin.nume)
        inaltime.setText(pinguin.inaltime.toString())
        greutate.setText(pinguin.greutate.toString())
        data.setText(pinguin.dataNasterii)
        specie.setText(pinguin.specie)
        pret.setText(pinguin.pret.toString())
        stare.setText(pinguin.stare.toString())
        id.setText(pinguin.id.toString())
    }
}