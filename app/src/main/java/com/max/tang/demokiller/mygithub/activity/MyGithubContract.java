package com.max.tang.demokiller.mygithub.activity;

import com.max.tang.demokiller.BasePresenter;
import com.max.tang.demokiller.BaseView;
import com.max.tang.demokiller.mygithub.data.entities.GithubRepo;
import java.util.List;

/**
 * Created by zhihuitang on 2017-03-22.
 */

public interface MyGithubContract {
    interface Presenter extends BasePresenter {
        void unsubscribe();

        void loadData();

        void subscribe();
    }

    interface View extends BaseView<Presenter> {
        void dataLoaded(List< GithubRepo > githubRepoList);
    }
}
