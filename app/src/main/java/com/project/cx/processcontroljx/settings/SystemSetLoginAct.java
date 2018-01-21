package com.project.cx.processcontroljx.settings;

import android.content.Context;
import android.os.Bundle;

import com.project.cx.processcontroljx.R;
import com.project.cx.processcontroljx.net.MHttpParams;
import com.project.cx.processcontroljx.theme.MBaseActivity;

/**
 * Created by Administrator on 2017/12/28 0028.
 */

public class SystemSetLoginAct extends MBaseActivity {
    Context mContext;
    MHttpParams mHttpParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_systemsetting);
        initData();
        initView();
    }

    private void initData() {
        mHttpParams=MHttpParams.getInstance();
        mHttpParams.init(mContext);
    }
    private void initView() {

    }
}
