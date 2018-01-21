package com.project.cx.processcontroljx.utils;

import android.content.Context;
import android.view.WindowManager;

/**
 * Created by Administrator on 2017/12/7 0007.
 */

public class MetricUtil {
    public static int getScreenWith(Context context){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getWidth();
    }
    public static int getScreenHeight(Context context){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return wm.getDefaultDisplay().getHeight();
    }
}
