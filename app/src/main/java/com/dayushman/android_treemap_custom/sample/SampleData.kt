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



        treeModel.addChild(TreeModel(AndroidMapItem("Utilities",-1.22,800.19)))
        treeModel.addChild(TreeModel(AndroidMapItem("Consumer Staples",1.51,572.42)))
        treeModel.addChild(TreeModel(AndroidMapItem("Consumer States",1.1,5222.42)))
        treeModel.addChild(TreeModel(AndroidMapItem("Energy",-1.22,800.19)))
        treeModel.addChild(TreeModel(AndroidMapItem("Technology",-0.18,1288.26)))
        treeModel.addChild(TreeModel(AndroidMapItem("Health Care",-2.38,236.01)))
        treeModel.addChild(TreeModel(AndroidMapItem("Consumer States",18.51,52.42)))
        treeModel.addChild(TreeModel(AndroidMapItem("Communication Services",-0.63,149.53)))
    }
}
