package com.dy.hbjg;

import com.dy.baseutils.utils.dialog.ToastUtils;

import dy.utils.libhttp.config.ILibHttp;
import dy.utils.libhttp.config.LibHttpConfig;

/**
 * Auth : dy
 * Time : 2017/4/13 14:49
 * Email: dymh21342@163.com
 * Description:
 */

public class IHTTPTlmp implements ILibHttp{
    private String base_server_url;
    public IHTTPTlmp(String base_server_url){
        this.base_server_url = base_server_url;
    }
    @Override
    public String getBaseUrl() {
        return base_server_url;
    }

    @Override
    public LibHttpConfig buildHttpConfig() {
        return null;
    }

    @Override
    public void showToast(String text) {
        ToastUtils.ShowToast(App.getApp(),text);
    }
}
