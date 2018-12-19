package com.example.pagerheaderdemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_pager_header.*

class PagerHeaderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pager_header)

        contentList.apply {
            adapter = ContentAdapter().apply {
                repeat(30) {
                    list.add(Simple())
                }
                list.add(Page())
            }

        }
    }
}
