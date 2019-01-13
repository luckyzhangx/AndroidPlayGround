package com.luckyzhangx.customviewlib.textview.span

import android.text.TextPaint
import android.text.style.MetricAffectingSpan

class BaseLineOffsetSpan(val offset: Int) : MetricAffectingSpan() {

    override fun updateMeasureState(textPaint: TextPaint) {
        textPaint.baselineShift += offset
    }

    override fun updateDrawState(tp: TextPaint) {
        tp.baselineShift += offset
    }
}