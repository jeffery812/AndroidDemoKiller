package com.max.tang.demokiller.mygithub.activity;

import com.max.tang.demokiller.mygithub.data.ApiManager;
import com.max.tang.demokiller.mygithub.data.entities.GithubRepo;
import java.util.List;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainPresenterImpl implements MainPresenter {

	private ApiManager apiManager;
	private MainView mainView;

	public MainPresenterImpl(MainView mainView) {
		this.mainView = mainView;
	}

	@Override
	public void unsubscribe() {

	}

	@Override
	public void loadData() {
		apiManager.getGithubRepos(new Callback<List<GithubRepo>>() {
			@Override
			public void success(List<GithubRepo> githubRepos, Response response) {
				mainView.loadData(githubRepos);
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
}
