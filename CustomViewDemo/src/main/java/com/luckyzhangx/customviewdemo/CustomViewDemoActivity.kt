package com.luckyzhangx.customviewdemo

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.luckyzhangx.customviewdemo.textview.CustomTextViewDemoActivity
import kotlinx.android.synthetic.main.activity_custom_view_demo.*

class CustomViewDemoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_view_demo)

        CustomTextViewDemo.setOnClickListener {
            startActivity(Intent(this@CustomViewDemoActivity, CustomTextViewDemoActivity::class.java))
        }
    }
}
