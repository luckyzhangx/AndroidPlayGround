package com.luckyzhangx.coreandroidlibs.bitmappool

import android.graphics.Bitmap
import android.graphics.Point
import android.util.Log

// Created by luckyzhangx on 2019-08-07.
class TPBitmapPool {

    private val mddPoolMgr = SizeBitmapMgr()
    private val poiPoolMgr = SizeBitmapMgr()

    fun recyclerMdd() {
        mddPoolMgr.recycleAll()
    }

    fun recyclePoi() {
        poiPoolMgr.recycleAll()
    }

    fun getMdd(width: Int, height: Int): Bitmap {
        return mddPoolMgr.get(width, height)
    }

    fun getPoi(width: Int, height: Int): Bitmap {
        return poiPoolMgr.get(width, height)
    }
}

class SizeBitmapMgr {
    private val point = Point()

    private val poolMap = mutableMapOf<Point, SizeBitMapList>()
    private val poolUsedMap = mutableMapOf<Point, SizeBitMapList>()

    fun recycleAll() {
        poolUsedMap.entries.forEach {
            poolMap.getOrPut(it.key) { SizeBitMapList(Point(it.key.x, it.key.y)) }.exchange(it.value)
        }
    }

    fun get(width: Int, height: Int): Bitmap {

        point.x = width
        point.y = height

        return (poolMap.getBitmapList(point)
                .getAndRemove()
                ?: createBitmap(width, height))
                .apply {
                    poolUsedMap.getBitmapList(point).add(this)
                }
    }

    private fun createBitmap(width: Int, height: Int): Bitmap {
        return Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
                .apply {
                    Log.d("bitmap", "create $width, $height")
                }
    }

    private fun MutableMap<Point, SizeBitMapList>.getBitmapList(point: Point): SizeBitMapList {
        var list = get(point)
        if (list == null) {
            val newPoint = Point(point.x, point.y)
            list = SizeBitMapList(newPoint)
            put(newPoint, list)
        }
        return list
    }

}


//inline fun <K, V> MutableMap<K, V>.getOrPutKey(key: K, newKey: () -> K, defaultValue: () -> V): V {
//    val value = get(key)
//    return if (value == null) {
//        val answer = defaultValue()
//        put(newKey(), answer)
//        answer
//    } else {
//        value
//    }
//}


