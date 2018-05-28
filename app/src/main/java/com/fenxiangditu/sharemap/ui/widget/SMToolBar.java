package com.fenxiangditu.sharemap.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import sharemap.R;import com.fenxiangditu.sharemap.ui.base.BaseActivity;
import com.fenxiangditu.sharemap.utils.DementionUtil;
import com.fenxiangditu.sharemap.utils.ViewUtil;


/**
 * <pre>
 *     desc   : 标题栏
 *     version: 1.0
 * </pre>
 */
public class SMToolBar extends RelativeLayout implements View.OnClickListener {

    private Context mContext;
    /**
     * 左侧退出按钮
     */
    private ImageView mIvLeft;
    /**
     * 中间标题
     */
    private TextView mTvMiddle;
    /**
     * 右侧文字
     */
    private TextView mTvRight;
    /**
     * 右侧按钮
     */
    private ImageView mIvRight;
    private NavOnClickListener navOnClickListener;
    /**
     * bar背景，bar的左测图标，bar title的颜色，bar back按钮的padding,bar title字体的大小，bar右侧多个控件的之间的间距
     */
    private int bcgroundResId,
            barNavigationIcon,
            barTitleTextColor,
            backPaddingDimen,
            barTitleTextSize,
            barMenuMargin;

    public SMToolBar(Context context) {
        super(context);
        this.mContext = context;
    }

    public SMToolBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init(attrs);
    }

    public SMToolBar(@NonNull Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init(attrs);
    }

    private void init(AttributeSet attrs) {

        View contentView =
                LayoutInflater.from(this.mContext).inflate(R.layout.layout_amp_toolbar, this, true);
        mIvLeft = (ImageView) contentView.findViewById(R.id.id_nav_left);
        mIvRight = (ImageView) contentView.findViewById(R.id.iv_nav_right);
        mTvMiddle = (TextView) contentView.findViewById(R.id.tv_middle);
        mTvRight = (TextView) contentView.findViewById(R.id.tv_nav_right);
        initAttr(attrs);
        mIvLeft.setOnClickListener(this);
        mTvRight.setOnClickListener(this);
    }

    private void initAttr(AttributeSet attrs) {
        TypedArray types = mContext.obtainStyledAttributes(attrs, R.styleable.nav_top_bar);
        try {
            barTitleTextColor =
                    types.getColor(
                            R.styleable.nav_top_bar_barTitleTextColor,
                            ContextCompat.getColor(mContext, R.color.black));
            barNavigationIcon =
                    types.getResourceId(R.styleable.nav_top_bar_barNavigationIcon, R.drawable.ic_back);
            backPaddingDimen =
                    types.getInteger(
                            R.styleable.nav_top_bar_barBackPaddingDimen,
                            getResources().getDimensionPixelSize(R.dimen.dp_15));
            barTitleTextSize = types.getInteger(R.styleable.nav_top_bar_barTitleTextSize, 18);
            barMenuMargin =
                    types.getInteger(
                            R.styleable.nav_top_bar_barMenuMargin, DementionUtil.dip2px(getContext(), 27));
        } finally {
            types.recycle(); // TypeArray用完需要recycle
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.id_nav_left:
                hintKeyboard();
                if (navOnClickListener != null) {
                    navOnClickListener.leftOnClickListener();
                } else {
                    if (mContext instanceof Activity) {
                        ((Activity) mContext).finish();
                    }
                }
                break;
            case R.id.tv_nav_right:
                if (navOnClickListener != null) {
                    navOnClickListener.rightOnClickListener();
                }
                break;
            case R.id.iv_nav_right:
                if (navOnClickListener != null) {
                    navOnClickListener.rightOnClickListener();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 隐藏键盘
     */
    private void hintKeyboard() {
        if (mContext instanceof BaseActivity) {
            final BaseActivity activity = (BaseActivity) mContext;
            final InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(this.getWindowToken(), 0);
            }
        }
    }

    /**
     * 设置标题栏点击
     *
     * @param onClickListener
     */
    public void setNavOnClickListener(NavOnClickListener onClickListener) {
        navOnClickListener = onClickListener;
    }

    public interface NavOnClickListener {
        /**
         * 左侧点击
         */
        void leftOnClickListener();

        /**
         * 右侧点击
         */
        void rightOnClickListener();
    }

    /**
     * 设置默认的左边按钮的背景图
     *
     * @param resId
     */
    public void setLeftImage(int resId) {
        mIvLeft.setImageResource(resId);
    }

    /**
     * 设置默认的右边按钮的背景图
     *
     * @param resId
     */
    public void setRightImage(int resId) {
        mIvRight.setImageResource(resId);
        ViewUtil.setViewVisibility(mIvRight, VISIBLE);
    }

    /**
     * 设置默认的右边标题
     *
     * @param resId
     */
    public void setRightTitle(int resId) {
        mTvRight.setText(getResources().getString(resId));
        ViewUtil.setViewVisibility(mTvRight, VISIBLE);
    }

    /**
     * 设置中间标题
     *
     * @param resId
     */
    public void setMiddleTitle(int resId) {
        mTvMiddle.setText(getResources().getString(resId));
    }


}
