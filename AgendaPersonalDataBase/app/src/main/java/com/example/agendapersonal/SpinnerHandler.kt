package com.example.agendapersonal

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.graphics.component1

class SpinnerHandler (private val context: Context, private val spinner: Spinner) : Activity(), AdapterView.OnItemSelectedListener{

    private val sharedPref = context.getSharedPreferences("AppSharedPreferences", MODE_PRIVATE)
    private val editor = sharedPref.edit()

    init {
        setupSpinner()

    }

    private fun setupSpinner() {
        ArrayAdapter.createFromResource(
            context,
            R.array.theme_colors,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
        spinner.setSelection(0)
        spinner.onItemSelectedListener = this
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        Log.d("BackgroundChange", "Item selected at position: $position")

        val colors = context.resources.getIntArray(R.array.theme_colors_values)
        if (position != 0) {
            val selectedColor = colors[position]

            editor.apply {
                putInt("background_color", selectedColor)
                apply()
            }

            // Set the background color
            val mainLayout = (context as MainActivity).findViewById<ConstraintLayout>(R.id.main)
            if (mainLayout != null) {
                mainLayout.setBackgroundColor(selectedColor)
            } else {
                Log.d("BackgroundChange", "View with ID R.id.main not found")
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        //TODO("Not yet implemented")
    }


}