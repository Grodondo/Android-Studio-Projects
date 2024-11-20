package com.example.agendapersonal

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ContactInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_contact_info)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val backButton = findViewById<TextView>(R.id.backButton)

        backButton.setOnClickListener {
            //Navega a la actividad padre
            NavUtils.navigateUpFromSameTask(this)
        }

        val nombre = findViewById<TextView>(R.id.nombre)
        val nTelf = findViewById<TextView>(R.id.nTelf)
        val correo = findViewById<TextView>(R.id.correo)

        nombre.text = "Nombre: " + intent.getStringExtra("NOMBRE")

        nTelf.text = "Telefono: " + intent.getStringExtra("N_TELF")

        correo.text = "Correo: " + intent.getStringExtra("CORREO")

    }
}