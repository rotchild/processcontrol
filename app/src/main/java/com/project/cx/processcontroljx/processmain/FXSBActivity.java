package com.project.cx.processcontroljx.processmain;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.cx.processcontroljx.R;
import com.project.cx.processcontroljx.beans.RiskWarm;
import com.project.cx.processcontroljx.beans.SelectedTask;
import com.project.cx.processcontroljx.beans.TaskCK;
import com.project.cx.processcontroljx.beans.TaskDS;
import com.project.cx.processcontroljx.beans.TaskRole;
import com.project.cx.processcontroljx.net.OkhttpDataHandler;
import com.project.cx.processcontroljx.theme.MBaseActivity;
import com.project.cx.processcontroljx.ui.Dialog_detailtip;
import com.project.cx.processcontroljx.utils.AppManager;
import com.project.cx.processcontroljx.utils.OkCallbackManager;
import com.project.cx.processcontroljx.utils.TelphoneUtil;
import com.project.cx.processcontroljx.utils.UserManager;

import java.util.ArrayList;
import java.util.Iterator;

import okhttp3.Callback;

/**
 * Created by Administrator on 2017/12/7 0007.
 */

public class FXSBActivity extends MBaseActivity implements View.OnClickListener{
    private final String TAG=getClass().getSimpleName();
    Context mContext;
    ContentValues selectTask;
    UserManager userManager;
    Dialog_detailtip detailtip=null;
    TextView fxsb_edit_caseNo,fxsb_edit_caseTime,fxsb_edit_outTime,
             fxsb_edit_reporter,fxsb_edit_reporterPhone,fxsb_edit_vehicleBrand;
//风险类型
    TextView fxsb_edit_type1,fxsb_edit_type2,fxsb_edit_type3,fxsb_edit_type4,fxsb_edit_type5,
             fxsb_edit_type6,fxsb_edit_type7,fxsb_edit_type8,fxsb_edit_type9,fxsb_edit_typeqt;

    int isSelect1,isSelect2,isSelect3,isSelect4,isSelect5,isSelect6,isSelect7,isSelect8,isSelect9,isSelectqt=0;//0未选中,1选中


    ArrayList<Integer> risktypesolid=new ArrayList<Integer>();//存储来自接口的风险类型;
    ArrayList<Integer> risktypecustom=new ArrayList<Integer>();//存储来自接口的风险类型;
    ArrayList<Integer> risktypeall=new ArrayList<Integer>();//存储来自接口的风险类型;
    EditText qt_input;
    EditText bz_input;

    Button fxsb_edit_yes,fxsb_edit_no;
    ImageView fxsb_go_back;

    String tasktype="";//CK,DS 均有风险上报
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG,"onCreate");
        setContentView(R.layout.activity_fxsb_edit);
        mContext=this;
        initData();//must before initView
        initView();
    }

    private void initView() {
        fxsb_go_back= (ImageView) findViewById(R.id.setting_back);
        fxsb_edit_caseNo= (TextView) findViewById(R.id.fxsb_edit_caseNo);
        fxsb_edit_caseTime= (TextView) findViewById(R.id.fxsb_edit_caseTime);
        fxsb_edit_outTime= (TextView) findViewById(R.id.fxsb_edit_outTime);
        fxsb_edit_reporter= (TextView) findViewById(R.id.fxsb_edit_reporter);
        fxsb_edit_reporterPhone= (TextView) findViewById(R.id.fxsb_edit_reporterPhone);
        fxsb_edit_reporterPhone.setOnClickListener(this);
        fxsb_edit_vehicleBrand= (TextView) findViewById(R.id.fxsb_edit_vehicleBrand);

        fxsb_edit_yes=(Button) findViewById(R.id.fxsb_edit_yes);
        fxsb_edit_yes.setOnClickListener(this);
        fxsb_edit_no=(Button) findViewById(R.id.fxsb_edit_no);
        fxsb_edit_no.setOnClickListener(this);

        if(tasktype.equals("CK")){
            fxsb_edit_caseNo.setText(selectTask.getAsString(TaskCK.caseNo));
            fxsb_edit_caseTime.setText(selectTask.getAsString(TaskCK.caseTime));
            fxsb_edit_outTime.setText(selectTask.getAsString(TaskCK.outTime));
            fxsb_edit_reporter.setText(selectTask.getAsString(TaskCK.reporter));
            fxsb_edit_reporterPhone.setText(selectTask.getAsString(TaskCK.reporterPhone));
            fxsb_edit_vehicleBrand.setText(selectTask.getAsString(TaskCK.vehicleBrand));
        }else if(tasktype.equals("DS")){
            fxsb_edit_caseNo.setText(selectTask.getAsString(TaskDS.caseNo));
            fxsb_edit_caseTime.setText(selectTask.getAsString(TaskDS.caseTime));
            fxsb_edit_outTime.setText(selectTask.getAsString(TaskDS.outTime));
            fxsb_edit_reporter.setText(selectTask.getAsString(TaskDS.reporter));
            fxsb_edit_reporterPhone.setText(selectTask.getAsString(TaskDS.reporterPhone));
            fxsb_edit_vehicleBrand.setText(selectTask.getAsString(TaskDS.vehicleBrand));
        }


        //风险类型
        fxsb_edit_type1=(TextView) findViewById(R.id.fxsb_edit_type1);
        fxsb_edit_type2=(TextView) findViewById(R.id.fxsb_edit_type2);
        fxsb_edit_type3=(TextView) findViewById(R.id.fxsb_edit_type3);
        fxsb_edit_type4=(TextView) findViewById(R.id.fxsb_edit_type4);
        fxsb_edit_type5=(TextView) findViewById(R.id.fxsb_edit_type5);
        fxsb_edit_type6=(TextView) findViewById(R.id.fxsb_edit_type6);
        fxsb_edit_type7=(TextView) findViewById(R.id.fxsb_edit_type7);
        fxsb_edit_type8=(TextView) findViewById(R.id.fxsb_edit_type8);
        fxsb_edit_type9=(TextView) findViewById(R.id.fxsb_edit_type9);
        fxsb_edit_typeqt=(TextView) findViewById(R.id.fxsb_edit_typeqt);

        qt_input=(EditText)findViewById(R.id.qt_input);

        bz_input=(EditText) findViewById(R.id.bz_input);

        fxsb_go_back.setOnClickListener(this);

        fxsb_edit_type1.setOnClickListener(this);
        fxsb_edit_type2.setOnClickListener(this);
        fxsb_edit_type3.setOnClickListener(this);
        fxsb_edit_type4.setOnClickListener(this);
        fxsb_edit_type5.setOnClickListener(this);
        fxsb_edit_type6.setOnClickListener(this);
        fxsb_edit_type7.setOnClickListener(this);
        fxsb_edit_type8.setOnClickListener(this);
        fxsb_edit_type9.setOnClickListener(this);
        fxsb_edit_typeqt.setOnClickListener(this);

        LinearLayout risk_notice_tip=(LinearLayout) findViewById(R.id.risk_notice_tip);
        risk_notice_tip.setOnClickListener(this);

        setRiskType();//须放在onclickListener后面
    }

    private void initData() {
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        String from=bundle.getString("from");
        if(from.equals("DetailDCK")){
            selectTask=SelectedTask.getTaskDCK();
            tasktype="CK";
        }else if(from.equals("DetailYCK")){
            selectTask= SelectedTask.getTaskYCK();
            tasktype="CK";
        }else if(from.equals("DetailDDS")){
            selectTask= SelectedTask.getTaskDDS();
            tasktype="DS";
        }else if(from.equals("DetailDSZ")){
            selectTask= SelectedTask.getTaskDSZ();
            tasktype="DS";
        }else if(from.equals("DetailYDS")){
            selectTask= SelectedTask.getTaskYDS();
            tasktype="DS";
        }
        userManager=UserManager.getInstance();
        userManager.init(mContext);
    }
    //根据riskwarm的返回数据设置选中状态,并且存储
    private void setRiskType(){
        ArrayList<String> risknameArray=new ArrayList<String>();
        Iterator<ContentValues> iterator= RiskWarm.riskwarmArray.iterator();
        while (iterator.hasNext()){
            String riskname =iterator.next().getAsString(RiskWarm.RISKNAME);
            risknameArray.add(riskname);
            Log.i(TAG,"riskname"+riskname);
        }
       if(risknameArray.contains("风险类型1")){
           setSelecct(R.id.fxsb_edit_type1);
           fxsb_edit_type1.setClickable(false);
           risktypesolid.add(1);
       }
        if(risknameArray.contains("风险类型2")){
            setSelecct(R.id.fxsb_edit_type2);
            fxsb_edit_type2.setClickable(false);
            risktypesolid.add(2);
        }
        if(risknameArray.contains("风险类型3")){
            setSelecct(R.id.fxsb_edit_type3);
            fxsb_edit_type3.setClickable(false);
            risktypesolid.add(3);
        }
        if(risknameArray.contains("风险类型4")){
            setSelecct(R.id.fxsb_edit_type4);
            fxsb_edit_type4.setClickable(false);
            risktypesolid.add(4);
        }
        if(risknameArray.contains("风险类型5")){
            setSelecct(R.id.fxsb_edit_type5);
            fxsb_edit_type5.setClickable(false);
            risktypesolid.add(5);
        }
        if(risknameArray.contains("风险类型6")){
            setSelecct(R.id.fxsb_edit_type6);
            fxsb_edit_type6.setClickable(false);
            risktypesolid.add(6);
        }
        if(risknameArray.contains("风险类型7")){
            setSelecct(R.id.fxsb_edit_type7);
            fxsb_edit_type7.setClickable(false);
            risktypesolid.add(7);
        }
        if(risknameArray.contains("风险类型8")){
            setSelecct(R.id.fxsb_edit_type8);
            fxsb_edit_type8.setClickable(false);
            risktypesolid.add(8);
        }
        if(risknameArray.contains("风险类型9")){
            setSelecct(R.id.fxsb_edit_type9);
            fxsb_edit_type9.setClickable(false);
            risktypesolid.add(9);
        }
    }

    //提交风险上报数据
    public void commitRiskRecordData(String token,String frontrole,String taskid,String task_role,String risktype,String risktype_sys,String risktype_man,
                                     String others, String remark,Callback Callback){
        OkhttpDataHandler okhandler=new OkhttpDataHandler(mContext);
        okhandler.setmIsShowProgressDialog(true);
        okhandler.commitRiskRecordHttp(token,frontrole,taskid,task_role,risktype,risktype_sys,risktype_man,others,remark,Callback);
    }
    public void  detailShow(){
        detailtip=new Dialog_detailtip(mContext,R.style.RiskTipDialog);
        detailtip.setCancelable(false);
        detailtip.initDialog();
        detailtip.setOnPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(detailtip!=null&&detailtip.isShowing()){
                    detailtip.dismiss();
                    detailtip=null;
                }
            }
        });
        detailtip.show();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fxsb_edit_yes:
                String risktypeStr=getRiskTypeString();
                String others=qt_input.getText().toString().trim();
                String remark=bz_input.getText().toString().trim();
                risktypecustom=getLeft(risktypeall,risktypesolid);
                String risktype_sysStr=array2String(risktypesolid);
                String risktype_manStr=array2String(risktypecustom);

                if(tasktype.equals("CK")){
                    commitRiskRecordData(userManager.getUserToken(),userManager.getFrontRole(),selectTask.getAsString(TaskCK.id),
                            TaskRole.ck,risktypeStr,risktype_sysStr,risktype_manStr, others,remark, OkCallbackManager.getInstance().commitRiskRecordCallback(mContext,FXSBActivity.this));
                }else if(tasktype.equals("DS")){
                    commitRiskRecordData(userManager.getUserToken(),userManager.getFrontRole(),selectTask.getAsString(TaskDS.id),
                            TaskRole.ds,risktypeStr,risktype_sysStr,risktype_manStr, others,remark, OkCallbackManager.getInstance().commitRiskRecordCallback(mContext,FXSBActivity.this));
                }

                break;
            case R.id.risk_notice_tip:
                detailShow();
                break;
            case R.id.setting_back:
                AppManager.getAppManager().finishActivity();
                break;
            case R.id.fxsb_edit_no:
                AppManager.getAppManager().finishActivity();
                break;
            case R.id.fxsb_edit_type1:
            case R.id.fxsb_edit_type2:
            case R.id.fxsb_edit_type3:
            case R.id.fxsb_edit_type4:
            case R.id.fxsb_edit_type5:
            case R.id.fxsb_edit_type6:
            case R.id.fxsb_edit_type7:
            case R.id.fxsb_edit_type8:
            case R.id.fxsb_edit_type9:
            case R.id.fxsb_edit_typeqt:
                setSelecct(v.getId());
                break;
            case R.id.fxsb_edit_reporterPhone:
                TelphoneUtil.toDial(mContext,selectTask.getAsString(TaskDS.reporterPhone));
                break;

            default:
                break;
        }
    }
    public void setSelecct(int viewId){
       /* TextView selectedView=(TextView) findViewById(viewId);
        selectedView.setBackground(mContext.getResources().getDrawable(R.drawable.select));*/
       switch (viewId){
           case R.id.fxsb_edit_type1:
                   if(isSelect1==0){
                       isSelect1=1;
                       fxsb_edit_type1.setBackground(mContext.getResources().getDrawable(R.drawable.select));
                   }else if(isSelect1==1){
                       isSelect1=0;
                       fxsb_edit_type1.setBackground(mContext.getResources().getDrawable(R.drawable.type_border_radius));
                   }
               break;
           case R.id.fxsb_edit_type2:
                   if(isSelect2==0){
                       isSelect2=1;
                       fxsb_edit_type2.setBackground(mContext.getResources().getDrawable(R.drawable.select));
                   }else if(isSelect2==1){
                       isSelect2=0;
                       fxsb_edit_type2.setBackground(mContext.getResources().getDrawable(R.drawable.type_border_radius));
                   }
               break;
           case R.id.fxsb_edit_type3:
               if(isSelect3==0){
                   isSelect3=1;
                   fxsb_edit_type3.setBackground(mContext.getResources().getDrawable(R.drawable.select));
               }else if(isSelect3==1){
                   isSelect3=0;
                   fxsb_edit_type3.setBackground(mContext.getResources().getDrawable(R.drawable.type_border_radius));
               }
               break;
           case R.id.fxsb_edit_type4:
               if(isSelect4==0){
                   isSelect4=1;
                   fxsb_edit_type4.setBackground(mContext.getResources().getDrawable(R.drawable.select));
               }else if(isSelect4==1){
                   isSelect4=0;
                   fxsb_edit_type4.setBackground(mContext.getResources().getDrawable(R.drawable.type_border_radius));
               }
               break;
           case R.id.fxsb_edit_type5:
               if(isSelect5==0){
                   isSelect5=1;
                   fxsb_edit_type5.setBackground(mContext.getResources().getDrawable(R.drawable.select));
               }else if(isSelect5==1){
                   isSelect5=0;
                   fxsb_edit_type5.setBackground(mContext.getResources().getDrawable(R.drawable.type_border_radius));
               }
               break;
           case R.id.fxsb_edit_type6:
               if(isSelect6==0){
                   isSelect6=1;
                   fxsb_edit_type6.setBackground(mContext.getResources().getDrawable(R.drawable.select));
               }else if(isSelect6==1){
                   isSelect6=0;
                   fxsb_edit_type6.setBackground(mContext.getResources().getDrawable(R.drawable.type_border_radius));
               }
               break;
           case R.id.fxsb_edit_type7:
               if(isSelect7==0){
                   isSelect7=1;
                   fxsb_edit_type7.setBackground(mContext.getResources().getDrawable(R.drawable.select));
               }else if(isSelect7==1){
                   isSelect7=0;
                   fxsb_edit_type7.setBackground(mContext.getResources().getDrawable(R.drawable.type_border_radius));
               }
               break;
           case R.id.fxsb_edit_type8:
               if(isSelect8==0){
                   isSelect8=1;
                   fxsb_edit_type8.setBackground(mContext.getResources().getDrawable(R.drawable.select));
               }else if(isSelect8==1){
                   isSelect8=0;
                   fxsb_edit_type8.setBackground(mContext.getResources().getDrawable(R.drawable.type_border_radius));
               }
               break;
           case R.id.fxsb_edit_type9:
               if(isSelect9==0){
                   isSelect9=1;
                   fxsb_edit_type9.setBackground(mContext.getResources().getDrawable(R.drawable.select));
               }else if(isSelect9==1){
                   isSelect9=0;
                   fxsb_edit_type9.setBackground(mContext.getResources().getDrawable(R.drawable.type_border_radius));
               }
               break;
           case R.id.fxsb_edit_typeqt:
               if(isSelectqt==0){
                   isSelectqt=1;
                   fxsb_edit_typeqt.setBackground(mContext.getResources().getDrawable(R.drawable.select));
                   //设置可编辑
                /*   qt_input.setFocusableInTouchMode(true);
                   qt_input.setFocusable(true);
                   qt_input.requestFocus();*/
                qt_input.setVisibility(View.VISIBLE);
               }else if(isSelectqt==1){
                   isSelectqt=0;
                   fxsb_edit_typeqt.setBackground(mContext.getResources().getDrawable(R.drawable.type_border_radius));
                   //设置不可编辑
                 /*  qt_input.setFocusable(false);
                   qt_input.setFocusableInTouchMode(false);*/
                   qt_input.setVisibility(View.GONE);
               }
               break;

           default:
               break;
       }
    }
//获取格式化的risktype
    public String getRiskTypeString(){
        StringBuffer risktype=new StringBuffer();
        String risktypeStr="";
        if(isSelect1==1){
            risktype.append("1,");
            risktypeall.add(1);
        }
        if(isSelect2==1){
            risktype.append("2,");
            risktypeall.add(2);
        }
        if(isSelect3==1){
            risktype.append("3,");
            risktypeall.add(3);
        }
        if(isSelect4==1){
            risktype.append("4,");
            risktypeall.add(4);
        }
        if(isSelect5==1){
            risktype.append("5,");
            risktypeall.add(5);
        }
        if(isSelect6==1){
            risktype.append("6,");
            risktypeall.add(6);
        }
        if(isSelect7==1){
            risktype.append("7,");
            risktypeall.add(7);
        }
        if(isSelect8==1){
            risktype.append("8,");
            risktypeall.add(8);
        }
        if(isSelect9==1){
            risktype.append("9,");
            risktypeall.add(9);
        }
        risktypeStr=risktype.toString();
        if(risktypeStr.length()==0){
            return "";
        }
        return risktypeStr.substring(0,risktypeStr.length()-1);//去掉最后的,
    }

    /**
     * 获取差值
     * @param all
     * @param solid
     * @return
     */
    public ArrayList<Integer> getLeft(ArrayList<Integer> all,ArrayList<Integer> solid){
        ArrayList<Integer> left=new ArrayList<Integer>();
        ArrayList<Integer> longlist=new ArrayList<Integer>();
        ArrayList<Integer> shortlist=new ArrayList<Integer>();

        for(int i:all){
            longlist.add(i);
        }
        for(int i:solid){
            shortlist.add(i);
        }

/*        for(int i:solid){
            if(longlist.contains(i)){
                longlist.remove(i);
            }
        }*/
        longlist.removeAll(shortlist);
        left=longlist;
        return left;
    }

    /**
     * 将数组转为需要的字符串"1,2,3"
     * @param arrayList
     * @return
     */
    public String array2String(ArrayList<Integer> arrayList){
        StringBuffer arrayBuffer=new StringBuffer();
        String arrayStr="";
        if(arrayList.size()>0){
            for(int i:arrayList){
                arrayBuffer.append(String.valueOf(i)+",");
            }
            arrayStr=arrayBuffer.toString();
            return arrayStr.substring(0,arrayStr.length()-1);
        }
        arrayStr=arrayBuffer.toString();
        return arrayStr;

    }
}
