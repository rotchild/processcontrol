package com.project.cx.processcontroljx.taskdetail;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

public class DetailDDS extends MBaseActivity implements View.OnClickListener {
    Context mContext;
    public int intentType;//已读未读
    ContentValues selectDDS;
    ImageView setting_back;
    TextView dds_detail_caseNo,dds_detail_caseTime,dds_detail_outTime,dds_detail_outNumber,dds_detail_reporter,
            dds_detail_reporterPhone,dds_detail_reporter1,dds_detail_appointTime,dds_detail_reporterPhone1,
            dds_detail_licenseno,dds_detail_riskstate, dds_detail_assess_address,dds_detail_risktype,
            dds_detail_expect_amount,dds_detail_vehicleBrand,dds_detail_car_role;
    Button dds_authority_book;
    Button safe_book;
    Button task_claim;
    Button ck_task_change;
    Button urge_tel;//电话催办定损
    Button authority_book;//超权限上报
    Button task_change;//任务改派
    LinearLayout dds_operator_linear;//只用为自己的待定损任务时才显示
    LinearLayout ck_dds_operator_linear;//只用为自己的待定损任务时才显示
    public ArrayList<ContentValues> riskArray = new ArrayList<ContentValues>();//上报任务列表数据
    UserManager userManager;
    public LinearLayout dds_risktype_wrapper;
    public LinearLayout dds_risklist_wrapper;
    public String TAG="";
    int risktask_start=0;
    int risktask_limit=100;//?如何设置

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_dds);
        mContext=this;
        initData();
        initView();
        getRisksWarnHttpData(userManager.getUserToken(), userManager.getFrontRole(), TaskRole.ds,
                selectDDS.getAsString(TaskDS.id), OkCallbackManager.getInstance().getRiskWarmCallback(mContext, DetailDDS.this));
    }

    private void initView() {
        dds_risktype_wrapper= (LinearLayout) findViewById(R.id.dds_risktype_wrapper);
        dds_risklist_wrapper= (LinearLayout) findViewById(R.id.dds_risklist_wrapper);

        task_change=(Button)findViewById(R.id.task_change);
        task_change.setOnClickListener(this);

        ck_task_change=(Button)findViewById(R.id.ck_task_change);
        ck_task_change.setOnClickListener(this);

        authority_book=(Button)findViewById(R.id.authority_book);
        authority_book.setOnClickListener(this);

        urge_tel=(Button)findViewById(R.id.urge_tel);
        urge_tel.setOnClickListener(this);

        task_claim=(Button)findViewById(R.id.task_claim);
        task_claim.setOnClickListener(this);

        setting_back= (ImageView) findViewById(R.id.setting_back);
        setting_back.setOnClickListener(this);

        safe_book= (Button) findViewById(R.id.safe_book);
        safe_book.setOnClickListener(this);

        dds_detail_caseNo= (TextView) findViewById(R.id.dds_detail_caseNo);
        dds_detail_caseTime= (TextView) findViewById(R.id.dds_detail_caseTime);
        dds_detail_outTime= (TextView) findViewById(R.id.dds_detail_outTime);
        //dds_detail_outNumber= (TextView) findViewById(R.id.dds_detail_outNumber);
        dds_detail_reporter= (TextView) findViewById(R.id.dds_detail_reporter);
        dds_detail_reporterPhone= (TextView) findViewById(R.id.dds_detail_reporterPhone);
        dds_detail_reporter1= (TextView) findViewById(R.id.dds_detail_reporter1);
        dds_detail_reporterPhone1= (TextView) findViewById(R.id.dds_detail_reporterPhone1);
        dds_detail_expect_amount=(TextView) findViewById(R.id.dds_detail_expect_amount);
        dds_detail_vehicleBrand=(TextView) findViewById(R.id.dds_detail_vehicleBrand);
        dds_detail_assess_address=(TextView) findViewById(R.id.dds_detail_assess_address);
        dds_detail_appointTime=(TextView) findViewById(R.id.dds_detail_appointTime);
        dds_detail_car_role=(TextView) findViewById(R.id.dds_detail_car_role);
        dds_detail_licenseno= (TextView) findViewById(R.id.dds_detail_licenseno);
        dds_detail_riskstate= (TextView) findViewById(R.id.dds_detail_riskstate);
        dds_detail_caseNo.setText(selectDDS.getAsString(TaskDS.caseNo));
        Long Ctime_long=Long.valueOf(selectDDS.getAsString(TaskDS.caseTime))*1000;
        String Ctime_str=String.valueOf(Ctime_long);
        String Ctime_result= TimeUtil.stampToDate(Ctime_str);
        dds_detail_caseTime.setText(Ctime_result);
        Long Otime_long=Long.valueOf(selectDDS.getAsString(TaskDS.outTime))*1000;
        String Otime_str=String.valueOf(Otime_long);
        String Otime_result= TimeUtil.stampToDate(Otime_str);
        dds_detail_outTime.setText(Otime_result);
        //dds_detail_outNumber.setText(selectDDS.getAsString(TaskDS.outNumber));
        Log.i("selectDDS1","selectDDS1:"+selectDDS.getAsString(TaskDS.reporter));
        dds_detail_reporter.setText(selectDDS.getAsString(TaskDS.reporter));
        dds_detail_reporterPhone.setText(selectDDS.getAsString(TaskDS.reporterPhone));
        dds_detail_reporterPhone.setOnClickListener(this);

        dds_detail_reporter1.setText(selectDDS.getAsString(TaskDS.reporter1));
        dds_detail_reporterPhone1.setText(selectDDS.getAsString(TaskDS.reporterPhone1));
        dds_detail_reporterPhone1.setOnClickListener(this);

        dds_detail_expect_amount.setText(selectDDS.getAsString(TaskDS.expect_amount));
        dds_detail_vehicleBrand.setText(selectDDS.getAsString(TaskDS.vehicleBrand));
        dds_detail_assess_address.setText(selectDDS.getAsString(TaskDS.assess_address));
        Long Atime_long=Long.valueOf(selectDDS.getAsString(TaskDS.appointTime))*1000;
        String Atime_str=String.valueOf(Atime_long);
        String Atime_result= TimeUtil.stampToDate(Atime_str);
        dds_detail_appointTime.setText(Atime_result);
        String rolestate_str="--";
        Long rolestate_long=Long.valueOf(selectDDS.getAsString(TaskDS.car_role));
        if(rolestate_long==1){
            rolestate_str="标的车";
        }else if(rolestate_long==2){
            rolestate_str="三者车";
        }
        dds_detail_car_role.setText(rolestate_str);
        dds_detail_licenseno.setText(selectDDS.getAsString(TaskDS.licenseno));
        String riskstate_str="--";
        Long riskstate_long=Long.valueOf(selectDDS.getAsString(TaskDS.riskstate));
        if(riskstate_long==0){
            riskstate_str="未上报";
        }else if(riskstate_long==1){
            riskstate_str="已上报";
        }
        dds_detail_riskstate.setText(riskstate_str);

        dds_operator_linear= (LinearLayout) findViewById(R.id.dds_operator_linear);
        ck_dds_operator_linear= (LinearLayout) findViewById(R.id.ck_dds_operator_linear);
       /* if(selectDDS.getAsString(TaskDS.assessorNo).equals(userManager.getJobNo())){//只有是自己的任务时展示
            if(userManager.getFrontRole().equals("1")){//CK员只有任务改派
                ck_dds_operator_linear.setVisibility(View.VISIBLE);
                dds_operator_linear.setVisibility(View.GONE);
            }else{
                ck_dds_operator_linear.setVisibility(View.GONE);
                dds_operator_linear.setVisibility(View.VISIBLE);
            }


        }else{
            ck_dds_operator_linear.setVisibility(View.GONE);
            dds_operator_linear.setVisibility(View.GONE);
        }
*/
        if(userManager.getFrontRole().equals("1")|| (!userManager.getJobNo().equals(selectDDS.getAsString(TaskDS.assessorNo)))){//CK员,查看定损员不是自己的任务只有任务改派,
            ck_dds_operator_linear.setVisibility(View.VISIBLE);
            dds_operator_linear.setVisibility(View.GONE);
        }else{
            ck_dds_operator_linear.setVisibility(View.GONE);
            dds_operator_linear.setVisibility(View.VISIBLE);
        }
        setting_back= (ImageView) findViewById(R.id.setting_back);
        setting_back.setOnClickListener(this);
        //dds_authority_book=(Button)findViewById(R.id.dds_authority_book);
        //dds_authority_book.setOnClickListener(this);

    }

    private void initData() {
        TAG=getClass().getSimpleName();
        Intent intent=getIntent();
        intentType=intent.getIntExtra("intentType", DetailIntentType.UNREAD);
        selectDDS= SelectedTask.getTaskDDS();
        userManager=UserManager.getInstance();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.setting_back:
                AppManager.getAppManager().finishActivity();
                break;
         /*   case R.id.dds_authority_book:
                Intent intent=new Intent(DetailDDS.this, AuthorityActivity.class);
                startActivity(intent);
                break;*/
            case R.id.task_claim:
                //认领任务
                claimTaskHttpData(userManager.getUserToken(),userManager.getFrontRole(),selectDDS.getAsString(TaskDS.id), OkCallbackManager.getInstance().claimTaskCallback(mContext,DetailDDS.this));
                break;

            case R.id.urge_tel:
                Intent dialIntent =  new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + selectDDS.getAsString(TaskDS.reporterPhone1)));//跳转到拨
                startActivity(dialIntent);
                break;

            case R.id.authority_book:
                Intent toAuthority =  new Intent(DetailDDS.this, AuthorityActivity.class);//跳转超权限上报
                toAuthority.putExtra("type","DetailDDS");
                startActivity(toAuthority);
                break;

            case R.id.safe_book:
                Intent intent = new Intent(DetailDDS.this, FXSBActivity.class);
                intent.putExtra("from", "DetailDDS");
                //startActivity(intent);
                startActivityForResult(intent, MRequestCode.DDS);
                break;

            case R.id.ck_task_change:
            case R.id.task_change:
                Intent toDsappoint=new Intent(DetailDDS.this, DSappointment.class);
                toDsappoint.putExtra("type","DDS");
                startActivity(toDsappoint);
                AppManager.getAppManager().finishActivity(DetailDDS.this);
                break;
                //dds_detail_reporterPhone
            case R.id.dds_detail_reporterPhone:
                TelphoneUtil.toDial(mContext,selectDDS.getAsString(TaskDS.reporterPhone));
                break;
            case R.id.dds_detail_reporterPhone1:
                TelphoneUtil.toDial(mContext,selectDDS.getAsString(TaskDS.reporterPhone1));
                break;
            default:
                break;
        }
    }

    //获取认领数据
    public void claimTaskHttpData(String token,String frontrole,String taskid,Callback Callback){
        OkhttpDataHandler okhandler=new OkhttpDataHandler(mContext);
        okhandler.setmIsShowProgressDialog(true);
        okhandler.claimTaskHttp(token,frontrole,taskid,Callback);
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
                    selectDDS.getAsString(TaskDS.caseNo),selectDDS.getAsString(TaskDS.licenseno),risktask_start,risktask_limit, OkCallbackManager.getInstance().getTaskRiskCallback(mContext,DetailDDS.this));
        }
    }
}
