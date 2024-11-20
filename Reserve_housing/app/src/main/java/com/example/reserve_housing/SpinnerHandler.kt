package com.example.reserve_housing

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.constraintlayout.widget.ConstraintLayout

class SpinnerHandler (private val context: Context, private val spinner: Spinner, private val arrayResId: Int) : Activity(), AdapterView.OnItemSelectedListener{

    var selectedValue: String? = null

    init {
        setupSpinner()

    }

    private fun setupSpinner() {
        ArrayAdapter.createFromResource(
            context,
            arrayResId,  //R.arrays.X
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }
        spinner.setSelection(0)
        spinner.onItemSelectedListener = this
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        Log.d("Spinner-onItemSelected", "Item selected at position: $position")

        val values = context.resources.getStringArray(arrayResId)
        if (position != 0) {
            this.selectedValue = values[position]
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        //TODO("Not yet implemented")
    }


}
