package treemap


/**
 *
 * The interface for all treemap layout algorithms.
 * If you write your own algorith, it should conform
 * to this interface.
 *
 *
 * IMPORTANT: if you want to be able to automatically plug
 * your algorithm into the various demos and test harnesses
 * included in the treemap package, it should have
 * an empty constructor.
 *
 */
interface MapLayout {
    /**
     * Arrange the items in the given MapModel to fill the given rectangle.
     *
     * @param model The MapModel.
     * @param bounds The boundsing rectangle for the layout.
     */
    fun layout(model: MapModel, bounds: Rect)

    /**
     * Return a human-readable name for this layout;
     * used to label figures, tables, etc.
     *
     * @return String naming this layout.
     */
    val name: String

    /**
     * Return a longer description of this layout;
     * Helpful in creating online-help,
     * interactive catalogs or indices to lists of algorithms.
     *
     * @return String describing this layout.
     */
    val description: String
}