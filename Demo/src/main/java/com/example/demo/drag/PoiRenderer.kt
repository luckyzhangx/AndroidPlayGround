package com.example.demo.drag

import android.view.ViewGroup
import android.widget.Toast
import com.daimajia.swipe.SwipeLayout
import com.example.demo.R
import com.luckyzhangx.coreandroidlibs.renderer.renderadapter.BasicVH
import com.luckyzhangx.coreandroidlibs.renderer.renderadapter.GenericViewRenderer
import kotlinx.android.synthetic.main.layout_poi.view.*
import kotlinx.android.synthetic.main.layout_poi_distance.view.tv

// Created by luckyzhangx on 2019-07-31.
class PoiRenderer(val index: Int = -1) : GenericViewRenderer<PoiVH>

class PoiVH(parent: ViewGroup) : BasicVH<PoiRenderer>(parent, R.layout.layout_poi) {

    init {
        itemView.swipe.apply {
            showMode = SwipeLayout.ShowMode.LayDown
//            addDrag(SwipeLayout.DragEdge.Right, itemView.bottomView)
        }
    }

    override fun onBind(data: PoiRenderer, pos: Int) {
        itemView.tv.text = pos.toString()
        itemView.bottomView.setOnClickListener {
            Toast.makeText(it.context, "hhh", Toast.LENGTH_SHORT).show()
        }
    }
}