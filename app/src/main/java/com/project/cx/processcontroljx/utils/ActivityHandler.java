package com.project.cx.processcontroljx.utils;


import android.os.Handler;
import android.os.Message;

import com.project.cx.processcontroljx.processmain.ProcessMain;

/**
 * Created by Administrator on 2017/12/4 0004.
 */

public class ActivityHandler  extends Handler {
    private ProcessMain processMain;
    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
    }
    public ActivityHandler(ProcessMain pm){
        this.processMain=pm;

    }
}
