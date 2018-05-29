package com.fenxiangditu.sharemap.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fenxiangditu.sharemap.application.AppContext;
import com.fenxiangditu.sharemap.manager.UserInfoManager;
import com.fenxiangditu.sharemap.net.bean.LoginBean;
import com.fenxiangditu.sharemap.ui.base.BaseActivity;
import com.fenxiangditu.sharemap.ui.login.LoginActivity;
import com.fenxiangditu.sharemap.ui.widget.FabTagLayout;
import com.fenxiangditu.sharemap.ui.widget.FloatingActionButtonPlus;
import com.fenxiangditu.sharemap.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import sharemap.R;

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
    private TextView mNameView;
    private ImageView mAvatarView;

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

    private void initNavigationHeaderView() {
        View mHeaderView = mNavigationView.getHeaderView(0);
        mAvatarView = (ImageView) mHeaderView.findViewById(R.id.iv_nav_header);
        mNameView = (TextView) mHeaderView.findViewById(R.id.tv_nav_header);
    }

    protected void initViews() {
        initFragments();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUserData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initViews();
        ActionBarDrawerToggle mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar,
                0,
                0);
        mToggle.syncState();
        mDrawerLayout.addDrawerListener(mToggle);
        mNavigationView.setItemIconTintList(null);
        mNavigationView.setNavigationItemSelectedListener(onNavigationItemSelectedListener);
        initNavigationHeaderView();

    }
    private void setUserData() {
        if (UserInfoManager.isLogin()) {
            LoginBean userBean = UserInfoManager.getUserInfo();
//            if (userBean != null) {
//                mNameView.setText(userBean.getUsername());
//                GlideLoaderManager.loadImage(userBean.getIcon(), mAvatarView, Const.IMAGE_LOADER.HEAD_IMG);
//            }
        } else {
            mNameView.setText("未登录");
        }
        mNameView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!UserInfoManager.isLogin()){
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }
            }
        });
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

    private long mExitTime;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {

            if (mDrawerLayout.isDrawerOpen(Gravity.START)) {
                mDrawerLayout.closeDrawer(Gravity.START);
                return true;
            }

            if (System.currentTimeMillis() - mExitTime < 2000) {
                finish();
            } else {
                mExitTime = System.currentTimeMillis();
                ToastUtils.showToast(AppContext.getContext(), "请再按一次退出程序");
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
