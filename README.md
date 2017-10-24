# NavigatorBar
一个适合做多级目录的文件导航栏控件，可缓存各级数据，可设置是否需要始终显示根目录

# 使用方法
1 layout中

```
<com.zhubibo.navigatorbarlib.NavigatorBarView
         android:layout_width="match_parent"
         android:layout_height="wrap_content"
         android:id="@+id/navBarView"
         app:navbar_background="#0F0"
         app:navbar_height="40dp"
         app:navbar_root_text="根目录"
         app:navbar_always_show_root="true"
         app:navbar_text_color="#f00"
         app:navbar_text_size="16sp"
         app:navbar_divider="@drawable/custom_nav_next"/>
```
        
2 Activity中
```
Activity implements NavigatorBarView.OnNavItemClickCallback
NavigatorBarView mNavBarView = findViewById(R.id.navBarView);
```

3 设置缓存数据的处理
```
    设置根目录的缓存数据
    mNavBarView.addRootCache(mFirstEntities);

    添加导航栏的子元素，设置缓存数据
    mNavBarView.addNavEntityWithCache(dataEntity.dataId, dataEntity.dataName, dataEntity.dataEntities);

    读取缓存数据，在回调方法`onNavClick`中处理
    mDataAdapter.setData((ArrayList<DataEntity>) navEntity.dataList);
```
4 回调中处理点击事件，详见Demo
public void onNavClick(NavEntity navEntity)
