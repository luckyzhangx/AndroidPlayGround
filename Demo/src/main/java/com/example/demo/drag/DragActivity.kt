package com.example.demo.drag

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.demo.R
import com.luckyzhangx.coreandroidlibs.recyclerview.MoveItemAnimator
import com.luckyzhangx.coreandroidlibs.renderer.renderadapter.DiffViewRendererAdapter
import kotlinx.android.synthetic.main.activity_drag.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class DragActivity : AppCompatActivity() {


    private val adapter1 = DiffViewRendererAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        attachHelper()


        refreshList()

    }

    private fun attachHelper() {
        val helper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
                val isHolder = viewHolder is PoiVH
                return makeMovementFlags(
                        if (isHolder) ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
                        else 0,
                        0)
            }

            override fun canDropOver(recyclerView: RecyclerView, current: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                val drop = current is PoiVH && target is PoiVH
                Log.d("target", "canDrop:$drop")
                return drop
            }

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                val fromPosition = viewHolder.getAdapterPosition()
                val toPosition = target.getAdapterPosition()

                if (viewHolder !is PoiVH) return false

                Collections.swap(renderList, fromPosition, toPosition)

                adapter1.postList(renderList)

                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

            }

            override fun chooseDropTarget(selected: RecyclerView.ViewHolder, dropTargets: MutableList<RecyclerView.ViewHolder>, curX: Int, curY: Int): RecyclerView.ViewHolder? {
                val right = curX + selected.itemView.width
                val bottom = curY + selected.itemView.height
                var winner: RecyclerView.ViewHolder? = null
                var winnerScore = -1
                val dx = curX - selected.itemView.left
                val dy = curY - selected.itemView.top
                val targetsSize = dropTargets.size

                Log.d("target", "dy:$dy")
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
                                Log.d("target", "winner: dy < 0 : diff$diff, targetTop:${target.itemView.top} selTop:${selected.itemView.top}")
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
                                Log.d("target", "winner: dy > 0 : diff$diff, targetTop:${target.itemView.top} selTop:${selected.itemView.top}")
                            }
                        }
                    }
                }
                Log.d("target", "winner:" + winner.toString())
                return winner

            }

//            override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
//                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
//
//                if (actionState == ACTION_STATE_SWIPE) {//如果是侧滑行为
//                    val translateX = 400    //获得菜单的宽度也就是我们主视图需要便宜的量
//                    val contentView = viewHolder.itemView //获得侧滑时操作那个View去做偏移行为
//                    contentView.translationX = maxOf(dX, -400f)
//                } else//不是侧滑，可能是长按拖拽也可能是上下滑动，点击啊 什么动作的执行原来的行为。
//                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
//            }

        })
        helper.attachToRecyclerView(rv1)
    }

    val renderList = mutableListOf<Any>()

    private fun refreshList() {
        renderList.clear()
        repeat(100) { time ->
            renderList.add(PoiRenderer(time * 2))
//            renderList.add(PoiDistanceRenderer(time * 2 + 1))
        }
        renderList.removeAt(renderList.lastIndex)

        adapter1.postList(renderList)
    }

}
