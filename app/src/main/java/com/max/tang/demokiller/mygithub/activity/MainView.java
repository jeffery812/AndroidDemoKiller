package com.max.tang.demokiller.mygithub.activity;

import com.max.tang.demokiller.mygithub.data.entities.GithubRepo;
import java.util.List;

public interface MainView {
	void loadData(List<GithubRepo> githubRepoList);
}
