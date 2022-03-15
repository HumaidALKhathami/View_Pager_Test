package com.example.viewpagertest

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView

private const val TAG = "RecyclerViewFragment"

class RecyclerViewFragment : Fragment() {

    private lateinit var rv: RecyclerView

    private val viewModel: RecyclerViewViewModel by lazy { ViewModelProvider(this)[RecyclerViewViewModel::class.java] }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       val view = inflater.inflate(R.layout.fragment_recycler_view, container, false)
        Log.d(TAG, "onCreateView: RecyclerView fragment Created")

        rv = view.findViewById(R.id.rv)

        return view
    }

    override fun onResume() {
        super.onResume()
        rv.adapter = RecyclerViewAdapter(viewModel.innerList)
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause: Rv fragment")

    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop: RecyclerView fragment Destroyed")
        rv.adapter = null
    }
}