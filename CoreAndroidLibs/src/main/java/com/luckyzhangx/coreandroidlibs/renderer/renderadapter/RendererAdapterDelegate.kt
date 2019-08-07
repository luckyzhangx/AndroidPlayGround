package com.luckyzhangx.coreandroidlibs.renderer.renderadapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.luckyzhangx.coreandroidlibs.renderer.renderadapter.RendererAdapterDelegate.Companion.INVALID_TYPE

/**
 * 一个 [RecyclerView.Adapter] 对应一个 [RendererAdapterDelegate]。
 * [RecyclerView.Adapter]需要提供一个[ItemCallback] 给 [RendererAdapterDelegate]
 *
 * Adapter 要支持 ViewRenderer，可以先将 getType 和 createView 交给 [RendererAdapterDelegate].
 * 如果 [getViewHolderType] 返回[INVALID_TYPE],则 Adapter 自己返回合适的 type。
 * 如果 [createViewHolder] 返回 null，则 Adapter 自己创建合适的 ViewHolder。
 */
class RendererAdapterDelegate<VH : RecyclerView.ViewHolder>(private val itemCallback: ItemCallback) {

    companion object {
        const val INVALID_TYPE = -1
    }

    private var lastLookUpItem: Any? = null

    fun getViewHolderType(pos: Int): Int {

        fun isViewHolderProvider(item: Any): Boolean {
            return item is ViewRenderer<*>
                    || ViewHolderTypesCache.getHolderType(item::class.java) != null
        }

        lastLookUpItem = itemCallback.getItem(pos)

        return lastLookUpItem
                ?.takeIf { isViewHolderProvider(it) }
                ?.let { InternalViewRenderUtil.getViewRenderType(lastLookUpItem!!) }
                ?: INVALID_TYPE
    }

    fun createViewHolder(container: ViewGroup, type: Int): VH? {
        if (type == -1) {
            throw IllegalStateException("type $type is invalid type")
        }

        if (InternalViewRenderUtil.getViewRenderType(lastLookUpItem!!) == type) {
            return InternalViewRenderUtil.createViewHolder(lastLookUpItem!!, container) as VH
        }

        return null
    }
}

typealias ItemCallback = GetItemByPosition
