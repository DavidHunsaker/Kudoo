package com.example.kudoo

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.example.kudoo.db.AppDatabase
import com.example.kudoo.db.DB
import com.example.kudoo.view.main.RecyclerListAdapter

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class MainActivity : AppCompatActivity(), CoroutineScope {

    private lateinit var db: AppDatabase  // Stores an AppDatabase object

    val uiScope = CoroutineScope(coroutineContext + SupervisorJob())

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        db = AppDatabase.getDatabse(applicationContext)
        setUpRecyclerView()

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

    }

    private fun setUpRecyclerView() = with(recyclerViewTodos) {
        uiScope.launch {
            val todos = sampleData().toMutableList()
            adapter = RecyclerListAdapter(todos)  // Populates adapter/list with data
        }
        layoutManager = LinearLayoutManager(this@MainActivity)
        itemAnimator = DefaultItemAnimator()
        addItemDecoration(
            DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL)
        )
    }

    private suspend fun sampleData() =
        withContext(DB) { db.todoItemDao().loadAllTodos() }
}
