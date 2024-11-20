package com.example.reserve_housing

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
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

        val spinnerHousing = SpinnerHandler(this, findViewById(R.id.spinnerHousing), R.array.housing)
        val spinnerAdult = SpinnerHandler(this, findViewById(R.id.spinnerAdult), R.array.n_adults)
        val spinnerKid = SpinnerHandler(this, findViewById(R.id.spinnerKid), R.array.n_kids)

        val pickTimeButtonD = findViewById<ImageButton>(R.id.pickTime)
        val pickTimeButtonH = findViewById<ImageButton>(R.id.pickHour)

        val desde = findViewById<TextView>(R.id.desdeTextView)
        val hasta = findViewById<TextView>(R.id.hastaTextView)

        val enviar = findViewById<Button>(R.id.enviar)

        pickTimeButtonD.setOnClickListener {
            val newDate = DatePickerFragment(desde)
            newDate.show(supportFragmentManager, "datePicker")
        }

        pickTimeButtonH.setOnClickListener {
            val newDate = DatePickerFragment(hasta)
            newDate.show(supportFragmentManager, "datePicker")
        }

        enviar.setOnClickListener {
            if(desde.text != "Desde" && hasta.text != "Hasta" && spinnerHousing.selectedValue != null && spinnerKid.selectedValue != null && spinnerAdult.selectedValue != null){

                val intent = Intent(
                    this@MainActivity,
                    ResultsActivity::class.java
                )

                intent.putExtra("DESDE", desde.text)
                intent.putExtra("HASTA", hasta.text)
                intent.putExtra("HOUSING", spinnerHousing.selectedValue)
                intent.putExtra("KIDS", spinnerKid.selectedValue)
                intent.putExtra("ADULTS", spinnerAdult.selectedValue)

                startActivity(intent)
            }
            else {
                showPopup()
            }

        }

    }

    private fun showPopup() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("You need to fill the values first")
            .setPositiveButton("OK", DialogInterface.OnClickListener { dialog, id ->
                dialog.dismiss()
            })
        val alert = builder.create()
        alert.show()
    }

}