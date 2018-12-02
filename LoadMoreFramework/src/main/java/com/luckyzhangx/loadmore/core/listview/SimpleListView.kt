package com.luckyzhangx.loadmore.core.listview

import com.luckyzhangx.loadmore.core.list.IObservableList
import com.luckyzhangx.loadmore.core.list.OnListChangedCallback

interface SimpleListLoadingView<T> : StateView, SimpleListView<T>

interface SimpleListView<T> : IListView<T> {
    fun refreshData(list: List<T>)
    fun updateData(list: List<T>)
}

class SimpleOnListChangeCallback<T>(val view: SimpleListView<T>) :
        OnListChangedCallback<T> {
    override fun onChanged(sender: IObservableList<T>) {
        view.refreshData(sender)
    }

    override fun onItemRangeChanged(sender: IObservableList<T>, positionStart: Int, itemCount: Int) {
        view.updateData(sender)
    }

    override fun onItemRangeInserted(sender: IObservableList<T>, positionStart: Int, itemCount: Int) {
        view.updateData(sender)
    }

    override fun onItemRangeMoved(sender: IObservableList<T>, fromPosition: Int, toPosition: Int, itemCount: Int) {
        view.updateData(sender)
    }

    override fun onItemRangeRemoved(sender: IObservableList<T>, positionStart: Int, itemCount: Int) {
        view.updateData(sender)
    }
}