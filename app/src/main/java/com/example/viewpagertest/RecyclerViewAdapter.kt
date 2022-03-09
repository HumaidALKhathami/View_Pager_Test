package com.example.viewpagertest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class RecyclerViewAdapter(
    private val innerList: List<String>
): RecyclerView.Adapter<RecyclerViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_view,parent,false)
        return RecyclerViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        val item = innerList[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = innerList.size
}

class RecyclerViewHolder(view: View): RecyclerView.ViewHolder(view){

    private val textView : TextView = view.findViewById(R.id.textView)

    fun bind (item: String){
        textView.text = item
    }
}