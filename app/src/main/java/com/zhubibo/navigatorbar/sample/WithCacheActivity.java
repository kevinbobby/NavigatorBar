package com.zhubibo.navigatorbar.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.zhubibo.navigatorbarlib.NavEntity;
import com.zhubibo.navigatorbarlib.NavigatorBarView;

import java.util.ArrayList;

public class WithCacheActivity extends AppCompatActivity implements
        AdapterView.OnItemClickListener,
        NavigatorBarView.OnNavItemClickCallback {

    // 导航信息
    private NavigatorBarView mNavBarView;
    private ListView mDataLv;
    private DataAdapter mDataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        bindViews();
        initValues();
        bindEvents();
    }

    private void bindViews() {
        // 初始化导航list
        mNavBarView = findViewById(R.id.navBarView);
        mDataLv = findViewById(R.id.mainDataLv);
    }

    private void initValues() {
        // 需要缓存，则必须要设置根元素的缓存值
        mNavBarView.addRootCache(DataUtil.mFirstEntities);
    }

    private void bindEvents() {
        mNavBarView.setNavItemClickCallback(this);
        mDataAdapter = new DataAdapter(DataUtil.mFirstEntities, this);
        mDataLv.setAdapter(mDataAdapter);
        mDataLv.setOnItemClickListener(this);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        DataEntity dataEntity = (DataEntity) parent.getItemAtPosition(position);
        // 如果dataEntity的子元素不为空
        if (dataEntity.dataEntities != null && dataEntity.dataEntities.size() > 0) {
            // 添加导航栏的子元素，设置缓存数据
            mNavBarView.addNavEntityWithCache(dataEntity.dataId, dataEntity.dataName, dataEntity.dataEntities);
            // 请求数据，刷新dataList
            mDataAdapter.setData(dataEntity.dataEntities);
        } else {
            // 如果dataEntity的子元素为空
            Toast.makeText(this, dataEntity.dataName, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNavClick(NavEntity navEntity) {
        // 使用了addNavEntityWithData缓存数据，则可以直接获取缓存
        mDataAdapter.setData((ArrayList<DataEntity>) navEntity.dataList);

    }
}
