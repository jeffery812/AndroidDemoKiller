package com.max.tang.demokiller.mygithub.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.max.tang.demokiller.R;
import com.max.tang.demokiller.mygithub.adapter.GithubRecyclerAdapter;
import com.max.tang.demokiller.mygithub.data.RestAdapter;
import com.max.tang.demokiller.mygithub.data.entities.GithubRepo;
import java.util.List;

public class MyGithubActivity extends AppCompatActivity implements MainView {
    private MainPresenter mainPresenter;
    private GithubRecyclerAdapter adapter;


    @BindView(R.id.activity_main_toolbar) Toolbar mToolbar;
    @BindView(R.id.sample_list) RecyclerView mRecyclerView;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_github);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

        ButterKnife.bind(this);

        adapter = new GithubRecyclerAdapter(this);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(adapter);
        mainPresenter = new MainPresenterImpl(this);
        mainPresenter.subscribe();
        mainPresenter.loadData();
    }

    @Override public void loadData(List<GithubRepo> githubRepoList) {
        adapter.setGithubRepoList(githubRepoList);
        mToolbar.setTitle("Github" + RestAdapter.Nodes.username);
    }
}
