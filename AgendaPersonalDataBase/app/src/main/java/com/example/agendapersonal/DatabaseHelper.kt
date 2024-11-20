package com.example.bbddfer

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(TABLE_CREATE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLIENTES)
        onCreate(db)
    }

    companion object {
        private const val DATABASE_NAME = "BBDDContactos.db"
        private const val DATABASE_VERSION = 1

        const val TABLE_CLIENTES: String = "contactos"
        const val COLUMN_ID: String = "id"
        const val COLUMN_NOMBRE: String = "nombre"
        const val COLUMN_MOVIL: String = "movil"
        const val COLUMN_EMAIL: String = "email"


        private const val TABLE_CREATE = "CREATE TABLE " + TABLE_CLIENTES + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NOMBRE + " TEXT NOT NULL, " +
                COLUMN_MOVIL + " TEXT NOT NULL, " +
                COLUMN_EMAIL + " TEXT);"
    }

}