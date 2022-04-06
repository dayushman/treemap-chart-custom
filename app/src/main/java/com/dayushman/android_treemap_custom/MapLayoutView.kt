/*
 * Copyright 2013 Robert Theis
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dayushman.android_treemap_custom

import android.R.attr
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.core.content.res.ResourcesCompat
import treemap.*
import treemap.Rect


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
                selectedIndices = -1
                drawOverLay(canvas,item.getBoundsRectF())
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

    private fun drawOverLay(canvas: Canvas, rectF: RectF){
        val d = resources.getDrawable(R.drawable.ic_bg_overlay, null)
        d.setBounds(rectF.left.toInt(),rectF.top.toInt(),rectF.right.toInt(),rectF.bottom.toInt());
        d.draw(canvas)
    }


}