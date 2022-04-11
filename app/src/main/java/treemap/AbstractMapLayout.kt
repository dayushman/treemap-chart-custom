package treemap

/**
 *
 * Abstract class holding utility routines that several
 * implementations of MapLayout use.
 *
 */
abstract class AbstractMapLayout : MapLayout {
    /** Subclasses implement this method themselves.  */
    abstract fun layout(items: Array<Mappable>, bounds: Rect)
    override fun layout(model: MapModel, bounds: Rect) {
        layout(model.items, bounds)
    }

    // For a production system, use a quicksort...
    fun sortDescending(items: Array<Mappable?>): Array<Mappable?> {
        val sortedArray = arrayOfNulls<Mappable>(items.size)
        System.arraycopy(items, 0, sortedArray, 0, items.size)
        val size = sortedArray.size
        var outOfOrder = true
        while (outOfOrder) {
            outOfOrder = false
            for (i in 0 until size - 1) {
                val wrong = sortedArray[i]!!.size < sortedArray[i + 1]!!.size
                if (wrong) {
                    val temp = sortedArray[i]
                    sortedArray[i] = sortedArray[i + 1]
                    sortedArray[i + 1] = temp
                    outOfOrder = true
                }
            }
        }
        return sortedArray
    }

    companion object {
        // Flags for type of rectangle division
        // and sort orders.
        const val VERTICAL = 0
        const val HORIZONTAL = 1
        const val ASCENDING = 0
        const val DESCENDING = 1
        @JvmOverloads
        fun totalSize(items: Array<Mappable>, start: Int = 0, end: Int = items.size - 1): Double {
            var sum = 0.0
            for (i in start..end) sum += items[i].size
            return sum
        }

        @JvmStatic
        @JvmOverloads
        fun sliceLayout(
            items: Array<Mappable>,
            start: Int,
            end: Int,
            bounds: Rect,
            orientation: Int,
            order: Int = ASCENDING
        ) {
            val total = totalSize(items, start, end)
            var a = 0.0
            val vertical = orientation == VERTICAL
            for (i in start..end) {
                val r = Rect()
                val b = items[i].size / total
                if (vertical) {
                    r.x = bounds.x
                    r.w = bounds.w
                    if (order == ASCENDING) r.y = bounds.y + bounds.h * a else r.y =
                        bounds.y + bounds.h * (1 - a - b)
                    r.h = bounds.h * b
                } else {
                    if (order == ASCENDING) r.x = bounds.x + bounds.w * a else r.x =
                        bounds.x + bounds.w * (1 - a - b)
                    r.w = bounds.w * b
                    r.y = bounds.y
                    r.h = bounds.h
                }
                items[i].bounds = r
                a += b
            }
        }
    }
}