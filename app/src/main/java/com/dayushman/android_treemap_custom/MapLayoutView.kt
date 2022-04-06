package com.dayushman.android_treemap_custom

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.skydoves.balloon.*
import treemap.*
import treemap.Rect
import kotlin.math.roundToInt
import kotlin.math.roundToLong


internal class MapLayoutView : View {
    private var mapLayout: AbstractMapLayout? = null
    private lateinit var mappableItems: Array<Mappable>
    private var mRectBackgroundPaint: Paint? = null
    private var mRectBorderPaint: Paint? = null
    private var mTextPaint: Paint? = null
    private var mOverlayPaint: Paint? = null
    private var selectedIndices: Int = -1

    constructor(context: Context?, attributeSet: AttributeSet?) : super(context) {}
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
        for (i in mappableItems.indices){
            val item = mappableItems[i] as AndroidMapItem
            if(item.isClicked(event!!.x.toDouble(),event.y.toDouble())){
                selectedIndices = i
                Log.e("TAG", "onTouchEvent: ${item.getLabel()}")
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
                    mRectBackgroundPaint!!.color = resources.getColor(R.color.red)
                }
                else -> {
                    // Set up the Paint for the rectangle background
                    mRectBackgroundPaint!!.color = resources.getColor(R.color.green)
                }
            }
            drawRectangle(canvas, item.getBoundsRectF())
            drawText(canvas, item.getLabel(), item.getBoundsRectF())
            if(selectedIndices == i){
                drawOverLay(canvas,item.getBoundsRectF(),item)
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
        if (rectF.width() > 300) {
//            val textSize = max(rectF.width() / 7, 12f)
            mTextPaint!!.textSize = 40f
            canvas.drawText(
                text, rectF.left + 2, rectF.top + 40f / 2 + rectF.height() / 2,
                mTextPaint!!
            )
        }else{
            mTextPaint!!.textSize = 40f
            canvas.drawText(
                "...", rectF.left + 2, rectF.top + 40f / 2 + rectF.height() / 2,
                mTextPaint!!
            )
        }
    }

    private fun drawOverLay(canvas: Canvas, rectF: RectF,item: AndroidMapItem){
        val d = resources.getDrawable(R.drawable.ic_bg_overlay, null)
        d.setBounds(rectF.left.toInt(),rectF.top.toInt(),rectF.right.roundToInt(),rectF.bottom.roundToInt())
        d.draw(canvas)

        val balloon = Balloon.Builder(context)
            .setHeight(BalloonSizeSpec.WRAP)
            .setText(item.getLabel())
            .setTextSize(15f)
            .setArrowOrientation(ArrowOrientation.TOP)
            .setArrowPositionRules(ArrowPositionRules.ALIGN_BALLOON)
            .setArrowSize(10)
            .setArrowPosition(0.5f)
            .setPadding(12)
            .setCornerRadius(8f)
            .setAutoDismissDuration(1000L)
            .setBalloonAnimation(BalloonAnimation.ELASTIC)
            .build()

        balloon.showAtCenter(this,rectF.left.roundToInt().minus(rectF.width().roundToInt().div(2)),rectF.top.roundToInt().minus(rectF.height().roundToInt().div(2)))
    }


}