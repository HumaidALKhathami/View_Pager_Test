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
        val adapter = TestAdapter(viewModel.outerList)

        viewPager.adapter = adapter

        Log.d(TAG, "onCreateView: innerList ${viewModel.innerList}")

        TabLayoutMediator(tabLayout,viewPager) { tab , position ->
            tab.text = "tab #${position + 1}"
            Log.d(TAG, "onCreateView: ${tab.text}")

        }.attach()

        return view
    }

    inner class TestAdapter(
        private val items : List<String>
    ): RecyclerView.Adapter<TestViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestViewHolder {
            val view = layoutInflater.inflate(R.layout.item_view_pager, parent, false)
            return TestViewHolder(view)
        }

        override fun onBindViewHolder(holder: TestViewHolder, position: Int) {


            val adapter = RecyclerViewAdapter(viewModel.innerList)

            holder.rv.adapter = adapter

        }

        override fun getItemCount(): Int = items.size
    }

    inner class  TestViewHolder(view: View): RecyclerView.ViewHolder(view){

        val rv : RecyclerView = view.findViewById(R.id.rv)
    }

    inner class RecyclerViewAdapter(
        private val innerList: List<String>
    ): RecyclerView.Adapter<RecyclerViewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
            val view = layoutInflater.inflate(R.layout.item_recycler_view,parent,false)
            return RecyclerViewHolder(view)
        }

        override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
            val item = innerList[position]
            holder.bind(item)
        }

        override fun getItemCount(): Int = innerList.size
    }

    inner class RecyclerViewHolder(view: View): RecyclerView.ViewHolder(view){

        private val textView : TextView = view.findViewById(R.id.textView)

        fun bind (item: String){
            textView.text = item
        }
    }
}