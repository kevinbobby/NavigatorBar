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
    // 设置根元素是否始终显示，如果为false，则只有根元素的时候，会隐藏导航栏
    private boolean alwaysShowRoot;

    private View mBarView;
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
        alwaysShowRoot = typedArray.getBoolean(R.styleable.NavigatorBarView_navbar_always_show_root, true);
        typedArray.recycle();
        init(context);
    }

    private void init(Context context) {
        mBarView = LayoutInflater.from(context).inflate(R.layout.view_navigator_bar, this);
        mBarView.setVisibility(GONE);

        mNavRv = findViewById(R.id.navigatorRv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mNavRv.setLayoutManager(layoutManager);
        mNavRv.setOverScrollMode(View.OVER_SCROLL_NEVER);

        mNavAdapter = new NavAdapter(mNavEntities, navDivider, navTextSize, navTextColor);
        mNavRv.setAdapter(mNavAdapter);
        mNavAdapter.setNavItemClickListener(this);

        // 设置控件背景色
        mBarView.setBackgroundColor(navBackgroundColor);
        // 设置控件高度
        mBarView.setMinimumHeight(navHeight);
        // 设置控件是否显示
        if (mBarView.getVisibility() == GONE && alwaysShowRoot) {
            mBarView.setVisibility(VISIBLE);
        }
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
            mBarView.setVisibility(GONE);
        }
        // 点击事件
        clickCallback.onNavClick(navEntity);
    }

    /**
     * 增加导航元素，不需要缓存数据
     * @param dataId        导航元素id，用于后续点击事件主键的获取
     * @param navTitle     导航元素名称
     */
    public void addNavEntityNoCache(Object dataId, String navTitle) {
        NavEntity navEntity = new NavEntity(mNavEntities.size(), navTitle, dataId);
        addNavEntity(navEntity);
    }

    /**
     * 增加导航元素，需要缓存数据
     * @param dataId        导航元素id，用于后续点击事件主键的获取
     * @param navTitle     导航元素名称
     * @param cacheData     缓存数据
     */
    public void addNavEntityWithCache(Object dataId, String navTitle, Object cacheData) {
        NavEntity navEntity = new NavEntity(mNavEntities.size(), navTitle, dataId, cacheData);
        addNavEntity(navEntity);
    }

    /**
     * 增加导航元素
     * @param navEntity     导航元素
     */
    private void addNavEntity(NavEntity navEntity) {
        if (mBarView.getVisibility() == GONE) {
            mBarView.setVisibility(VISIBLE);
        }
        mNavEntities.add(navEntity);
        mNavAdapter.setData(mNavEntities);
        mCurrEntity = navEntity;
        // TODO 如果滑动到0，则后续数据都不显示，原因未知
        if (navEntity.index > 0) {
            mNavRv.smoothScrollToPosition(navEntity.index);
        }
    }

    public int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 设置导航item的点击回调事件
     * @param itemClickCallback     导航item的点击回调事件
     */
    public void setNavItemClickCallback(OnNavItemClickCallback itemClickCallback) {
        this.clickCallback = itemClickCallback;
    }

    public interface OnNavItemClickCallback {

        /**
         * 导航栏点击事件
         * @param navEntity  当前点击的NavEntity
         */
        void onNavClick(NavEntity navEntity);
    }
}
