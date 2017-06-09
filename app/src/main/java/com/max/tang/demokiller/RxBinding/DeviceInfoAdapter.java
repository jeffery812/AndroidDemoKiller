package com.max.tang.demokiller.RxBinding;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.max.tang.demokiller.R;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhihuitang on 2017-03-04.
 */

public class DeviceInfoAdapter extends RecyclerView.Adapter<DeviceInfoAdapter.InfoViewHolder> {
    private Context context;
    private List<DeviceInfo> mData = new ArrayList<>();

    public DeviceInfoAdapter(Context context) {
        this.context = context;
    }

    @Override public InfoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 实例化展示的view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_device_info_view, parent, false);
        // 实例化viewholder
        return new InfoViewHolder(v);

    }

    @Override public void onBindViewHolder(InfoViewHolder holder, int position) {
        DeviceInfo item = mData.get(position);
        holder.populate(item.getName(), item.getValue());
    }

    @Override public int getItemCount() {
        return mData.size();
    }

    void setData(List<DeviceInfo> data) {
        this.mData = data;
    }

    void clearData(){
        mData.clear();
        notifyDataSetChanged();
    }
    void addData(DeviceInfo deviceInfo) {

        mData.add(deviceInfo);
        notifyDataSetChanged();
    }

    static class InfoViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_name) TextView tvName;
        @BindView(R.id.tv_value) TextView tvValue;

        private InfoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void populate(final String name, final String value) {
            tvName.setText(name + "-");
            tvValue.setText(value);
        }
    }
}
