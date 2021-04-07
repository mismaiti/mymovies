package com.mismaiti.mymovies.util

import android.view.View
import android.widget.TextView
import java.util.*


fun TextView.set(text: String?) {
    this.text = text
}

fun TextView.reset() {
    text = null
}

fun TextView.get() = text.toString()

fun TextView.getDayGreeting() {
    val c: Calendar = Calendar.getInstance()
    val s = when (c.get(Calendar.HOUR_OF_DAY)) {
        in 0..11 -> "Good Morning,"
        in 12..15 -> "Good Afternoon,"
        in 16..20 -> "Good Evening,"
        else -> "Good Night,"
    }
    text = s
}

fun View.show() {
    visibility =  View.VISIBLE
}

fun View.hide() {
    visibility =  View.GONE }

fun View.invisible() {
    visibility = View.INVISIBLE }
