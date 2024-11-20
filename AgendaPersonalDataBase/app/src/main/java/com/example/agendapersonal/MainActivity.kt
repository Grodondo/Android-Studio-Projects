package com.example.agendapersonal

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //TODO checheakr GetSupportActionBar

        val buscar = findViewById<Button>(R.id.BuscarButton)
        val eliminar = findViewById<Button>(R.id.EliminarButton)
        val add = findViewById<Button>(R.id.AddButton)
        val editar = findViewById<Button>(R.id.EditarButton)

        val spinner: Spinner = findViewById(R.id.spinner_theme)
        val sh = SpinnerHandler(this, spinner)

        val sharedPref = getSharedPreferences("AppSharedPreferences", MODE_PRIVATE)
        val backgroundColor = sharedPref.getInt("background_color", 0)
        this.findViewById<ConstraintLayout>(R.id.main).setBackgroundColor(backgroundColor)

        buscar.setOnClickListener {
           buscar("buscar")
        }
        eliminar.setOnClickListener {
            buscar("eliminar")
        }

        add.setOnClickListener {
            val intent = Intent(
                this@MainActivity,
                AddActivity::class.java
            )
            startActivity(intent)
        }

        editar.setOnClickListener {
            buscar("editar")
        }


    }


    fun buscar(modo: String) {
        val intent = Intent(
            this@MainActivity,
            BuscarActivity::class.java
        )
        intent.putExtra("modo", modo)

        startActivity(intent)
    }
}