package com.example.carpool2

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class RideDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "rides.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "rides"
        private const val COLUMN_ID = "id"
        private const val COLUMN_PICKUP = "pickup_location"
        private const val COLUMN_DROP = "drop_location"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = ("CREATE TABLE $TABLE_NAME ("
                + "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "$COLUMN_PICKUP TEXT,"
                + "$COLUMN_DROP TEXT)")
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun saveRide(ride: Ride): Long {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_PICKUP, ride.pickupLocation)
        values.put(COLUMN_DROP, ride.dropLocation)
        val result = db.insert(TABLE_NAME, null, values)
        db.close()
        return result
    }

    fun getAllRides(): List<Ride> {
        val rideList = ArrayList<Ride>()
        val selectQuery = "SELECT * FROM $TABLE_NAME"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                // Correct the column names
                val pickup = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PICKUP))
                val drop = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DROP))
                val ride = Ride(pickup, drop)
                rideList.add(ride)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return rideList
    }

}





