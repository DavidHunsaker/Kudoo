package com.example.kudoo.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import android.arch.persistence.room.OnConflictStrategy.IGNORE
import com.example.kudoo.model.TodoItem

@Dao
interface TodoItemDao {
    @Query("SELECT * FROM todos")
    fun loadAllTodos(): LiveData<List<TodoItem>>  // Allows retrieving all to-do items

    @Insert(onConflict = IGNORE)  // Does nothing if entry with ID already exists
    fun insertTodo(todo: TodoItem)  // Allows inserting a new to-do item

    @Delete
    fun deleteTodo(todo: TodoItem)  // Allows deleting an existing to-do item
}