package com.example.agendapersonal

import android.annotation.SuppressLint
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.bbddfer.DatabaseHelper


class BuscarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_buscar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }




        val sharedPref = getSharedPreferences("AppSharedPreferences", MODE_PRIVATE)
        val backgroundColor = sharedPref.getInt("background_color", 0)
        this.findViewById<View>(android.R.id.content).setBackgroundColor(backgroundColor)

        val contenedor = findViewById<LinearLayout>(R.id.contenedor)
        val button = findViewById<Button>(R.id.addButton)

        val backButton = findViewById<TextView>(R.id.backButton)

        backButton.setOnClickListener {
            //Navega a la actividad padre
            NavUtils.navigateUpFromSameTask(this)
        }


        if (intent.getStringExtra("modo") == "eliminar") {
            for (contacto in DatabaseManager.Companion.allContacts(this)) {
                contenedor.addView(createUserCheckBox(contacto))
            }

            button.text = "Eliminar"
            button.setTextColor(Color.RED)
            button.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    val checked = contenedor.childCount
                    for (i in 0 until checked) {
                        val view = contenedor.getChildAt(i)
                        if (view is CheckBox) {
                            if (view.isChecked) {
                                val id = view.tag as Int
                                Log.d("ID", "Deleting id $id")
                                DatabaseManager.Companion.deleteContactDB(this@BuscarActivity, id)
                            }
                        }
                    }
                    //NavUtils.navigateUpFromSameTask(this@BuscarActivity)
                    val menuPrincipal: Intent =
                        Intent(
                            this@BuscarActivity,
                            MainActivity::class.java
                        )
                    startActivity(menuPrincipal)
                }
            })
        }
        else if (intent.getStringExtra("modo") == "editar") {
            button.text = "Editar Contactos"

            for(contacto in DatabaseManager.Companion.allContacts(this)){
                contenedor.addView(editUserButton(contacto))
            }
        }
        else {
            for(contacto in DatabaseManager.Companion.allContacts(this)){
                contenedor.addView(createUserButton(contacto))
            }

            button.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    val newContact = Contacto(-1, "Dont click me", "666", "WachuLookingAt@gmail.com")
                    contenedor.addView(createUserButton(newContact))
                }
            })
        }
    }

    private fun createUserCheckBox(contacto: Contacto): TextView {
        return CheckBox(this).apply {
            this.layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            this.text = "${contacto.id} - ${contacto.nombre} - ${contacto.telefono}"
            this.tag = contacto.id
            //button.background = getDrawable(R.drawable.formato)
            this.textSize = 22f
            this.setTextColor(Color.BLACK)
            this.typeface = Typeface.DEFAULT_BOLD


        }
    }

    private fun editUserButton(contacto: Contacto): TextView {
        return Button(this).apply {
            this.layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            this.text = contacto.nombre
            this.textSize = 35f
            this.setTextColor(Color.BLACK)
            this.typeface = Typeface.DEFAULT_BOLD

            val nom = contacto.nombre
            val telf = contacto.telefono
            val correo = contacto.correo


            this.setOnClickListener { v: View? ->
                DatabaseManager.Companion.deleteContactDB(this@BuscarActivity, contacto.id)
                val intentInfo: Intent =
                    Intent(
                        this@BuscarActivity,
                        AddActivity::class.java
                    )
                intentInfo.putExtra("edit", "true")
                intentInfo.putExtra("NOMBRE", nom)
                intentInfo.putExtra("N_TELF", telf)
                intentInfo.putExtra("CORREO", correo)
                startActivity(intentInfo)
            }
        }
    }


    public fun createUserButton(contacto: Contacto): TextView {
        return Button(this).apply {
            this.layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            this.text = contacto.nombre
            //button.background = getDrawable(R.drawable.formato)
            this.textSize = 35f
            this.setTextColor(Color.BLACK)
            this.typeface = Typeface.DEFAULT_BOLD

            this.setOnClickListener { v: View? ->
                val intentInfo: Intent =
                    Intent(
                        this@BuscarActivity,
                        ContactInfoActivity::class.java
                    )
                intentInfo.putExtra("NOMBRE", contacto.nombre)
                intentInfo.putExtra("N_TELF", contacto.telefono)
                intentInfo.putExtra("CORREO", contacto.correo)
                startActivity(intentInfo)
            }
        }
    }
}