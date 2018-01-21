package com.project.cx.processcontroljx.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import com.project.cx.processcontroljx.net.MSocketHelper;

import java.lang.ref.WeakReference;
import java.util.Stack;

/**
 * Created by cx on 2017/8/14.
 */

public class AppManager {
    public static Stack<Activity> activityStack;
    public static AppManager instance;
    private WeakReference<Activity> sCurrentActivityWeakRef;
    private AppManager(){}

    /**
     * 单例模式
     * @return
     */
    public static AppManager getAppManager(){
        if(instance==null){
        instance=new AppManager();
    }
        return instance;
    }

    /**
     * 添加activity到栈
     * @param activity
     */
    public void addActivity(Activity activity){
        if(activityStack==null){
            activityStack=new Stack<Activity>();
        }
        activityStack.add(activity);
    }
    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity(){
        Activity activity=activityStack.lastElement();
        return activity;
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity){
        if(activity!=null){
            activityStack.remove(activity);
            activity.finish();
            activity=null;
        }
    }
    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity(){
        Activity activity=activityStack.lastElement();
        finishActivity(activity);
    }
    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls){
        for (Activity activity : activityStack) {
            if(activity.getClass().equals(cls) ){
                finishActivity(activity);
            }
        }
    }
    /**
     * 结束所有Activity
     */
    public void finishAllActivity(){
        for (int i = 0, size = activityStack.size(); i < size; i++){
            if (null != activityStack.get(i)){
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }
    /**
     * 退出应用程序
     */
    public void AppExit(Context context) {
        try {
            UserManager.getInstance().saveIsLogin(false);
            MSocketHelper.getInstance().logout();//销毁socket
            finishAllActivity();
            ActivityManager activityMgr= (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.restartPackage(context.getPackageName());
            System.exit(0);
        } catch (Exception e) {	}
    }

    public Activity getCurrentActivity(){
        Activity currentActivity=null;
        if(sCurrentActivityWeakRef!=null){
            currentActivity=sCurrentActivityWeakRef.get();
        }
        return currentActivity;
    }
    public void setCurrentActivity(Activity activity){
            sCurrentActivityWeakRef=new WeakReference<Activity>(activity);
    }
}
