package com.luckyzhangx.viewrenderer

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import org.jetbrains.anko.find
import org.jetbrains.anko.verticalLayout

class ViewRendererActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_renderer)

        verticalLayout()
    }
}
