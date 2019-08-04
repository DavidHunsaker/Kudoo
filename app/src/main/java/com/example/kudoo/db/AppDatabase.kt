package com.example.kudoo.db

import android.arch.persistence.room.*
import android.content.Context  // Needs access to Android context to build DB object
import com.example.kudoo.model.TodoItem

@Database(entities = [TodoItem::class], version = 1)  // TodoItem is only DB entity
abstract class AppDatabase : RoomDatabase() {

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getDatabse(ctx: Context): AppDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(ctx, AppDatabase::class.java, "AppDatabase")
                    .build()
            }
            return INSTANCE!!
        }
    }

    abstract fun todoItemDao(): TodoItemDao // Triggers Room to provide an impl
}