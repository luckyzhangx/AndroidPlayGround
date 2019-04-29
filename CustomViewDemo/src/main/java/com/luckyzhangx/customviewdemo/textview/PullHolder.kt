package com.luckyzhangx.customviewdemo.textview

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.luckyzhangx.corejavalibs.core.configure
import com.luckyzhangx.corejavalibs.core.use
import com.luckyzhangx.customviewdemo.R
import com.luckyzhangx.customviewlib.horizonpullbounce.attachToRecyclerView
import kotlinx.android.synthetic.main.holder_hor_pull.view.*
import org.jetbrains.anko.dip

// Created by luckyzhangx on 2019-04-24.
class PullHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.holder_hor_pull, parent, false)
) {


    init {
        use(itemView) {
            configure(list) {
                layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context, androidx.recyclerview.widget.LinearLayoutManager.HORIZONTAL, false)
                adapter = com.luckyzhangx.customviewdemo.recyclerview.Adapter(4)
//                com.luckyzhangx.customviewdemo.recyclerview.NoPaddingPagerSnapHelper().attachToRecyclerView(this)

                addItemDecoration(object : RecyclerView.ItemDecoration() {
                    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                        super.getItemOffsets(outRect, view, parent, state)
                        configure(outRect) {
                            left = dip(20)
                        }
                    }
                })
                itemView.loadMoreLayout.attachToRecyclerView(this, dip(20))
            }
//
        }
    }
}