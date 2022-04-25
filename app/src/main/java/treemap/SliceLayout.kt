
package treemap


import kotlin.jvm.JvmOverloads

/**
 * The original slice-and-dice layout for treemaps.
 */
class SliceLayout @JvmOverloads constructor(private val orientation: Int = ALTERNATE) :
    AbstractMapLayout() {
    override fun layout(items: Array<Mappable>, bounds: Rect) {
        if (items.size == 0) return
        val o = orientation
        if (o == BEST) layoutBest(items, 0, items.size - 1, bounds) else if (o == ALTERNATE) layout(
            items,
            bounds,
            items[0].depth % 2
        ) else layout(items, bounds, o)
    }

    override val description: String
        get() = "This is the original treemap algorithm, " +
                "which has excellent stability properies " +
                "but leads to high aspect ratios."

    override val name: String
        get() = "Slice-and-dice"


    companion object {
        const val BEST = 2
        const val ALTERNATE = 3
        fun layoutBest(items: Array<Mappable>?, start: Int, end: Int, bounds: Rect) {
            sliceLayout(
                items!!, start, end, bounds,
                if (bounds.x > bounds.h) HORIZONTAL else VERTICAL, ASCENDING
            )
        }

        fun layoutBest(items: Array<Mappable>, start: Int, end: Int, bounds: Rect, order: Int) {
            sliceLayout(
                items, start, end, bounds,
                if (bounds.w > bounds.h) HORIZONTAL else VERTICAL, order
            )
        }

        fun layout(items: Array<Mappable>, bounds: Rect?, orientation: Int) {
            sliceLayout(items, 0, items.size - 1, bounds!!, orientation)
        }
    }
}