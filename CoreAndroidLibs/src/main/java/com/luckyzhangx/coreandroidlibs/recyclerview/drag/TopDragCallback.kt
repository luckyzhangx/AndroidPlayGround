package com.luckyzhangx.coreandroidlibs.recyclerview.drag

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

// Created by luckyzhangx on 2019-08-02.
abstract class TopDragCallback : ItemTouchHelper.Callback() {

    override fun chooseDropTarget(selected: RecyclerView.ViewHolder, dropTargets: MutableList<RecyclerView.ViewHolder>, curX: Int, curY: Int): RecyclerView.ViewHolder? {
        val right = curX + selected.itemView.width
        val bottom = curY + selected.itemView.height
        var winner: RecyclerView.ViewHolder? = null
        var winnerScore = -1
        val dx = curX - selected.itemView.left
        val dy = curY - selected.itemView.top
        val targetsSize = dropTargets.size

        for (i in 0 until targetsSize) {
            val target = dropTargets[i]
            if (dx > 0) {
                val diff = target.itemView.right - right
                if (diff < 0 && target.itemView.right > selected.itemView.right) {
                    val score = Math.abs(diff)
                    if (score > winnerScore) {
                        winnerScore = score
                        winner = target
                    }
                }
            }
            if (dx < 0) {
                val diff = target.itemView.left - curX
                if (diff > 0 && target.itemView.left < selected.itemView.left) {
                    val score = Math.abs(diff)
                    if (score > winnerScore) {
                        winnerScore = score
                        winner = target
                    }
                }
            }
            if (dy < 0) {
                val diff = target.itemView.top - curY
                if (diff > 0 && target.itemView.top < selected.itemView.top) {
                    val score = Math.abs(diff)
                    if (score > winnerScore) {
                        winnerScore = score
                        winner = target
                    }
                }
            }

            if (dy > 0) {
                val diff = target.itemView.top - curY
                if (diff < 0 && target.itemView.top > selected.itemView.top) {
                    val score = Math.abs(diff)
                    if (score > winnerScore) {
                        winnerScore = score
                        winner = target
                    }
                }
            }
        }
        return winner

    }

}