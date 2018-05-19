package com.jt.sharemap.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;

import com.jt.sharemap.R;
import com.jt.sharemap.ui.base.BaseActivity;
import com.jt.sharemap.ui.widget.FloatingActionMenu;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.fab_1)
    FloatingActionButton mFab_1;
    @BindView(R.id.fab_2)
    FloatingActionButton mFab_2;
    @BindView(R.id.nav_view)
    NavigationView mNavView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.fab_3)
    FloatingActionButton mFab3;
    @BindView(R.id.fab_menu)
    FloatingActionMenu mFabMenu;

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
        ft.add(R.id.fb_container, homeFragment).show(homeFragment).commitAllowingStateLoss();
    }


    @Override
    protected boolean initToolbar() {
        mToolbar.setTitle(R.string.app_name);
        mToolbar.setNavigationIcon(R.drawable.ic_menu_white_24dp);
        return true;
    }

    @Override
    protected void getIntent(Intent intent) {

    }

    @Override
    protected void initViews() {
        initFragments();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        ActionBarDrawerToggle mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar,
                0,
                0);
        mToggle.syncState();
        mDrawerLayout.addDrawerListener(mToggle);
    }
}
