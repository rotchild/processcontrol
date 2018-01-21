package com.project.cx.processcontroljx.taskdetail;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.cx.processcontroljx.R;
import com.project.cx.processcontroljx.beans.DetailIntentType;
import com.project.cx.processcontroljx.beans.SelectedTask;
import com.project.cx.processcontroljx.beans.TaskDS;
import com.project.cx.processcontroljx.beans.Taskhurt;
import com.project.cx.processcontroljx.theme.MBaseActivity;
import com.project.cx.processcontroljx.utils.AppManager;
import com.project.cx.processcontroljx.utils.TelphoneUtil;
import com.project.cx.processcontroljx.utils.TimeUtil;

/**
 * Created by Administrator on 2017/11/30 0030.
 */

public class DetailRSHIS extends MBaseActivity implements View.OnClickListener {
    Context mContext;
    int intentType;//已读未读,人伤无已读未读，默认成已读
    ImageView setting_back;
    ContentValues followRS;
    TextView follow_detail_caseNo,follow_detail_caseTime,follow_detail_contactName,
            follow_detail_contactPhone,follow_detail_licenseno,follow_detail_dangerDetail;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_follow);
        mContext=this;
        initData();
        initView();
    }

    private void initView() {
        follow_detail_caseNo=(TextView) findViewById(R.id.follow_detail_caseNo);
        follow_detail_caseTime=(TextView) findViewById(R.id.follow_detail_caseTime);
        follow_detail_contactName=(TextView) findViewById(R.id.follow_detail_contactName);
        follow_detail_contactPhone=(TextView) findViewById(R.id.follow_detail_contactPhone);
        follow_detail_licenseno=(TextView) findViewById(R.id.follow_detail_licenseno);
        follow_detail_dangerDetail=(TextView) findViewById(R.id.follow_detail_dangerDetail);
        Log.i("MViewManager","selece position:"+followRS.getAsString(Taskhurt.caseNo));
        follow_detail_caseNo.setText(followRS.getAsString(Taskhurt.caseNo));
        Long Ctime_long=Long.valueOf(followRS.getAsString(TaskDS.caseTime))*1000;
        String Ctime_str=String.valueOf(Ctime_long);
        String Ctime_result= TimeUtil.stampToDate(Ctime_str);
        follow_detail_caseTime.setText(Ctime_result);
        follow_detail_contactName.setText(followRS.getAsString(Taskhurt.contactName));
        follow_detail_contactPhone.setText(followRS.getAsString(Taskhurt.contactPhone));
        follow_detail_contactPhone.setOnClickListener(this);
        follow_detail_licenseno.setText(followRS.getAsString(Taskhurt.licenseno));
        follow_detail_dangerDetail.setText(followRS.getAsString(Taskhurt.dangerDetail));

        setting_back= (ImageView) findViewById(R.id.setting_back);
        setting_back.setOnClickListener(this);


    }

    private void initData() {
        Intent intent=getIntent();
        intentType=intent.getIntExtra("intentType", DetailIntentType.READ);
        followRS= SelectedTask.getTaskRSHIS();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.setting_back:
                AppManager.getAppManager().finishActivity();
                break;
            case R.id.follow_detail_contactPhone:
                TelphoneUtil.toDial(mContext,followRS.getAsString(Taskhurt.contactPhone));
                break;
            default:
                break;
        }
    }
}
