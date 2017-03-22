package com.max.tang.demokiller.mygithub.activity;

import com.max.tang.demokiller.mygithub.data.ApiManager;
import com.max.tang.demokiller.mygithub.data.entities.GithubRepo;
import java.util.List;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainPresenterImpl implements MyGithubContract.Presenter {

	private ApiManager apiManager;
	private MyGithubContract.View view;

	public MainPresenterImpl(MyGithubContract.View view) {
		this.view = view;
	}

	@Override
	public void unsubscribe() {

	}

	@Override
	public void loadData() {
		apiManager.getGithubRepos(new Callback<List<GithubRepo>>() {
			@Override
			public void success(List<GithubRepo> githubRepos, Response response) {
				view.dataLoaded(githubRepos);
			}

			@Override
			public void failure(RetrofitError error) {

			}
		});
	}

	@Override
	public void subscribe() {
		apiManager = new ApiManager();
	}

	@Override public void start() {

	}
}
