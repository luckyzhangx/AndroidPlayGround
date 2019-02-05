package com.luckyzhangx.customviewdemo.textview

import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.luckyzhangx.customviewdemo.R
import com.luckyzhangx.customviewlib.textview.span.CustomReplacementSpan
import kotlinx.android.synthetic.main.activity_custom_text_view_demo.*

class CustomTextViewDemoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_text_view_demo)

        val spannable = SpannableString("123123a bullet shot on my face. how do you feel? feel " +
                "nothing?" +
                " fell everything?")
        tv2.setText(spannable, TextView.BufferType.SPANNABLE)
//        spannable.setSpan(
//                BulletSpan(10, Color.RED),
//                /* start index */ 9, /* end index */ 18,
//                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
//        spannable.setSpan(
//                BulletSpan(20, Color.BLACK),
//                /* start index */ 20, /* end index */ spannable.length,
//                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
//        spannable.setSpan(
//                LeadingMarginSpan.Standard(100, 50), 0, spannable.length - 1, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)
//        spannable.setSpan(TopAlignRelativeSizeSpan(1f), 3, 4, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)
//        spannable.setSpan(BaseLineOffsetSpan(100), 20, 30, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)
        spannable.setSpan(CustomReplacementSpan(), 32, 36, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)
        val typeface = Typeface.create("din_bold", Typeface.BOLD)
        spannable.setSpan(typeface, 0, 3, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)
//        spannable.setSpan(SuperscriptSpan(), 5, 7, Spanned.SPAN_EXCLUSIVE_INCLUSIVE)
        tv.setText(spannable, TextView.BufferType.SPANNABLE)
//        println(typeface)
//        tv.typeface = typeface
    }
}
