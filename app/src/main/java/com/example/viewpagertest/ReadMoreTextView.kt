package com.android.mujaz.ui_components

import android.content.Context
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.text.style.TextAppearanceSpan
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import androidx.annotation.Nullable
import com.example.viewpagertest.R

private const val TAG = "ReadMoreTextView"

class ReadMoreTextView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0,
) : androidx.appcompat.widget.AppCompatTextView(context, attributeSet, defStyle) {

    private var showingLine = 1
    private var showingChar = 0
    private var isCharEnable = false

    private var showMore = "Show more"
    private var showLess = "Show less"

    private var showMoreTextColor = Color.RED
    private var showLessTextColor = Color.RED

    private var mainText: String? = null

    private var isAlreadySet = false
    private var callback: OnReadMoreTextViewListener? = null

    init {

        setOnClickListener {
            callback?.onReadMoreTextViewClicked()
            Log.d(TAG, "ReadMoreTextView: Tv has been clicked")
        }
    }



    private fun addReadMore() {
        val vto: ViewTreeObserver = viewTreeObserver
        vto.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                Log.d(TAG, "onGlobalLayout: ")
                val text: String = text.toString()

                if (!isAlreadySet) {
                    Log.d(TAG, "onGlobalLayout: isAlreadySet = false")
                    mainText = getText().toString()
                    isAlreadySet = true
                }
                var showingText = ""
                if (isCharEnable) {
                    Log.d(TAG, "onGlobalLayout: isCharEnable condition")
                    if (showingChar >= text.length) {
                        try {
                            throw Exception("Character count cannot be exceed total line count")
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                    var newText = text.substring(0, showingChar)
                    newText += showMore
                    SaveState.isCollapse = true
                    setText(newText)
//                    Log.d(TAG, "Text: $newText")
                } else {
                    Log.d(TAG, "onGlobalLayout: else")
                    if (showingLine >= getLineCount()) {
                        try {
                            throw Exception("Line Number cannot be exceed total line count")
                        } catch (e: Exception) {
//                            e.printStackTrace()
//                            Log.e(TAG, "Error: cannot be exceed total line count" )
                        }
                        viewTreeObserver.removeOnGlobalLayoutListener(this)
                        return
                    }
                    try {
                        Log.d(TAG, "onGlobalLayout: else try")
                        var start = 0
                        var end: Int
                        for (i in 0 until showingLine) {
                            end = getLayout().getLineEnd(i)
                            showingText += text.substring(start, end)
                            start = end
                        }
                        var newText = showingText.substring(
                            0,
                            showingText.length - (showMore.length + MAGIC_NUMBER)
                        )
//                    Log.d(TAG, "Text: $newText")
//                    Log.d(TAG, "Text: $showingText")
                        newText += showMore
                        SaveState.isCollapse = true
                        setText(newText)
                    } catch (e: Exception) {
//                            e.printStackTrace()
//                            Log.e(TAG, "Error: cannot be exceed total line count" )
                    }

                }
                setShowMoreColoringAndClickable()
                viewTreeObserver.removeOnGlobalLayoutListener(this)
                Log.d(TAG, "onGlobalLayout: bottom of function")
            }
        })
    }

    private fun setShowMoreColoringAndClickable() {
        val spannableString = SpannableString(text)
//        Log.d(TAG, "Text: $text")
        spannableString.setSpan(
            object : ClickableSpan() {
                override fun updateDrawState(ds: TextPaint) {
                    ds.isUnderlineText = false
                }

                override fun onClick(@Nullable view: View) {
                    maxLines = Int.MAX_VALUE
                    text = mainText
                    SaveState.isCollapse = false
                    showLessButton()
                    Log.d(TAG, "Read more clicked")
//                    Log.d(TAG, "Item clicked: $mainText")
                     callback?.onReadMoreTextViewClicked()
                }
            },
            text.length - (showMore.length), text.length, 0
        )
        spannableString.setSpan(
            ForegroundColorSpan(resources.getColor(R.color.teal_700, null)),
            (text.length) - (showMore.length),
            text.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        spannableString.setSpan(
            TextAppearanceSpan(context, R.style.TextAppearance_AppCompat_Body1),
            (text.length) - (showMore.length - 1),
            text.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        movementMethod = LinkMovementMethod.getInstance()
        setText(spannableString, BufferType.SPANNABLE)
    }

    private fun showLessButton() {
        val text: String = text.toString() + showLess
        val spannableString = SpannableString(text)
        spannableString.setSpan(
            object : ClickableSpan() {
                override fun updateDrawState(ds: TextPaint) {
                    ds.isUnderlineText = false
                }

                override fun onClick(@Nullable view: View) {
                    maxLines = showingLine
                   // addReadMore()
                    Log.d(TAG, "Item clicked: ")
                }
            },
            text.length - (showLess.length),
            text.length, 0
        )
        spannableString.setSpan(
            ForegroundColorSpan(showLessTextColor),
            text.length - (showLess.length),
            text.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        setMovementMethod(LinkMovementMethod.getInstance())
        setText(spannableString, BufferType.SPANNABLE)
    }


    /*
     * User added field
     * */

    /*
     * User added field
     * */
    /**
     * User can add minimum line number to show collapse text
     *
     * @param lineNumber int
     */
    fun setShowingLine(lineNumber: Int) {
        if (lineNumber == 0) {
            try {
                throw Exception("Line Number cannot be 0")
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return
        }
        isCharEnable = false
        showingLine = lineNumber
        setMaxLines(showingLine)
        if (SaveState.isCollapse) {
            addReadMore()
        } else {
            setMaxLines(Int.MAX_VALUE)
            showLessButton()
        }
    }

    /**
     * User can limit character limit of text
     *
     * @param character int
     */
    fun setShowingChar(character: Int) {
        if (character == 0) {
            try {
                throw Exception("Character length cannot be 0")
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return
        }
        isCharEnable = true
        showingChar = character
        if (SaveState.isCollapse) {
            addReadMore()
        } else {
            setMaxLines(Int.MAX_VALUE)
            showLessButton()
        }
    }

    /**
     * User can add their own  show more text
     *
     * @param text String
     */
    fun addShowMoreText(text: String) {
        showMore = text
    }

    /**
     * User can add their own show less text
     *
     * @param text String
     */
    fun addShowLessText(text: String) {
        showLess = text
    }

    /**
     * User Can add show more text color
     *
     * @param color Integer
     */
    fun setShowMoreColor(color: Int) {
        showMoreTextColor = color
    }

    /**
     * User can add show less text color
     *
     * @param color Integer
     */
    fun setShowLessTextColor(color: Int) {
        showLessTextColor = color
    }


    fun setOnReadMoreClickedListener(listener: () -> Unit) {
        this.callback = object : OnReadMoreTextViewListener {
            override fun onReadMoreTextViewClicked() {
                listener()
            }

        }

    }


    interface OnReadMoreTextViewListener {
        fun onReadMoreTextViewClicked()
    }

    object SaveState {
        var isCollapse = true
    }

    companion object {
        private const val MAGIC_NUMBER = 5
    }
}