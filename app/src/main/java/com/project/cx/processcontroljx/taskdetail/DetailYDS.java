package com.project.cx.processcontroljx.taskdetail;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.project.cx.processcontroljx.R;
import com.project.cx.processcontroljx.beans.DetailIntentType;
import com.project.cx.processcontroljx.beans.SelectedTask;
import com.project.cx.processcontroljx.beans.TaskDS;
import com.project.cx.processcontroljx.beans.TaskRole;
import com.project.cx.processcontroljx.net.OkhttpDataHandler;
import com.project.cx.processcontroljx.processmain.FXSBActivity;
import com.project.cx.processcontroljx.theme.MBaseActivity;
import com.project.cx.processcontroljx.utils.AppManager;
import com.project.cx.processcontroljx.utils.OkCallbackManager;
import com.project.cx.processcontroljx.utils.TelphoneUtil;
import com.project.cx.processcontroljx.utils.TimeUtil;
import com.project.cx.processcontroljx.utils.UserManager;

import java.util.ArrayList;

import okhttp3.Callback;

/**
 * Created by Administrator on 2017/11/30 0030.
 */

public class DetailYDS extends MBaseActivity implements View.OnClickListener {
    Context mContext;
    public int intentType;//已读未读
    ContentValues selectYDS;
    ImageView setting_back;
    TextView yds_detail_caseNo,yds_detail_caseTime,yds_detail_outTime,yds_detail_finishTime,yds_detail_outNumber,yds_detail_reporter,
            yds_detail_reporterPhone,yds_detail_reporter1,yds_detail_appointTime,yds_detail_reporterPhone1,yds_detail_licenseno,
            yds_detail_riskstate,yds_detail_assess_address,yds_detail_risktype,yds_detail_expect_amount,yds_detail_vehicleBrand,
            yds_detail_car_role;
    Button  safe_book;
    UserManager userManager;
    public ListView yds_detail_risklist;
    public String TAG="";
    int risktask_start=0;
    int risktask_limit=100;//?如何设置
    public ArrayList<ContentValues> riskArray=new ArrayList<ContentValues>();//上报任务列表数据

    public LinearLayout yds_risktype_wrapper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_yds);
        mContext=this;
        initData();
        initView();
    }

    private void initView() {
        yds_risktype_wrapper= (LinearLayout) findViewById(R.id.yds_risktype_wrapper);

        getRisksWarnHttpData(userManager.getUserToken(),userManager.getFrontRole(), TaskRole.ds,
                selectYDS.getAsString(TaskDS.id), OkCallbackManager.getInstance().getRiskWarmCallback(mContext,DetailYDS.this));


        safe_book= (Button) findViewById(R.id.safe_book);
        safe_book.setOnClickListener(this);

        setting_back= (ImageView) findViewById(R.id.setting_back);
        setting_back.setOnClickListener(this);
        yds_detail_caseNo= (TextView) findViewById(R.id.yds_detail_caseNo);
        yds_detail_caseTime= (TextView) findViewById(R.id.yds_detail_caseTime);
        yds_detail_outTime= (TextView) findViewById(R.id.yds_detail_outTime);
        yds_detail_finishTime= (TextView) findViewById(R.id.yds_detail_finishTime);
        //dds_detail_outNumber= (TextView) findViewById(R.id.dds_detail_outNumber);
        yds_detail_reporter= (TextView) findViewById(R.id.yds_detail_reporter);
        yds_detail_reporterPhone= (TextView) findViewById(R.id.yds_detail_reporterPhone);
        yds_detail_reporter1= (TextView) findViewById(R.id.yds_detail_reporter1);
        yds_detail_reporterPhone1= (TextView) findViewById(R.id.yds_detail_reporterPhone1);
        yds_detail_expect_amount=(TextView) findViewById(R.id.yds_detail_expect_amount);
        yds_detail_vehicleBrand=(TextView) findViewById(R.id.yds_detail_vehicleBrand);
        yds_detail_assess_address=(TextView) findViewById(R.id.yds_detail_assess_address);
        yds_detail_appointTime=(TextView) findViewById(R.id.yds_detail_appointTime);
        yds_detail_car_role=(TextView) findViewById(R.id.yds_detail_car_role);
        yds_detail_licenseno= (TextView) findViewById(R.id.yds_detail_licenseno);
        yds_detail_riskstate= (TextView) findViewById(R.id.yds_detail_riskstate);
        yds_detail_risklist= (ListView) findViewById(R.id.yds_detail_risklist);


        yds_detail_caseNo.setText(selectYDS.getAsString(TaskDS.caseNo));
        Long Ctime_long=Long.valueOf(selectYDS.getAsString(TaskDS.caseTime))*1000;
        String Ctime_str=String.valueOf(Ctime_long);
        String Ctime_result= TimeUtil.stampToDate(Ctime_str);
        yds_detail_caseTime.setText(Ctime_result);
        Long Otime_long=Long.valueOf(selectYDS.getAsString(TaskDS.outTime))*1000;
        String Otime_str=String.valueOf(Otime_long);
        String Otime_result= TimeUtil.stampToDate(Otime_str);
        yds_detail_outTime.setText(Otime_result);
        Long Ftime_long=Long.valueOf(selectYDS.getAsString(TaskDS.finishtime))*1000;
        String Ftime_str=String.valueOf(Ftime_long);
        String Ftime_result= TimeUtil.stampToDate(Ftime_str);
        yds_detail_finishTime.setText(Ftime_result);

        //dds_detail_outNumber.setText(selectDDS.getAsString(TaskDS.outNumber));
        yds_detail_reporter.setText(selectYDS.getAsString(TaskDS.reporter));
        yds_detail_reporterPhone.setText(selectYDS.getAsString(TaskDS.reporterPhone));
        yds_detail_reporterPhone.setOnClickListener(this);

        yds_detail_reporter1.setText(selectYDS.getAsString(TaskDS.reporter1));
        yds_detail_reporterPhone1.setText(selectYDS.getAsString(TaskDS.reporterPhone1));
        yds_detail_reporterPhone1.setOnClickListener(this);

        yds_detail_expect_amount.setText(selectYDS.getAsString(TaskDS.expect_amount));
        yds_detail_vehicleBrand.setText(selectYDS.getAsString(TaskDS.vehicleBrand));
        yds_detail_assess_address.setText(selectYDS.getAsString(TaskDS.assess_address));
        Long Atime_long=Long.valueOf(selectYDS.getAsString(TaskDS.appointTime))*1000;
        String Atime_str=String.valueOf(Atime_long);
        String Atime_result= TimeUtil.stampToDate(Atime_str);
        yds_detail_appointTime.setText(Atime_result);
        String rolestate_str="--";
        Long rolestate_long=Long.valueOf(selectYDS.getAsString(TaskDS.car_role));
        if(rolestate_long==1){
            rolestate_str="标的车";
        }else if(rolestate_long==2){
            rolestate_str="三者车";
        }
        yds_detail_car_role.setText(rolestate_str);
        yds_detail_licenseno.setText(selectYDS.getAsString(TaskDS.licenseno));
        String riskstate_str="--";
        Long riskstate_long=Long.valueOf(selectYDS.getAsString(TaskDS.riskstate));
        if(riskstate_long==0){
            riskstate_str="未上报";
        }else if(riskstate_long==1){
            riskstate_str="已上报";
        }
        yds_detail_riskstate.setText(riskstate_str);


    }

    private void initData() {
        TAG=getClass().getSimpleName();
        Intent intent=getIntent();
        intentType=intent.getIntExtra("intentType", DetailIntentType.UNREAD);
        selectYDS= SelectedTask.getTaskYDS();
        userManager= UserManager.getInstance();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.setting_back:
                AppManager.getAppManager().finishActivity();
                break;
            case R.id.safe_book:
                //风险上报
                Intent intent=new Intent(DetailYDS.this, FXSBActivity.class);
                intent.putExtra("from","DetailYDS");
                startActivity(intent);
                break;
            case R.id.yds_detail_reporterPhone:
                TelphoneUtil.toDial(mContext,selectYDS.getAsString(TaskDS.reporterPhone));
                break;
            case R.id.yds_detail_reporterPhone1:
                TelphoneUtil.toDial(mContext,selectYDS.getAsString(TaskDS.reporterPhone1));
                break;
            default:
                break;
        }
    }

    public void startActivity(Class activity,int intentType){
        Intent intent=new Intent(mContext,activity);
        intent.putExtra("intentType",intentType);//intentType 0:未读 1:已读
        startActivity(intent);
    }

    //获取风险提示数据
    public void getRisksWarnHttpData(String token, String frontrole, String task_role, String taskid, Callback Callback){
        OkhttpDataHandler okhandler=new OkhttpDataHandler(mContext);
        okhandler.setmIsShowProgressDialog(true);
        okhandler.getRisksWarnHttp(token,frontrole,task_role,taskid,Callback);
    }

    //获取风险上报任务数据
    public void getTaskRiskHttpData(String token,String frontrole, String task_role,String caseNo,String licenseNo,int start,int limit,Callback Callback){
        OkhttpDataHandler okhandler=new OkhttpDataHandler(mContext);
        okhandler.setmIsShowProgressDialog(true);
        okhandler.getTaskRiskHttp(token,frontrole,task_role,caseNo,licenseNo,start,limit,Callback);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(userManager!=null){//更新risk表
            Log.i(TAG,"onResume  userManager !=null enter");
            getTaskRiskHttpData(userManager.getUserToken(),userManager.getFrontRole(), TaskRole.ds,
                    selectYDS.getAsString(TaskDS.caseNo), selectYDS.getAsString(TaskDS.licenseno),risktask_start,risktask_limit, OkCallbackManager.getInstance().getTaskRiskCallback(mContext,DetailYDS.this));
        }
    }
}
