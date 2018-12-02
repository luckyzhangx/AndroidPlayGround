package com.luckyzhangx.loadmore.core

sealed class DataState {
    object DATA_NEW : DataState()
    object DATA_UPDATE : DataState()
    object DATA_EMPTY : DataState()

    object LOADING_NEW : DataState()
    object LOADING_UPDATE : DataState()

    class ERROR_NEW(val error: Throwable) : DataState()
    class ERROR_UPDATE(val error: Throwable) : DataState()
}