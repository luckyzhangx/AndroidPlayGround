package com.luckyzhangx.coreandroidlibs.recyclerview.drag

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView


// Created by luckyzhangx on 2019-08-02.
internal class TypeDragCallback : TopDragCallback() {

    private var startPos = -1
    private var endPos = -1

    interface MoveListener {
        fun onMove(from: Int, to: Int): Boolean
        fun onMoveFinish(from: Int, to: Int)
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
    }

    internal var moveListener: MoveListener? = null

    private val dragHolderTypes = mutableListOf<Class<out RecyclerView.ViewHolder>>()

    internal fun registerHolder(clazz: Class<out RecyclerView.ViewHolder>) {
        dragHolderTypes.add(clazz)
    }

    protected fun isDragableHolder(holder: RecyclerView.ViewHolder): Boolean {
        dragHolderTypes.forEach {
            if (it.isAssignableFrom(holder.javaClass)) {
                return true
            }
        }
        return false
    }


    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        val fromPosition = viewHolder.adapterPosition
        val toPosition = target.adapterPosition
        endPos = toPosition

        if (!(isDragableHolder(target) && isDragableHolder(target))) return false

        return moveListener?.onMove(fromPosition, toPosition) ?: false
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        super.onSelectedChanged(viewHolder, actionState)

        if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
            startPos = viewHolder?.adapterPosition ?: -1
            endPos = -1
        }

        if (actionState == ItemTouchHelper.ACTION_STATE_IDLE) {
            if (startPos >= 0 && endPos >= 0 && startPos != endPos)
                moveListener?.onMoveFinish(startPos, endPos)

        }

    }


    override fun canDropOver(recyclerView: RecyclerView, current: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        return isDragableHolder(current) && isDragableHolder(target)
    }

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        return makeMovementFlags(if (isDragableHolder(viewHolder))
            ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        else 0, 0)

    }

    //    override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, target: RecyclerView.ViewHolder?): Boolean {
////        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }

//    override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
}