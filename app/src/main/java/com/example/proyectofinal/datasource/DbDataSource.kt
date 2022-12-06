package com.example.proyectofinal.datasource

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.proyectofinal.model.User
import com.example.proyectofinal.model.UserDao

@Database(entities = [User::class], version = 1)
abstract class DbDataSource : RoomDatabase(){
    abstract fun userDao() : UserDao
}