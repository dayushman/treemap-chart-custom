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

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import treemap.*
import kotlin.math.max

internal class MapLayoutView : View {
    private var mapLayout: AbstractMapLayout? = null
    private lateinit var mappableItems: Array<Mappable>
    private var mRectBackgroundPaint: Paint? = null
    private var mRectBorderPaint: Paint? = null
    private var mTextPaint: Paint? = null

    constructor(context: Context?, attributeSet: AttributeSet?) : super(context) {}
    constructor(context: Context?, model: TreeModel) : super(context) {
        mapLayout = SquarifiedLayout()
        mappableItems = model.treeItems //getItems();

        // Set up the Paint for the rectangle background
        mRectBackgroundPaint = Paint()
        mRectBackgroundPaint!!.color = Color.CYAN
        mRectBackgroundPaint!!.style = Paint.Style.FILL

        // Set up the Paint for the rectangle border
        mRectBorderPaint = Paint()
        mRectBorderPaint!!.color = Color.BLACK
        mRectBorderPaint!!.style = Paint.Style.STROKE // outline the rectangle
        mRectBorderPaint!!.strokeWidth = 0f // single-pixel outline

        // Set up the Paint for the text label
        mTextPaint = Paint()
        mTextPaint!!.color = Color.BLACK
        mTextPaint!!.textSize = 20f
    }

    public override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        // Lay out the placement of the rectangles within the area available to this view
        mapLayout!!.layout(mappableItems, Rect(0.0, 0.0, w.toDouble(), h.toDouble()))
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Draw all the rectangles and their labels
        for (i in mappableItems.indices) {
            val item = mappableItems[i] as AndroidMapItem
            drawRectangle(canvas, item.getBoundsRectF())
            drawText(canvas, item.getLabel(), item.getBoundsRectF())
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
        if (rectF.width() > 30) {
            val textSize = max(rectF.width() / 7, 12f)
            mTextPaint!!.textSize = textSize
            canvas.drawText(
                text, rectF.left + 2, rectF.top + textSize / 2 + rectF.height() / 2,
                mTextPaint!!
            )
        }
    }
}