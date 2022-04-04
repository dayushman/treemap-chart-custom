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

import android.graphics.RectF
import treemap.MapItem
import java.util.*
import kotlin.collections.ArrayList

class AndroidMapItem( private val label: String,val value: Double, weight: Double) : MapItem(),
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