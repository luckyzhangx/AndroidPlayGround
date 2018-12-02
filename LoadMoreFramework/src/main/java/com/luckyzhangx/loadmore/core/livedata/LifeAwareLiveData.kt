package com.luckyzhangx.loadmore.core.livedata

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer

open class LifeAwareLiveData<T> : MutableLiveData<T>() {

    override fun observeForever(observer: Observer<T>) {
        super.observeForever(observer)
        tryNotifyObserve(observer)
    }

    override fun observe(owner: LifecycleOwner, observer: Observer<T>) {
        super.observe(owner, observer)
        tryNotifyObserve(observer)
    }

    override fun removeObserver(observer: Observer<T>) {
        super.removeObserver(observer)
        if (observer is LiveDataLifeAwarer<*>) {
            (observer as LiveDataLifeAwarer<T>).onRemove(this)
        }
    }


}

private fun <T> LifeAwareLiveData<T>.tryNotifyObserve(observer: Observer<T>) {
    if (observer is LiveDataLifeAwarer<*>) {
        notifyObserve(observer as LiveDataLifeAwarer<T>)
    }
}

private fun <T> LifeAwareLiveData<T>.notifyObserve(awarer: LiveDataLifeAwarer<T>) {
    awarer.onObserve(this)
}

interface LiveDataLifeAwarer<T> {
    fun onObserve(liveData: LiveData<T>)
    fun onRemove(liveData: LiveData<T>)
}