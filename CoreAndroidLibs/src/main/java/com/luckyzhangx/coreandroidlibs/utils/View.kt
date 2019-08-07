package com.luckyzhangx.coreandroidlibs.utils

import android.view.View
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.actor

// Created by luckyzhangx on 2019-08-07.
fun View.onClickStart(delay: Long = 0, action: suspend (v: View) -> Unit) {
    val eventActor = GlobalScope.actor<View>(Dispatchers.Main) {
        for (view in channel) {
            action.invoke(view)
            if (delay > 0) {
                kotlinx.coroutines.delay(delay)
            }
        }
    }

    setOnClickListener {
        eventActor.offer(it)
    }
}