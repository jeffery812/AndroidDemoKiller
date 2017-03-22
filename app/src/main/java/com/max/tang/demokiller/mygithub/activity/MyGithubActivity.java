package com.max.tang.demokiller.mygithub.activity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.max.tang.demokiller.R;
import com.max.tang.demokiller.mygithub.adapter.GithubRecyclerAdapter;
import com.max.tang.demokiller.mygithub.data.RestAdapter;
import com.max.tang.demokiller.mygithub.data.entities.GithubRepo;
import de.hdodenhof.circleimageview.CircleImageView;
import java.util.List;

public class MyGithubActivity extends AppCompatActivity implements MyGithubContract.View {
    private MyGithubContract.Presenter presenter;
    private GithubRecyclerAdapter adapter;


    @BindView(R.id.activity_main_ctl) CollapsingToolbarLayout mCollapsingToolBarLayout;
    @BindView(R.id.activity_main_toolbar) Toolbar mToolbar;
    @BindView(R.id.sample_list) RecyclerView mRecyclerView;
    @BindView(R.id.activity_main_civ) CircleImageView mAvatarImage;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_github);

        ButterKnife.bind(this);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mToolbar.setNavigationIcon(R.mipmap.ic_drawer_home);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        /*
        fab.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null)
                    .show();
            }
        });

         */
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
            .setAction("Action", null)
            .show());


        adapter = new GithubRecyclerAdapter(this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);
        presenter = new MainPresenterImpl(this);
        presenter.subscribe();
        presenter.loadData();
    }

    @Override public void dataLoaded(List<GithubRepo> githubRepoList) {
        adapter.setGithubRepoList(githubRepoList);
        //mToolbar.setTitle("Github" + RestAdapter.Nodes.username);
        // http://www.jianshu.com/p/2e135628e0fa
        mCollapsingToolBarLayout.setTitle("Github" + RestAdapter.Nodes.username);
        mCollapsingToolBarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(this, R.color.colorWhite));
        mCollapsingToolBarLayout.setExpandedTitleColor(ContextCompat.getColor(this, R.color.colorGuide3));
        if( githubRepoList != null && !githubRepoList.isEmpty() ) {
            Glide.with(this).load(githubRepoList.get(0).getOwner().getAvatar_url()).into(mAvatarImage);
        }
    }

    @Override public void setPresenter(MyGithubContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override public void initViews(View view) {

    }
}
