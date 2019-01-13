package com.luckyzhangx.customviewlib.textview.span

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.text.style.ReplacementSpan

class CustomReplacementSpan : ReplacementSpan() {
    override fun getSize(paint: Paint, text: CharSequence?, start: Int, end: Int, fm: Paint.FontMetricsInt?): Int {
        fm?.apply {
            //            fm.top += fm.top
//            fm.ascent += fm.ascent
            fm.descent += 4 * fm.descent
            fm.bottom += 4 * fm.bottom
        }
        return 80
    }

    override fun draw(canvas: Canvas, text: CharSequence?, start: Int, end: Int, x: Float, top: Int, y: Int, bottom: Int, paint: Paint) {
        paint.color = Color.RED
        canvas.drawRect(x, y.toFloat(), x + 20, bottom.toFloat(), paint)
        canvas.drawText((text?.subSequence(start, end) ?: "").toString(), x, y.toFloat(), paint)
    }
}