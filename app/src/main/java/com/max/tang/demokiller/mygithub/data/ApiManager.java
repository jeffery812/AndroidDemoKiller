package com.max.tang.demokiller.mygithub.data;

import com.max.tang.demokiller.mygithub.Constants;
import com.max.tang.demokiller.mygithub.data.entities.GithubRepo;
import com.max.tang.demokiller.mygithub.data.entities.OwnerResponse;
import java.util.List;
import retrofit.Callback;
import retrofit.RequestInterceptor;
import retrofit.android.AndroidLog;

public class ApiManager implements RestAdapter {

	private RestAdapter restAdapter;
	private RequestInterceptor requestInterceptor;

	public ApiManager() {
		requestInterceptor = request -> request.addHeader(Constants.USER_AGENT, "zhihuitang");
		retrofit.RestAdapter restAdapter = initRestAdapter();
		this.restAdapter = restAdapter.create(RestAdapter.class);
	}

	private retrofit.RestAdapter initRestAdapter() {
		return new retrofit.RestAdapter.Builder().
			setLogLevel(retrofit.RestAdapter.LogLevel.FULL).
			setLog(new AndroidLog("TEST")).
			setRequestInterceptor(requestInterceptor).
			setEndpoint(Constants.REST_ENDPOINT).
			build();
	}

	@Override
	public void getGithubRepos(Callback<List<GithubRepo>> callback) {
		restAdapter.getGithubRepos(callback);
	}

	@Override
	public void getOwner(Callback<OwnerResponse> callback) {
		restAdapter.getOwner(callback);
	}
}
