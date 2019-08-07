package com.luckyzhangx.coreandroidlibs.recyclerview

import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView

// Created by luckyzhangx on 2019-08-07.
class MoveItemAnimator : DefaultItemAnimator() {

    override fun animateAdd(holder: RecyclerView.ViewHolder?): Boolean {
        dispatchChangeFinished(holder, false);
        return false
    }

    override fun animateChange(oldHolder: RecyclerView.ViewHolder?, newHolder: RecyclerView.ViewHolder?, fromX: Int, fromY: Int, toX: Int, toY: Int): Boolean {
        dispatchChangeFinished(oldHolder, true);
        dispatchChangeFinished(newHolder, false);
        return false
    }

    override fun animateAppearance(viewHolder: RecyclerView.ViewHolder, preLayoutInfo: ItemHolderInfo?, postLayoutInfo: ItemHolderInfo): Boolean {
        dispatchChangeFinished(viewHolder, false);
        return false
    }

    override fun animateDisappearance(viewHolder: RecyclerView.ViewHolder, preLayoutInfo: ItemHolderInfo, postLayoutInfo: ItemHolderInfo?): Boolean {
        dispatchChangeFinished(viewHolder, true);
        return false
    }

//    override fun animateMove(holder: RecyclerView.ViewHolder?, fromX: Int, fromY: Int, toX: Int, toY: Int): Boolean {
//        dispatchChangeFinished(holder, true);
//        dispatchChangeFinished(holder, false);
//        return false
//    }

//    override fun animatePersistence(viewHolder: RecyclerView.ViewHolder, preInfo: ItemHolderInfo, postInfo: ItemHolderInfo): Boolean {
//        dispatchChangeFinished(viewHolder, true);
//        dispatchChangeFinished(viewHolder, false);
//        return false
//    }
}