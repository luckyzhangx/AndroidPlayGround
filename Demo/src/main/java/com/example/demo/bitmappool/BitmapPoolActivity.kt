package com.example.demo.bitmappool

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.demo.R
import com.luckyzhangx.coreandroidlibs.bitmappool.TPBitmapPool
import kotlinx.android.synthetic.main.activity_bitmap_pool.*

class BitmapPoolActivity : AppCompatActivity() {

    private val mgr = TPBitmapPool()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bitmap_pool)

        getPoi.setOnClickListener {
            mgr.getPoi(bWidth, bHeight)
        }

        getMdd.setOnClickListener {
            mgr.getMdd(bWidth, bHeight)

        }

        recyclePoi.setOnClickListener {
            mgr.recyclePoi()
        }

        recyclePoi.setOnClickListener {
            mgr.recyclerMdd()
        }

    }

    private val bWidth: Int
        get() {
            return width.text.toString().toInt()
        }
    private val bHeight: Int
        get() {
            return width.text.toString().toInt()
        }
}
