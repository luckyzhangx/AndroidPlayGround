package com.luckyzhangx.loadmore.core.list

import com.luckyzhangx.loadmore.core.callback.CallbackManager

class ObservableArrayList<T> : ObservableArrayListImpl<T>(mutableListOf())

open class ObservableArrayListImpl<T>(val list: MutableList<T>) : IObservableList<T>,
        MutableList<T> by list {

    private val callbackManager = CallbackManager<OnListChangedCallback<T>>()

    override fun addListCallback(callback: OnListChangedCallback<T>) {
        callbackManager.addCallback(callback)
    }

    override fun removeListCallback(callback: OnListChangedCallback<T>) {
        callbackManager.addCallback(callback)
    }

    //<editor-fold desc="mutable list">
    override fun add(element: T): Boolean {
        val oldSize = list.size
        return list.add(element).also {
            callbackManager.forEach { callback ->
                callback.onItemRangeInserted(this, oldSize - 1, 1)
            }
        }
    }

    override fun add(index: Int, element: T) {
        list.add(index, element).also {
            callbackManager.forEach { callback ->
                callback.onItemRangeInserted(this, index, 1)
            }
        }
    }

    override fun addAll(index: Int, elements: Collection<T>): Boolean {
        return list.addAll(index, elements).also {
            callbackManager.forEach { callback ->
                callback.onItemRangeInserted(this, index, elements.size)
            }
        }
    }

    override fun addAll(elements: Collection<T>): Boolean {
        val oldSize = list.size
        return list.addAll(elements).also {
            callbackManager.forEach { callback ->
                callback.onItemRangeInserted(this, oldSize - 1, elements.size)
            }
        }
    }

    override fun clear() {
        val oldSize = list.size
        list.clear().also {
            callbackManager.forEach { callback ->
                callback.onItemRangeRemoved(this, 0, oldSize)
            }
        }
    }

    override fun remove(element: T): Boolean {
        val index = list.indexOf(element)
        return list.remove(element).also {
            if (index >= 0)
                callbackManager.forEach { callback ->
                    callback.onItemRangeRemoved(this, index, 1)
                }
        }
    }

    override fun removeAll(elements: Collection<T>): Boolean {
        return list.removeAll(elements)
    }

    override fun removeAt(index: Int): T {
        return list.removeAt(index).also {
            callbackManager.forEach { callback ->
                callback.onItemRangeRemoved(this, index, 1)
            }
        }
    }

//    override fun replaceAll(operator: UnaryOperator<T>) {
//        list.replaceAll(operator)
//    }

    override fun retainAll(elements: Collection<T>): Boolean {
        return list.retainAll(elements).also {
            callbackManager.forEach { callback ->
                callback.onChanged(this)
            }
        }
    }

    override fun set(index: Int, element: T): T {
        return list.set(index, element).also {
            callbackManager.forEach { callback ->
                callback.onItemRangeChanged(this, index, 1)
            }
        }
    }

//    override fun sort(c: Comparator<in T>?) {
//        list.sort(c)
//    }
    //</editor-fold>
}

