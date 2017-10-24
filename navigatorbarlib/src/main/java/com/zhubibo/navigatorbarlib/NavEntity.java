package com.zhubibo.navigatorbarlib;

import java.util.ArrayList;

/**
 * Created by Kevin on 2016/10/3 0:11.
 * Email: bibo.software@gmail.com
 */
public class NavEntity {

    // 第index级
    public int index;

    // 导航标题
    public String dataTitle;

    // 导航 string id
    public String dataId;

    // 当前目录缓存的数据
    public ArrayList<?> dataList;

    public NavEntity(int index, String dataTitle, String dataId, ArrayList<?> dataList) {
        this.index = index;
        this.dataTitle = dataTitle;
        this.dataId = dataId;
        this.dataList = dataList;
    }
}
