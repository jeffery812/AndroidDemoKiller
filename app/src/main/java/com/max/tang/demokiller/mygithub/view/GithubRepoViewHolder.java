package com.max.tang.demokiller.mygithub.view;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.max.tang.demokiller.R;
import com.max.tang.demokiller.mygithub.activity.OnGithubRepoViewClickListener;
import com.max.tang.demokiller.mygithub.data.entities.GithubRepo;

/**
 * Created by zhihuitang on 2017-02-19.
 */

public class GithubRepoViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.cardview_github_tv_header) TextView mTvHeader;
    @BindView(R.id.cardview_github_tv_stars) TextView mTvStars;
    @BindView(R.id.cardview_github_tv_watchers) TextView mTvWatchers;
    @BindView(R.id.cardview_github_tv_forks) TextView mTvForks;
    //@Bind(R.id.activity_details_vw_circle) View mFab;

    @BindString(R.string.stars) String mStars;
    @BindString(R.string.watchers) String mWatchers;
    @BindString(R.string.forks) String mForks;

    private int currentPosition;


    public GithubRepoViewHolder(View itemView) {
        super(itemView);
    }

    public GithubRepoViewHolder(View itemView,
        OnGithubRepoViewClickListener onGithubRepoViewClickListenerMaterial) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void updateViews(GithubRepo githubRepo, int position) {
        currentPosition = position;
        new Handler(Looper.getMainLooper()).post(() -> {
            mTvHeader.setText(githubRepo.getName());
            mTvStars.setText(mStars + " " + githubRepo.getStargazersCount());
            mTvWatchers.setText(mWatchers + " " + githubRepo.getWatchersCount());
            mTvForks.setText(mForks + " " + githubRepo.getForksCount());
        });
    }
}
