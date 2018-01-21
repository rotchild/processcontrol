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
import com.project.cx.processcontroljx.beans.RiskWarm;
import com.project.cx.processcontroljx.beans.SelectedTask;
import com.project.cx.processcontroljx.beans.TaskCK;
import com.project.cx.processcontroljx.beans.TaskDS;
import com.project.cx.processcontroljx.beans.TaskRole;
import com.project.cx.processcontroljx.net.OkhttpDataHandler;
import com.project.cx.processcontroljx.processmain.FXSBActivity;
import com.project.cx.processcontroljx.theme.MBaseActivity;
import com.project.cx.processcontroljx.ui.Dialog_risktip;
import com.project.cx.processcontroljx.utils.AppManager;
import com.project.cx.processcontroljx.utils.LayoutAddDanamic;
import com.project.cx.processcontroljx.utils.OkCallbackManager;
import com.project.cx.processcontroljx.utils.TelphoneUtil;
import com.project.cx.processcontroljx.utils.TimeUtil;
import com.project.cx.processcontroljx.utils.UserManager;

import java.util.ArrayList;

import okhttp3.Callback;

/**
 * Created by Administrator on 2017/11/30 0030.
 */

public class DetailDCK extends MBaseActivity implements View.OnClickListener {
    private static  String TAG;
    Context mContext;
    Dialog_risktip risktipDg=null;
    Button godetail_btn;
    ContentValues selectDCK;
    UserManager userManager=null;
    public ListView dck_detail_risklist;
   // OkhttpDataHandler okhandler;

    public ArrayList<ContentValues> riskArray=new ArrayList<ContentValues>();//上报任务列表数据
    int risktask_start=0;
    int risktask_limit=100;//?如何设置

    public int intentType;//已读未读

    TextView dck_caseNo,dck_caseTime,dck_outTime,dck_outNumber,dck_reporter,
             dck_reporterPhone,dck_licenseno,dck_riskstate,dck_risktype;
    ImageView setting_back;

    private Button btn_safe_book;
    private Button cbrs_btn;

    public LinearLayout dck_risktype_wrapper;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_dck);
        mContext=this;
        TAG=mContext.getClass().getSimpleName();
        initData();
        initView();
        getRisksWarnHttpData(userManager.getUserToken(), userManager.getFrontRole(), TaskRole.ds,
                selectDCK.getAsString(TaskDS.id), OkCallbackManager.getInstance().getRiskWarmCallback(mContext, DetailDCK.this));
        Log.e(TAG,"onCreate enter");
    }
    public void startActivity(Class activity,int intentType){
        Intent intent=new Intent(mContext,activity);
        intent.putExtra("intentType",intentType);//intentType 0:未读 1:已读
        startActivity(intent);
    }


    private void initView() {
/*        if(intentType==DetailIntentType.UNREAD){
            //并发获取风险提示
            getRisksWarnHttpData(userManager.getUserToken(),userManager.getFrontRole(), TaskRole.ck,
                    selectDCK.getAsString(TaskCK.id), OkCallbackManager.getInstance().getRiskWarmCallback(mContext,DetailDCK.this));
        }*/
        //getRiskWarnHttp多处用到，需要做的通用

        dck_risktype_wrapper= (LinearLayout) findViewById(R.id.dck_risktype_wrapper);
        //获取风险类型,为风险上报提供数据
        /*getRisksWarnHttpData(userManager.getUserToken(),userManager.getFrontRole(), TaskRole.ck,
                selectDCK.getAsString(TaskCK.id), OkCallbackManager.getInstance().getRiskWarmCallback(mContext,DetailDCK.this));*/

        dck_risktype_wrapper.removeAllViews();
        LayoutAddDanamic.getInstance().addRiskTypeListView(mContext,dck_risktype_wrapper, RiskWarm.riskwarmArray);


        dck_detail_risklist= (ListView) findViewById(R.id.dck_detail_risklist);
        godetail_btn=(Button)findViewById(R.id.go_detail);
        godetail_btn.setOnClickListener(this);
        btn_safe_book=(Button)findViewById(R.id.safe_book);
        btn_safe_book.setOnClickListener(this);

        dck_caseNo= (TextView) findViewById(R.id.dck_detail_caseNo);
        dck_caseTime= (TextView) findViewById(R.id.dck_detail_caseTime);
        dck_outTime= (TextView) findViewById(R.id.dck_detail_outTime);
        dck_outNumber= (TextView) findViewById(R.id.dck_detail_outNumber);
        dck_reporter= (TextView) findViewById(R.id.dck_detail_reporter);
        dck_reporterPhone= (TextView) findViewById(R.id.dck_detail_reporterPhone);
        dck_licenseno= (TextView) findViewById(R.id.dck_detail_licenseno);
        dck_riskstate= (TextView) findViewById(R.id.dck_detail_riskstate);
        dck_caseNo.setText(selectDCK.getAsString(TaskCK.caseNo));
        Long Ctime_long=Long.valueOf(selectDCK.getAsString(TaskCK.caseTime))*1000;
        String Ctime_str=String.valueOf(Ctime_long);
        String Ctime_result= TimeUtil.stampToDate(Ctime_str);
        dck_caseTime.setText(Ctime_result);
        Long Otime_long=Long.valueOf(selectDCK.getAsString(TaskCK.outTime))*1000;
        String Otime_str=String.valueOf(Otime_long);
        String Otime_result= TimeUtil.stampToDate(Otime_str);
        dck_outTime.setText(Otime_result);
        dck_outNumber.setText(selectDCK.getAsString(TaskCK.outNumber)+"次");
        dck_reporter.setText(selectDCK.getAsString(TaskCK.reporter));
        dck_reporterPhone.setText(selectDCK.getAsString(TaskCK.reporterPhone));
        dck_reporterPhone.setOnClickListener(this);
        dck_licenseno.setText(selectDCK.getAsString(TaskCK.licenseno));
        String riskstate_str="--";
        Long riskstate_long=Long.valueOf(selectDCK.getAsString(TaskCK.riskstate));
        if(riskstate_long==0){
            riskstate_str="未上报";
        }else if(riskstate_long==1){
            riskstate_str="已上报";
        }
        dck_riskstate.setText(riskstate_str);

        setting_back= (ImageView) findViewById(R.id.setting_back);
        setting_back.setOnClickListener(this);

        cbrs_btn= (Button) findViewById(R.id.cbrs_btn);
        cbrs_btn.setOnClickListener(this);
        if(selectDCK.getAsString(TaskCK.hurt_state).equals("1")){//只有hurt_state为1显示催办人伤
            cbrs_btn.setVisibility(View.VISIBLE);
        }else{
            cbrs_btn.setVisibility(View.GONE);
        }
        //dck_risktype.setText(selectDCK.getAsString(TaskCK.risktype));//dck_risktype is gone?
    }

    private void initData() {
        Intent intent=getIntent();
        intentType=intent.getIntExtra("intentType", DetailIntentType.UNREAD);
        selectDCK= SelectedTask.getTaskDCK();
        userManager= UserManager.getInstance();
        userManager.init(mContext);
        /*okhandler=new OkhttpDataHandler(mContext);
        okhandler.setmIsShowProgressDialog(true);*/
        //获取风险上报任务列表
      /*  getTaskRiskHttpData(userManager.getUserToken(),userManager.getFrontRole(), TaskRole.ck,
                selectDCK.getAsString(TaskCK.id),risktask_start,risktask_limit, OkCallbackManager.getInstance().getTaskRiskCallback(mContext,DetailDCK.this));*/
    }

    public void showRiskDg(ArrayList<ContentValues> riskwarmArray){
        risktipDg=new Dialog_risktip(mContext,R.style.RiskTipDialog,riskwarmArray);
        risktipDg.setCancelable(false);
        risktipDg.initDialog();
        risktipDg.setOnPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(risktipDg!=null&&risktipDg.isShowing()){
                    risktipDg.dismiss();
                    risktipDg=null;
                }
            }
        });
        risktipDg.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG,"onResume enter");
        if(userManager!=null){//更新risk表
            Log.i(TAG,"onResume  userManager !=null enter");
            getTaskRiskHttpData(userManager.getUserToken(),userManager.getFrontRole(), TaskRole.ck,
                    selectDCK.getAsString(TaskCK.caseNo),selectDCK.getAsString(TaskCK.licenseno),risktask_start,risktask_limit, OkCallbackManager.getInstance().getTaskRiskCallback(mContext,DetailDCK.this));
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.go_detail:

                break;
            case R.id.setting_back:
                AppManager.getAppManager().finishActivity();
                break;
            case R.id.safe_book:
               Intent intent=new Intent(DetailDCK.this, FXSBActivity.class);
               intent.putExtra("from","DetailDCK");
               startActivity(intent);
                break;
            case R.id.cbrs_btn://催办人伤
                urgeDealHurtHttpData(userManager.getUserToken(),userManager.getFrontRole(),selectDCK.getAsString(TaskCK.id),OkCallbackManager.getInstance().urgeDealhuitCallback(mContext,DetailDCK.this));
                break;
            case R.id.dck_detail_reporterPhone://dial
                TelphoneUtil.toDial(mContext,selectDCK.getAsString(TaskCK.reporterPhone));
                break;
            default:
                break;
        }
    }

    //获取风险上报任务数据
    public void getTaskRiskHttpData(String token,String frontrole, String task_role,String caseNo,String licenseNo,int start,int limit,Callback Callback){
        OkhttpDataHandler okhandler=new OkhttpDataHandler(mContext);
        okhandler.setmIsShowProgressDialog(true);
        okhandler.getTaskRiskHttp(token,frontrole,task_role,caseNo,licenseNo,start,limit,Callback);
    /*    if(okhandler!=null){
            okhandler.getTaskRiskHttp(token,frontrole,task_role,taskid,start,limit,Callback);
        }else{
            Log.e(TAG,"okhandler is null");
        }*/
    }
    //获取风险提示数据
    public void getRisksWarnHttpData(String token,String frontrole, String task_role,String taskid,Callback Callback){
        OkhttpDataHandler okhandler=new OkhttpDataHandler(mContext);
        okhandler.setmIsShowProgressDialog(true);
        okhandler.getRisksWarnHttp(token,frontrole,task_role,taskid,Callback);
/*        if(okhandler!=null){
            okhandler.getRisksWarnHttp(token,frontrole,task_role,taskid,Callback);
        }else{
            Log.e(TAG,"okhandler is null");
        }*/
    }

    /**
     * 获取催办人伤数据
     * @param token
     * @param frontrole
     * @param taskid
     * @param Callback
     */
    public void urgeDealHurtHttpData(String token,String frontrole,String taskid,Callback Callback){
        OkhttpDataHandler okhandler=new OkhttpDataHandler(mContext);
        okhandler.setmIsShowProgressDialog(true);
        okhandler.urgeDealHurtHttp(token,frontrole,taskid,Callback);
    }
}
