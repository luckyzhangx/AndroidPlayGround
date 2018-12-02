package com.luckyzhangx.loadmore.core.list

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import com.luckyzhangx.loadmore.core.callback.CallbackManager
import com.luckyzhangx.loadmore.core.livedata.LifeAwareLiveData
import com.luckyzhangx.loadmore.core.livedata.LiveDataLifeAwarer

class LiveObservableArrayList<T> : LiveObservableArrayListImpl<T>(ObservableArrayList())

open class LiveObservableArrayListImpl<T>(val list: IObservableList<T>) :
        LifeAwareLiveData<IObservableList<T>>(),
        IObservableList<T> by list {

    private val wrapperMgr = CallbackManager<CallbackWrapper<T>>()

    fun observeCallback(owner: LifecycleOwner, callback: OnListChangedCallback<T>) {
        super.observe(owner, CallbackWrapper(list, callback).also {
            wrapperMgr.addCallback(it)
        })
    }

    fun removeCallback(owner: LifecycleOwner, callback: OnListChangedCallback<T>) {
        wrapperMgr.find { it.callback == callback }?.apply {
            super.removeObserver(this)
            wrapperMgr.removeCallback(this)
        }
    }
}

private class CallbackWrapper<T>(val list: IObservableList<T>, val callback: OnListChangedCallback<T>) :
        LiveDataLifeAwarer<IObservableList<T>>, Observer<IObservableList<T>> {

    override fun onChanged(t: IObservableList<T>?) {
        t?.apply {
            callback.onChanged(this)
        }
    }


    override fun onObserve(liveData: LiveData<IObservableList<T>>) {
        list.addListCallback(callback)
    }

    override fun onRemove(liveData: LiveData<IObservableList<T>>) {
        list.removeListCallback(callback)
    }

}

