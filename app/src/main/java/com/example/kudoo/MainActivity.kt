package com.example.kudoo

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.example.kudoo.view.main.RecyclerListAdapter
import com.example.kudoo.model.TodoItem

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        setUpRecyclerView()
    }

    private fun setUpRecyclerView() = with(recyclerViewTodos) {
        adapter = RecyclerListAdapter(sampleData())  // Populates adapter/list with data
        layoutManager = LinearLayoutManager(this@MainActivity)
        itemAnimator = DefaultItemAnimator()
        addItemDecoration(
            DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL)
        )
    }

    private fun sampleData() = mutableListOf(
        TodoItem("Implement RecyclerView"),
        TodoItem("Store to-dos in database"),
        TodoItem("Delete to-dos on click")
    )
}
