package com.luckyzhangx.loadmore.core.list

interface IObservableList<T> : MutableList<T> {
    fun addListCallback(callback: OnListChangedCallback<T>)
    fun removeListCallback(callback: OnListChangedCallback<T>)
}

interface OnListChangedCallback<T> {

    /**
     * Called whenever a change of unknown type has occurred, such as the entire list being
     * set to new values.
     *
     * @param sender The changing list.
     */
    fun onChanged(sender: IObservableList<T>)

    fun onItemRangeChanged(sender: IObservableList<T>, positionStart: Int, itemCount: Int)

    fun onItemRangeInserted(sender: IObservableList<T>, positionStart: Int, itemCount: Int)

    fun onItemRangeMoved(sender: IObservableList<T>, fromPosition: Int, toPosition: Int,
                         itemCount: Int)

    fun onItemRangeRemoved(sender: IObservableList<T>, positionStart: Int, itemCount: Int)
}
