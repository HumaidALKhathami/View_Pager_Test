package com.example.viewpagertest

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.TextAppearanceSpan
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.android.mujaz.ui_components.ReadMoreTextView
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





//
//        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
//            override fun onTabSelected(tab: TabLayout.Tab?) {
//
//            }
//
//            override fun onTabUnselected(tab: TabLayout.Tab?) {
//            }
//
//            override fun onTabReselected(tab: TabLayout.Tab?) {
//            }
//        })
        return view
    }

    override fun onResume() {
        super.onResume()
        viewPager.adapter  = TestAdapter(viewModel.fragments , requireActivity() as AppCompatActivity)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = "tab #${position + 1}"
            Log.d(TAG, "onCreateView: ${tab.text}")

        }.attach()
    }

    override fun onPause() {
        super.onPause()
        viewPager.adapter = null
    }

    override fun onDestroy() {
        super.onDestroy()

        viewPager.adapter = null
    }
}

const val THREE_LINES_LONG = 150

fun TextView.readMoreText(text: String, showMore: String, showLess: String) {

    if (text.length >= THREE_LINES_LONG) {

        val newText = text.substring(0, THREE_LINES_LONG - showMore.length)
            .plus(showMore)

        val spannableString = SpannableString(newText)

        val spannableString2 = SpannableString(text.plus(showLess))

        spannableString.setSpan(
            object : ClickableSpan() {
                override fun onClick(widget: View) {
                    Log.d(TAG, "onClick: is clicked")

                    movementMethod = LinkMovementMethod.getInstance()

                    setText(spannableString2, TextView.BufferType.SPANNABLE)
                }
            },
            newText.length - showMore.length, newText.length, 0
        )

        spannableString2.setSpan(
            object : ClickableSpan() {
                override fun onClick(widget: View) {
                    movementMethod = LinkMovementMethod.getInstance()

                    setText(spannableString, TextView.BufferType.SPANNABLE)
                }
            },
            text.length, text.length + showLess.length, 0
        )

        movementMethod = LinkMovementMethod.getInstance()

        setText(spannableString, TextView.BufferType.SPANNABLE)

    } else {
        setText(text)
    }


}