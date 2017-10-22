package com.dy.baseutils.module.base.view;


/**
 * Auth : dy
 * Time : 2017/2/6 11:50
 * Email: dymh21342@163.com
 * Description:
 */

public interface IBaseView {
    /**
     * 显示加载中
     */
    void showLoading();

    /**
     * 显示异常
     */
    void showError(String msg);

    /**
     * 显示无数据
     */
    void showEmpty();

    /**
     * 隐藏所有的加载页面
     */
    void showDataView();

}
