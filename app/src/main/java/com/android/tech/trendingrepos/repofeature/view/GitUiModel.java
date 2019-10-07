package com.android.tech.trendingrepos.repofeature.view;

import com.android.tech.trendingrepos.repofeature.model.localdata.entities.TrendingRepoEntity;

import java.util.List;

public class GitUiModel {

    private List<TrendingRepoEntity> mRepoList ;

    public GitUiModel(List<TrendingRepoEntity> mRepoList) {
        this.mRepoList = mRepoList;
    }

    public List<TrendingRepoEntity> getmRepoList() {
        return mRepoList;
    }

    public void setmRepoList(List<TrendingRepoEntity> mRepoList) {
        this.mRepoList = mRepoList;
    }
}
