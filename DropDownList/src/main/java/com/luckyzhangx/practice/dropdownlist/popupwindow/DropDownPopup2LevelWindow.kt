package com.luckyzhangx.practice.dropdownlist.popupwindow

import android.app.Activity
import android.content.Context
import android.graphics.Point
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.PopupWindow
import com.luckyzhangx.practice.dropdownlist.R

// Created by luckyzhangx on 2018/8/27.

class DropDownPopup2LevelWindow(context: Context) : PopupWindow() {
    init {
        initView(context)
    }

    protected var mainRecycler: RecyclerView? = null
    protected var subRecycler: RecyclerView? = null

    fun attachDelegate(delegate: AdapterDelegate<*>) {

        mainRecycler?.apply {
            adapter = MyAdapter(delegate)
        }

        if (delegate is MainTestAdapterDelegate) {
            subRecycler?.adapter = MyAdapter(delegate.getSubDelegate())
        } else {
            subRecycler?.visibility = View.GONE
        }
    }

    private fun initView(context: Context) {

        width = WindowManager.LayoutParams.MATCH_PARENT
        height = WindowManager.LayoutParams.MATCH_PARENT

        contentView = LayoutInflater.from(context).inflate(R.layout.dropdown_popupwindow2, null)

        val delegate: MainTestAdapterDelegate = MainTestAdapterDelegate()

        mainRecycler = contentView.findViewById(R.id.recycler1)

        mainRecycler?.apply {
            layoutManager = LinearLayoutManager(context)

            viewTreeObserver.addOnPreDrawListener(ViewTreeObserver.OnPreDrawListener {
                if (height > 1920 / 5 * 3) {
                    layoutParams.height = 1920 / 5 * 3
                    requestLayout()
                    return@OnPreDrawListener false
                }
                true
            })
        }

        subRecycler = contentView.findViewById(R.id.recycler2)

        subRecycler?.apply {
            layoutManager = LinearLayoutManager(context)

            adapter = MyAdapter(delegate.getSubDelegate())
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

interface MainAdapterDelegate<VH : RecyclerView.ViewHolder> : AdapterDelegate<VH> {
    fun getSubDelegate(): AdapterDelegate<*>
}