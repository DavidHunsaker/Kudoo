package com.example.kudoo.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.example.kudoo.db.AppDatabase
import com.example.kudoo.db.DB
import com.example.kudoo.db.dbScope
import com.example.kudoo.model.TodoItem
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TodoViewModel(app: Application): AndroidViewModel(app){
    private val dao by lazy { AppDatabase.getDatabse(getApplication()).todoItemDao()}

    suspend fun getTodos(): LiveData<List<TodoItem>> = withContext(DB) {
        dao.loadAllTodos()
    }

    fun add(todo: TodoItem) = dbScope.launch { dao.insertTodo(todo) }

    fun delete(todo: TodoItem) = dbScope.launch { dao.deleteTodo(todo) }
}