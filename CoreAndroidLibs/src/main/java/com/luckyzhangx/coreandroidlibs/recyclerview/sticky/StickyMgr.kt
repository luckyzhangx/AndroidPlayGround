package com.luckyzhangx.coreandroidlibs.recyclerview.sticky

import android.util.SparseBooleanArray
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.luckyzhangx.coreandroidlibs.R
import com.luckyzhangx.coreandroidlibs.recyclerview.tryFindFirstVisiblePosition
import com.luckyzhangx.coreandroidlibs.recyclerview.tryFindLastVisiblePosition

// Created by luckyzhangx on 2019-09-16.

//todo：View in holder：for properly exposure
//todo：observe data change
//todo：support no smooth scroll
class StickyMgr(
        val wrapper: StickViewOuter,
        val add: Boolean = false) {


    private val stickyTypes = SparseBooleanArray()
    fun registerType(type: Int) {
        stickyTypes.put(type, true)
    }

    private val stickPos = mutableSetOf<Int>()

    private fun calStickPos(adapter: RecyclerView.Adapter<*>) {
        for (pos in 0 until adapter.itemCount) {
            if (stickyTypes.get(adapter.getItemViewType(pos))) {
                stickPos.add(pos)
            }
        }
    }

    fun observeData(recyclerView: RecyclerView) {
        val adapter = recyclerView.adapter
        adapter?.apply adapter@{
            calStickPos(this@adapter)
            registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
                override fun onChanged() {
                    super.onChanged()
                    calStickPos(this@adapter)
                }

                override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                    super.onItemRangeRemoved(positionStart, itemCount)
                    calStickPos(this@adapter)
                }

                override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {
                    super.onItemRangeMoved(fromPosition, toPosition, itemCount)
                    calStickPos(this@adapter)
                }

                override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                    super.onItemRangeInserted(positionStart, itemCount)
                    calStickPos(this@adapter)
                }

                override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {
                    super.onItemRangeChanged(positionStart, itemCount)
                    calStickPos(this@adapter)
                }

                override fun onItemRangeChanged(positionStart: Int, itemCount: Int, payload: Any?) {
                    super.onItemRangeChanged(positionStart, itemCount, payload)
                    calStickPos(this@adapter)
                }
            })

        }
    }

    private fun shouldStick(adapterPos: Int): Boolean {
        return stickPos.contains(adapterPos)
    }

    private val viewMap = mutableMapOf<Int, View>()

    private fun ensureView(pos: Int) {
        if (viewMap[pos] == null) {
            val holder = recyclerView.adapter!!.createViewHolder(recyclerView, recyclerView.adapter!!.getItemViewType(pos))
            recyclerView.adapter!!.bindViewHolder(holder, pos)
            (holder.itemView as StickViewInner).getChildAt(0)?.apply {
                measure(
                        View.MeasureSpec.makeMeasureSpec(recyclerView.width, View.MeasureSpec.EXACTLY),
                        View.MeasureSpec.makeMeasureSpec(recyclerView.height, View.MeasureSpec.AT_MOST
                        ))
                saveView(pos, this)
            }

        }
    }


    private fun saveView(pos: Int, view: View) {
        viewMap[pos] = view
    }

    fun hasView(data: Any?, position: Int): Boolean {
        return viewMap.keys.contains(position)
    }

    fun getView(data: Any?, position: Int): View? {
        return viewMap[position]
    }

    private fun shouldInVH(vh: RecyclerView.ViewHolder): Boolean {
        return false
    }

    private lateinit var recyclerView: RecyclerView

    fun attach(recyclerView: RecyclerView) {
        this.recyclerView = recyclerView
        observeData(recyclerView)

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                recyclerView.forVisibleView { adapterPos, view ->
                    if (shouldStick(adapterPos)) {
                        if (viewMap.keys.contains(adapterPos)) {

                        } else {
                            (view?.itemView as? StickViewInner)?.getChildAt(0)?.apply {
                                saveView(adapterPos, this)
                            }
                        }
                    }
                }

                if (!add) {
                    //推
                    val first = recyclerView.layoutManager!!.tryFindFirstVisiblePosition()
                    val end = recyclerView.layoutManager!!.tryFindLastVisiblePosition()

                    val sortedKeys = stickPos.sorted()
                    val firstStick = sortedKeys.lastOrNull { it <= first } ?: -1
                    val lastStick = sortedKeys.lastOrNull() { it <= end } ?: -1

                    sortedKeys.forEach {
                        if (it in firstStick..lastStick) {
                            ensureView(it)
                            viewMap[it]?.visibility = View.VISIBLE
                        } else {
                            viewMap[it]?.visibility = View.GONE
                        }
                    }

                    var lastTop = Float.MAX_VALUE

                    for (it in lastStick downTo firstStick) {
                        if (!sortedKeys.contains(it)) continue
                        val v = viewMap[it]!!


                        val vh = recyclerView.findViewHolderForAdapterPosition(it)?.itemView as? StickViewInner
                        when {
                            it in (first + 1)..end -> {
                                //in VH
                                changeParent(v,
                                        recyclerView.findViewHolderForAdapterPosition(it)?.itemView as StickViewInner,
                                        true)
                                v.translationY = 0f
                            }

                            it == first -> {
                                //top >= 0: inRV top < 0: in wrapper
                                if (vh!!.top > 0) {
                                    changeParent(v, vh, true)
                                    v.translationY = 0f
                                } else {
                                    changeParent(v, vh, false)
                                    v.translationY = minOf(0f, lastTop - v.height)
                                }
                            }

                            it < first -> {
                                //in wrapper
                                changeParent(v, vh, false)
                                v.translationY = minOf(0f, lastTop - v.height)

                            }
                            else -> {

                            }
                        }
                        if (vh != null) {
                            lastTop = vh.top.toFloat()
                        }
                    }
                } else {
                    //不推
                    val first = recyclerView.layoutManager!!.tryFindFirstVisiblePosition()
                    val end = recyclerView.layoutManager!!.tryFindLastVisiblePosition()


                    val sortedKeys = viewMap.keys.sorted()
                    val firstStick = sortedKeys.firstOrNull() ?: -1
                    val lastStick = sortedKeys.lastOrNull() { it <= end } ?: -1

                    sortedKeys.forEach {
                        if (it in firstStick..lastStick) {
                            viewMap[it]?.visibility = View.VISIBLE
                        } else {
                            viewMap[it]?.visibility = View.GONE
                        }
                    }

                    var lastTran = Float.MIN_VALUE

                    for (it in firstStick..lastStick) {
                        if (!sortedKeys.contains(it)) continue

                        val view = viewMap[it]

                        if (view != null) {
                            view.translationY = maxOf(
                                    lastTran,
                                    (recyclerView.findViewHolderForAdapterPosition(it)?.itemView?.top
                                            ?: 0).toFloat()
                            )
                            lastTran = view.translationY + view.height
                        }
                    }

                }
            }
        })

        recyclerView.stickMgr = this
    }

    private fun changeParent(view: View, container: StickViewInner?, inRv: Boolean) {
        if (inRv) {
            if (view.parent == container)
                return
            else {
                (view.parent as? ViewGroup)?.removeView(view)
                container?.addView(view)
            }
        } else {
            if (view.parent == wrapper)
                return
            else {
                (view.parent as? ViewGroup)?.removeView(view)
                wrapper.addView(view)
                view.measure(
                        View.MeasureSpec.makeMeasureSpec(recyclerView.width, View.MeasureSpec.EXACTLY),
                        View.MeasureSpec.makeMeasureSpec(recyclerView.height, View.MeasureSpec.AT_MOST
                        ))
                view.layout(0, 0, view.measuredWidth, view.measuredHeight)
            }
        }
    }
}

var View.stickMgr: StickyMgr?
    get() {
        return getTag(R.id.stick) as? StickyMgr
    }
    set(value) {
        setTag(R.id.stick, value)
    }

fun RecyclerView.forVisibleView(action: (adapterPos: Int, view: RecyclerView.ViewHolder?) -> Unit) {
    val first = layoutManager!!.tryFindFirstVisiblePosition()
    val end = layoutManager!!.tryFindLastVisiblePosition()

    for (i in first..end) {
        val v = layoutManager!!.findViewByPosition(i)
        if (v != null) {
            val vh = getChildViewHolder(v)
            action.invoke(i, vh)
        }
    }

}