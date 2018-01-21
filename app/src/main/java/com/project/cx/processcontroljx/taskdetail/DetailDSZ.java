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
import android.widget.TextView;

import com.project.cx.processcontroljx.R;
import com.project.cx.processcontroljx.beans.DetailIntentType;
import com.project.cx.processcontroljx.beans.MRequestCode;
import com.project.cx.processcontroljx.beans.SelectedTask;
import com.project.cx.processcontroljx.beans.TaskDS;
import com.project.cx.processcontroljx.beans.TaskRole;
import com.project.cx.processcontroljx.net.OkhttpDataHandler;
import com.project.cx.processcontroljx.processmain.AuthorityActivity;
import com.project.cx.processcontroljx.processmain.DSappointment;
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

public class DetailDSZ extends MBaseActivity implements View.OnClickListener {
    Context mContext;
    public int intentType;//已读未读
    ContentValues selectDSZ;
    ImageView setting_back;
    TextView dsz_detail_caseNo,dsz_detail_caseTime,dsz_detail_outTime,dsz_detail_outNumber,dsz_detail_reporter,
            dsz_detail_reporterPhone,dsz_detail_reporter1,dsz_detail_appointTime,dsz_detail_reporterPhone1,dsz_detail_licenseno,
            dsz_detail_riskstate,dsz_detail_assess_address,dsz_detail_risktype,dsz_detail_expect_amount,dsz_detail_vehicleBrand,
            dsz_detail_car_role;

    Button task_change;
    Button safe_book;
    Button urge_tel;
    Button authority_book;//超权限上报
    UserManager userManager;
    public LinearLayout dsz_risktype_wrapper;
    public LinearLayout dsz_risklist_wrapper;
    public String TAG="";
    int risktask_start=0;
    int risktask_limit=100;//?如何设置
    public ArrayList<ContentValues> riskArray = new ArrayList<ContentValues>();//上报任务列表数据


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_dsz);
        mContext=this;
        initData();
        initView();
        getRisksWarnHttpData(userManager.getUserToken(), userManager.getFrontRole(), TaskRole.ds,
                selectDSZ.getAsString(TaskDS.id), OkCallbackManager.getInstance().getRiskWarmCallback(mContext, DetailDSZ.this));
    }

    private void initView() {
        TAG=getClass().getSimpleName();

        dsz_risktype_wrapper= (LinearLayout) findViewById(R.id.dsz_risktype_wrapper);
        dsz_risklist_wrapper= (LinearLayout) findViewById(R.id.dsz_risklist_wrapper);

        authority_book= (Button) findViewById(R.id.authority_book);
        authority_book.setOnClickListener(this);

        urge_tel= (Button) findViewById(R.id.urge_tel);
        urge_tel.setOnClickListener(this);

        safe_book= (Button) findViewById(R.id.safe_book);
        safe_book.setOnClickListener(this);

        setting_back= (ImageView) findViewById(R.id.setting_back);
        setting_back.setOnClickListener(this);

        dsz_detail_caseNo= (TextView) findViewById(R.id.dsz_detail_caseNo);
        dsz_detail_caseTime= (TextView) findViewById(R.id.dsz_detail_caseTime);
        dsz_detail_outTime= (TextView) findViewById(R.id.dsz_detail_outTime);
        //dds_detail_outNumber= (TextView) findViewById(R.id.dds_detail_outNumber);
        dsz_detail_reporter= (TextView) findViewById(R.id.dsz_detail_reporter);
        dsz_detail_reporterPhone= (TextView) findViewById(R.id.dsz_detail_reporterPhone);
        dsz_detail_reporter1= (TextView) findViewById(R.id.dsz_detail_reporter1);
        dsz_detail_reporterPhone1= (TextView) findViewById(R.id.dsz_detail_reporterPhone1);
        dsz_detail_expect_amount=(TextView) findViewById(R.id.dsz_detail_expect_amount);
        dsz_detail_vehicleBrand=(TextView) findViewById(R.id.dsz_detail_vehicleBrand);
        dsz_detail_assess_address=(TextView) findViewById(R.id.dsz_detail_assess_address);
        dsz_detail_appointTime=(TextView) findViewById(R.id.dsz_detail_appointTime);
        dsz_detail_car_role=(TextView) findViewById(R.id.dsz_detail_car_role);
        dsz_detail_licenseno= (TextView) findViewById(R.id.dsz_detail_licenseno);
        dsz_detail_riskstate= (TextView) findViewById(R.id.dsz_detail_riskstate);
        dsz_detail_caseNo.setText(selectDSZ.getAsString(TaskDS.caseNo));
        Long Ctime_long=Long.valueOf(selectDSZ.getAsString(TaskDS.caseTime))*1000;
        String Ctime_str=String.valueOf(Ctime_long);
        String Ctime_result= TimeUtil.stampToDate(Ctime_str);
        dsz_detail_caseTime.setText(Ctime_result);
        Long Otime_long=Long.valueOf(selectDSZ.getAsString(TaskDS.outTime))*1000;
        String Otime_str=String.valueOf(Otime_long);
        String Otime_result= TimeUtil.stampToDate(Otime_str);
        dsz_detail_outTime.setText(Otime_result);
        //dds_detail_outNumber.setText(selectDDS.getAsString(TaskDS.outNumber));
        dsz_detail_reporter.setText(selectDSZ.getAsString(TaskDS.reporter));
        dsz_detail_reporterPhone.setText(selectDSZ.getAsString(TaskDS.reporterPhone));
        dsz_detail_reporterPhone.setOnClickListener(this);

        dsz_detail_reporter1.setText(selectDSZ.getAsString(TaskDS.reporter1));
        dsz_detail_reporterPhone1.setText(selectDSZ.getAsString(TaskDS.reporterPhone1));
        dsz_detail_reporterPhone1.setOnClickListener(this);

        dsz_detail_expect_amount.setText(selectDSZ.getAsString(TaskDS.expect_amount));
        dsz_detail_vehicleBrand.setText(selectDSZ.getAsString(TaskDS.vehicleBrand));
        dsz_detail_assess_address.setText(selectDSZ.getAsString(TaskDS.assess_address));
        Long Atime_long=Long.valueOf(selectDSZ.getAsString(TaskDS.appointTime))*1000;
        String Atime_str=String.valueOf(Atime_long);
        String Atime_result= TimeUtil.stampToDate(Atime_str);
        dsz_detail_appointTime.setText(Atime_result);
        String rolestate_str="--";
        Long rolestate_long=Long.valueOf(selectDSZ.getAsString(TaskDS.car_role));
        if(rolestate_long==1){
            rolestate_str="标的车";
        }else if(rolestate_long==2){
            rolestate_str="三者车";
        }
        dsz_detail_car_role.setText(rolestate_str);
        dsz_detail_licenseno.setText(selectDSZ.getAsString(TaskDS.licenseno));
        String riskstate_str="--";
        Long riskstate_long=Long.valueOf(selectDSZ.getAsString(TaskDS.riskstate));
        if(riskstate_long==0){
            riskstate_str="未上报";
        }else if(riskstate_long==1){
            riskstate_str="已上报";
        }
        dsz_detail_riskstate.setText(riskstate_str);

        task_change= (Button) findViewById(R.id.task_change);
        task_change.setOnClickListener(this);
    }

    private void initData() {
        userManager=UserManager.getInstance();
        Intent intent=getIntent();
        intentType=intent.getIntExtra("intentType", DetailIntentType.UNREAD);
        selectDSZ= SelectedTask.getTaskDSZ();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.setting_back:
                AppManager.getAppManager().finishActivity();
                break;
            case R.id.safe_book:
                Intent tosafe_book = new Intent(DetailDSZ.this, FXSBActivity.class);
                tosafe_book.putExtra("from", "DetailDSZ");
                //startActivity(intent);
                startActivityForResult(tosafe_book, MRequestCode.DSZ);
                break;
            case R.id.urge_tel:
             /*   Intent dialIntent =  new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + selectDSZ.getAsString(TaskDS.reporterPhone1)));//跳转到拨
                startActivity(dialIntent);*/
                TelphoneUtil.toDial(mContext,selectDSZ.getAsString(TaskDS.reporterPhone1));//拨打电话
                break;
            case R.id.task_change:
                Intent intent=new Intent(DetailDSZ.this, DSappointment.class);
                intent.putExtra("type","DSZ");
                startActivity(intent);
                AppManager.getAppManager().finishActivity(DetailDSZ.this);
                break;

            case R.id.authority_book:
                Intent toAuthority =  new Intent(DetailDSZ.this, AuthorityActivity.class);//跳转超权限上报
                toAuthority.putExtra("type","DetailDSZ");
                startActivity(toAuthority);
                break;

            case R.id.dsz_detail_reporterPhone:
              TelphoneUtil.toDial(mContext,selectDSZ.getAsString(TaskDS.reporterPhone));
                break;
            case R.id.dsz_detail_reporterPhone1:
                TelphoneUtil.toDial(mContext,selectDSZ.getAsString(TaskDS.reporterPhone1));
                break;
            default:
                break;
        }
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
                    selectDSZ.getAsString(TaskDS.caseNo),selectDSZ.getAsString(TaskDS.licenseno),risktask_start,risktask_limit, OkCallbackManager.getInstance().getTaskRiskCallback(mContext,DetailDSZ.this));
        }
    }
}
