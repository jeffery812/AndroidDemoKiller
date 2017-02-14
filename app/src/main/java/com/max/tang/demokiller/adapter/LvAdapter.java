package com.max.tang.demokiller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.max.tang.demokiller.R;
import com.max.tang.demokiller.model.TestBean;
import com.max.tang.demokiller.utils.log.Logger;
import java.util.List;
import com.bumptech.glide.Glide;

/**
 * Created by zhihuitang on 2017-02-14.
 */

public class LvAdapter extends BaseAdapter {
    final String TAG = "ListViewDemo";
    private List<TestBean> mDatas;
    private Context mContext;
    private LayoutInflater mInflater;

    public LvAdapter(List<TestBean> mDatas, Context mContext) {
        this.mDatas = mDatas;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return null != mDatas ? mDatas.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return null != mDatas ? mDatas.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Logger.d(TAG, "嵌套第1层的 getView() called with: position = [" + position + "], convertView = [" + convertView + "], parent = [" + parent + "]");
        LvViewHolder holder;
        if (null == convertView) {
            convertView = mInflater.inflate(R.layout.item_list_view, parent, false);
            holder = new LvViewHolder();
            holder.tv = (TextView) convertView.findViewById(R.id.tv);
            holder.iv = (ImageView) convertView.findViewById(R.id.iv);
            convertView.setTag(holder);
        } else {
            holder = (LvViewHolder) convertView.getTag();
        }
        TestBean testBean = mDatas.get(position);
        holder.tv.setText(testBean.getName());
        Glide.with(mContext)
            .load(testBean.getUrl())
            .into(holder.iv);

        return convertView;
    }

    private static class LvViewHolder {
        TextView tv;
        ImageView iv;
    }

}