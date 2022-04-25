package treemap


/**
 * A simple implementation of the Mappable interface.
 */
open class MapItem constructor(
    override var size: Double = 1.0,
    override var bounds: Rect = Rect(),
    override var order: Int = 0,
    override var depth: Int = 0
) : Mappable {


    override fun setBounds(x: Double, y: Double, w: Double, h: Double) {
        bounds.setRect(x, y, w, h)
    }
}