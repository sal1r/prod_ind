package com.example.lifestylehub.utils

import android.view.View

class PageManager(private val pages: List<View>) {
    private var currentPage: Int = 0

    fun showPage(index: Int) {
        pages[currentPage].visibility = View.GONE
        pages[index].visibility = View.VISIBLE
        currentPage = index
    }
}