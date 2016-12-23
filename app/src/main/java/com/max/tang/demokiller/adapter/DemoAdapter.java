package com.max.tang.demokiller.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.max.tang.demokiller.R;
import com.max.tang.demokiller.databinding.ItemDemoBinding;
import com.max.tang.demokiller.fragment.OnFragmentInteractionListener;
import com.max.tang.demokiller.model.DemoEntity;
import com.max.tang.demokiller.model.RecycleViewDemoListener;
import com.max.tang.demokiller.utils.log.Logger;

import java.util.List;

/**
 * Created by zhihuitang on 2016-11-01.
 */

public class DemoAdapter extends RecyclerView.Adapter<DemoAdapter.DemoViewHolder> {

    private OnFragmentInteractionListener mListener;
    private List<DemoEntity> mDemoEntityList;

    public DemoAdapter(OnFragmentInteractionListener eventHandler, List<DemoEntity> demoEntities) {
        this.mListener = eventHandler;
        this.mDemoEntityList = demoEntities;
    }

    @Override
    public DemoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //return new DemoViewHolder(LayoutInflater.from(mActivity).inflate(R.layout.item_demo, parent, false));
        ItemDemoBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_demo, parent, false);
        return new DemoViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(final DemoViewHolder holder, final int position) {
        holder.bindConnection(mDemoEntityList.get(position));
        holder.binding.setListener(new RecycleViewDemoListener() {
            @Override
            public void demoClicked(View view) {
                Logger.d("demo item clicked: " + mDemoEntityList.get(position).getDescription());
                if( mListener != null ) {
                    mListener.startDemo(mDemoEntityList.get(position).getClassName());
                }
            }
        });
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

    class DemoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ItemDemoBinding binding;

        private DemoViewHolder(ItemDemoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        private void bindConnection(DemoEntity demo) {
            binding.setDemo(demo);
        }

        @Override
        public void onClick(View view) {
            Logger.d("DemoViewHolder onClick event");
        }
    }
}
