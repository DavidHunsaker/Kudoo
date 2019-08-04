package com.example.kudoo.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "todos")  // Indicates this is a database entity
data class TodoItem(val title: String) {
    @PrimaryKey(autoGenerate = true)  // Unique primary key must identify an object
    var id: Long = 0  // 0 is considered 'not set' by Room
}