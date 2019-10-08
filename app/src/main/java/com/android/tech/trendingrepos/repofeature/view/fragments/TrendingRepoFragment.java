package com.android.tech.trendingrepos.repofeature.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.tech.trendingrepos.R;
import com.android.tech.trendingrepos.app.di.viewmodel.AppViewModelFactory;
import com.android.tech.trendingrepos.app.utils.network.OnlineChecker;
import com.android.tech.trendingrepos.app.view.BaseFragment;
import com.android.tech.trendingrepos.repofeature.model.localdata.entities.TrendingRepoEntity;
import com.android.tech.trendingrepos.repofeature.view.GitUiModel;
import com.android.tech.trendingrepos.repofeature.view.adapters.TrendingRepoAdapter;
import com.android.tech.trendingrepos.repofeature.viewmodel.TrendingRepoViewModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class TrendingRepoFragment extends BaseFragment {

    private ConstraintLayout layout_error;
    private ConstraintLayout layout_empty;
    private ConstraintLayout layout_loading;
    private MaterialButton button_retry;

    private RecyclerView recycler_view;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TrendingRepoAdapter mAdapter;
    private ArrayList<TrendingRepoEntity> trendingRepos;

    private TrendingRepoViewModel mViewModel;

    @Inject
    public AppViewModelFactory mViewModelFactory;

    private CompositeDisposable mSubscription;

    @Inject
    public OnlineChecker mOnlineChecker;

    @Inject
    public TrendingRepoFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSubscription = new CompositeDisposable();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.trending_repo_frag, container, false);
        initLayouts(root);
        return root;
    }

    private void initLayouts(View root) {
        layout_error = root.findViewById(R.id.layout_error);
        layout_empty = root.findViewById(R.id.layout_empty);
        button_retry = root.findViewById(R.id.button_retry);
        layout_loading = root.findViewById(R.id.layout_loading);

        recycler_view = root.findViewById(R.id.recycler_view);
        swipeRefreshLayout = root.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(this::swipeRefresh);

        trendingRepos = new ArrayList<>();
        mAdapter = new TrendingRepoAdapter(getActivity(), trendingRepos);
        recycler_view.setAdapter(mAdapter);

        button_retry.setOnClickListener(v -> {
            hideZeroStateViews();
            fetchTrendingRepo(false);
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(TrendingRepoViewModel.class);
        fetchTrendingRepo(false);
    }

    private void swipeRefresh(){
        fetchTrendingRepo(true);
    }

    private void fetchTrendingRepo(boolean isForcedCall) {
        showLoader(isForcedCall);

        if (!mOnlineChecker.isOnline()) {
            Snackbar.make(layout_loading, R.string.offline_data, Snackbar.LENGTH_SHORT).show();
        }

        mViewModel.getTrendingRepo(isForcedCall)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<GitUiModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mSubscription.add(d);
                    }

                    @Override
                    public void onSuccess(GitUiModel trendingreposModel) {
                        hideLoader(isForcedCall);
                        inflateData(trendingreposModel);
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideLoader(isForcedCall);
                        showError();
                    }
                });
    }

    private void inflateData(GitUiModel data) {
        if(data!=null && data.getmRepoList()!=null
            && data.getmRepoList().size()>0) {
            trendingRepos.clear();
            trendingRepos.addAll(data.getmRepoList());
            mAdapter.notifyDataSetChanged();
        }else{
            showEmptyView();
        }
    }

    private void showEmptyView() {
        layout_empty.setVisibility(View.VISIBLE);
    }

    private void showError() {
        layout_error.setVisibility(View.VISIBLE);
    }

    private void hideZeroStateViews(){
        layout_error.setVisibility(View.GONE);
        layout_empty.setVisibility(View.GONE);
    }


    private void showLoader(boolean isForcedCall) {
        if(!isForcedCall)
            layout_loading.setVisibility(View.VISIBLE);
    }

    private void hideLoader(boolean isForcedCall) {
        swipeRefreshLayout.setRefreshing(false);
        if(!isForcedCall)
            layout_loading.setVisibility(View.GONE);

    }

    @Override
    public void onDestroy() {
        mSubscription.dispose();
        super.onDestroy();
    }
}
