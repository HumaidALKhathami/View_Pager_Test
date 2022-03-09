package com.example.viewpagertest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


class TestAdapter(
    private val items : List<String>,
    private val viewModel: TestViewModel
): RecyclerView.Adapter<TestViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view_pager,parent , false)
        return TestViewHolder(view)
    }

    override fun onBindViewHolder(holder: TestViewHolder, position: Int) {


        val adapter = RecyclerViewAdapter(viewModel.innerList)

        holder.rv.adapter = adapter

    }

    override fun getItemCount(): Int = items.size
}

class  TestViewHolder(view: View): RecyclerView.ViewHolder(view){

    val rv : RecyclerView = view.findViewById(R.id.rv)
}
