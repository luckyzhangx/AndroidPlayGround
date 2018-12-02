package com.luckyzhangx.loadmore.core.listviewmodel

import com.luckyzhangx.loadmore.core.presenter.IPresenter

/**
 * called by [IPresenter]
 */
interface IListViewModel<T> : MutableList<T> {
    fun refresh(newList: List<T>)
    fun empty()

    fun loadingNew()
    fun loadingMore()

    fun errorNewData(error: Any)
    fun errorUpdate(error: Any)
}