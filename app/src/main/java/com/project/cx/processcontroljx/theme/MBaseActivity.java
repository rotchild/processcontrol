package com.project.cx.processcontroljx.theme;

import android.content.Context;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.project.cx.processcontroljx.net.NetBroadcastReceiver;
import com.project.cx.processcontroljx.net.NetUtil;
import com.project.cx.processcontroljx.utils.AppManager;

import io.socket.client.Socket;

public class MBaseActivity extends FragmentActivity implements NetBroadcastReceiver.NetEvevt {

    private int netMobile;
    public Context mContext;
    public static NetBroadcastReceiver.NetEvevt evevt;
    NetBroadcastReceiver mNetBroadreceiver;
    Socket mSocket=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(getClass().getSimpleName(), "onCreate() savedInstanceState:" + savedInstanceState);
        super.onCreate(savedInstanceState);
        mContext=this;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        AppManager.getAppManager().addActivity(this);
        inspectNet();
       // InitSocket();

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                hideKeyboard(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }


    public boolean inspectNet() {
        this.netMobile = NetUtil.getNetWorkState(MBaseActivity.this);
        return isNetConnect();
    }

    @Override
    protected void onPause() {
        Log.i(getClass().getSimpleName(), "onPause");
        super.onPause();
        if(mNetBroadreceiver!=null){
            unregisterReceiver(mNetBroadreceiver);
        }

    }

    @Override
    protected void onResume() {
        Log.i(getClass().getSimpleName(), "onResume");

        super.onResume();
        evevt = this;
        if(mNetBroadreceiver==null){
            mNetBroadreceiver=new NetBroadcastReceiver();
        }
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(mNetBroadreceiver,intentFilter);
    }

    @Override
    protected void onDestroy() {
        Log.i(getClass().getSimpleName(), "onDestroy");
        //MViewManager.getInstance().finishAllAdapter();//销毁所有adapter
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        Log.i(getClass().getSimpleName(), "onStart");
        super.onStart();
    }

    @Override
    protected void onStop() {
        Log.i(getClass().getSimpleName(), "onStop");
        super.onStop();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.i(getClass().getSimpleName(), "onRestoreInstanceState:" + savedInstanceState);
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.i(getClass().getSimpleName(), "onSaveInstanceState:" + outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {//按返回退出
        Log.i(getClass().getSimpleName(), "onBackPressed");
        AppManager.getAppManager().finishActivity();
        super.onBackPressed();
    }

    public boolean isNetConnect() {
        if (netMobile == 1) {
            return true;
        } else if (netMobile == 0) {
            return true;
        } else if (netMobile == -1) {
            return false;
        }
        return false;
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                v.clearFocus();
                return true;
            }
        }
        // 如果焦点不是EditText则忽略，这个发生在视图刚绘制完，第一个焦点不在EditText上，和用户用轨迹球选择其他的焦点
        return false;
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     *
     * @param token
     */
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    public void onNetChange(int netMobile) {
        Log.e("MBase","onNetChange enter,netMobile is:"+netMobile);
        if(netMobile==-1){
            Toast.makeText(mContext,"当前无法检测到网络",Toast.LENGTH_SHORT).show();
            Log.e("MBase","无网络");
        }else if(netMobile==0){
            Log.e("MBase","移动网络");
        }else if(netMobile==1){
            Log.e("MBase","wifi网络");
        }
    }
}
