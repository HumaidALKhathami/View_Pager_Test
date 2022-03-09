package com.example.viewpagertest

import androidx.lifecycle.ViewModel

class TestViewModel : ViewModel() {
    val innerList : MutableList<String> = mutableListOf()

    val outerList : MutableList<String> = mutableListOf()


    init {
        for ( i in 1..100){
            innerList.add("item #$i")
        }

        for ( i in 1..20){
            outerList.add("tab #$i")
        }

    }

}