package com.luckyzhangx.customviewlib.textview.span

import android.os.Parcel
import android.text.TextPaint
import android.text.style.RelativeSizeSpan

class TopAlignRelativeSizeSpan : RelativeSizeSpan {
    constructor(proportion: Float) : super(proportion)
    constructor(src: Parcel) : super(src)

    override fun updateDrawState(ds: TextPaint) {
        super.updateDrawState(ds)
        ds.baselineShift += (ds.ascent() / 2).toInt()
    }

    override fun updateMeasureState(ds: TextPaint) {
        super.updateMeasureState(ds)
        ds.baselineShift += (ds.ascent() / 2).toInt()
    }
}