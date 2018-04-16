package com.jt.sharemap.view;

/**
 */

public interface IView {


    /**
     * 显示进度条
     *
     */
    void showLoading(String msg);

    /**
     * 隐藏进度条
     */
    void hideLoading();

    /**
     * 失败
     *
     * @param msg
     */
    void showFail(String msg);

    /**
     * 错误
     */
    void showError();

    /**
     * 没有数据
     */
    void showEmpty();//没有数据

    void collect(boolean isCollect, String result); //收藏Result


}
