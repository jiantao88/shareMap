package com.jt.sharemap.ui.home;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.widget.FrameLayout;

import com.jt.sharemap.R;
import com.jt.sharemap.ui.base.BaseActivity;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.container)
    FrameLayout mContainer;
    @BindView(R.id.fab_1)
    FloatingActionButton mFab_1;
    @BindView(R.id.fab_2)
    FloatingActionButton mFab_2;
    @BindView(R.id.nav_view)
    NavigationView mNavView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @Override
    protected void receiveEvent(Object object) {

    }

    @Override
    protected String registerEvent() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    private void initFragments() {
        Fragment homeFragment = new HomeFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.container,homeFragment ).show(homeFragment).commitAllowingStateLoss();
    }


    @Override
    protected boolean initToolbar() {
        return false;
    }

    @Override
    protected void getIntent(Intent intent) {

    }

    @Override
    protected void initViews() {
        initFragments();
    }
}
