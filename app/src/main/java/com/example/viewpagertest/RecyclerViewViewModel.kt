package com.example.viewpagertest

import androidx.lifecycle.ViewModel

class RecyclerViewViewModel : ViewModel() {

    val innerList: MutableList<String> = mutableListOf()

    init {
        for (i in 1..100) {
            innerList.add("The quick brown fox jumps over the lazy dog 1 The quick brown fox jumps over the lazy dog 2 The quick brown fox jumps over the lazy dog 3 The quick brown fox jumps over the lazy dog 4 The quick brown fox jumps over the lazy dog 5 The quick brown fox jumps over the lazy dog 6 The quick brown fox jumps over the lazy dog 7 The quick brown fox jumps over the lazy dog 8 ")
        }
    }
}