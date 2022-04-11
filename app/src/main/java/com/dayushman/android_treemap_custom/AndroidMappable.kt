package com.dayushman.android_treemap_custom

import android.graphics.RectF
import treemap.Mappable

interface AndroidMappable : Mappable {
    fun getBoundsRectF(): RectF
    fun getLabel(): String
}