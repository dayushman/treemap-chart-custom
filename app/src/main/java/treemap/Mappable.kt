package treemap

/**
 *
 * Interface representing an object that can be placed
 * in a treemap layout.
 *
 *
 * The properties are:
 *
 *  *  size: corresponds to area in map.
 *  *  order: the sort order of the item.
 *  *  depth: the depth in hierarchy.
 *  *  bounds: the bounding rectangle of the item in the map.
 *
 *
 */
interface Mappable {
    var size: Double
    var bounds: Rect
    fun setBounds(x: Double, y: Double, w: Double, h: Double)
    var order: Int
    var depth: Int
}