package com.luckyzhangx.coreandroidlibs.renderer.renderadapter

import androidx.recyclerview.widget.RecyclerView
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

// Created by luckyzhangx on 2018/9/6.

/**
 * 缓存 Class 与 ViewHolder 类型的对应关系
 */

object ViewHolderTypesCache {

    private val holdersMap = mutableMapOf<Class<*>, Class<out RecyclerView.ViewHolder>>()
    private val hasHolderType = mutableMapOf<Class<*>, Boolean>()

    fun getHolderType(clazz: Class<*>): Class<out RecyclerView.ViewHolder>? {

        if (hasHolderType.containsKey(clazz)) {
            return getHolderTypeInternal(clazz)
        }

        parseHolderType(clazz)

        return getHolderTypeInternal(clazz)
    }

    private fun getHolderTypeInternal(clazz: Class<*>): Class<out RecyclerView.ViewHolder>? {
        if (hasHolderType[clazz] == true) {
            return holdersMap[clazz]!!
        }

        if (hasHolderType[clazz] == false) {
            return null
        }
        return null
    }

    private fun parseHolderType(clazz: Class<*>): Class<out RecyclerView.ViewHolder>? {
        var vhClazz: Class<out RecyclerView.ViewHolder>? = null

        var tempClazz: Class<*>? = clazz

        while (vhClazz == null && tempClazz != null) {
            vhClazz = tryParseInterfaceHolderType(tempClazz)

            tempClazz = tempClazz.superclass
        }

        hasHolderType[clazz] = (vhClazz != null)

        if (vhClazz != null) {
            holdersMap[clazz] = vhClazz
        }

        return vhClazz
    }

    private fun tryParseInterfaceHolderType(clazz: Class<*>): Class<out RecyclerView.ViewHolder>? {

        fun isViewRenderer(type: Type): Boolean {
            if (type is ParameterizedType)
                return (type.rawType == ViewRenderer::class.java
                        || type.rawType == ViewRenderer::class.java
                        || type.rawType == GenericViewRenderer::class.java)

            return false
        }

        clazz.genericInterfaces.forEach {
            if (isViewRenderer(it)) {
                if (it is ParameterizedType) {
                    val vhType = it.actualTypeArguments[0] as Class<*>
                    if (RecyclerView.ViewHolder::class.java.isAssignableFrom(vhType)) {
                        return vhType as Class<out RecyclerView.ViewHolder>
                    }
                }
            }
        }
        return null
    }
}