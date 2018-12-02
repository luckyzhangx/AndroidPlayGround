package com.luckyzhangx.loadmore.core.listview

import com.luckyzhangx.corejavalibs.annotations.Incubating
import com.luckyzhangx.loadmore.core.list.IObservableList
import com.luckyzhangx.loadmore.core.list.OnListChangedCallback
import java.lang.IllegalStateException

@Incubating
interface SensativeListView<T> : IListView<T> {
    //todo: 响应
}

@Incubating
class SensativeOnListChangeCallback<T>(val view: SensativeListView<T>) :
        OnListChangedCallback<T> {
    override fun onChanged(sender: IObservableList<T>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemRangeChanged(sender: IObservableList<T>, positionStart: Int, itemCount: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemRangeInserted(sender: IObservableList<T>, positionStart: Int, itemCount: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemRangeMoved(sender: IObservableList<T>, fromPosition: Int, toPosition: Int, itemCount: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemRangeRemoved(sender: IObservableList<T>, positionStart: Int, itemCount: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

object ListChangeBackBuilder {
    fun <T> build(view: IListView<T>): OnListChangedCallback<T> {
        return when (view) {
            is SimpleListView -> {
                SimpleOnListChangeCallback(view)
            }
            is SensativeListView -> {
                SensativeOnListChangeCallback(view)
            }
            else -> {
                throw IllegalStateException()
            }
        }
    }
}