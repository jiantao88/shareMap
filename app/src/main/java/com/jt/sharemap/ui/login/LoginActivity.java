package com.jt.sharemap.ui.login;


import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import com.jt.sharemap.R;
import com.jt.sharemap.presenter.BasePresenter;
import com.jt.sharemap.ui.base.BasePresenterActivity;
import com.jt.sharemap.utils.KeyBoardUtil;

import java.util.Objects;

import butterknife.BindView;

/**
 * @author zhangjiantao
 */
public class LoginActivity extends BasePresenterActivity {


    @BindView(R.id.layout_tab_login)
    TabLayout mLayoutTabLogin;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.fl_containt)
    FrameLayout mFlContaint;
    private LoginSmsFragment mLoginSmsFragment;
    private LoginAcountFragment mLoginAcountFragment;
    private FragmentManager mFragmentManager;

    public static final String FRAGMENT_TAG_ACCOUNT = "accountfragment";
    public static final String FRAGMENT_TAG_SMS = "smsfragment";

    @Override
    public void showError() {

    }

    @Override
    public void showEmpty() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onNavigationClick();
            }
        });
        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("登录");
    }

    @Override
    protected void initViews() {
        mLoginAcountFragment = new LoginAcountFragment();
        mLoginSmsFragment = new LoginSmsFragment();
        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.beginTransaction()
                .add(R.id.fl_containt, mLoginAcountFragment, FRAGMENT_TAG_ACCOUNT)
                .commit();
        mLayoutTabLogin.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment fragmentById = mFragmentManager.findFragmentById(R.id.fl_containt);
                if (fragmentById.getTag().equals(FRAGMENT_TAG_ACCOUNT)){
                    mFragmentManager.beginTransaction()
                            .replace(R.id.fl_containt, mLoginSmsFragment, FRAGMENT_TAG_SMS)
                            .commit();
                }else {
                    mFragmentManager.beginTransaction()
                            .replace(R.id.fl_containt, mLoginAcountFragment, FRAGMENT_TAG_ACCOUNT)
                            .commit();
                }
                KeyBoardUtil.toggle(LoginActivity.this);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}

