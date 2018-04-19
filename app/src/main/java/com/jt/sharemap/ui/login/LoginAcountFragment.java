package com.jt.sharemap.ui.login;

import com.jt.sharemap.ui.base.BasePresenterActivity;

/**
 * <pre>
 *     @author : zhangjiantao
 *     e-mail : zhangjiantao@chehejia.com
 *     time   : 2018/04/19
 *     desc   :
 *     version: 1.0
 * </pre>
 */
public class LoginAcountFragment extends BasePresenterActivity<LoginAccountPresenter,LoginAcountContract.ILoginAcountView> implements LoginAcountContract.ILoginAcountView{


    @Override
    protected LoginAccountPresenter createPresenter() {
        return new LoginAccountPresenter();
    }

    @Override
    public void showError() {

    }

    @Override
    public void showEmpty() {

    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void initViews() {

    }
}
