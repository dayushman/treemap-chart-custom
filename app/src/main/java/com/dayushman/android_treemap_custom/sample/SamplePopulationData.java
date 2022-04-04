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
package com.dayushman.android_treemap_custom.sample;



import com.dayushman.android_treemap_custom.AndroidMapItem;

import java.util.Set;

import treemap.TreeModel;

public class SamplePopulationData {

    private Set<AndroidMapItem> androidMapItems;
    private TreeModel treeModel;

    public Set<AndroidMapItem> getItems() {
        return androidMapItems;
    }

    public TreeModel getTreeModel() {
        return treeModel;
    }

    public SamplePopulationData() {
        /*
        AndroidMapItem rootItem = new AndroidMapItem(null,7000000, "World");
        treeModel = new TreeModel(rootItem);

        treeData.add(CustomTreeChartData(id= "Financials",parent ="Nifty 50",status = -1.0,value = 2645788))
        treeData.add(CustomTreeChartData(id= "Consumer Staples",parent ="Nifty 50",status = 3.2,value = 538772))
        treeData.add(CustomTreeChartData(id= "IT",parent ="Nifty 50",status = 4.3,value = 1352124))
        treeData.add(CustomTreeChartData(id= "Consumer Discretion",parent ="Nifty 50",status = -6.0,value = 531967))
        treeData.add(CustomTreeChartData(id= "Health Care",parent ="Nifty 50",status = 3.0,value = 255008))
        treeData.add(CustomTreeChartData(id= "Materials",parent ="Nifty 50",status = -2.3,value = 603074))
        treeData.add(CustomTreeChartData(id= "Industrials",parent ="Nifty 50",status = -4.5,value = 302426))
        treeData.add(CustomTreeChartData(id= "Utilities",parent ="Nifty 50",status =10.0,value = 137092))
        treeData.add(CustomTreeChartData(id= "Communication Service",parent ="Nifty 50",status = 4.2,value = 152253))*/
    }

}
