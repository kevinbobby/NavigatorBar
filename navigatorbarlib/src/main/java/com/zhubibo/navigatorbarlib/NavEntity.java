package com.zhubibo.navigatorbarlib;

/**
 * Created by Kevin on 2016/10/3 0:11.
 * Email: bibo.software@gmail.com
 */
public class NavEntity {

    // 第index级
    public int index;

    // 导航标题
    public String navTitle;

    // 导航id
    public Object dataId;

    // 当前目录缓存的数据
    public Object cacheData;

    public NavEntity(int index, String navTitle, Object dataId) {
        this.index = index;
        this.navTitle = navTitle;
        this.dataId = dataId;
    }

    public NavEntity(int index, String navTitle, Object dataId, Object cacheData) {
        this.index = index;
        this.navTitle = navTitle;
        this.dataId = dataId;
        this.cacheData = cacheData;
    }
}
