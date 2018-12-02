package com.luckyzhangx.loadmore.demo

import android.content.Context
import android.widget.Toast
import com.luckyzhangx.loadmore.core.listview.SimpleListLoadingView

interface SimpleListStateViewImpl<T> : SimpleListLoadingView<T> {

    val stateViewProvider: StateViewProvider

    override fun onRefreshing() {
        toast("refreshing")
    }

    override fun onLoadingMore() {
        toast("loading more")
    }

    override fun onNoMore() {
        toast("no more")
    }

    override fun onShowList() {
        toast("show list")
    }

    override fun onShowEmpty() {
        toast("empty")
    }

    override fun onShowErrorRefresh(error: Throwable) {
        toast("error refresh")
    }

    override fun onShowErrorUpdate(error: Throwable) {
        toast("error update")
    }

    private fun toast(toast: String) {
        Toast.makeText(stateViewProvider.context, toast, Toast.LENGTH_SHORT).show()
    }
}

interface StateViewProvider {
    val context: Context
}

