package com.luckyzhangx.customviewdemo

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.luckyzhangx.customviewlib.blur.BlurUtil
import kotlinx.android.synthetic.main.activity_pullbouncedemo.*
import org.jetbrains.anko.imageBitmap

// Created by luckyzhangx on 2019/4/4.
class PullBounceDemoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pullbouncedemo)

        val bitmap = BitmapFactory.decodeResource(this@PullBounceDemoActivity.resources, R.drawable.bayonetta)
        val yellowBitmap = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
        val pureBitmap = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)

        Canvas(yellowBitmap).apply {
            drawColor(Color.YELLOW)
        }

        Canvas(pureBitmap).apply {
            drawColor(Color.WHITE)
        }
        img.setImageBitmap(BlurUtil.with(this@PullBounceDemoActivity)
                .bitmap(bitmap)
                .scale(maxOf(1, 30))
                .radius(20)
                .blur())
        img.alpha = 0.7f
        imgBelow.imageBitmap = yellowBitmap

    }
}
