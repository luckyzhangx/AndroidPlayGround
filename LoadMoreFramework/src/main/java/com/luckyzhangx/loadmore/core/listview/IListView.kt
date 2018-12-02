package com.luckyzhangx.loadmore.core.listview

/**
 * 响应 [DataState] 的 View：需要根据不同状态更改对应 View 的 visibility
 */
interface StateView {
    fun onRefreshing()
    fun onLoadingMore()
    fun onNoMore()
    fun onShowList()
    fun onShowEmpty()
    fun onShowErrorRefresh(error: Throwable)
    fun onShowErrorUpdate(error: Throwable)
}

/**
 * 标记一个能够更新列表的 View。
 * 不要实现这个类！
 */
interface IListView<T>