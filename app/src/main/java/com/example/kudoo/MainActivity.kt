package com.example.kudoo

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.example.kudoo.db.dbScope
import com.example.kudoo.model.TodoItem
import com.example.kudoo.view.common.getViewModel
import com.example.kudoo.view.main.RecyclerListAdapter
import com.example.kudoo.viewmodel.TodoViewModel

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.coroutines.*
import android.arch.lifecycle.Observer
import android.content.Intent
import com.example.kudoo.view.add.AddTodoActivity
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {

    private lateinit var viewModel: TodoViewModel

    val uiScope = CoroutineScope(coroutineContext + SupervisorJob())

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private fun setUpFloatingActionButton() {
        fab.setOnClickListener {
            val intent = Intent(this, AddTodoActivity::class.java)
            startActivity(intent) // switches to AddTodoActivity
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        viewModel = getViewModel(TodoViewModel::class)
        setUpRecyclerView()
        setUpFloatingActionButton()

        dbScope.launch {
            repeat(3) {
                delay(1000)
                viewModel.add(TodoItem("Celebrate"))
            }
        }
    }

    private fun setUpRecyclerView() = with(recyclerViewTodos) {
        with(recyclerViewTodos) {
            adapter = RecyclerListAdapter(mutableListOf())   // Populates adapter/list with data
            layoutManager = LinearLayoutManager(this@MainActivity)
            itemAnimator = DefaultItemAnimator()
            addItemDecoration(
                DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL)
            )
        }

        uiScope.launch {
            val todosLiveData = viewModel.getTodos()
            todosLiveData.observe(this@MainActivity, Observer { todos ->
                todos?.let {
                    val adapter = (recyclerViewTodos.adapter as RecyclerListAdapter)
                    adapter.setItems(it)
                }
            })
        }
    }
}
