package com.luckyzhangx.loadmore.core

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import com.luckyzhangx.loadmore.core.list.LiveObservableArrayList
import com.luckyzhangx.loadmore.core.listview.IListView
import com.luckyzhangx.loadmore.core.listview.ListChangeBackBuilder
import com.luckyzhangx.loadmore.core.listview.StateView
import com.luckyzhangx.loadmore.core.listviewmodel.IListViewModel

open class ListViewModel<T> : ViewModel(), IListViewModel<T> {

    fun <View> attachView(view: View)
            where View : StateView,
                  View : IListView<T> {
        list.observeCallback(view as LifecycleOwner, ListChangeBackBuilder.build(view))
        stateLiveData.observe(view as LifecycleOwner, Observer {
            when (it) {
                DataState.LOADING_UPDATE -> {
                    view.onLoadingMore()
                }

                DataState.LOADING_NEW -> {
                    view.onRefreshing()
                }
            }
        })

    }

    private val list = LiveObservableArrayList<T>()

    private val stateLiveData: MutableLiveData<DataState> = MutableLiveData()

    //<editor-fold desc="boiler plate for mutable list">

    private fun notifyUpdate() {
        stateLiveData.value = DataState.DATA_UPDATE
    }

    private fun notifyNew() {
        stateLiveData.value = DataState.DATA_NEW
    }

    override fun refresh(newList: List<T>) {
        list.clear()
        list.addAll(newList)
        notifyNew()
    }

    override val size: Int
        get() = list.size

    override fun add(element: T): Boolean {
        return list.add(element).also {
            notifyUpdate()
        }
    }

    override fun add(index: Int, element: T) {
        return list.add(index, element).also {
            notifyUpdate()
        }
    }

    override fun addAll(index: Int, elements: Collection<T>): Boolean {
        return list.addAll(index, elements).also {
            notifyUpdate()
        }
    }

    override fun addAll(elements: Collection<T>): Boolean {
        return list.addAll(elements).also {
            notifyUpdate()
        }
    }

    override fun clear() {
        list.clear()
        notifyUpdate()
    }

    override fun contains(element: T): Boolean {
        return list.contains(element)
    }

    override fun containsAll(elements: Collection<T>): Boolean {
        return list.containsAll(elements)
    }

    override fun get(index: Int): T {
        return list.get(index)
    }

    override fun indexOf(element: T): Int {
        return list.indexOf(element)
    }

    override fun isEmpty(): Boolean {
        return list.isEmpty()
    }

    override fun iterator(): MutableIterator<T> {
        return list.iterator()
    }

    override fun lastIndexOf(element: T): Int {
        return list.lastIndexOf(element)
    }

    override fun listIterator(): MutableListIterator<T> {
        return list.listIterator()
    }

    override fun listIterator(index: Int): MutableListIterator<T> {
        return list.listIterator(index)
    }

    override fun remove(element: T): Boolean {
        return list.remove(element)
    }

    override fun removeAll(elements: Collection<T>): Boolean {
        return list.removeAll(elements)
    }

    override fun removeAt(index: Int): T {
        return list.removeAt(index)
    }

    override fun retainAll(elements: Collection<T>): Boolean {
        return list.retainAll(elements)
    }

    override fun set(index: Int, element: T): T {
        return list.set(index, element)
    }

    override fun subList(fromIndex: Int, toIndex: Int): MutableList<T> {
        return list.subList(fromIndex, toIndex)
    }
    //</editor-fold>

    override fun loadingNew() {
        stateLiveData.value = DataState.LOADING_NEW
    }

    override fun loadingMore() {
        stateLiveData.value = DataState.LOADING_UPDATE
    }

    override fun empty() {
        list.clear()
        stateLiveData.value = DataState.DATA_EMPTY
    }

    override fun errorNewData(error: Any) {
        stateLiveData.value = DataState.ERROR_NEW(error as Throwable)
    }

    override fun errorUpdate(error: Any) {
        stateLiveData.value = DataState.ERROR_UPDATE(error as Throwable)
    }
}