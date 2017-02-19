package com.max.tang.demokiller.mygithub.adapter;

import android.app.Activity;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.max.tang.demokiller.R;
import com.max.tang.demokiller.mygithub.activity.OnGithubRepoViewClickListener;
import com.max.tang.demokiller.mygithub.data.entities.GithubRepo;
import com.max.tang.demokiller.mygithub.view.GithubRepoViewHolder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhihuitang on 2017-02-19.
 */

public class GithubRecyclerAdapter extends RecyclerView.Adapter<GithubRepoViewHolder> {
    private List<GithubRepo> githubRepoList = new ArrayList<>();
    private Activity mMainActivity;

    private OnGithubRepoViewClickListener
        onGithubRepoViewClickListenerMaterial = new OnGithubRepoViewClickListener() {
        @Override
        public void onItemClick(GithubRepoViewHolder viewHolder, int repoPosition) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                //Intent intent = DetailsActivity.getIntent(mMainActivity, githubRepoList.get(repoPosition));
                //ActivityOptionsCompat option =
                //	ActivityOptionsCompat.makeSceneTransitionAnimation(mMainActivity,
                //		new Pair<View, String>(viewHolder.getFab(), "reveal"));
                //ActivityCompat.startActivity(mMainActivity, intent, option.toBundle());
            }
        }
    };
    public GithubRecyclerAdapter(Activity mMainActivity) {
        this.mMainActivity = mMainActivity;
    }

    @Override public GithubRepoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.cardview_githubrepo, parent, false);
        return new GithubRepoViewHolder(view, onGithubRepoViewClickListenerMaterial);

    }

    @Override public void onBindViewHolder(GithubRepoViewHolder holder, int position) {
        holder.updateViews(githubRepoList.get(position), position);
    }

    @Override public int getItemCount() {
        return githubRepoList.size();
    }

    public void setGithubRepoList(List<GithubRepo> githubRepoList) {
        this.githubRepoList = githubRepoList;
        notifyDataSetChanged();
    }
}
