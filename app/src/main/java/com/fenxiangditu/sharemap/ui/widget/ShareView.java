package com.fenxiangditu.sharemap.ui.widget;

import android.app.Activity;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import sharemap.R;


public class ShareView implements View.OnClickListener {
  private Activity mContext;

  private PopupWindow mPopWindow;
  private View mSharePopView;

  private TextView mTvShareWeixin;
  private TextView mTvShareFriends;
  private TextView mTvShareQq;
  private TextView mTvShareQzone;
  private TextView mTvShareCopy;
  private TextView mTvShareWeibo;
  private TextView mTvPopShareCancel;

  /** 设置popwindow的背景 */
  private int backgroundPop = 0xa0000000;

  public ShareView(Activity context) {
    this.mContext = context;

    findView();

    setListener();

    initSharePop();
  }

  private void findView() {
    LayoutInflater layoutInflater = LayoutInflater.from(mContext);
    mSharePopView = layoutInflater.inflate(R.layout.pop_share, null);

    mTvPopShareCancel = (TextView) mSharePopView.findViewById(R.id.tv_pop_share_cancel);
    mTvShareCopy = (TextView) mSharePopView.findViewById(R.id.tv_share_copy);
    mTvShareFriends = (TextView) mSharePopView.findViewById(R.id.tv_share_friends);
    mTvShareQq = (TextView) mSharePopView.findViewById(R.id.tv_share_qq);
    mTvShareQzone = (TextView) mSharePopView.findViewById(R.id.tv_share_qzone);
    mTvShareWeibo = (TextView) mSharePopView.findViewById(R.id.tv_share_weibo);
    mTvShareWeixin = (TextView) mSharePopView.findViewById(R.id.tv_share_weixin);
  }

  private void setListener() {
    mTvShareWeixin.setOnClickListener(this);
    mTvShareCopy.setOnClickListener(this);
    mTvPopShareCancel.setOnClickListener(this);
    mTvShareFriends.setOnClickListener(this);
    mTvShareQq.setOnClickListener(this);
    mTvShareQzone.setOnClickListener(this);
    mTvShareWeibo.setOnClickListener(this);
  }

  private void initSharePop() {

    mPopWindow =
        new PopupWindow(
            mSharePopView,
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT);
    mPopWindow.setAnimationStyle(R.style.AnimBottom);
    mPopWindow.setTouchable(true);
    mPopWindow.setOutsideTouchable(false);
    mPopWindow.setFocusable(true);
    mPopWindow.setOnDismissListener(
        new PopupWindow.OnDismissListener() {
          @Override
          public void onDismiss() {
            Handler h = new Handler();
            h.postDelayed(
                new Runnable() {
                  @Override
                  public void run() {
                    WindowManager.LayoutParams lp = mContext.getWindow().getAttributes();
                    lp.alpha = 1f;
                    mContext.getWindow().setAttributes(lp);
                  }
                },
                500);
          }
        });
  }

  public void showSharePop() {
    if (!isShow()) {
      WindowManager.LayoutParams lp = mContext.getWindow().getAttributes();
      lp.alpha = 0.5f;
      mContext.getWindow().setAttributes(lp);
      mPopWindow.showAtLocation(mSharePopView, Gravity.BOTTOM, 0, 0);
    }
  }

  public void hideSharePop() {
    if (isShow()) {
      mPopWindow.dismiss();
    }
  }

  public boolean isShow() {
    return mPopWindow.isShowing();
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.tv_pop_share_cancel:
        hideSharePop();
        break;
      case R.id.tv_share_copy:
        break;
      case R.id.tv_share_friends:
        break;
      case R.id.tv_share_qq:
        break;
      case R.id.tv_share_qzone:
        break;
      case R.id.tv_share_weibo:
        break;
    }
  }
}
