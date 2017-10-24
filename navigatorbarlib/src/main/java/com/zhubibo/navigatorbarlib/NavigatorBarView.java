package com.zhubibo.navigatorbarlib;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * Created by Kevin on 2016/10/13 0:44.
 * Email: bibo.software@gmail.com
 */
public class NavigatorBarView extends LinearLayout implements NavAdapter.OnNavItemClickListener {

    private int navBackgroundColor;
    private int navHeight;
    private Drawable navDivider;
    private int navTextColor;
    private int navTextSize;
    // 不可为空
    private String navRootText;
    // 设置根元素是否始终显示，如果为false，则只有根元素的时候，会隐藏导航栏
    private boolean alwaysShowRoot;

    private View mRootView;
    // 导航信息
    private ArrayList<NavEntity> mNavEntities = new ArrayList<>();
    private RecyclerView mNavRv;
    private NavAdapter mNavAdapter;

    // 当前目录
    private NavEntity mCurrEntity;

    private OnNavItemClickCallback clickCallback;

    public NavigatorBarView(Context context) {
        this(context, null);
    }

    public NavigatorBarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NavigatorBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.NavigatorBarView, defStyleAttr, 0);
        navBackgroundColor = typedArray.getColor(R.styleable.NavigatorBarView_navbar_background, Color.GRAY);
        navHeight = typedArray.getDimensionPixelSize(R.styleable.NavigatorBarView_navbar_height, dip2px(context, 40));
        navDivider = typedArray.getDrawable(R.styleable.NavigatorBarView_navbar_divider);
        navTextColor = typedArray.getColor(R.styleable.NavigatorBarView_navbar_text_color, Color.BLACK);
        navTextSize = typedArray.getDimensionPixelSize(R.styleable.NavigatorBarView_navbar_text_size, dip2px(context, 14));
        navRootText = typedArray.getString(R.styleable.NavigatorBarView_navbar_root_text);
        alwaysShowRoot = typedArray.getBoolean(R.styleable.NavigatorBarView_navbar_always_show_root, true);
        typedArray.recycle();
        init(context);
    }

    private void init(Context context) {
        mRootView = LayoutInflater.from(context).inflate(R.layout.view_navigator_bar, this);
        mRootView.setVisibility(GONE);

        mNavRv = findViewById(R.id.navigatorRv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mNavRv.setLayoutManager(layoutManager);
        mNavRv.setOverScrollMode(View.OVER_SCROLL_NEVER);

        mNavAdapter = new NavAdapter(mNavEntities, navDivider, navTextSize, navTextColor);
        mNavRv.setAdapter(mNavAdapter);
        mNavAdapter.setNavItemClickListener(this);

        // 设置控件背景色
        mRootView.setBackgroundColor(navBackgroundColor);
        // 设置控件高度
        mRootView.setMinimumHeight(navHeight);
        // 设置控件根目录
        initRootEntity(navRootText, null);
    }

    public void setNavItemClickCallback(OnNavItemClickCallback itemClickCallback) {
        this.clickCallback = itemClickCallback;
    }

    @Override
    public void onNavItemClick(NavEntity navEntity) {
        if (mCurrEntity.index == navEntity.index) {
            return;
        }
        mNavEntities = new ArrayList<>(mNavEntities.subList(0, navEntity.index + 1));
        mNavAdapter.setData(mNavEntities);
        mNavRv.smoothScrollToPosition(navEntity.index);
        // 如果alwaysShowRoot为false
        if (!alwaysShowRoot && navEntity.index == 0) {
            mRootView.setVisibility(GONE);
        }
        // 点击事件
        clickCallback.onNavClick(navEntity);
    }

    /**
     * 初始化根元素
     * @param rootName  根元素名称
     * @param dataList  缓存数据
     */
    public void initRootEntity(String rootName, ArrayList<?> dataList) {
        if (mRootView.getVisibility() == GONE && alwaysShowRoot) {
            mRootView.setVisibility(VISIBLE);
        }
        NavEntity mRootEntity = new NavEntity(0, rootName, "0", dataList);
        mNavEntities.add(mRootEntity);
        mCurrEntity = mRootEntity;
        mNavAdapter.setData(mNavEntities);
    }

    /**
     * 如果需要缓存，则必须要设置根元素的缓存值
     * @param dataList      根元素的数据list
     */
    public void addRootCache(ArrayList<?> dataList) {
        mNavEntities.get(0).dataList = dataList;
    }

    /**
     * 增加导航元素，不需要缓存数据
     * @param dataId        导航元素id，用于后续点击事件主键的获取
     * @param dataTitle     导航元素名称
     */
    public void addNavEntityNoCache(String dataId, String dataTitle) {
        addNavEntityWithCache(dataId, dataTitle, null);
    }

    /**
     * 增加导航元素，需要缓存数据
     * @param dataId        导航元素id，用于后续点击事件主键的获取
     * @param dataTitle     导航元素名称
     * @param dataList      缓存数据
     */
    public void addNavEntityWithCache(String dataId, String dataTitle, ArrayList<?> dataList) {
        if (mRootView.getVisibility() == GONE) {
            mRootView.setVisibility(VISIBLE);
        }
        NavEntity navEntity = new NavEntity(mNavEntities.size(), dataTitle, dataId, dataList);
        mNavEntities.add(navEntity);
        mNavAdapter.setData(mNavEntities);
        mCurrEntity = navEntity;
        mNavRv.smoothScrollToPosition(navEntity.index);
    }

    public int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public interface OnNavItemClickCallback {

        /**
         * 导航栏点击事件
         * @param navEntity  当前点击的NavEntity
         */
        void onNavClick(NavEntity navEntity);
    }
}
