package com.zhubibo.navigatorbar.sample;

import java.util.ArrayList;

/**
 * Created by Kevin on 2016/10/10 0:30.
 * Email: bibo.software@gmail.com
 */
public class DataEntity {

    public String dataId;

    public String dataName;

    // 0: 文件夹  1：文件
    public int dataType;

    public ArrayList<DataEntity> dataEntities = new ArrayList<>();
}
