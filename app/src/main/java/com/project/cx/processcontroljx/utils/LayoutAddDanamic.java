package com.project.cx.processcontroljx.utils;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.cx.processcontroljx.R;
import com.project.cx.processcontroljx.beans.SelectedTask;
import com.project.cx.processcontroljx.beans.SelectedThirdCar;
import com.project.cx.processcontroljx.beans.TaskRisk;
import com.project.cx.processcontroljx.beans.TaskRiskWarm;
import com.project.cx.processcontroljx.beans.TaskThirdcars;
import com.project.cx.processcontroljx.processmain.FxsbDetailActivity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/12/20 0020.
 */

public class LayoutAddDanamic {
    private static LayoutAddDanamic mInstance;
    private Context mContext;

    public interface DanamicItemClickListener{
        public void onClick();
    }
    public interface DanamicAddClickListener{
        public void onClick(String thirdcarStr);
    }
    public interface DanamicCancelClickListener{
        public void onClick();
    }

    private DanamicItemClickListener mdanamicDetailClickListener,mdanamicyuyueClickListener;
    private DanamicAddClickListener mdanamicaddThirdcar;
    private DanamicCancelClickListener mdanamicancelThirdcar;

    public void setDanamicItemDetailClick(DanamicItemClickListener mclicklistener){
        mdanamicDetailClickListener=mclicklistener;
    }
    public void setDanamicItemYuyueClick(DanamicItemClickListener mclicklistener){
        mdanamicyuyueClickListener=mclicklistener;
    }

    public void setDanamicItemaddClick(DanamicAddClickListener mclicklistener){
        mdanamicaddThirdcar=mclicklistener;
    }

    public void setDanamicItemCancelClick(DanamicCancelClickListener mclicklistener){
        mdanamicancelThirdcar=mclicklistener;
    }

    private LayoutAddDanamic(){}
    public static LayoutAddDanamic getInstance(){
        if(mInstance==null){
            mInstance=new LayoutAddDanamic();
        }
        return mInstance;
    }

    public void init(Context ctx){
        mContext=ctx;
    }

public void loadThirdCarLayout(LinearLayout parentLayout, ArrayList<ContentValues> thirdcarDatas){
    for(ContentValues data:thirdcarDatas){
        addChildView(parentLayout,data);
    }
}

public void addChildView(LinearLayout parent,ContentValues data){
    RelativeLayout childRL=new RelativeLayout(mContext);
    RelativeLayout.LayoutParams rlPara=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
    rlPara.setMargins(10,10,10,10);
    TextView carNoTV=new TextView(mContext);
    carNoTV.setText(data.getAsString(TaskThirdcars.thirdlicenseno));
    Boolean hasAppoint=false;
    if(data.getAsString(TaskThirdcars.isAppoint).equals("1")){
        hasAppoint=true;
    }
    Button operateBtn=new Button(mContext);
    RelativeLayout.LayoutParams operatePara=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
    operatePara.setMargins(10,0,100,0);
    operatePara.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
    operatePara.addRule(RelativeLayout.CENTER_VERTICAL);
    operateBtn.setLayoutParams(operatePara);
    if(hasAppoint){
        operateBtn.setText("详情");
    }else{
        operateBtn.setText("定损预约");
    }
}

//风险流程追踪列表
public void addTraceListView(Context context,LinearLayout parentLayout,ArrayList<ContentValues> datas){
LinearLayout listLayout=new LinearLayout(context);
LinearLayout.LayoutParams listPra=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
listLayout.setLayoutParams(listPra);
listLayout.setOrientation(LinearLayout.VERTICAL);
parentLayout.addView(listLayout);
listLayout.setPadding(5,0,5,0);
for(int i=0;i<datas.size();i++){
    addItem(context,listLayout,datas.get(i));
}

}
    //风险流程追踪列表item
public void addItem(final Context context, LinearLayout parentLayout, ContentValues data){
    String createTimeStr=data.getAsString(TaskRisk.createtime);//待修改
    String stateStr=data.getAsString(TaskRisk.state);
    String btnText="详情";

    Long Ctime_long=Long.valueOf(createTimeStr)*1000;
    String Ctime_str=String.valueOf(Ctime_long);
    String Ctime_result= TimeUtil.stampToDate(Ctime_str);

    String mstate=stateStr;
    String state="";
    if(mstate.equals("0")){
        state="审核中";
    }else if(mstate.equals("1")){
        state="审核通过";
    }if(mstate.equals("2")){
        state="驳回";
    }

    LinearLayout itemLayout=new LinearLayout(context);
    LinearLayout.LayoutParams itemPra=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
    //itemPra.setMargins(5,5,5,5);
    itemLayout.setLayoutParams(itemPra);
    itemLayout.setGravity(Gravity.CENTER);
    itemLayout.setOrientation(LinearLayout.HORIZONTAL);

    TextView createTimeTV=new TextView(context);
    LinearLayout.LayoutParams createTimePra=new LinearLayout.LayoutParams(1,56,3);
    //createTimePra.gravity= Gravity.CENTER;
    createTimeTV.setLayoutParams(createTimePra);
    createTimeTV.setGravity(Gravity.CENTER);
    createTimeTV.setText(Ctime_result);
    createTimeTV.setTextColor(context.getResources().getColor(R.color.maintext));
    createTimeTV.setTextSize(TypedValue.COMPLEX_UNIT_DIP,14);
    itemLayout.addView(createTimeTV);

    TextView stateTV=new TextView(context);
    LinearLayout.LayoutParams stateTVPra=new LinearLayout.LayoutParams(1,56,1);
    //stateTVPra.gravity= Gravity.CENTER;
    stateTV.setLayoutParams(stateTVPra);
    stateTV.setGravity(Gravity.CENTER);
    stateTV.setText(state);
    stateTV.setTextColor(context.getResources().getColor(R.color.check_green));
    stateTV.setTextSize(TypedValue.COMPLEX_UNIT_DIP,14);
    itemLayout.addView(stateTV);

    RelativeLayout btnwrap=new RelativeLayout(context);
    LinearLayout.LayoutParams btnwrapPra=new LinearLayout.LayoutParams(1,(int)context.getResources().getDimension(R.dimen.list_height),1);
    //btnwrapPra.gravity=Gravity.CENTER;
    btnwrap.setLayoutParams(btnwrapPra);
    btnwrap.setGravity(Gravity.CENTER);
    itemLayout.addView(btnwrap);

    TextView toDetail=new TextView(context);
    LinearLayout.LayoutParams detailPra=new LinearLayout.LayoutParams(120,56);
    //detailPra.gravity=Gravity.CENTER;
    //detailPra.setMargins(5,5,5,5);

    toDetail.setLayoutParams(detailPra);
    toDetail.setGravity(Gravity.CENTER);
    toDetail.setTag(data);

    toDetail.setText("详情");
    toDetail.setGravity(Gravity.CENTER);
    toDetail.setTextColor(context.getResources().getColor(R.color.white));
    toDetail.setTextSize(TypedValue.COMPLEX_UNIT_DIP,14);
    toDetail.setBackground(context.getResources().getDrawable(R.drawable.button_roundbig_red));
    toDetail.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            ContentValues selectedTask=(ContentValues)v.getTag();
            SelectedTask.storeTaskRisk(selectedTask);
            Intent toFxsbDetail=new Intent(context,FxsbDetailActivity.class);
            context.startActivity(toFxsbDetail);
        }
    });
    btnwrap.addView(toDetail);

    parentLayout.addView(itemLayout);
}


//YCK中三者车列表
public void addThirdCarListView(Context context,LinearLayout parentLayout,ArrayList<ContentValues> datas){

        for(int i=0;i<datas.size();i++){
        addThirdcarItem(context,parentLayout,datas.get(i));
        }
        }

    //YCK中三者车列表item
public void addThirdcarItem(final Context context, LinearLayout parentLayout, ContentValues data){
        String carNoStr=data.getAsString(TaskThirdcars.thirdlicenseno);
        final String isAppointStr=data.getAsString(TaskThirdcars.isAppoint);
        String operateStr="";
        if(isAppointStr==null || isAppointStr.length()<=0){
            operateStr="error";
        }else if(isAppointStr.equals("0")){
            operateStr="定损预约";
        }else if(isAppointStr.equals("1")){
            operateStr="详情";
        }


        LinearLayout itemLayout=new LinearLayout(context);
        LinearLayout.LayoutParams itemPra=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        itemPra.setMargins(5,0,5,0);
        itemLayout.setLayoutParams(itemPra);
        itemLayout.setGravity(Gravity.CENTER);
        itemLayout.setOrientation(LinearLayout.HORIZONTAL);

        TextView carNoTV=new TextView(context);
        LinearLayout.LayoutParams carNoPra=new LinearLayout.LayoutParams(1,50,2);
        //createTimePra.gravity= Gravity.CENTER;
        carNoTV.setLayoutParams(carNoPra);
        carNoTV.setGravity(Gravity.CENTER);
        carNoTV.setText(carNoStr);
        carNoTV.setTextColor(context.getResources().getColor(R.color.maintext));
        carNoTV.setTextSize(TypedValue.COMPLEX_UNIT_DIP,14);
        itemLayout.addView(carNoTV);

        RelativeLayout btnwrap=new RelativeLayout(context);
        LinearLayout.LayoutParams btnwrapPra=new LinearLayout.LayoutParams(1,(int)context.getResources().getDimension(R.dimen.list_height),1);
        //btnwrapPra.gravity=Gravity.CENTER;
        btnwrap.setLayoutParams(btnwrapPra);
        btnwrap.setGravity(Gravity.CENTER);
        itemLayout.addView(btnwrap);

        TextView operateTv=new TextView(context);
        LinearLayout.LayoutParams operatePra=new LinearLayout.LayoutParams(220,100);
        //detailPra.gravity=Gravity.CENTER;
        operateTv.setLayoutParams(operatePra);
        operateTv.setGravity(Gravity.CENTER);
        operateTv.setTag(data);

        operateTv.setText(operateStr);
        operateTv.setGravity(Gravity.CENTER);
        operateTv.setTextColor(context.getResources().getColor(R.color.white));
        operateTv.setTextSize(TypedValue.COMPLEX_UNIT_DIP,14);
        operateTv.setBackground(context.getResources().getDrawable(R.drawable.button_roundbig_red));
        operateTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues selectedThirdCar=(ContentValues)v.getTag();
                SelectedThirdCar.storeThirdCar(selectedThirdCar);//存储选中的三者车信息
             /*   Intent toFxsbDetail=new Intent(context,FxsbDetailActivity.class);
                context.startActivity(toFxsbDetail);*/
                if(isAppointStr.equals("0")){//定损预约
                    mdanamicyuyueClickListener.onClick();
                }else if(isAppointStr.equals("1")){//详情
                    mdanamicDetailClickListener.onClick();
                }
            }
        });
        btnwrap.addView(operateTv);

        parentLayout.addView(itemLayout);
    }

    //YCK中三者车编辑框
    public void addThirdcarEditItem(final Context context, final LinearLayout parentLayout){

        final LinearLayout itemLayout=new LinearLayout(context);
        LinearLayout.LayoutParams itemPra=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        //itemPra.setMargins(5,5,5,5);
        itemLayout.setLayoutParams(itemPra);
        itemLayout.setGravity(Gravity.CENTER);
        itemLayout.setOrientation(LinearLayout.HORIZONTAL);

        RelativeLayout editWrap=new RelativeLayout(context);
        LinearLayout.LayoutParams editWrapPra=new LinearLayout.LayoutParams(1,100,3);
        editWrap.setLayoutParams(editWrapPra);
        editWrap.setGravity(Gravity.CENTER);
        itemLayout.addView(editWrap);

        final EditText thirdcarEt=new EditText(context);
        RelativeLayout.LayoutParams thirdcarEtPra=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,RelativeLayout.LayoutParams.MATCH_PARENT);
        thirdcarEtPra.setMargins(10,10,10,10);
        thirdcarEtPra.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        thirdcarEtPra.addRule(RelativeLayout.CENTER_VERTICAL);
        thirdcarEt.setLayoutParams(thirdcarEtPra);
        thirdcarEt.setHint("请输入车牌号:");
        thirdcarEt.setTextColor(context.getResources().getColor(R.color.maintext));
        thirdcarEt.setTextSize(14);
        thirdcarEt.setBackground(context.getResources().getDrawable(R.drawable.sfrag_radius_halftrans));
        editWrap.addView(thirdcarEt);

     /*   //设置取消按钮
        TextView cancelTV=new TextView(context);
        LinearLayout.LayoutParams cancelPra=new LinearLayout.LayoutParams(1,80,1);
        thirdcarEtPra.setMargins(10,10,10,10);
        cancelTV.setLayoutParams(cancelPra);
        cancelTV.setText("取消");
        cancelTV.setGravity(Gravity.CENTER);
        cancelTV.setTextColor(context.getResources().getColor(R.color.orangered));
        cancelTV.setTextSize(TypedValue.COMPLEX_UNIT_DIP,14);
        cancelTV.setBackground(context.getResources().getDrawable(R.drawable.red_radius_halftrans));
        cancelTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //parentLayout.removeAllViews();
                mdanamicancelThirdcar.onClick();
               parentLayout.removeView(itemLayout);
            }
        });
        itemLayout.addView(cancelTV);*/
        //设置确定按钮
        ImageView sureIV=new ImageView(context);
        LinearLayout.LayoutParams sureIVPra=new LinearLayout.LayoutParams(1,80,1);
        //thirdcarEtPra.setMargins(10,10,10,10);
        sureIV.setLayoutParams(sureIVPra);
        sureIV.setImageResource(R.drawable.gou);
        sureIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String thridcarStr=thirdcarEt.getText().toString();
                mdanamicaddThirdcar.onClick(thridcarStr);
            }
        });
        itemLayout.addView(sureIV);
        //设置取消按钮
        ImageView cancelIV1=new ImageView(context);
        LinearLayout.LayoutParams sureIVPra1=new LinearLayout.LayoutParams(1,80,1);
        //thirdcarEtPra.setMargins(10,10,10,10);
        cancelIV1.setLayoutParams(sureIVPra1);
        cancelIV1.setImageResource(R.drawable.cha);
        cancelIV1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mdanamicancelThirdcar.onClick();
                parentLayout.removeView(itemLayout);
            }
        });
        itemLayout.addView(cancelIV1);

     /*   //设置确定按钮
        TextView confirmTV=new TextView(context);
        LinearLayout.LayoutParams confirmPra=new LinearLayout.LayoutParams(1,80,1);
        thirdcarEtPra.setMargins(10,10,10,10);
        confirmTV.setLayoutParams(confirmPra);
        confirmTV.setText("确定");
        confirmTV.setGravity(Gravity.CENTER);
        confirmTV.setTextColor(context.getResources().getColor(R.color.white));
        confirmTV.setTextSize(TypedValue.COMPLEX_UNIT_DIP,14);
        confirmTV.setBackground(context.getResources().getDrawable(R.drawable.button_roundbig_red));
        confirmTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String thridcarStr=thirdcarEt.getText().toString();
                mdanamicaddThirdcar.onClick(thridcarStr);
            }
        });
        itemLayout.addView(confirmTV);

        parentLayout.addView(itemLayout);*/

        //设置取消按钮
      /*  ImageView cancelIV=new ImageView(context);
        LinearLayout.LayoutParams cancelIVPra=new LinearLayout.LayoutParams(1,80,1);
        //thirdcarEtPra.setMargins(10,10,10,10);
        sureIV.setLayoutParams(cancelIVPra);
        sureIV.setImageResource(R.drawable.cha);
        sureIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mdanamicancelThirdcar.onClick();
                parentLayout.removeView(itemLayout);
            }
        });
        itemLayout.addView(cancelIV);*/
        parentLayout.addView(itemLayout);
    }

    //YCK中风险类型列表
    public void addRiskTypeListView(Context context,LinearLayout parentLayout,ArrayList<ContentValues> datas){

        for(int i=0;i<datas.size();i++){
            addRiskTypeItem(context,parentLayout,datas.get(i));
        }
    }
    public void addRiskTypeItem(Context context,LinearLayout parentLayout,ContentValues data){
        TextView riskTypeTV=new TextView(context);
        LinearLayout.LayoutParams risktypePra=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,60);
        risktypePra.gravity=Gravity.CENTER_VERTICAL;
        //risktypePra.setMargins(5,10,5,10);
        riskTypeTV.setLayoutParams(risktypePra);
        riskTypeTV.setGravity(Gravity.CENTER_VERTICAL);
        riskTypeTV.setText(data.getAsString(TaskRiskWarm.riskname));
        riskTypeTV.setTextColor(context.getResources().getColor(R.color.maintext));
        riskTypeTV.setTextSize(TypedValue.COMPLEX_UNIT_DIP,14);
        parentLayout.addView(riskTypeTV);
    }

}
