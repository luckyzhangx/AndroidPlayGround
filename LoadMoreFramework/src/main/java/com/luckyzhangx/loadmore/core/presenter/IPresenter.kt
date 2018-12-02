package com.luckyzhangx.loadmore.core.presenter

import com.luckyzhangx.loadmore.core.listviewmodel.IListViewModel


interface IPresenter<DATA, VM : IListViewModel<DATA>> {
    val vm: VM?
    fun attachVM(vm: IListViewModel<DATA>)
    fun detachVM(vm: IListViewModel<DATA>)
}