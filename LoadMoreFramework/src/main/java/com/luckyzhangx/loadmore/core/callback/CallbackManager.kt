package com.luckyzhangx.loadmore.core.callback

interface ICallbackManger<Callback> : Iterable<Callback> {
    fun addCallback(t: Callback)
    fun removeCallback(t: Callback)
}

class CallbackManager<Callback> : ICallbackManger<Callback> {
    val list = mutableListOf<Callback>()

    override fun addCallback(t: Callback) {
        list.add(t)
    }

    override fun removeCallback(t: Callback) {
        list.remove(t)
    }

    override fun iterator(): Iterator<Callback> {
        return list.iterator()
    }
}