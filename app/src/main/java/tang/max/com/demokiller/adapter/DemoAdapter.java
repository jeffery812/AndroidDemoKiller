package tang.max.com.demokiller.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import tang.max.com.demokiller.R;
import tang.max.com.demokiller.model.DemoEntity;
import tang.max.com.demokiller.utils.log.Logger;

/**
 * Created by zhihuitang on 2016-11-01.
 */

public class DemoAdapter extends RecyclerView.Adapter<DemoAdapter.DemoViewHolder> {

    private Activity mActivity;
    private List<DemoEntity> mDemoEntityList;

    public DemoAdapter(AppCompatActivity activity, List<DemoEntity> demoEntities) {
        this.mActivity = activity;
        this.mDemoEntityList = demoEntities;
    }

    @Override
    public DemoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new DemoViewHolder(LayoutInflater.from(mActivity).inflate(R.layout.item_demo, parent, false));
    }

    @Override
    public void onBindViewHolder(DemoViewHolder holder, int position) {

        final DemoEntity demoEntity = mDemoEntityList.get(position);
        holder.mTextView.setText(demoEntity.getDescription());
        holder.mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mActivity, demoEntity.getClassName());
                mActivity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        Logger.d("RecyclerView size: " + mDemoEntityList.size());
        return mDemoEntityList.size();
    }

    public void addAll(List<DemoEntity> items) {
        int pos = getItemCount();
        mDemoEntityList.addAll(items);
        //notifyDataSetChanged();
        notifyItemRangeInserted(pos, mDemoEntityList.size());
    }

    class DemoViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_test_title)
        TextView mTextView;
        View mItemView;
        public DemoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.mItemView = itemView;
        }
    }
}
