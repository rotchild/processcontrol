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
import com.project.cx.processcontroljx.beans.SelectedTask;
import com.project.cx.processcontroljx.beans.TaskDS;
import com.project.cx.processcontroljx.beans.TaskRole;
import com.project.cx.processcontroljx.net.OkhttpDataHandler;
import com.project.cx.processcontroljx.theme.MBaseActivity;
import com.project.cx.processcontroljx.ui.Dialog_risktip;
import com.project.cx.processcontroljx.utils.AppManager;
import com.project.cx.processcontroljx.utils.OkCallbackManager;
import com.project.cx.processcontroljx.utils.TimeUtil;
import com.project.cx.processcontroljx.utils.UserManager;

import okhttp3.Callback;


/**
 * Created by Administrator on 2017/11/30 0030.
 */

public class DetailHP extends MBaseActivity implements View.OnClickListener {
    Context mContext;
    public int intentType;//已读未读
    Button approve_btn;
    Dialog_risktip risktipDg=null;
    ImageView setting_back;
    ContentValues selectHP;
    UserManager userManager;
    TextView hp_detail_ticket_state,hp_detail_caseNo,hp_detail_caseTime,hp_detail_outTime,hp_detail_licenseno,
            hp_detail_vehicleBrand,hp_detail_car_role,hp_detail_risklevel,hp_detail_should_ticket_garage,
            hp_detail_should_ticket_amount,hp_detail_real_ticket_garage,hp_detail_real_ticket_time,hp_detail_cooperative_name,
            hp_detail_ticket_amount,hp_ticket_amount_list1,hp_ticket_amount_list2,hp_ticket_amount_list3,hp_ticket_amount_list4,
            hp_ticket_amount_list5,hp_ticket_amount_list6;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_hp);
        mContext=this;
        initData();
        initView();
    }

    private void initView() {
        getRisksWarnHttpData(userManager.getUserToken(),userManager.getFrontRole(), TaskRole.ds,
                selectHP.getAsString(TaskDS.id), OkCallbackManager.getInstance().getRiskWarmCallback(mContext,DetailHP.this));

        approve_btn=(Button)findViewById(R.id.approve_btn);
        approve_btn.setOnClickListener(this);
        setting_back= (ImageView) findViewById(R.id.setting_back);
        setting_back.setOnClickListener(this);


        hp_detail_ticket_state= (TextView) findViewById(R.id.hp_detail_ticket_state);
        hp_detail_caseNo= (TextView) findViewById(R.id.hp_detail_caseNo);
        hp_detail_caseTime= (TextView) findViewById(R.id.hp_detail_caseTime);
        hp_detail_outTime= (TextView) findViewById(R.id.hp_detail_outTime);
        hp_detail_licenseno= (TextView) findViewById(R.id.hp_detail_licenseno);
        hp_detail_vehicleBrand= (TextView) findViewById(R.id.hp_detail_vehicleBrand);
        hp_detail_car_role= (TextView) findViewById(R.id.hp_detail_car_role);
        hp_detail_risklevel= (TextView) findViewById(R.id.hp_detail_risklevel);

        hp_detail_should_ticket_garage= (TextView) findViewById(R.id.hp_detail_should_ticket_garage);
        hp_detail_should_ticket_amount= (TextView) findViewById(R.id.hp_detail_should_ticket_amount);
        hp_detail_real_ticket_garage= (TextView) findViewById(R.id.hp_detail_real_ticket_garage);
        hp_detail_real_ticket_time= (TextView) findViewById(R.id.hp_detail_real_ticket_time);
        hp_detail_cooperative_name= (TextView) findViewById(R.id.hp_detail_cooperative_name);
        hp_detail_ticket_amount= (TextView) findViewById(R.id.hp_detail_ticket_amount);
        LinearLayout hp_ticket_amount_list1= (LinearLayout)findViewById(R.id.hp_ticket_amount_list1);
        LinearLayout hp_ticket_amount_list2= (LinearLayout)findViewById(R.id.hp_ticket_amount_list2);
        LinearLayout hp_ticket_amount_list3= (LinearLayout)findViewById(R.id.hp_ticket_amount_list3);
        LinearLayout hp_ticket_amount_list4= (LinearLayout)findViewById(R.id.hp_ticket_amount_list4);
        LinearLayout hp_ticket_amount_list5= (LinearLayout)findViewById(R.id.hp_ticket_amount_list5);
        LinearLayout hp_ticket_amount_list6= (LinearLayout)findViewById(R.id.hp_ticket_amount_list6);

        String ticketstate_str="--";
        Long tickestate_long=Long.valueOf(selectHP.getAsString(TaskDS.ticket_state));
        if(tickestate_long==-1){
            ticketstate_str="未获票";
            hp_ticket_amount_list1.setVisibility(View.VISIBLE);
            hp_ticket_amount_list2.setVisibility(View.VISIBLE);
            hp_ticket_amount_list3.setVisibility(View.GONE);
            hp_ticket_amount_list4.setVisibility(View.GONE);
            hp_ticket_amount_list5.setVisibility(View.GONE);
            hp_ticket_amount_list6.setVisibility(View.GONE);
        }else if(tickestate_long==1){
            ticketstate_str="已获票";
            hp_ticket_amount_list1.setVisibility(View.GONE);
            hp_ticket_amount_list2.setVisibility(View.GONE);
            hp_ticket_amount_list3.setVisibility(View.VISIBLE);
            hp_ticket_amount_list4.setVisibility(View.VISIBLE);
            hp_ticket_amount_list5.setVisibility(View.VISIBLE);
            hp_ticket_amount_list6.setVisibility(View.VISIBLE);
        }else{
            ticketstate_str="--";
        }
        hp_detail_ticket_state.setText(ticketstate_str);
        hp_detail_caseNo.setText(selectHP.getAsString(TaskDS.caseNo));
        Long Ctime_long=Long.valueOf(selectHP.getAsString(TaskDS.caseTime))*1000;
        String Ctime_str=String.valueOf(Ctime_long);
        String Ctime_result= TimeUtil.stampToDate(Ctime_str);
        hp_detail_caseTime.setText(Ctime_result);
        Long Otime_long=Long.valueOf(selectHP.getAsString(TaskDS.outTime))*1000;
        String Otime_str=String.valueOf(Otime_long);
        String Otime_result= TimeUtil.stampToDate(Otime_str);
        hp_detail_outTime.setText(Otime_result);
        hp_detail_licenseno.setText(selectHP.getAsString(TaskDS.licenseno));
        hp_detail_vehicleBrand.setText(selectHP.getAsString(TaskDS.vehicleBrand));
        String rolestate_str="--";
        Long rolestate_long=Long.valueOf(selectHP.getAsString(TaskDS.car_role));
        if(rolestate_long==1){
            rolestate_str="标的车";
        }else if(rolestate_long==2){
            rolestate_str="三者车";
        }
        hp_detail_car_role.setText(rolestate_str);

        String levelstate_str="--";
        Long levelstate_long=Long.valueOf(selectHP.getAsString(TaskDS.risklevel));
        if(levelstate_long==0){
            levelstate_str="无风险";
        }else if(levelstate_long==1){
            levelstate_str="风险案件";
        }
        hp_detail_risklevel.setText(levelstate_str);
        hp_detail_should_ticket_garage.setText(selectHP.getAsString(TaskDS.should_ticket_garage));
        hp_detail_should_ticket_amount.setText(selectHP.getAsString(TaskDS.should_ticket_amount)+"元");
        hp_detail_real_ticket_garage.setText(selectHP.getAsString(TaskDS.real_ticket_garage));

        Long Htime_long=Long.valueOf(selectHP.getAsString(TaskDS.real_ticket_time))*1000;
        String Htime_str=String.valueOf(Htime_long);
        String Htime_result= TimeUtil.stampToDate(Htime_str);
        hp_detail_real_ticket_time.setText(Htime_result);
        hp_detail_cooperative_name.setText(selectHP.getAsString(TaskDS.cooperative_name));
        hp_detail_ticket_amount.setText(selectHP.getAsString(TaskDS.ticket_amount)+"元");


    }

    private void initData() {
        Intent intent=getIntent();
        intentType=intent.getIntExtra("intentType", DetailIntentType.UNREAD);
        selectHP= SelectedTask.getTaskHP();
        userManager=UserManager.getInstance();
        userManager.init(mContext);
        Log.i("selectHP","selectHP"+selectHP);

    }
    public void showRiskDg(){
        risktipDg=new Dialog_risktip(mContext,R.style.RiskTipDialog);
        risktipDg.setCancelable(false);
        risktipDg.initDialog();
        risktipDg.setOnPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String risktipReason=risktipDg.getRistipReason();

                TicketReportHttp(userManager.getUserToken(),userManager.getFrontRole(),selectHP.getAsString(TaskDS.id),
                        risktipReason, OkCallbackManager.getInstance().verifyTicketCallback(mContext,DetailHP.this));
                if(risktipDg!=null&&risktipDg.isShowing()){
                    risktipDg.dismiss();
                    risktipDg=null;
                }
            }
        });
        risktipDg.setOnNagetiveListener(new View.OnClickListener() {
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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.approve_btn:
                showRiskDg();
                break;
            case R.id.setting_back:
                AppManager.getAppManager().finishActivity();
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

    //提交上报审批数据
    public void TicketReportHttp(String token, String frontrole, String taskid, String remarks, Callback Callback){
        OkhttpDataHandler okhandler=new OkhttpDataHandler(mContext);
        okhandler.setmIsShowProgressDialog(true);
        okhandler.TicketReportHttp(token,frontrole,taskid,remarks,Callback);
    }
}
