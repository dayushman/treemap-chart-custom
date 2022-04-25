package treemap

import java.util.*
import kotlin.collections.ArrayList

/**
 *
 * An implementation of MapModel that represents
 * a hierarchical structure. It currently cannot
 * handle structural changes to the tree, since it
 * caches a fair amount of information.
 *
 */
class TreeModel : MapModel {
    val mapItem: Mappable
    private var childItems : Array<Mappable>? = null
    private var cachedTreeItems // we assume tree structure doesn't change.
            : Array<Mappable>? = null
    private var cachedLeafModels: Array<MapModel>? = null
    private var parent: TreeModel? = null
    private val children: Vector<TreeModel> = Vector<TreeModel>()
    private var sumsChildren = false

    constructor() {
        mapItem = MapItem()
        sumsChildren = true
    }

    constructor(mapItem: Mappable) {
        this.mapItem = mapItem
    }

    fun setOrder(order: Int) {
        mapItem.order = order
    }

    fun getLeafModels(): Array<MapModel> {
        if (cachedLeafModels != null) return cachedLeafModels!!
        val v = emptyList<TreeModel>()
        addLeafModels(v as ArrayList<TreeModel>)
        val n = v.size
        val m = ArrayList<TreeModel>()
        m.addAll(v)
        cachedLeafModels = m.toTypedArray()
        return m.toTypedArray()
    }


    private fun addLeafModels(v: ArrayList<TreeModel>): ArrayList<TreeModel> {
        if (!hasChildren()) {
            System.err.println("Somehow tried to get child model for leaf!!!")
            return v
        }
        if (!getChild(0).hasChildren()) v.add(this) else for (i in childCount() - 1 downTo 0) getChild(
            i
        ).addLeafModels(v)
        return v
    }

    fun depth(): Int {
        return if (parent == null) 0 else 1 + parent!!.depth()
    }

    @JvmOverloads
    fun layout(tiling: MapLayout, bounds: Rect? = mapItem.bounds) {
        mapItem.bounds = bounds!!
        if (!hasChildren()) return
        val s = sum()
        tiling.layout(this, bounds)
        for (i in childCount() - 1 downTo 0) getChild(i).layout(tiling)
    }

    val treeItems: Array<Mappable>
        get() {
            if (cachedTreeItems != null) return cachedTreeItems!!
            val v: Vector<Mappable> = Vector<Mappable>()
            addTreeItems(v)
            val m  = ArrayList<Mappable>()
            m.addAll(v.toMutableList())
            cachedTreeItems = m.toTypedArray()
            return m.toTypedArray()
        }

    private fun addTreeItems(v: Vector<Mappable>) {
        if (!hasChildren()) v.add(mapItem) else for (i in childCount() - 1 downTo 0) getChild(
            i
        ).addTreeItems(v)
    }

    private fun sum(): Double {
        if (!sumsChildren) return mapItem.size
        var s = 0.0
        for (i in childCount() - 1 downTo 0) s += getChild(i).sum()
        mapItem.size = s
        return s
    }

    override val items: Array<Mappable>
        get() {
            if (childItems != null) return childItems!!
            val n = childCount()
            childItems = emptyArray()
            for (i in 0 until n) {
                childItems!![i] = getChild(i).mapItem
                childItems!![i].depth = 1 + depth()
            }
            return childItems!!
        }

    fun addChild(child: TreeModel) {
        child.setParent(this)
        children.addElement(child)
        childItems = null
    }

    fun setParent(parent: TreeModel?) {
        var p = parent
        while (p != null) {
            require(!(p === this)) { "Circular ancestry!" }
            p = p.getParent()
        }
        this.parent = parent
    }

    fun getParent(): TreeModel? {
        return parent
    }

    fun childCount(): Int {
        return children.size
    }

    fun getChild(n: Int): TreeModel {
        return children.elementAt(n) as TreeModel
    }

    fun hasChildren(): Boolean {
        return children.size > 0
    }

    fun print() {
        print("")
    }

    private fun print(prefix: String) {
        println(prefix + "size=" + mapItem.size)
        for (i in 0 until childCount()) getChild(i).print("$prefix..")
    }
}