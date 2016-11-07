package com.max.tang.demokiller.adapter;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.max.tang.demokiller.R;
import com.max.tang.demokiller.databinding.ItemDemoBinding;
import com.max.tang.demokiller.model.DemoEntity;

import java.util.List;

/**
 * Created by zhihuitang on 2016-11-01.
 */

public class DemoAdapter extends RecyclerView.Adapter<DemoAdapter.DemoViewHolder> {

    private Activity mActivity;
    private List<DemoEntity> mDemoEntityList;

    public DemoAdapter(Activity activity, List<DemoEntity> demoEntities) {
        this.mActivity = activity;
        this.mDemoEntityList = demoEntities;
    }

    @Override
    public DemoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //return new DemoViewHolder(LayoutInflater.from(mActivity).inflate(R.layout.item_demo, parent, false));
        ItemDemoBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_demo, parent, false);
        return new DemoViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(DemoViewHolder holder, int position) {
        holder.bindConnection(mDemoEntityList.get(position));
    }

    @Override
    public int getItemCount() {
        return mDemoEntityList.size();
    }

    public void addAll(List<DemoEntity> items) {
        int pos = getItemCount();
        mDemoEntityList.addAll(items);
        //notifyDataSetChanged();
        notifyItemRangeInserted(pos, mDemoEntityList.size());
    }

    class DemoViewHolder extends RecyclerView.ViewHolder {

       private ItemDemoBinding binding;

        public DemoViewHolder(ItemDemoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindConnection(DemoEntity demo){
            binding.setDemo(demo);
        }
    }
}
