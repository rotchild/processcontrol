package com.project.cx.processcontroljx.processmain;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.project.cx.processcontroljx.R;

/**
 * Created by Administrator on 2017/12/7 0007.
 */

public class ApproveActivity extends Activity {
    private final String TAG=getClass().getSimpleName();
    Context mContext;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG,"onCreate");
        setContentView(R.layout.dialog_risktip);
        mContext=this;
        initData();//must before initView
        initView();
    }

    private void initView() {

    }

    private void initData() {

    }
}
