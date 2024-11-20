package com.example.agendapersonal

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bbddfer.DatabaseHelper
import com.google.android.material.textfield.TextInputEditText


class AddActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setBackgroundColor()

        val addButtton = findViewById<Button>(R.id.addButton)

        val inputNom = findViewById<TextInputEditText>(R.id.inputNom)
        val inputTelf = findViewById<TextInputEditText>(R.id.inputTelf)
        val inputEmail = findViewById<TextInputEditText>(R.id.inputEmail)

        val backButton = findViewById<Button>(R.id.backButton)
        backButton.setOnClickListener {
            //Navega a la actividad padre
            NavUtils.navigateUpFromSameTask(this)
        }

        if (intent.getStringExtra("edit") == "true") {
            val nombre = intent.getStringExtra("NOMBRE")
            val telefono = intent.getStringExtra("N_TELF")
            val email = intent.getStringExtra("CORREO")
            inputNom.setText(nombre)
            inputTelf.setText(telefono)
            inputEmail.setText(email)

            addButtton.text = "Editar"

            inputNom.hint = nombre
            inputTelf.hint = telefono
            inputEmail.hint = email

        }

        addButtton.setOnClickListener(){
            if (intent.getStringExtra("edit") == "true") {
                //TODO deleteContactDB()
            }
            val newContacto = Contacto(-1, inputNom.text.toString(), inputTelf.text.toString(), inputEmail.text.toString())
            DatabaseManager.Companion.addContactDB(this, newContacto)

            val intent = Intent(
                this@AddActivity,
                MainActivity::class.java
            )
            startActivity(intent)
            // Regresa a la actividad padre
        }
    }

    fun setBackgroundColor(){
        val sharedPref = getSharedPreferences("AppSharedPreferences", MODE_PRIVATE)
        val backgroundColor = sharedPref.getInt("background_color", 0)
        this.findViewById<View>(android.R.id.content).setBackgroundColor(backgroundColor)
    }
}