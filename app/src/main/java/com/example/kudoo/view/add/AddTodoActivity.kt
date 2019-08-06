package com.example.kudoo.view.add

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.kudoo.R
import com.example.kudoo.db.dbScope
import com.example.kudoo.model.TodoItem
import com.example.kudoo.view.common.getViewModel
import com.example.kudoo.viewmodel.TodoViewModel
import kotlinx.android.synthetic.main.activity_add_todo.*
import kotlinx.coroutines.launch

class AddTodoActivity : AppCompatActivity() {

    private lateinit var viewModel: TodoViewModel // Use the view model as well

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_todo)

        viewModel = getViewModel(TodoViewModel::class)
        setUpListeners()
    }

    private fun setUpListeners() {
        btnAddTodo.setOnClickListener {
            val newTodo = etNewTodo.text.toString()
            dbScope.launch { viewModel.add(TodoItem(newTodo)) }
            finish()
        }
    }
}
