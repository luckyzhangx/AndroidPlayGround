package com.luckyzhangx.spaceitemdecoration

import android.graphics.Rect
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import com.luckyzhangx.spaceitemdecoration.vh.*
import kotlinx.android.synthetic.main.activity_space_item_decoration.*

class SpaceItemDecorationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_space_item_decoration)

        rv.apply {
            adapter = Adapter().apply {
                list.apply {
                    add(Header())
                    add(Header())
                    add(Anchor())
                    add(Content())
                    add(Content())
                    add(Content())
                    add(Content())
                    add(AnchorEnd())
                }

                btn.setOnClickListener {
                    notifyDataSetChanged()
                }
            }


            addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
                    if (parent?.getChildViewHolder(view)!!.adapterPosition ==
                            parent!!.adapter!!.itemCount - 1) {

                        val first = (parent!!.layoutManager!! as LinearLayoutManager)
                                .findFirstVisibleItemPosition()
                        var top = -1
                        var bottom = Int.MAX_VALUE
                        for (i in first until parent!!.adapter!!.itemCount) {
                            if (parent!!.adapter.getItemViewType(i) == Anchor::class.java.hashCode()) {
                                top = parent!!.layoutManager.findViewByPosition(i).top
                            }
                            if (i == parent!!.adapter!!.itemCount - 2) {
                                bottom = parent!!.layoutManager.findViewByPosition(i).bottom
                            }
                        }
                        if (top >= 0) {
                            log("top:$top")
                            log("bottom:$bottom")

                            outRect?.bottom = parent?.height - (bottom - top)-1
                            log("offset:${outRect!!.bottom}")
                        }
                    }
                }
            })
        }
    }
}

private fun log(msg: String) {
    Log.d("pin", msg)
}
