package com.pawat.cryptoapp.extensions

fun Double.numFormatter(): String{
    return if (this > 99999999999) {
        "${String.format("%,.2f", this/100000000000)} trillion"
    } else if (this > 999999999) {
        "${String.format("%,.2f", this/1000000000)} billion"
    } else if (this > 999999){
        "${String.format("%,.2f", this/1000000)} million"
    } else {
        String.format("%,.2f", this)
    }
}