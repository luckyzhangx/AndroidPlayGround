package com.luckyzhangx.practice.dropdownlist.popupwindow

import android.app.Activity
import android.content.Context
import android.graphics.Point
import android.graphics.drawable.BitmapDrawable
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.PopupWindow
import com.luckyzhangx.practice.dropdownlist.R

// Created by luckyzhangx on 2018/8/27.

class DropDownPopupWindow(context: Context) : PopupWindow() {
    init {
        initView(context)
    }

    private var bottomSheetListViewAdapter: RecyclerView.Adapter<*>? = null
    protected var recyclerView: RecyclerView? = null

    private fun initView(context: Context) {

        setBackgroundDrawable(BitmapDrawable())

        width = WindowManager.LayoutParams.MATCH_PARENT
        height = WindowManager.LayoutParams.MATCH_PARENT

        contentView = LayoutInflater.from(context).inflate(R.layout.dropdown_popupwindow, null)
        bottomSheetListViewAdapter = MyAdapter(TestSingleSelAdapterDelegate())

        recyclerView = contentView.findViewById(R.id.recycler)
        recyclerView?.apply {
            layoutManager = LinearLayoutManager(context)

            adapter = bottomSheetListViewAdapter
            viewTreeObserver.addOnPreDrawListener(ViewTreeObserver.OnPreDrawListener {
                if (height > 1920 / 5 * 3) {
                    layoutParams.height = 1920 / 5 * 3
                    requestLayout()
                    return@OnPreDrawListener false
                }
                true
            })
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

        super.showAsDropDown(anchor)

        contentView.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                contentView.viewTreeObserver.removeOnPreDrawListener(this)
                return false
            }
        })

    }
}

class MyAdapter<VH : RecyclerView.ViewHolder>(val delegate: AdapterDelegate<VH>) : RecyclerView.Adapter<VH>() {
    init {
        if (delegate is TestSingleSelAdapterDelegate) {
            delegate.attach(this)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return delegate.createViewHolder(parent, viewType).apply {
            itemView.setOnClickListener {
                delegate.onItemClick(this, adapterPosition)
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount(): Int {
        return delegate.getItemCount()
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        return delegate.bindViewHolder(holder, position)
    }
}

interface AdapterDelegate<VH : RecyclerView.ViewHolder> {
    fun createViewHolder(parent: ViewGroup, viewType: Int): VH
    fun getItemCount(): Int
    fun bindViewHolder(holder: VH, position: Int)
    fun onItemClick(vh: VH, position: Int)
    fun renderSel(holder: VH)
    fun renderUnSel(holder: VH)
    fun checkSel(position: Int): Boolean
}