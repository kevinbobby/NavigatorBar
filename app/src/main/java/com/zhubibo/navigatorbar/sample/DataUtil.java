package com.zhubibo.navigatorbar.sample;

import java.util.ArrayList;

/**
 * Created by kevin on 2017/10/24.
 */

public class DataUtil {

    // 列表信息
    public static ArrayList<DataEntity> mFirstEntities = new ArrayList<>();
    public static ArrayList<DataEntity> mSecondDataEntities = new ArrayList<>();
    public static ArrayList<DataEntity> mThirdDataEntities = new ArrayList<>();

    static {

        mThirdDataEntities = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            DataEntity dataEntity = new DataEntity();
            dataEntity.dataId = i + "";
            dataEntity.dataType = 1;
            dataEntity.dataName = "三子文件" + i;
            mThirdDataEntities.add(dataEntity);
        }

        mSecondDataEntities = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            DataEntity dataEntity = new DataEntity();
            dataEntity.dataId = i + "";
            if (i < 5) {
                dataEntity.dataType = 0;
                dataEntity.dataName = "二级文件夹" + i;
                dataEntity.dataEntities = mThirdDataEntities;
            } else {
                dataEntity.dataType = 1;
                dataEntity.dataName = "二级文件" + i;
            }
            mSecondDataEntities.add(dataEntity);
        }

        for (int i = 0; i < 20; i++) {
            DataEntity dataEntity = new DataEntity();
            dataEntity.dataId = i + "";
            if (i < 10) {
                dataEntity.dataType = 0;
                dataEntity.dataName = "一级文件夹" + i;
                dataEntity.dataEntities = mSecondDataEntities;
            } else {
                dataEntity.dataType = 1;
                dataEntity.dataName = "一级文件" + i;
            }
            mFirstEntities.add(dataEntity);
        }
    }
}
