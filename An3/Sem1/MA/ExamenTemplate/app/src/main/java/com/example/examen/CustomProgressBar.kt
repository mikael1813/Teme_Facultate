package com.example.examen

import android.view.View
import android.widget.ProgressBar

class CustomProgressBar {

    var progressBar: ProgressBar
    var queue = 0

    constructor(progressBar: ProgressBar) {
        this.progressBar = progressBar
    }

    @Synchronized
    fun add() {
        queue += 1
    }

    @Synchronized
    fun substract() {
        queue -= 1
        if (queue == 0) {
            progressBar.visibility = View.INVISIBLE
        }
    }
}