package com.android.tech.trendingrepos.repofeature.model.remotedata;

import com.android.tech.trendingrepos.app.utils.Constants;
import com.android.tech.trendingrepos.repofeature.model.pojo.GitHubRepo;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface RemoteApiService {

    @GET(Constants.REMOTE_TRENDING_REPO_URI)
    Single<List<GitHubRepo>> getRemoteTrendingRepos();
}
