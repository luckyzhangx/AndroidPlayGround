package com.luckyzhangx.coreandroidlibs.bitmappool

import android.graphics.Bitmap
import android.graphics.Point

// Created by luckyzhangx on 2019-08-07.

class SizeBitMapList(val size: Point) {

    private val list = mutableListOf<Bitmap>()

    fun add(bitmap: Bitmap) {
        throwIfSizeError(bitmap)
        list.add(bitmap)
    }

    fun getAndRemove(): Bitmap? {
        if (list.isNotEmpty())
            return list.removeAt(0)
        return null
    }

    private fun checkBitmapSize(bitmap: Bitmap): Boolean {
        if (bitmap.width != size.x || bitmap.height != size.y) {
            return false
        }
        return true
    }

    private fun throwIfSizeError(bitmap: Bitmap) {
        if (!checkBitmapSize(bitmap)) {
            throw IllegalStateException("Bitmap 尺寸错误：pool: (w:${size.x}, h:${size.y}, bitmap: (w:${bitmap.width}, h:${bitmap.height})")
        }
    }

    fun exchange(from: SizeBitMapList) {
        if (size != from.size)
            return

        var bitmap: Bitmap? = from.getAndRemove()
        while (bitmap != null) {
            this.add(bitmap)
            bitmap = from.getAndRemove()
        }
    }
}