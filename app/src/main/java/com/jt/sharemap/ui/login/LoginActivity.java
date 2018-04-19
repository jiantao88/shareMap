package com.jt.sharemap.ui.login;


import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.jt.sharemap.R;
import com.jt.sharemap.common.Const;
import com.jt.sharemap.event.Event;
import com.jt.sharemap.event.RxEvent;
import com.jt.sharemap.ui.base.BasePresenterActivity;
import com.jt.sharemap.utils.ToastUtils;
import com.orhanobut.logger.Logger;

import java.util.Objects;

import butterknife.BindView;

/**
 * @author zhangjiantao
 */
public class LoginActivity extends BasePresenterActivity<LoginPresenter, LoginContract.ILoginRegisterView> implements LoginContract.ILoginRegisterView {


    @BindView(R.id.layout_tab_login)
    TabLayout mLayoutTabLogin;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;


    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @Override
    public void showResult(String msg) {
        ToastUtils.showToast(this, msg);
        RxEvent.getInstance().postEvent(Const.EVENT_ACTION.REFRESH_DATA, new Event(Event.Type.LIST, null));
        finish();
    }

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

        mLayoutTabLogin.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                Logger.d("onTabSelected position:" + tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                Logger.d("onTabUnselected position:" + tab.getPosition());
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                Logger.d("onTabReselected");
            }
        });
    }
}

