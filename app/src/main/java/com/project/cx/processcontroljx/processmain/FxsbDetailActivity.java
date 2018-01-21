package com.project.cx.processcontroljx.processmain;

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
import android.widget.TextView;

import com.project.cx.processcontroljx.R;
import com.project.cx.processcontroljx.beans.DetailIntentType;
import com.project.cx.processcontroljx.beans.RiskWarm;
import com.project.cx.processcontroljx.beans.SelectedTask;
import com.project.cx.processcontroljx.beans.TaskRisk;
import com.project.cx.processcontroljx.taskdetail.DetailDCK;
import com.project.cx.processcontroljx.theme.MBaseActivity;
import com.project.cx.processcontroljx.utils.AppManager;
import com.project.cx.processcontroljx.utils.LayoutAddDanamic;
import com.project.cx.processcontroljx.utils.TimeUtil;
import com.project.cx.processcontroljx.utils.UserManager;

/**
 * Created by Administrator on 2017/12/7 0007.
 */

public class FxsbDetailActivity extends MBaseActivity implements View.OnClickListener{
    private final String TAG=getClass().getSimpleName();
    Context mContext;
    ContentValues selectRisk;
    UserManager userManager;
    int intentType;//已读未读,人伤无已读未读，默认成已读

    TextView fxsb_detail_caseNo,fxsb_detail_caseTime,fxsb_detail_outTime,
             fxsb_detail_reporter,fxsb_detail_state,fxsb_detail_auditor;

    Button fxsb_detail_yes,fxsb_detail_no;
    ImageView setting_back;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG,"onCreate");
        setContentView(R.layout.activity_fxsb_detail);
        mContext=this;
        initData();//must before initView
        initView();
    }

    private void initView() {
        setting_back= (ImageView) findViewById(R.id.setting_back);
        setting_back.setOnClickListener(this);

        fxsb_detail_caseNo= (TextView) findViewById(R.id.fxsb_detail_caseNo);
        fxsb_detail_caseTime= (TextView) findViewById(R.id.fxsb_detail_caseTime);
        fxsb_detail_outTime= (TextView) findViewById(R.id.fxsb_detail_outTime);
        fxsb_detail_reporter= (TextView) findViewById(R.id.fxsb_detail_reporter);
        fxsb_detail_state= (TextView) findViewById(R.id.fxsb_detail_state);
        fxsb_detail_auditor= (TextView) findViewById(R.id.fxsb_detail_auditor);
        fxsb_detail_yes= (Button) findViewById(R.id.fxsb_detail_yes);
        fxsb_detail_yes.setOnClickListener(this);
        fxsb_detail_no= (Button) findViewById(R.id.fxsb_detail_no);
        fxsb_detail_no.setOnClickListener(this);

        fxsb_detail_caseNo.setText(selectRisk.getAsString(TaskRisk.caseNo));
        Long Ctime_long=Long.valueOf(selectRisk.getAsString(TaskRisk.caseTime))*1000;
        String Ctime_str=String.valueOf(Ctime_long);
        String caseTime_str= TimeUtil.stampToDate(Ctime_str);
        fxsb_detail_caseTime.setText(caseTime_str);
        Long Otime_long=Long.valueOf(selectRisk.getAsString(TaskRisk.createtime))*1000;
        String Otime_str=String.valueOf(Ctime_long);
        String OcaseTime_str= TimeUtil.stampToDate(Otime_str);
        Log.i("selectDCK","OcaseTime_str:"+OcaseTime_str);
        fxsb_detail_outTime.setText(OcaseTime_str);

        fxsb_detail_reporter.setText(selectRisk.getAsString(TaskRisk.reporter_name));//报案人和上报人是有区别

        String riskexamine_str="--";
        Long riskexamine_long=Long.valueOf(selectRisk.getAsString(TaskRisk.state));
        if(riskexamine_long==0){
            riskexamine_str="审核中";
            fxsb_detail_state.setTextColor(mContext.getResources().getColor(R.color.check_yellow));
        }else if(riskexamine_long==1){
            riskexamine_str="审核通过";
            fxsb_detail_state.setTextColor(mContext.getResources().getColor(R.color.check_green));
        }else if(riskexamine_long==2){
            riskexamine_str="驳回";
            fxsb_detail_state.setTextColor(mContext.getResources().getColor(R.color.check_red));
        }
        fxsb_detail_state.setText(riskexamine_str);

        LinearLayout risktype_wrapper= (LinearLayout) findViewById(R.id.dck_risktype_wrapper);
        risktype_wrapper.removeAllViews();
        LayoutAddDanamic.getInstance().addRiskTypeListView(mContext,risktype_wrapper, RiskWarm.riskwarmArray);

        fxsb_detail_auditor.setText(selectRisk.getAsString(TaskRisk.auditor));
    }

    private void initData() {
        Intent intent=getIntent();
        intentType=intent.getIntExtra("intentType", DetailIntentType.READ);
        selectRisk= SelectedTask.getTaskRisk();
        //Log.i("selectDCK","selece position:"+selectDCK);
        Log.i("selectDCK","selece position:"+selectRisk);

        userManager=UserManager.getInstance();
        userManager.init(mContext);
    }

/*    //提交风险上报数据
    public void commitRiskRecordData(String token,String frontrole,String taskid,String task_role,String risktype,
                                     String others, String remark,Callback Callback){
        OkhttpDataHandler okhandler=new OkhttpDataHandler(mContext);
        okhandler.setmIsShowProgressDialog(true);
        okhandler.commitRiskRecordHttp(token,frontrole,taskid,task_role,risktype,others,remark,Callback);
    }*/

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fxsb_detail_yes://已取消
                AppManager.getAppManager().finishActivity(FxsbDetailActivity.this);
                break;
            case R.id.fxsb_detail_no://已取消
                AppManager.getAppManager().finishActivity(FxsbDetailActivity.this);
                break;
            case R.id.setting_back:
                AppManager.getAppManager().finishActivity(FxsbDetailActivity.this);
                break;
            default:
                break;
        }
    }
}
