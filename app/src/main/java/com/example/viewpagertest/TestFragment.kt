package com.example.viewpagertest

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

private const val TAG = "TestFragment"

class TestFragment : Fragment() {

    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    private val viewModel: TestViewModel by lazy { ViewModelProvider(this)[TestViewModel::class.java] }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.test_fragment, container, false)

        viewPager = view.findViewById(R.id.view_pager)
        tabLayout = view.findViewById(R.id.tab_layout)
        val adapter = TestAdapter(viewModel.outerList, viewModel)

        viewPager.adapter = adapter

        Log.d(TAG, "onCreateView: innerList ${viewModel.innerList}")

        TabLayoutMediator(tabLayout,viewPager) { tab , position ->
            tab.text = "tab #${position + 1}"
            Log.d(TAG, "onCreateView: ${tab.text}")

        }.attach()

        return view
    }

}