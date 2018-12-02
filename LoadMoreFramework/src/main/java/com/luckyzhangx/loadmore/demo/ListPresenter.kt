package com.luckyzhangx.loadmore.demo

import com.luckyzhangx.loadmore.core.listviewmodel.IListViewModel
import com.luckyzhangx.loadmore.core.presenter.IPresenter

class ListPresenter : IPresenter<String, IListViewModel<String>> {

    private var _vm: IListViewModel<String>? = null

    override val vm: IListViewModel<String>?
        get() = _vm

    override fun attachVM(vm: IListViewModel<String>) {
        _vm = vm
    }

    override fun detachVM(vm: IListViewModel<String>) {
        _vm = null
    }

    fun refresh() {
        vm?.loadingNew()
        vm?.refresh(generateStringList(1, 20))
    }

    fun loadMore() {
        vm?.loadingMore()
        vm?.addAll(generateStringList(vm!!.size + 1, 20))
    }
}

private fun generateStringList(start: Int, size: Int): List<String> {
    return mutableListOf<String>().apply {
        for (i in start until (start + size)) {
            add(i.toString())
        }
    }
}