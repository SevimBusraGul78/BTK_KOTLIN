package com.a.b.sqllite.roomDataBase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.a.b.sqllite.model.Tarif

@Database(entities = [Tarif::class], version = 1)  //tarifin versiyonunu değiştirirsen versiyonunuda değiştir
abstract class TarifDataBase : RoomDatabase() {
    abstract fun tarifDao(): TarifDAO
}