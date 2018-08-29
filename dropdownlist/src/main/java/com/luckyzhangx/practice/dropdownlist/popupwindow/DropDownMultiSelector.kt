package com.luckyzhangx.practice.dropdownlist.popupwindow

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.PopupWindow
import com.luckyzhangx.practice.dropdownlist.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.dropdown_multiselector.*
import kotlinx.android.synthetic.main.item_multiselector.*
import kotlinx.android.synthetic.main.item_sel.*
import org.jetbrains.anko.textColor

// Created by luckyzhangx on 2018/8/28.

typealias OnCollectFilterData = (delegateMgr: SelWrapperDelegateMgr) -> Unit

class DropDownMultiSelector(context: Context) : PopupWindow(), LayoutContainer {


    override val containerView: View?
        get() = contentView

    init {
        animationStyle = 0
        isFocusable = true
        isTouchable = true
        isOutsideTouchable = false

        width = WindowManager.LayoutParams.MATCH_PARENT
        height = WindowManager.LayoutParams.MATCH_PARENT

        contentView = LayoutInflater.from(context).inflate(R.layout.dropdown_multiselector, null)
        contentView.setOnClickListener { dismiss() }
    }

    fun attachDelegate(delegateMgr: SelWrapperDelegateMgr, callback: OnCollectFilterData) {
        filtersWrapperRecycler.apply {
            adapter = SelWrapperAdapter(delegateMgr)
            layoutManager = LinearLayoutManager(context)
        }

        getFilterData.setOnClickListener {
            dismiss()
            callback.invoke(delegateMgr)
        }
    }

    override fun showAsDropDown(anchor: View) {
        val locations = IntArray(2)
        anchor.getLocationOnScreen(locations)
        locations[1] += anchor.height

        val point = Point()
        (anchor.context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay.getSize(point)

        if (anchor.context is Activity) {
            val height = (anchor.context as Activity).findViewById<View>(android.R.id.content).height
            point.y = Math.max(point.y, height)
        }

        height = point.y - locations[1]

        filtersWrapperRecycler?.apply {

            viewTreeObserver.addOnPreDrawListener(ViewTreeObserver.OnPreDrawListener {
                if (height > 1920 / 5 * 3) {
                    layoutParams.height = 1920 / 5 * 3
                    requestLayout()
                    return@OnPreDrawListener false
                }
                true
            })
        }

        contentView.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                anim()
                contentView.viewTreeObserver.removeOnPreDrawListener(this)
                return false
            }
        })

        super.showAsDropDown(anchor)

    }

    private fun anim() {
        //背景渐变动画
        val alpha = AlphaAnimation(0f, 1f)
        //向下平移动画
        val translate = TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, -1f, Animation.RELATIVE_TO_SELF, 0f
        )
        val duration = 300
        alpha.duration = duration.toLong()
        translate.duration = duration.toLong()
        contentView.startAnimation(alpha)
        (contentView as ViewGroup).getChildAt(0).startAnimation(translate)
    }

}

private class SelAdapter<VH : RecyclerView.ViewHolder>(val delegate: SelWrapperDelegate<*, VH>) : RecyclerView.Adapter<VH>() {

    private var wrap = false

    fun wrapAdapter() {
        wrap = !wrap
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return delegate.createViewHolder(parent, viewType).apply {
            itemView.setOnClickListener {
                delegate.updateSel(adapterPosition)
                onBindViewHolder(this, adapterPosition)
            }
        }
    }

    override fun getItemCount(): Int {
        if (wrap) return Math.min(3, delegate.getCount())
        return delegate.getCount()
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        if (delegate.isSel(position)) {
            delegate.renderSel(holder)
        } else {
            delegate.renderUnSel(holder)
        }
    }
}

class SelViewHolder(context: Context, container: ViewGroup)
    : RecyclerView.ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_sel, container, false)), LayoutContainer {
    override val containerView: View?
        get() = itemView

    fun sel() {
        sel.textColor = Color.RED
    }

    fun unsel() {
        sel.textColor = Color.GRAY
    }
}

class SelWrapperAdapter(val mgr: SelWrapperDelegateMgr) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var snapshotPos = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return mgr.getDelegate(snapshotPos).createWrapperViewHolder(parent, viewType)
    }

    override fun getItemCount(): Int {
        return mgr.getCount()
    }

    override fun getItemViewType(position: Int): Int {
        snapshotPos = position
        return super.getItemViewType(position)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val delegate = mgr.getDelegate(position)
        //so ugly but I have no other way
        if (delegate is BaseSelWrapperDelegate && holder is SelWrapperViewHolder)
            delegate.bindWrapperViewHolder(holder, position)
    }
}

class SelWrapperViewHolder(context: Context, container: ViewGroup)
    : RecyclerView.ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_multiselector, container, false)), LayoutContainer {
    override val containerView: View?
        get() = itemView

    fun bind(delegate: SelWrapperDelegate<*, *>) {
        filtersTitle.text = delegate.getTitle()

        filterRecycler.apply {
            layoutManager = GridLayoutManager(context, 3).apply {
                isAutoMeasureEnabled = true
            }
            adapter = SelAdapter(delegate)
            filtersTitle.setOnClickListener { (adapter as SelAdapter).wrapAdapter() }
        }
    }
}

interface SelWrapperDelegateMgr {
    fun getCount(): Int
    fun getDelegate(pos: Int): SelWrapperDelegate<*, *>
}

interface VHRenderer<VH : RecyclerView.ViewHolder> {
    fun renderSel(holder: VH)
    fun renderUnSel(holder: VH)
}

interface SelWrapperDelegate<PVH : RecyclerView.ViewHolder, VH : RecyclerView.ViewHolder> : VHRenderer<VH> {

    fun getTitle(): String

    fun createWrapperViewHolder(parent: ViewGroup, viewType: Int): PVH

    fun bindWrapperViewHolder(holder: PVH, position: Int)

    fun createViewHolder(parent: ViewGroup, viewType: Int): VH

    fun bindHolderData(holder: VH, position: Int)

    fun isSel(pos: Int): Boolean

    fun updateSel(pos: Int)

    fun getSels(): Set<Int>

    fun getCount(): Int

}