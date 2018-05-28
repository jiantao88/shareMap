package com.jt.sharemap.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;

import com.jt.sharemap.R;
import com.jt.sharemap.ui.base.BaseActivity;
import com.jt.sharemap.ui.widget.FabTagLayout;
import com.jt.sharemap.ui.widget.FloatingActionButtonPlus;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.fab_1)
    FabTagLayout mFab_1;
    @BindView(R.id.nav_view)
    NavigationView mNavigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.fab_2)
    FabTagLayout mFab2;
    @BindView(R.id.fab_menu)
    FloatingActionButtonPlus mFabMenu;

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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu_setting,menu);
        return super.onCreateOptionsMenu(menu);
    }
    //Menu点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_search:
                startActivity(new Intent(this, SearchActivity.class));
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
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
        mNavigationView.setItemIconTintList(null);
        mNavigationView.setNavigationItemSelectedListener(onNavigationItemSelectedListener);
    }


    //设置侧滑item click
    private NavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener = new NavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem item) {
            switch (item.getItemId()) {

            }
            return true;
        }
    };
}
