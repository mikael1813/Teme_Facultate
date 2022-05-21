package com.example.examen

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class UpdateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update)

        var returnBtn = findViewById<Button>(R.id.returnButton)
        returnBtn.setOnClickListener {
            //val intent = Intent(this, MainActivity::class.java)
            //startActivity(intent)
            finish()
        }

        var produs: Produs = intent.getSerializableExtra("produs") as Produs

        var id = findViewById<EditText>(R.id.editId)
        id.setText(produs.id)
        var camera = findViewById<EditText>(R.id.editCamera)
        camera.setText(produs.camera)
        var etaj = findViewById<EditText>(R.id.editEtaj)
        camera.setText(produs.etaj)

        var btn = findViewById<Button>(R.id.button)
        btn.setOnClickListener {

        }

        var resultIntent: Intent = Intent()
        //resultIntent.putExtra("pinguin", pinguin)
        setResult(1, resultIntent)

        finish()


    }


}