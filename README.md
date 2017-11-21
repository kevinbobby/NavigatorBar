# NavigatorBar
一个适合做多级目录的文件导航栏控件，可缓存各级数据，可设置是否需要始终显示根目录

# 集成说明
1. 在项目的`build.gradle`中添加：
```
repositories {
        ...
        maven { url "https://jitpack.io" }
    }
```
2. 在应用的`build.gradle`中添加：
```
    compile 'com.github.kevinbobby:NavigatorBar:1.0.0'
```

# 使用方法
1. layout中

```
<com.zhubibo.navigatorbarlib.NavigatorBarView
         android:layout_width="match_parent"
         android:layout_height="wrap_content"
         android:id="@+id/navBarView"
         app:navbar_background="#0F0"
         app:navbar_height="40dp"
         app:navbar_always_show_root="true"
         app:navbar_text_color="#f00"
         app:navbar_text_size="16sp"
         app:navbar_divider="@drawable/custom_nav_next"/>
```
        
2. Activity中
```
    MainActivity implements NavigatorBarView.OnNavItemClickCallback
    
    NavigatorBarView mNavBarView = findViewById(R.id.navBarView);
    
    // 设置节点的点击事件回调
    mNavBarView.setNavItemClickCallback(this);
```

3.1 添加导航栏的子元素，没有缓存数据
```
    mNavBarView.addNavEntityNoCache(dataEntity.dataId, dataEntity.dataName);
```

3.2 添加导航栏的子元素，设置缓存数据
```
    mNavBarView.addNavEntityWithCache(dataEntity.dataId, dataEntity.dataName, dataEntity.dataEntities);
```
4. 回调中处理点击事件，详见Demo
```
    public void onNavClick(NavEntity navEntity)
```

