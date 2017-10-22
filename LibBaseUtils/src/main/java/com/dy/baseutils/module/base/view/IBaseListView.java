package com.dy.baseutils.module.base.view;

/**
 * Auth : dy
 * Time : 2017/2/10 09:05
 * Email: dymh21342@163.com
 * Description:
 */


import java.util.List;

/**
 * Auth : dy
 * Time : 2017/2/6 11:50
 * Email: dymh21342@163.com
 * Description: 下拉刷新的页面
 */

public interface IBaseListView<T> extends IBaseView{

    void setDate(List<T> list);
}
