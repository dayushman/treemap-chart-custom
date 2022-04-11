package com.dayushman.android_treemap_custom

import android.graphics.RectF
import treemap.MapItem
import java.util.*
import kotlin.collections.ArrayList

class AndroidMapItem(private val label: String, val value: Double, weight: Double) : MapItem(),
    AndroidMappable, Comparable<AndroidMapItem> {
    private val weight = 0.0
    override fun getLabel(): String {
        return label
    }

    /** Return an Android RectF that is the size of the bounds rectangle  */
    override fun getBoundsRectF(): RectF {
        val bounds = bounds
        return RectF(
            bounds.x.toFloat(),
            bounds.y.toFloat(),
            bounds.x.toFloat() + bounds.w.toFloat(),
            bounds.y.toFloat() + bounds.h.toFloat()
        )
    }

    fun isClicked(x: Double, y: Double): Boolean {
        if (bounds.x < x && ((bounds.x + bounds.w) >= x)) {
            if (bounds.y < y && ((bounds.y + bounds.h) >= y))
                return true
        }
        return false
    }

    override fun toString(): String {
        return AndroidMapItem::class.java.simpleName + "[label=" + label + ",weight=" + weight +
                ",bounds=" + bounds.toString() +
                ",boundsRectF=" + getBoundsRectF().toString() + "]"
    }

    companion object {
        fun <T : Comparable<T>?> asReverseSortedList(
            collection: Collection<T>?
        ): ArrayList<T> {
            val arrayList = ArrayList(collection)
            Collections.sort(arrayList, Collections.reverseOrder())
            return arrayList
        }
    }

    init {
        size = weight
    }

    override fun compareTo(other: AndroidMapItem): Int {
        return this.weight.compareTo(other.weight)
    }
}