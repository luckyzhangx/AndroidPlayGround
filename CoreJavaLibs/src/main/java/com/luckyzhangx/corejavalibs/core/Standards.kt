package com.luckyzhangx.corejavalibs.core

// Created by luckyzhangx on 2019-04-24.
/**
 * 没啥用，只是看 apply 函数看着不爽，用 configure 替换
 */
inline fun <T : Any> configure(t: T?, nullBlock: PoiBlock = {}, block: T.() -> Unit) {
    use(t, nullBlock, block)
}

inline fun <T : Any> use(t: T?, nullBlock: PoiBlock = {}, block: T.() -> Unit) {
    if (t == null) {
        nullBlock()
    } else {
        block(t)
    }
}

typealias PoiBlock = () -> Unit