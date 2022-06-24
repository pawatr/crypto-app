package com.pawat.cryptoapp.extensions

import android.content.Context
import android.content.Intent

fun Context.startActivity(cls: Class<*>, vararg extras: Pair<String, String>): Boolean {
    val intent = Intent(this, cls)

    for ((name, value) in extras) {
        intent.putExtra(name, value)
    }

    startActivity(intent)
    return true // support menu clicks
}