package com.zhubibo.navigatorbar.sample;

import android.util.SparseArray;
import android.view.View;

/**
 * Created by Kevin on 2015/9/8.
 * ViewHolder简洁写法
 */
public class ViewHolderUtil {

	/**
	 * 用法： ImageView bananaView = ViewHolder.get(convertView, R.id.banana);
	 * 
	 * @param convertView
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T extends View> T getView(View convertView, int id) {
		SparseArray<View> viewHolder = (SparseArray<View>) convertView.getTag();
		if (viewHolder == null) {
			viewHolder = new SparseArray<View>();
			convertView.setTag(viewHolder);
		}
		View childView = viewHolder.get(id);
		if (childView == null) {
			childView = convertView.findViewById(id);
			viewHolder.put(id, childView);
		}
		return (T) childView;
	}

}
