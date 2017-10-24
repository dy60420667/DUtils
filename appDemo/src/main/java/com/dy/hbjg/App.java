package com.dy.hbjg;

import android.app.Application;

import java.io.InputStream;
import java.util.Properties;

import dy.utils.libhttp.LibHttpSdk;


/**
 * Created by oosmart on 17/1/21.
 */

public class App extends Application {

    private static App instance = null;

    public static Application getApp(){
        return instance;

    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        readProperties();

        LibHttpSdk.initLibHttp(new IHTTPTlmp(base_server_url));
    }

    private String base_server_url;
    private void readProperties() {

        Properties props = new Properties();
        try {
            //方法一：通过activity中的context攻取setting.properties的FileInputStream
            InputStream in = getAssets().open("config.properties");
            //方法二：通过class获取setting.properties的FileInputStream
            //InputStream in = PropertiesUtill.class.getResourceAsStream("/assets/  setting.properties "));
            props.load(in);
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        base_server_url = props.getProperty("base_server_url");

    }
}
