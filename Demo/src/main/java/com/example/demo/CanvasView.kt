package com.example.demo

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Outline
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import android.graphics.RectF
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.FrameLayout
import androidx.annotation.RequiresApi

class CanvasView(context: Context, attrs: AttributeSet? = null)
  : FrameLayout(context, attrs, 0) {
  private val mRadius = 40f
  private val mProcessor: RoundProcessor = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
    OutlineRoundProcessor() else ClearOutRoundProcessor()

  override fun draw(canvas: Canvas) {
    mProcessor.beforeDraw(canvas)
    super.draw(canvas)
    mProcessor.afterDraw(canvas)
  }

  override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
    super.onSizeChanged(w, h, oldw, oldh)
    mProcessor.sizeChanged(w, h)
  }

  private interface RoundProcessor {
    fun setCornerRadius(radius: Float)
    fun beforeDraw(canvas: Canvas)
    fun afterDraw(canvas: Canvas)
    fun sizeChanged(w: Int, h: Int)
  }

  /**
   * View#setLayerType + DST_OUT
   * 不行，被圆角清除的区域会显示成黑色
   */
  private inner class HWRoundProcessor : RoundProcessor {
    private val mClipPath = Path()
    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mRectF = RectF()
    override fun setCornerRadius(radius: Float) {
      postInvalidate()
    }

    override fun beforeDraw(canvas: Canvas) {
      setLayerType(layerType, mPaint);
    }

    override fun afterDraw(canvas: Canvas) {
      mPaint.xfermode = (PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
      canvas.drawPath(mClipPath, mPaint);
      mPaint.xfermode = null;
    }

    override fun sizeChanged(w: Int, h: Int) {
      mRectF[0f, 0f, w.toFloat()] = h.toFloat()
      mClipPath.reset()
      val rect = RectF(paddingLeft.toFloat(), paddingTop.toFloat(),
          (w - paddingRight).toFloat(), (h - paddingBottom).toFloat())
      mClipPath.addRoundRect(rect, mRadius, mRadius, Path.Direction.CCW)
      mClipPath.addRect(0f, 0f, w.toFloat(), h.toFloat(), Path.Direction.CW)
    }

  }

  /**
   * DST_IN
   * View有padding时，padding区域的背景无法清除
   */
  private inner class DrawRoundProcessor : RoundProcessor {

    private val mPath = Path()
    private val mDestInPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
      xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_IN)
    }
    private val mRectF = RectF()
    override fun setCornerRadius(radius: Float) {
      postInvalidate()
    }

    override fun beforeDraw(canvas: Canvas) {
      canvas.saveLayer(mRectF, null, Canvas.ALL_SAVE_FLAG)
    }

    override fun afterDraw(canvas: Canvas) {
      canvas.drawPath(mPath, mDestInPaint)
      canvas.restore()
    }

    override fun sizeChanged(w: Int, h: Int) {
      mRectF[0f, 0f, w.toFloat()] = h.toFloat()
      mPath.reset()
      val rect = RectF(paddingLeft.toFloat(), paddingTop.toFloat(),
          (w - paddingRight).toFloat(), (h - paddingBottom).toFloat())
      mPath.addRoundRect(rect, mRadius, mRadius, Path.Direction.CW)
    }
  }

  /**DST_OUT
   * 效果ok，但saveLayer影响效率
   */
  private inner class ClearOutRoundProcessor : RoundProcessor {

    private val mClipPath = Path()
    private val mDestInPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
      xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OUT)
    }
    private val mRectF = RectF()
    override fun setCornerRadius(radius: Float) {
      postInvalidate()
    }

    override fun beforeDraw(canvas: Canvas) {
      canvas.saveLayer(mRectF, null, Canvas.ALL_SAVE_FLAG)
    }

    override fun afterDraw(canvas: Canvas) {
      canvas.drawPath(mClipPath, mDestInPaint)
      canvas.restore()
    }

    override fun sizeChanged(w: Int, h: Int) {
      mRectF[0f, 0f, w.toFloat()] = h.toFloat()
      mClipPath.reset()
      val rect = RectF(paddingLeft.toFloat(), paddingTop.toFloat(),
          (w - paddingRight).toFloat(), (h - paddingBottom).toFloat())
      mClipPath.addRoundRect(rect, mRadius, mRadius, Path.Direction.CCW)
      mClipPath.addRect(0f, 0f, w.toFloat(), h.toFloat(), Path.Direction.CW)
    }
  }

  /**
   * 在圆角外区域填充颜色
   * 不行，太依赖于外部颜色，后面不方便维护，背景也无法随便更改
   */
  private inner class HomeRoundProcessor : RoundProcessor {

    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
      color = Color.GRAY
    }
    private val mClearXfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    private val mRectF = RectF()

    override fun setCornerRadius(radius: Float) {
      postInvalidate()
    }

    override fun beforeDraw(canvas: Canvas) {
    }

    override fun afterDraw(canvas: Canvas) {
      canvas?.apply {
        mRectF.top = paddingTop.toFloat()
        mRectF.left = paddingLeft.toFloat()
        mRectF.right = width.toFloat() - paddingRight
        mRectF.bottom = height.toFloat() - paddingBottom
        saveLayer(
            mRectF,
            null,
            Canvas.ALL_SAVE_FLAG
        )
        mPaint.xfermode = null
        drawRect(mRectF, mPaint)
        mPaint.xfermode = mClearXfermode
        drawRoundRect(
            mRectF,
            mRadius,
            mRadius,
            mPaint
        )
        save()
      }
    }

    override fun sizeChanged(w: Int, h: Int) {
      mRectF[0f, 0f, w.toFloat()] = h.toFloat()
    }
  }

  /**
   * clipPath
   * 不行，无法抗锯齿，很难看
   */
  private inner class ClipPathRoundProcess : RoundProcessor {

    private val mPath = Path()
    private val mRectF = RectF()

    override fun setCornerRadius(radius: Float) {
      postInvalidate()
    }

    override fun beforeDraw(canvas: Canvas) {
      canvas.save()
      canvas.clipPath(mPath)
    }

    override fun afterDraw(canvas: Canvas) {
      canvas.restore()
    }

    override fun sizeChanged(w: Int, h: Int) {
      mRectF.set(0f, 0f, w.toFloat(), h.toFloat())
      mPath.reset()
      mPath.addRoundRect(mRectF, mRadius, mRadius, Path.Direction.CW)
    }
  }

  /**
   * outlineProvider
   * 效果和效率都ok，但不兼容4系统
   */
  @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
  private inner class OutlineRoundProcessor : RoundProcessor {
    private val mRect = Rect()
    private val mViewOutlineProvider: ViewOutlineProvider = object : ViewOutlineProvider() {
      override fun getOutline(view: View, outline: Outline) {
        outline.setRoundRect(mRect, mRadius)
      }
    }

    override fun setCornerRadius(radius: Float) {
      outlineProvider = mViewOutlineProvider
      invalidateOutline()
    }

    override fun beforeDraw(canvas: Canvas) {
      // empty
    }

    override fun afterDraw(canvas: Canvas) {
      // empty
    }

    override fun sizeChanged(w: Int, h: Int) {
      mRect.set(paddingLeft, paddingTop, w - paddingRight, h - paddingBottom)
      clipToOutline = true
      outlineProvider = mViewOutlineProvider
    }
  }

}