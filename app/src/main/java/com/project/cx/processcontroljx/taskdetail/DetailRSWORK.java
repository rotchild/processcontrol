package com.project.cx.processcontroljx.taskdetail;

import android.content.ContentValues;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.cx.processcontroljx.R;
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

public class DetailRSWORK extends MBaseActivity implements View.OnClickListener {
    Context mContext;
    int intentType;//已读未读,人伤无已读未读，默认成已读
    ImageView setting_back;
    TextView follow_detail_caseNo,follow_detail_caseTime,follow_detail_contactName,
            follow_detail_contactPhone,follow_detail_licenseno,follow_detail_dangerDetail;
    ContentValues workfollowRS;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_workfollow);
        mContext=this;
        initData();
        initView();
    }

    private void initView() {

        follow_detail_caseNo=(TextView) findViewById(R.id.workfollow_detail_caseNo);
        follow_detail_caseTime=(TextView) findViewById(R.id.workfollow_detail_caseTime);
        follow_detail_contactName=(TextView) findViewById(R.id.workfollow_detail_contactName);
        follow_detail_contactPhone=(TextView) findViewById(R.id.workfollow_detail_contactPhone);
        follow_detail_licenseno=(TextView) findViewById(R.id.workfollow_detail_licenseno);
        follow_detail_dangerDetail=(TextView) findViewById(R.id.workfollow_detail_dangerDetail);
        follow_detail_caseNo.setText(workfollowRS.getAsString(Taskhurt.caseNo));

        Long Ctime_long=Long.valueOf(workfollowRS.getAsString(TaskDS.caseTime))*1000;
        String Ctime_str=String.valueOf(Ctime_long);
        String Ctime_result= TimeUtil.stampToDate(Ctime_str);
        follow_detail_caseTime.setText(Ctime_result);

        follow_detail_contactName.setText(workfollowRS.getAsString(Taskhurt.contactName));
        follow_detail_contactPhone.setText(workfollowRS.getAsString(Taskhurt.contactPhone));
        follow_detail_contactPhone.setOnClickListener(this);
        follow_detail_licenseno.setText(workfollowRS.getAsString(Taskhurt.licenseno));
        follow_detail_dangerDetail.setText(workfollowRS.getAsString(Taskhurt.dangerDetail));

        setting_back= (ImageView) findViewById(R.id.setting_back);
        setting_back.setOnClickListener(this);


    }

    private void initData() {
        workfollowRS= SelectedTask.getTaskRSWORK();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.setting_back:
                AppManager.getAppManager().finishActivity();
                break;
            case R.id.workfollow_detail_contactPhone:
                Log.e("111","onCLick");
                TelphoneUtil.toDial(mContext,workfollowRS.getAsString(Taskhurt.contactPhone));
                break;
            default:
                break;
        }
    }
}
