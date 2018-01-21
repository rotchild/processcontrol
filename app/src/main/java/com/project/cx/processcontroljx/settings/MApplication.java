package com.project.cx.processcontroljx.settings;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.project.cx.processcontroljx.net.MHttpParams;
import com.project.cx.processcontroljx.utils.AppManager;
import com.zhouyou.http.EasyHttp;

/**
 * Created by Administrator on 2017/12/1 0001.
 */

public class MApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {//用来监听所有 Activity 的生命周期回调
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {
                AppManager.getAppManager().setCurrentActivity(activity);
            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
        initApp();
    }


    private void initApp() {
        MHttpParams.getInstance().init(getApplicationContext());//初始化参数配置

        EasyHttp.init(this);
        EasyHttp.getInstance()
                //可以全局统一设置全局URL
                .setBaseUrl(MHttpParams.BASEURL);//设置全局URL
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }


    public void exit(){
        logout();
    }

    private void logout() {
        //注销服务
    }
}
