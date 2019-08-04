package com.example.kudoo.db

import android.arch.persistence.room.*
import android.content.Context  // Needs access to Android context to build DB object
import com.example.kudoo.model.TodoItem
import android.arch.persistence.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch
import java.util.concurrent.Executors

val DB = Executors.newSingleThreadExecutor().asCoroutineDispatcher()

val dbScope = CoroutineScope(DB)

@Database(entities = [TodoItem::class], version = 1)  // TodoItem is only DB entity
abstract class AppDatabase : RoomDatabase() {

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getDatabse(ctx: Context): AppDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(ctx, AppDatabase::class.java, "AppDatabase")
                    .addCallback(prepopulateCallback(ctx))
                    .build()
            }
            return INSTANCE!!
        }

        private fun prepopulateCallback(ctx: Context): Callback {
            return object: Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    populateWithSampleData(ctx)
                }
            }
        }

        private fun populateWithSampleData(ctx: Context) {
            dbScope.launch {
                with(getDatabse(ctx).todoItemDao()) {
                    insertTodo(TodoItem("Create entity"))
                    insertTodo(TodoItem("Add a DAO for data access"))
                    insertTodo(TodoItem("Inherit from RoomDatabase"))
                }
            }
        }
    }

    abstract fun todoItemDao(): TodoItemDao // Triggers Room to provide an impl
}