package com.dayushman.android_treemap_custom.sample

import com.dayushman.android_treemap_custom.AndroidMapItem
import treemap.TreeModel

class SampleData() {
    private val androidMapItems: Set<AndroidMapItem>? = null
    private var treeModel: TreeModel

    fun getItems(): Set<AndroidMapItem?>? {
        return androidMapItems
    }

    fun getTreeModel(): TreeModel {
        return treeModel
    }

    init {
        val rootItem = AndroidMapItem("Nifty 50",0.0,1000000.0)
        treeModel = TreeModel(rootItem)
        treeModel.addChild(TreeModel(AndroidMapItem("Financial",1.5,20000.0)))
        treeModel.addChild(TreeModel(AndroidMapItem("Consumer Staples",1.5,30000.0)))
        treeModel.addChild(TreeModel(AndroidMapItem("IT",-1.5,10000.0)))
        treeModel.addChild(TreeModel(AndroidMapItem("Consumer Jurisdiction",-1.5,40000.0)))
    }
}