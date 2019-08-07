package com.luckyzhangx.coreandroidlibs.renderer.renderadapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import java.lang.reflect.Constructor

// Created by luckyzhangx on 2018/9/1.


// internal use only
object InternalViewRendererImpl {

    @JvmStatic
    fun getViewHolderType(renderer: Any): Int {
        return renderer::class.java.hashCode()
    }

    fun getViewHolderType(clazz: Class<*>): Int {
        return clazz.hashCode()
    }

    @JvmStatic
    fun <T : RecyclerView.ViewHolder> createViewHolder(renderer: Any, context: Context, container: ViewGroup?)
            : T {
        return ViewRendererFactory.buildViewHolderFromRenderer(renderer::class.java, context, container)
    }
}

private object ViewRendererFactory {

    /**
     * @param rendererClass must direct implement [GenericViewRenderer]
     */

    fun <T : RecyclerView.ViewHolder> buildViewHolderFromRenderer(rendererClass: Class<*>, context: Context, container: ViewGroup?): T =
            ViewHolderFactory.tryBuildViewHolder(getHolderClass(rendererClass), context, container)

    private fun getHolderClass(genericViewRendererClass: Class<*>): Class<out RecyclerView.ViewHolder> {
        return ViewHolderTypesCache.getHolderType(genericViewRendererClass)
                ?: throw IllegalStateException("""
                        $genericViewRendererClass 没有对应类型的 ViewHolder
                    """.trimIndent())

    }
}

/**
 * 反射获取 ViewHolder 的构造函数构造 ViewHolder
 */

object ViewHolderFactory {
    private val cachedContextViewGroupConstructors = mutableMapOf<Class<*>, Constructor<*>>()

    private val cachedContextConstructors = mutableMapOf<Class<*>, Constructor<*>>()

    fun <T : RecyclerView.ViewHolder> tryBuildViewHolder(viewHolderClass: Class<*>, context: Context, container: ViewGroup?): T {
        return buildViewHolder(viewHolderClass, container) as T?
                ?: buildViewHolder(viewHolderClass, context) as T?
                ?: throw IllegalStateException("""
                        $viewHolderClass neither has constructor(Context,ViewGroup)
                        nor constructor(Context)
                """.trimIndent())
    }

    private fun <T : RecyclerView.ViewHolder> buildViewHolder(viewHolderClass: Class<*>, container: ViewGroup?): T? =
            getViewGroupConstructor(viewHolderClass)?.newInstance(container) as T?

    private fun <T : RecyclerView.ViewHolder> buildViewHolder(viewHolderClass: Class<*>, context: Context): T? =
            getContextConstructor(viewHolderClass)?.newInstance(context) as T?

    private fun getViewGroupConstructor(viewHolderClass: Class<*>): Constructor<*>? {
        try {
            return cachedContextViewGroupConstructors[viewHolderClass]
                    ?: viewHolderClass.getConstructor(ViewGroup::class.java)
                            ?.apply { cachedContextViewGroupConstructors[viewHolderClass] = this }
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        }
        return null
    }

    private fun getContextConstructor(viewHolderClass: Class<*>): Constructor<*>? {
        try {
            return cachedContextConstructors[viewHolderClass]
                    ?: viewHolderClass.getConstructor(Context::class.java)
                            ?.apply { cachedContextConstructors[viewHolderClass] = this }
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        }
        return null
    }
}