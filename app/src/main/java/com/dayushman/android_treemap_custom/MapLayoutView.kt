package com.dayushman.android_treemap_custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.core.graphics.ColorUtils
import com.skydoves.balloon.*
import treemap.*
import kotlin.math.min
import kotlin.math.roundToInt


internal class MapLayoutView : View {
    private var mapLayout: AbstractMapLayout? = null
    private lateinit var mappableItems: Array<Mappable>
    private var mRectBackgroundPaint: Paint? = null
    private var mRectBorderPaint: Paint? = null
    private var mTextPaint: Paint? = null
    private var mOverlayPaint: Paint? = null
    private var selectedIndices: Int = -1

    constructor(context: Context?, attributeSet: AttributeSet?) : super(context)
    constructor(context: Context?, model: TreeModel) : super(context) {
        mapLayout = SquarifiedLayout()
        mappableItems = model.treeItems //getItems();
        mTextPaint = Paint()
        mRectBackgroundPaint = Paint()
        mRectBorderPaint = Paint()
        mOverlayPaint = Paint()


    }

    public override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        // Lay out the placement of the rectangles within the area available to this view
        mapLayout!!.layout(mappableItems, Rect(0.0, 0.0, w.toDouble(), h.toDouble()))
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        for (i in mappableItems.indices) {
            val item = mappableItems[i] as AndroidMapItem
            if (item.isClicked(event!!.x.toDouble(), event.y.toDouble())) {
                selectedIndices = i
                invalidate()
            }
        }
        return false
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Draw all the rectangles and their labels
        for (i in mappableItems.indices) {
            val item = mappableItems[i] as AndroidMapItem
            mRectBackgroundPaint!!.style = Paint.Style.FILL

            // Set up the Paint for the rectangle border
            mRectBorderPaint!!.color = Color.WHITE
            mRectBorderPaint!!.style = Paint.Style.STROKE // outline the rectangle
            mRectBorderPaint!!.strokeWidth = 6f // single-pixel outline

            // Set up the Paint for the text label
            mTextPaint!!.color = Color.WHITE
            mTextPaint!!.textSize = 20f



            when {
                item.value < 0 -> {
                    // Set up the Paint for the rectangle background
                    val color = ColorUtils.blendARGB(
                        resources.getColor(R.color.light_red),
                        resources.getColor(R.color.red),
                        item.value.div(-100).toFloat()
                    )

                    mRectBackgroundPaint!!.color = color
                }
                else -> {
                    // Set up the Paint for the rectangle background
                    val color = ColorUtils.blendARGB(
                        resources.getColor(R.color.green),
                        resources.getColor(R.color.dark_green),
                        item.value.div(100).toFloat()
                    )
                    mRectBackgroundPaint!!.color = color
                }
            }
            drawRectangle(canvas, item.getBoundsRectF())
            drawText(canvas, item.getLabel(), item.getBoundsRectF())
            if (selectedIndices == i) {
                drawOverLay(canvas, item.getBoundsRectF(), item)
                selectedIndices = -1
            }
        }
    }

    private fun drawRectangle(canvas: Canvas, rectF: RectF) {
        // Draw the rectangle's background
        canvas.drawRect(rectF, mRectBackgroundPaint!!)

        // Draw the rectangle's border
        canvas.drawRect(rectF, mRectBorderPaint!!)

    }

    private fun drawText(canvas: Canvas, text: String, rectF: RectF) {
        // Don't draw text for small rectangles
        mTextPaint!!.textAlign = Paint.Align.CENTER
        mTextPaint!!.textSize = 40f
        val xPos = rectF.width() / 2
        val yPos = (rectF.height() / 2 - (mTextPaint!!.descent() + mTextPaint!!.ascent()) / 2)
        var paintText = text
        if (rectF.width() <= mTextPaint!!.measureText(text) || rectF.height() <= 40f) {
            mTextPaint!!.textSize = min(mTextPaint!!.textSize, rectF.height())
            paintText = "..."
        }
        canvas.drawText(
            paintText, rectF.left + xPos, rectF.top + yPos,
            mTextPaint!!
        )
    }

    private fun drawOverLay(canvas: Canvas, rectF: RectF, item: AndroidMapItem) {
        val overlayDrawable = resources.getDrawable(R.drawable.ic_bg_overlay, null)
        overlayDrawable.setBounds(
            rectF.left.toInt(),
            rectF.top.toInt(),
            rectF.right.roundToInt(),
            rectF.bottom.roundToInt()
        )
        overlayDrawable.draw(canvas)


        /**
         * 79 is the x of first box and 159 is y of the Balloon atm
         */
        if (rectF.right <= this.measuredWidth / 3) {
            val xOff = rectF.right.toInt()
            val yOff = rectF.centerY().toInt() + 159
            val balloon = Balloon.Builder(context)
                .setHeight(BalloonSizeSpec.WRAP)
                .setWidth(BalloonSizeSpec.WRAP)
                .setText(item.getLabel())
                .setTextSize(15f)
                .setArrowOrientation(ArrowOrientation.START)
                .setArrowPositionRules(ArrowPositionRules.ALIGN_BALLOON)
                .setArrowOrientationRules(ArrowOrientationRules.ALIGN_FIXED)
                .setArrowSize(10)
                .setPadding(12)
                .setCornerRadius(8f)
                .build()
            balloon.showAsDropDown(this, xOff, yOff)
        } else if ((this.measuredWidth - rectF.left) <= measuredWidth / 3) {
            val xOff = rectF.left.toInt()
            val yOff = rectF.centerY().toInt()
            val balloon = Balloon.Builder(context)
                .setHeight(BalloonSizeSpec.WRAP)
                .setWidth(BalloonSizeSpec.WRAP)
                .setText(item.getLabel())
                .setTextSize(15f)
                .setArrowOrientation(ArrowOrientation.END)
                .setArrowPositionRules(ArrowPositionRules.ALIGN_BALLOON)
                .setArrowPosition(0.1f)
                .setArrowOrientationRules(ArrowOrientationRules.ALIGN_FIXED)
                .setArrowSize(10)
                .setPadding(12)
                .setCornerRadius(8f)
                .build()
            balloon.showAsDropDown(this, xOff, yOff)
        } else if (rectF.bottom <= this.measuredHeight / 2) {
            val yOff = rectF.bottom.toInt() + 159
            val xOff = rectF.centerX().toInt()
            val balloon = Balloon.Builder(context)
                .setHeight(BalloonSizeSpec.WRAP)
                .setWidth(BalloonSizeSpec.WRAP)
                .setText(item.getLabel())
                .setTextSize(15f)
                .setArrowOrientation(ArrowOrientation.TOP)
                .setArrowPosition(0.1f)
                .setArrowPositionRules(ArrowPositionRules.ALIGN_BALLOON)
                .setArrowOrientationRules(ArrowOrientationRules.ALIGN_FIXED)
                .setArrowSize(10)
                .setPadding(12)
                .setCornerRadius(8f)
                .build()
            balloon.showAsDropDown(this, xOff, yOff)
        } else {
            val xOff = rectF.centerX().toInt()
            val yOff = rectF.top.toInt()
            val balloon = Balloon.Builder(context)
                .setHeight(BalloonSizeSpec.WRAP)
                .setWidth(BalloonSizeSpec.WRAP)
                .setText(item.getLabel())
                .setTextSize(15f)
                .setArrowOrientation(ArrowOrientation.BOTTOM)
                .setArrowPosition(0.1f)
                .setArrowPositionRules(ArrowPositionRules.ALIGN_BALLOON)
                .setArrowOrientationRules(ArrowOrientationRules.ALIGN_FIXED)
                .setArrowSize(10)
                .setPadding(12)
                .setCornerRadius(8f)
                .build()
            balloon.showAsDropDown(this, xOff, yOff)

        }

    }


}