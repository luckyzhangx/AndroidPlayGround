package com.example.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DefaultItemAnimator
import com.luckyzhangx.coreandroidlibs.renderer.renderadapter.DiffViewRendererAdapter
import com.luckyzhangx.coreandroidlibs.utils.renderer.TextViewRenderer
import com.luckyzhangx.corejavalibs.core.configure
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    val mAdapter = DiffViewRendererAdapter()

    val mList = mutableListOf<TextViewRenderer>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        configure(list) {
            adapter = this@MainActivity.mAdapter
            itemAnimator = DefaultItemAnimator().apply {
                supportsChangeAnimations = false
                val duration = 1000L
                addDuration = duration
                changeDuration = duration
                removeDuration = duration
                moveDuration = duration
            }
        }
        mAdapter.postList(mList)

        insert.setOnClickListener { mList.add(TextViewRenderer("new")) }
        remove.setOnClickListener { mList.removeAt(mList.lastIndex) }
        change.setOnClickListener { mAdapter.notifyItemChanged(0) }
        post.setOnClickListener { mAdapter.postList(mList) }

    }
}
