package com.dy.baseutils.view.webactivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.dy.baseutils.R;
import com.dy.baseutils.module.base.DYBaseFragment;
import com.dy.baseutils.module.base.view.IBaseView;

import org.w3c.dom.Text;

/**
 * Auth : dy
 * Time : 2017/2/6 11:45
 * Email: dymh21342@163.com
 * Description:
 */

public class F_WebView extends DYBaseFragment implements IBaseView {
    public static F_WebView createFragment(String url) {
        F_WebView f_webView = new F_WebView();
        Bundle b = new Bundle();
        b.putString("url", url);
        f_webView.setArguments(b);
        return f_webView;
    }


    private WebView webView;
    private View layout_error;
    private View layout_loading;
    private TextView tv_neterror;

    @Override
    public void initView() {
        webView = (WebView) rootView.findViewById(R.id.webview);
        layout_error = rootView.findViewById(R.id.layout_error);
        layout_loading = rootView.findViewById(R.id.layout_loading);
        tv_neterror = (TextView) rootView.findViewById(R.id.tv_neterror);

        layout_error.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                webView.loadUrl(getArguments().getString("url"));
            }
        });

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadsImagesAutomatically(true);
        webView.getSettings().supportMultipleWindows();
        webView.getSettings().setBuiltInZoomControls(false);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setUserAgentString("User-Agent: Dalvik/1.6.0 (Linux; U; Android 4.1.2; MI 1SC MIUI/4.5.30)");
        webView.setWebViewClient(new MyWebViewClient());
        webView.loadUrl(getArguments().getString("url"));
    }


    @Override
    public int getFragmentLayout() {
        return R.layout.f_webview;
    }

    @Override
    public void showLoading() {
        layout_loading.setVisibility(View.VISIBLE);
        layout_error.setVisibility(View.GONE);
    }

    @Override
    public void showError(String msg) {
        layout_loading.setVisibility(View.GONE);
        layout_error.setVisibility(View.VISIBLE);
        if (!TextUtils.isEmpty(msg)) {
            tv_neterror.setText(msg);
        }
    }

    @Override
    public void showEmpty() {

    }


    @Override
    public void showDataView() {
        layout_loading.setVisibility(View.GONE);
        layout_error.setVisibility(View.GONE);
    }


    class MyWebViewClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            showLoading();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            showDataView();
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            showError("");
        }
    }
}
