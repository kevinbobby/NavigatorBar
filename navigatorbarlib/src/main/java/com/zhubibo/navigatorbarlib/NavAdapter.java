package com.zhubibo.navigatorbarlib;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by ZhuBibo on 2015/6/23.
 */
public class NavAdapter extends RecyclerView.Adapter<NavAdapter.NavViewHolder> {

    private ArrayList<NavEntity> mNavEntities;
    private Drawable navDivider;
    private int navTextSize;
    private int navTextColor;

    private OnNavItemClickListener navItemClickListener;

    public NavAdapter(ArrayList<NavEntity> navEntities, Drawable navDivider, int navTextSize, int navTextColor) {
        super();
        this.mNavEntities = navEntities;
        this.navDivider = navDivider;
        this.navTextSize = navTextSize;
        this.navTextColor = navTextColor;
    }

    public void setData(ArrayList<NavEntity> navEntities) {
        this.mNavEntities = navEntities;
        notifyDataSetChanged();
    }

    public void setNavItemClickListener(OnNavItemClickListener navItemClickListener) {
        this.navItemClickListener = navItemClickListener;
    }

    @Override
    public NavViewHolder onCreateViewHolder(ViewGroup viewGroup, int id) {
        View rootView = View.inflate(viewGroup.getContext(), R.layout.adapter_nav, null);
        return new NavViewHolder(rootView, navDivider, navTextSize, navTextColor);
    }

    @Override
    public void onBindViewHolder(NavViewHolder viewHolder, int position) {
        final NavEntity navEntity = mNavEntities.get(position);
        if(navEntity != null) {
            if (position == mNavEntities.size() - 1) {
                viewHolder.navLineIv.setVisibility(View.GONE);
            } else {
                viewHolder.navLineIv.setVisibility(View.VISIBLE);
            }
            viewHolder.navTitle.setText(navEntity.navTitle);
            viewHolder.navTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    navItemClickListener.onNavItemClick(navEntity);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mNavEntities.size();
    }

    static class NavViewHolder extends RecyclerView.ViewHolder {

        TextView navTitle;
        ImageView navLineIv;

        NavViewHolder(View itemView, Drawable navDivider, int navTextSize, int navTextColor) {
            super(itemView);
            navTitle = itemView.findViewById(R.id.navTitleTv);
            navTitle.setTextColor(navTextColor);
            navTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, navTextSize);
            navLineIv = itemView.findViewById(R.id.navLineIv);
            if (navDivider != null) {
                navLineIv.setImageDrawable(navDivider);
            }
        }
    }

    public interface OnNavItemClickListener {

        void onNavItemClick(NavEntity navEntity);
    }
}
