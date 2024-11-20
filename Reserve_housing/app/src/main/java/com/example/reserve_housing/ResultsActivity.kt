package com.example.reserve_housing

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ResultsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_results)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val backButton = findViewById<ImageButton>(R.id.back)

        setup()


        backButton.setOnClickListener {
            val intent = Intent(
                this@ResultsActivity,
                MainActivity::class.java
            )
            startActivity(intent)
        }
    }

    private fun setup() {
        val textHousing = findViewById<TextView>(R.id.housingTextView)
        val textAdults = findViewById<TextView>(R.id.adultsTextView)
        val textKids = findViewById<TextView>(R.id.kidsTextView)
        val textDesde = findViewById<TextView>(R.id.desdeTextView)
        val textHasta = findViewById<TextView>(R.id.hastaTextView)

        textHousing.text = intent.getStringExtra("HOUSING")
        textAdults.text = "${intent.getStringExtra("ADULTS")} adultos"
        textKids.text = "${intent.getStringExtra("KIDS")} niños"
        textDesde.text = "Desde: ${intent.getStringExtra("DESDE")} "
        textHasta.text = "Hasta: ${intent.getStringExtra("HASTA")}"
    }
}