package com.example.carpool2

import android.app.Application
import androidx.room.Room

class MainApplication : Application() {
    companion object {
        lateinit var database: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(this, AppDatabase::class.java, "myapp_database")
            .fallbackToDestructiveMigration()
            .build()
    }
}