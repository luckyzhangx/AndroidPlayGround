package com.luckyzhangx.customviewlib.horizonpullbounce

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.core.view.NestedScrollingParent2
import com.luckyzhangx.customviewlib.R
import kotlinx.android.synthetic.main.loadmore.view.*
import org.jetbrains.anko.dip

// Created by luckyzhangx on 2019-04-24.

private val Tag = "Horizontal"

class TRHorizontalLoadMoreLayout
@JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : HorizontalLoadMoreLayout(context, attrs, defStyleAttr), NestedScrollingParent2 {

    override fun onFinishInflate() {
        super.onFinishInflate()
        loadView = LayoutInflater.from(context).inflate(R.layout.loadmore, this, false)
        addView(loadView)

        listener = object : ScrollListener() {
            private var lastValue = 0
            private val max = dip(60)

            override fun scrolled(scrollX: Int) {
                if (max in scrollX..lastValue) {
                    loadContent.text = "滑动查看更多"
                    loadIcon.animate().rotation(0f).start()
                } else if (max in lastValue..scrollX) {
                    loadContent.text = "释放查看更多"
                    loadIcon.animate().rotation(180f).start()
                }
                lastValue = scrollX
            }
        }
    }
}