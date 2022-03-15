package com.example.viewpagertest

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel

class TestViewModel : ViewModel() {
    val outerList: MutableList<String> = mutableListOf()


    var fragments: List<Fragment> = listOf(
        RecyclerViewFragment(),
        RecyclerViewFragment(),
        RecyclerViewFragment(),
        RecyclerViewFragment(),
        RecyclerViewFragment(),
        RecyclerViewFragment(),
    )


    init {
        for (i in 1..6) {
            outerList.add("tab #$i")
        }
    }

}