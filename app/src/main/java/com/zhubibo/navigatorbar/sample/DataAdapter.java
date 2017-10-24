package com.zhubibo.navigatorbar.sample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Kevin on 2016/10/10 0:35.
 * Email: bibo.software@gmail.com
 */
public class DataAdapter extends BaseAdapter {

    private ArrayList<DataEntity> dataEntities;
    private Context context;

    public DataAdapter(ArrayList<DataEntity> dataEntities, Context context) {
        this.dataEntities = dataEntities;
        this.context = context;
    }

    public void setData(ArrayList<DataEntity> dataEntities) {
        this.dataEntities = dataEntities;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return dataEntities.size();
    }

    @Override
    public Object getItem(int position) {
        return dataEntities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adapter_data, parent, false);
        }
        ImageView typeIv = ViewHolderUtil.getView(convertView, R.id.dataTypeIv);
        TextView nameTv = ViewHolderUtil.getView(convertView, R.id.dataNameTv);

        DataEntity dataEntity = dataEntities.get(position);
        if (dataEntity == null) {
            return convertView;
        }
        if (dataEntity.dataType == 0) {
            typeIv.setImageResource(R.drawable.data_folder);
        } else {
            typeIv.setImageResource(R.drawable.data_file);
        }
        nameTv.setText(dataEntity.dataName);
        return convertView;
    }
}
