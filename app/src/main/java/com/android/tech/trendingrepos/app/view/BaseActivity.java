package com.android.tech.trendingrepos.app.view;

import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import com.android.tech.trendingrepos.R;

import dagger.android.support.DaggerAppCompatActivity;

public abstract class BaseActivity extends DaggerAppCompatActivity{


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutRes());
        initView(savedInstanceState);
    }

    @LayoutRes
    public abstract int getLayoutRes();

    public abstract void initView(Bundle savedInstanceState);

    public void replaceFragment(Fragment fragment) {
        int enter = R.anim.slide_in_right;
        int exit = R.anim.slide_out_left;
        int pop_enter = R.anim.slide_in_left;
        int pop_exit = R.anim.slide_out_right;

        getSupportFragmentManager().beginTransaction()
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .replace(R.id.contentFrame, fragment, fragment.getClass().getSimpleName())
                .setCustomAnimations(enter, exit, pop_enter, pop_exit)
                .commitAllowingStateLoss();

        //Creates call to onPrepareOptionsMenu()
        supportInvalidateOptionsMenu();
    }




}
