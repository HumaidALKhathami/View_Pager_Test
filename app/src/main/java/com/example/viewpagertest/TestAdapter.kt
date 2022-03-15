package com.example.viewpagertest

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import java.security.AccessController.getContext


class TestAdapter(
    private val items: List<Fragment>,
    activity: AppCompatActivity
) : FragmentStateAdapter(activity) {


    override fun getItemCount(): Int = items.size
    override fun createFragment(position: Int): Fragment {

        return items[position]
    }
}

