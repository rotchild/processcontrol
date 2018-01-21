package com.project.cx.processcontroljx.net;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;

import com.project.cx.processcontroljx.theme.MBaseActivity;


/**
 * Created by Administrator on 2017/10/30 0030.
 */

public class NetBroadcastReceiver extends BroadcastReceiver {
    public NetEvevt evevt = MBaseActivity.evevt;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e("onReceive","onReceive Enter");
        if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            int netWorkState = NetUtil.getNetWorkState(context);
            // 接口回调传过去状态的类型
            evevt.onNetChange(netWorkState);
        }
    }

    // 自定义接口
    public interface NetEvevt {
        public void onNetChange(int netMobile);
    }
}
