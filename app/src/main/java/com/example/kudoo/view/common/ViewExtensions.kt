package com.example.kudoo.view.common

import android.arch.lifecycle.*
import android.support.v4.app.FragmentActivity
import kotlin.reflect.KClass

fun <T: ViewModel> FragmentActivity.getViewModel(modelClass: KClass<T>): T =
    ViewModelProviders.of(this).get(modelClass.java)