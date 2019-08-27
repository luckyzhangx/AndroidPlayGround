package com.example.demo.drag

import android.view.ViewGroup
import com.example.demo.R
import com.luckyzhangx.coreandroidlibs.renderer.renderadapter.BasicVH
import com.luckyzhangx.coreandroidlibs.renderer.renderadapter.GenericViewRenderer
import kotlinx.android.synthetic.main.layout_poi_distance.view.*

// Created by luckyzhangx on 2019-07-31.
class PoiDistanceRenderer(val index: Int = -1) : GenericViewRenderer<PoiDistanceVH>

class PoiDistanceVH(parent: ViewGroup) : BasicVH<PoiDistanceRenderer>(parent, R.layout.layout_poi_distance) {
    override fun onBind(data: PoiDistanceRenderer, pos: Int) {
        itemView.tv.text = "distance: from: ${pos - 1} to ${pos + 1}"

    }
}