package com.example.agendapersonal

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.example.bbddfer.DatabaseHelper

class DatabaseManager {

    companion object {

        public fun deleteContactDB(context: Context, id: Int) {
            Log.d("DatabaseManager", "Entered deleteContactDB function")

            val dbHelper = DatabaseHelper(context);
            val db = dbHelper.getWritableDatabase();
            val rowsDeleted = db.delete(
                DatabaseHelper.TABLE_CLIENTES,
                DatabaseHelper.COLUMN_ID + "=?",
                arrayOf(id.toString()))
        }

        public fun addContactDB(context: Context, contacto: Contacto) {
            val dbHelper = DatabaseHelper(context);

            val db = dbHelper.writableDatabase
            val values = ContentValues()
            values.put(DatabaseHelper.COLUMN_NOMBRE, contacto.nombre)
            values.put(DatabaseHelper.COLUMN_MOVIL, contacto.telefono)
            values.put(DatabaseHelper.COLUMN_EMAIL, contacto.correo)
            val result = db.insert(
                DatabaseHelper.TABLE_CLIENTES,
                null, values
            )
        }

        public fun allContacts(context: Context): MutableList<Contacto> {
            Log.d("DatabaseManager", "Entered allContacts function")
            val contacts = mutableListOf<Contacto>()
            var cont: Int = 0

            val dbHelper = DatabaseHelper(context)
            val db: SQLiteDatabase = dbHelper.readableDatabase

            val cursor = db.query(
                DatabaseHelper.TABLE_CLIENTES,
                null,
                null,
                null,
                null,
                null,
                null
            )

            val idIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_ID)
            val nombreIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_NOMBRE)
            val movilIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_MOVIL)
            val emailIndex = cursor.getColumnIndex(DatabaseHelper.COLUMN_EMAIL)

            while (cursor.moveToNext()) {
                if (idIndex >= 0 && nombreIndex >= 0 && movilIndex >= 0 && emailIndex >= 0) {
                    Log.d("DatabaseManager", "Entered cursor loop")

                    val id = cursor.getInt(idIndex)
                    val nombre = cursor.getString(nombreIndex)
                    val movil = cursor.getString(movilIndex)
                    val email = cursor.getString(emailIndex)

                    contacts.add(Contacto(id, nombre, movil, email))
                    cont++
                }
            }


            return contacts
        }
    }

}