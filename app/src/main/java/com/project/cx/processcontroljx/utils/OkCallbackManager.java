package com.project.cx.processcontroljx.utils;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.project.cx.processcontroljx.R;
import com.project.cx.processcontroljx.beans.AccessDetailBean;
import com.project.cx.processcontroljx.beans.AccessDetailData;
import com.project.cx.processcontroljx.beans.DSArea;
import com.project.cx.processcontroljx.beans.DetailIntentType;
import com.project.cx.processcontroljx.beans.LoadType;
import com.project.cx.processcontroljx.beans.MResultCode;
import com.project.cx.processcontroljx.beans.ParamType;
import com.project.cx.processcontroljx.beans.RiskWarm;
import com.project.cx.processcontroljx.beans.SelectedTask;
import com.project.cx.processcontroljx.beans.SelectedThirdCar;
import com.project.cx.processcontroljx.beans.TaskArea;
import com.project.cx.processcontroljx.beans.TaskCK;
import com.project.cx.processcontroljx.beans.TaskDS;
import com.project.cx.processcontroljx.beans.TaskRisk;
import com.project.cx.processcontroljx.beans.TaskThirdcars;
import com.project.cx.processcontroljx.beans.Taskhurt;
import com.project.cx.processcontroljx.beans.ThirdCars;
import com.project.cx.processcontroljx.beans.UnReadCounts;
import com.project.cx.processcontroljx.beans.UrgeResponseData;
import com.project.cx.processcontroljx.processmain.AuthorityActivity;
import com.project.cx.processcontroljx.processmain.DSappointment;
import com.project.cx.processcontroljx.processmain.DSappointmentDetail;
import com.project.cx.processcontroljx.processmain.FXSBActivity;
import com.project.cx.processcontroljx.processmain.ProcessMain;
import com.project.cx.processcontroljx.taskdetail.DetailDCK;
import com.project.cx.processcontroljx.taskdetail.DetailDDS;
import com.project.cx.processcontroljx.taskdetail.DetailDSZ;
import com.project.cx.processcontroljx.taskdetail.DetailHP;
import com.project.cx.processcontroljx.taskdetail.DetailYCK;
import com.project.cx.processcontroljx.taskdetail.DetailYDS;
import com.project.cx.processcontroljx.theme.MBaseActivity;
import com.project.cx.processcontroljx.ui.Dialog_alert;
import com.project.cx.processcontroljx.ui.Dialog_risktip;
import com.project.cx.processcontroljx.update.MyUpdateObject;
import com.project.cx.processcontroljx.update.UpdateHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/12/1 0001.
 */

public class OkCallbackManager {
    String TAG="";
    //Context mContext;
    public static OkCallbackManager mCallbackManager;
    ArrayList<ContentValues> mArrayList=new ArrayList<ContentValues>();;
    Dialog_alert dialog_alert=null;
    Dialog_risktip risktipDg=null;
    private OkCallbackManager(){}
    public static OkCallbackManager getInstance(){
        if(mCallbackManager==null){
            mCallbackManager=new OkCallbackManager();
        }
        return mCallbackManager;
    }

    public Callback getCallback(final int loadtype, final Context ctx, final String type, final ProcessMain pm){
       // mArrayList=getArrayList(type,pm);
        if(loadtype== LoadType.REFRESH){//clear arrayList
            mArrayList=new ArrayList<ContentValues>();
        }else if(loadtype== LoadType.LOADMORE){// load more

        }

        Callback resultCallback=null;
        TAG=ctx.getClass().getSimpleName();
        if(type.equals(ParamType.DCK) || type.equals(ParamType.YCK)){//TaskCK表
            resultCallback=new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.e(TAG,"getCallbackFAIL/e"+e.getMessage());
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Log.i(TAG,"getCallbackSUCCESS/type:"+type);
                    if(response!=null){
                        String bodystr=response.body().string();
                        Log.i(TAG,"getCallbackResponsebodyStr:"+bodystr);
                        try{
                            JSONObject jsonObject=new JSONObject(bodystr);
                            boolean success=jsonObject.getBoolean("success");
                            if(success){
                                JSONObject data0=jsonObject.getJSONObject("data");
                                String serverTimeStamp=data0.getString("servertime");
                                JSONArray datas=data0.getJSONArray("data");
                                Log.i(TAG,"getCallbackResponsedatalength:"+datas.length());
                                if(datas!=null && datas.length()>0){
                                    for(int i=0;i<datas.length();i++){
                                        ContentValues values=new ContentValues();
                                        JSONObject mdata=datas.getJSONObject(i);
                                        values.put(TaskCK.id,mdata.getString(TaskCK.id));
                                        values.put(TaskCK.caseNo,mdata.getString(TaskCK.caseNo));
                                        values.put(TaskCK.case_state,mdata.getString(TaskCK.case_state));
                                        values.put(TaskCK.caseTime,mdata.getString(TaskCK.caseTime));
                                        values.put(TaskCK.outTime,mdata.getString(TaskCK.outTime));
                                        values.put(TaskCK.appointTime,mdata.getString(TaskCK.appointTime));
                                        values.put(TaskCK.createtime,mdata.getString(TaskCK.createtime));
                                        values.put(TaskCK.finishtime,mdata.getString(TaskCK.finishtime));
                                        values.put(TaskCK.outNumber,mdata.getString(TaskCK.outNumber));
                                        values.put(TaskCK.reporter,mdata.getString(TaskCK.reporter));
                                        values.put(TaskCK.reporterPhone,mdata.getString(TaskCK.reporterPhone));
                                        values.put(TaskCK.licenseno,mdata.getString(TaskCK.licenseno));
                                        values.put(TaskCK.vehicleBrand,mdata.getString(TaskCK.vehicleBrand));
                                        values.put(TaskCK.thirdlicenseno,mdata.getString(TaskCK.thirdlicenseno));
                                        values.put(TaskCK.investigatorNo,mdata.getString(TaskCK.investigatorNo));
                                        values.put(TaskCK.car_role,mdata.getString(TaskCK.car_role));
                                        values.put(TaskCK.riskstate,mdata.getString(TaskCK.riskstate));
                                        values.put(TaskCK.risktype,mdata.getString(TaskCK.risktype));
                                        values.put(TaskCK.risklevel,mdata.getString(TaskCK.risklevel));
                                        values.put(TaskCK.hurt_state,mdata.getString(TaskCK.hurt_state));
                                        values.put(TaskCK.lian_state,mdata.getString(TaskCK.lian_state));
                                        values.put(TaskCK.isRead,mdata.getString(TaskCK.isRead));
                                        values.put(TaskCK.lianTime,mdata.getString(TaskCK.lianTime));
                                        values.put(TaskCK.expect_amount,mdata.getString(TaskCK.expect_amount));
                                        values.put(TaskCK.reparations,mdata.getString(TaskCK.reparations));
                                        mArrayList.add(values);
                                    }

                                }else{

                                   /* pm.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            pm.xrv_dck.setLoadComplete(true);
                                            Toast.makeText(pm,"暂无更多数据", Toast.LENGTH_SHORT).show();
                                        }
                                    });*/
                                    Log.i(TAG,"datas is null or length=0");
                                }

                                if(loadtype== LoadType.REFRESH){//clear arrayList

                                }else if(loadtype== LoadType.LOADMORE){// load more,update loadstart
                                    if(type.equals(ParamType.DCK)){
                                        pm.loadStart_dck= pm.loadStart_dck+pm.loadLimit;

                                        Log.i(TAG,"loadStart_dck"+pm.loadStart_dck);
                                    }else if(type.equals(ParamType.YCK)){
                                        pm.loadStart_yck= pm.loadStart_yck+pm.loadLimit;
                                        Log.i(TAG,"loadStart_yck"+pm.loadStart_yck);
                                    }

                                }

                                    pm.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if(loadtype== LoadType.REFRESH){
                                                if(type==ParamType.DCK){
                                                    /*for(int i=0;i<mArrayList.size();i++){
                                                        ContentValues value=mArrayList.get(i);
                                                        String caseNo=value.getAsString(TaskCK.caseNo);
                                                        Log.i(TAG,"caseNo"+caseNo);
                                                    }*/
                                                    MViewManager.getInstance().setDCKLayout(ctx,mArrayList,LoadType.REFRESH,pm);
                                                }else if(type==ParamType.YCK){
                                                    MViewManager.getInstance().setYCKLayout(ctx,mArrayList,LoadType.REFRESH,pm);
                                                }
                                            }else if(loadtype== LoadType.LOADMORE){
                                                if(type==ParamType.DCK){
                                                    MViewManager.getInstance().setDCKLayout(ctx,mArrayList,LoadType.LOADMORE,pm);
                                                }else if(type==ParamType.YCK){
                                                    MViewManager.getInstance().setYCKLayout(ctx,mArrayList,LoadType.LOADMORE,pm);
                                                }
                                            }
                                            //pm.xrv_dck.stopLoadMore();
                                        }
                                    });
                            }else{
/*                                final String err=jsonObject.getString("err");

                                pm.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(mContext,err,Toast.LENGTH_SHORT).show();
                                    }
                                });*/
                                final JSONObject err=jsonObject.getJSONObject("err");//err是否一定是jsonobject?
                                final String msg=err.getString("message");
                                if(msg.equals("token已失效，请重新登录")){//据此判断是否下线
                                    //弹出alert
                                    pm.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            showCloseAlert(pm);
                                        }
                                    });
                                }else{
                                    pm.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(ctx,msg,Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }

                            }
                        }catch (Exception e){
                            Log.e(TAG,"exception info:"+e.getMessage());
                        }
                    }else{
                        Log.i(TAG,"loginCallbackResponse is null or length0");
                    }
                    if(type.equals(ParamType.DCK)){
                        Log.e(TAG,"DCK  stopRefresh stopLoad Enter");
                        pm.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //pm.xrv_dck.setLoadComplete(true);
                                pm.xrv_dck.stopLoadMore();
                                pm.xrv_dck.stopRefresh();
                            }
                        });
                    }else if(type.equals(ParamType.YCK)){
                        Log.e(TAG,"stopLoad Enter");
                        pm.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                pm.xrv_yck.stopLoadMore();
                                pm.xrv_yck.stopRefresh();
                            }
                        });
                    }
                }
            };
            return resultCallback;
        }
        if(type.equals(ParamType.DDS) || type.equals(ParamType.DSZ) || type.equals(ParamType.YDS)
                || type.equals(ParamType.HP)){
            resultCallback=new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Log.i(TAG,"getCallbackSUCCESS/type:"+type);
                    if(response!=null){
                        String bodystr=response.body().string();
                        Log.i(TAG,"getCallbackResponsebodyStr:"+bodystr);
                        try{
                            JSONObject jsonObject=new JSONObject(bodystr);
                            boolean success=jsonObject.getBoolean("success");
                            if(success){
                                JSONObject data0=jsonObject.getJSONObject("data");
                                String serverTimeStamp=data0.getString("servertime");
                                JSONArray datas=data0.getJSONArray("data");
                                if(datas!=null && datas.length()>0){
                                    for(int i=0;i<datas.length();i++){
                                        ContentValues values=new ContentValues();
                                        JSONObject mdata=datas.getJSONObject(i);
                                        Log.i("selectmdata","selectmdata:"+mdata);
                                        values.put(TaskDS.id,mdata.getString(TaskDS.id));
                                        values.put(TaskDS.caseNo,mdata.getString(TaskDS.caseNo));
                                        values.put(TaskDS.case_state,mdata.getString(TaskDS.case_state));
                                        values.put(TaskDS.caseTime,mdata.getString(TaskDS.caseTime));
                                        values.put(TaskDS.outTime,mdata.getString(TaskDS.outTime));
                                        values.put(TaskDS.appointTime,mdata.getString(TaskDS.appointTime));
                                        values.put(TaskDS.createtime,mdata.getString(TaskDS.createtime));
                                        values.put(TaskDS.finishtime,mdata.getString(TaskDS.finishtime));
                                        values.put(TaskDS.outNumber,mdata.getString(TaskDS.outNumber));
                                        values.put(TaskDS.reporter,mdata.getString(TaskDS.reporter));
                                        values.put(TaskDS.reporterPhone,mdata.getString(TaskDS.reporterPhone));
                                        values.put(TaskDS.licenseno,mdata.getString(TaskDS.licenseno));
                                        values.put(TaskDS.vehicleBrand,mdata.getString(TaskDS.vehicleBrand));
                                        //values.put(TaskCK.thirdlicenseno,mdata.getString(TaskCK.thirdlicenseno));//--
                                        values.put(TaskDS.investigatorNo,mdata.getString(TaskDS.investigatorNo));
                                        values.put(TaskDS.car_role,mdata.getString(TaskDS.car_role));
                                        values.put(TaskDS.riskstate,mdata.getString(TaskDS.riskstate));
                                        values.put(TaskDS.risktype,mdata.getString(TaskDS.risktype));
                                        values.put(TaskDS.risklevel,mdata.getString(TaskDS.risklevel));
                                        values.put(TaskDS.isRead,mdata.getString(TaskDS.isRead));
                                        //values.put(TaskCK.hurt_state,mdata.getString(TaskCK.hurt_state));//--
                                        //values.put(TaskCK.lian_state,mdata.getString(TaskCK.lian_state));//--
                                        values.put(TaskDS.case_from,mdata.getString(TaskDS.case_from));//++
                                        values.put(TaskDS.accept_time,mdata.getString(TaskDS.accept_time));//++
                                        values.put(TaskDS.assessorNo,mdata.getString(TaskDS.assessorNo));//++
                                        values.put(TaskDS.assessor_name,mdata.getString(TaskDS.assessor_name));//++
                                        values.put(TaskDS.assessor_mobile,mdata.getString(TaskDS.assessor_mobile));//++
                                        values.put(TaskDS.assess_address,mdata.getString(TaskDS.assess_address));//++
                                        values.put(TaskDS.assess_amount,mdata.getString(TaskDS.assess_amount));//++

                                        values.put(TaskDS.ticket_tag,mdata.getString(TaskDS.ticket_tag));//++
                                        values.put(TaskDS.ticket_state,mdata.getString(TaskDS.ticket_state));//++
                                        //values.put(TaskDS.undisposer,mdata.getString(TaskDS.undisposer));//++
                                        values.put(TaskDS.reporter1,mdata.getString(TaskDS.reporter1));
                                        values.put(TaskDS.reporterPhone1,mdata.getString(TaskDS.reporterPhone1));
                                        values.put(TaskDS.expect_amount,mdata.getString(TaskDS.expect_amount));

                                        values.put(TaskDS.should_ticket_garage,mdata.getString(TaskDS.should_ticket_garage));
                                        values.put(TaskDS.should_ticket_amount,mdata.getString(TaskDS.should_ticket_amount));
                                        values.put(TaskDS.real_ticket_garage,mdata.getString(TaskDS.real_ticket_garage));
                                        values.put(TaskDS.real_ticket_time,mdata.getString(TaskDS.real_ticket_time));
                                        values.put(TaskDS.cooperative_name,mdata.getString(TaskDS.cooperative_name));
                                        values.put(TaskDS.ticket_amount,mdata.getString(TaskDS.ticket_amount));
                                        values.put(TaskDS.reparations,mdata.getString(TaskDS.reparations));
                                        values.put(TaskDS.lianTime,mdata.getString(TaskDS.lianTime));

                                        //12.13 dm增加定损人，定损联系人号码

                                        mArrayList.add(values);
                                    }

                                }else{
                                    Log.i(TAG,"datas is null or length=0");
                                }
                                if(loadtype==LoadType.REFRESH){//clear arrayList

                                }else if(loadtype== LoadType.LOADMORE){// load more,update loadstart
                                    if(type.equals(ParamType.DDS)){
                                        pm.loadStart_dds= pm.loadStart_dds+pm.loadLimit;
                                        Log.i(TAG,"loadStart_dds"+pm.loadStart_dds);
                                    }else if(type.equals(ParamType.DSZ)){
                                        pm.loadStart_dsz= pm.loadStart_dsz+pm.loadLimit;
                                        Log.i(TAG,"loadStart_dsz"+pm.loadStart_dsz);
                                    }else if(type.equals(ParamType.YDS)){
                                        pm.loadStart_yds= pm.loadStart_yds+pm.loadLimit;
                                        Log.i(TAG,"loadStart_yds"+pm.loadStart_yds);
                                    }else if(type.equals(ParamType.HP)){
                                        pm.loadStart_hp= pm.loadStart_hp+pm.loadLimit;
                                        Log.i(TAG,"loadStart_hp"+pm.loadStart_hp);
                                    }
                                }


                                pm.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
/*                                            if(type.equals(ParamType.DDS)){
                                                //需要实现
                                                //MViewManager.getInstance().setDCKLayout(ctx,mArrayList,pm);
                                            }*/
                                        if(loadtype==LoadType.REFRESH){
                                            if(type==ParamType.DDS){
                                                MViewManager.getInstance().setDDSLayout(ctx,mArrayList,LoadType.REFRESH,pm);
                                            }else if(type==ParamType.DSZ){
                                                MViewManager.getInstance().setDSZLayout(ctx,mArrayList,LoadType.REFRESH,pm);
                                            }else if(type==ParamType.YDS){
                                                MViewManager.getInstance().setYDSLayout(ctx,mArrayList,LoadType.REFRESH,pm);
                                            }else if(type==ParamType.HP){
                                                MViewManager.getInstance().setHPLayout(ctx,mArrayList,LoadType.REFRESH,pm);
                                            }
                                        }else if(loadtype==LoadType.LOADMORE){
                                            if(type==ParamType.DDS){
                                                MViewManager.getInstance().setDDSLayout(ctx,mArrayList,LoadType.LOADMORE,pm);
                                            }else if(type==ParamType.DSZ){
                                                MViewManager.getInstance().setDSZLayout(ctx,mArrayList,LoadType.LOADMORE,pm);
                                            }else if(type==ParamType.YDS){
                                                MViewManager.getInstance().setYDSLayout(ctx,mArrayList,LoadType.LOADMORE,pm);
                                            }else if(type==ParamType.HP){
                                                MViewManager.getInstance().setHPLayout(ctx,mArrayList,LoadType.LOADMORE,pm);
                                            }
                                        }
                                    }
                                });
                            }else{
/*                                final String err=jsonObject.getString("err");
                                pm.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(mContext,err,Toast.LENGTH_SHORT).show();
                                    }
                                });*/

                                final JSONObject err=jsonObject.getJSONObject("err");//err是否一定是jsonobject?
                                final String msg=err.getString("message");
                                if(msg.equals("token已失效，请重新登录")){//据此判断是否下线
                                    //弹出alert
                                    pm.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            showCloseAlert(pm);
                                        }
                                    });
                                }else{
                                    pm.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(ctx,msg,Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }

                            }
                        }catch (Exception e){
                            Log.e(TAG,"exception info:"+e.getMessage());
                        }
                    }else{
                        Log.i(TAG,"loginCallbackResponse is null or length0");
                    }
                    if(type.equals(ParamType.DDS)){
                        Log.e(TAG,"stopLoad Enter");
                        pm.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                pm.xrv_dds.stopLoadMore();
                            }
                        });
                    }else if(type.equals(ParamType.DSZ)){
                        Log.e(TAG,"stopLoad Enter");
                        pm.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                pm.xrv_dsz.stopLoadMore();
                            }
                        });
                    }
                    else if(type.equals(ParamType.YDS)){
                        Log.e(TAG,"stopLoad Enter");
                        pm.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                pm.xrv_yds.stopLoadMore();
                            }
                        });
                    }
                    else if(type.equals(ParamType.HP)){
                        Log.e(TAG,"stopLoad Enter");
                        pm.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                pm.xrv_hp.stopLoadMore();
                            }
                        });
                    }
                }
            };
            return resultCallback;

        }

        if(type.equals(ParamType.GZ) || type.equals(ParamType.LS)){
            resultCallback=new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    Log.i(TAG,"getCallbackSUCCESS/type:"+type);
                    if(response!=null){
                        String bodystr=response.body().string();
                        Log.i(TAG,"getCallbackResponsebodyStr:"+bodystr);
                        try{
                            JSONObject jsonObject=new JSONObject(bodystr);
                            boolean success=jsonObject.getBoolean("success");
                            if(success){
                                //JSONObject data0=jsonObject.getJSONObject("data");
                                //String serverTimeStamp=data0.getString("servertime");
                                JSONArray datas=jsonObject.getJSONArray("data");
                                if(datas!=null && datas.length()>0){
                                    for(int i=0;i<datas.length();i++){
                                        ContentValues values=new ContentValues();
                                        JSONObject mdata=datas.getJSONObject(i);
                                        values.put(Taskhurt.id,mdata.getString(Taskhurt.id));
                                        values.put(Taskhurt.caseNo,mdata.getString(Taskhurt.caseNo));
                                        values.put(Taskhurt.caseTime,mdata.getString(Taskhurt.caseTime));
                                        values.put(Taskhurt.createtime,mdata.getString(Taskhurt.createtime));
                                        values.put(Taskhurt.state,mdata.getString(Taskhurt.state));
                                        values.put(Taskhurt.contactName,mdata.getString(Taskhurt.contactName));
                                        values.put(Taskhurt.contactPhone,mdata.getString(Taskhurt.contactPhone));
                                        values.put(Taskhurt.bruiseNo,mdata.getString(Taskhurt.bruiseNo));
                                        values.put(Taskhurt.dangerDetail,mdata.getString(Taskhurt.dangerDetail));
                                        values.put(Taskhurt.licenseno,mdata.getString(Taskhurt.licenseno));
                                        values.put(Taskhurt.isRead,mdata.getString(Taskhurt.isRead));
                                        mArrayList.add(values);
                                    }

                                }else{
                                    Log.i(TAG,"datas is null or length=0");
                                }


                                if(loadtype==LoadType.REFRESH){//clear arrayList

                                }else if(loadtype== LoadType.LOADMORE){// load more,update loadstart
                                    if(type.equals(ParamType.GZ)){
                                        pm.loadStart_gz= pm.loadStart_gz+pm.loadLimit;
                                        Log.i(TAG,"loadStart_gz"+pm.loadStart_gz);
                                    }else if(type.equals(ParamType.LS)){
                                        pm.loadStart_ls= pm.loadStart_ls+pm.loadLimit;
                                        Log.i(TAG,"loadStart_ls"+pm.loadStart_ls);
                                    }
                                }
                                pm.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
/*                                            if(type.equals(ParamType.DDS)){
                                                //需要实现
                                                //MViewManager.getInstance().setDCKLayout(ctx,mArrayList,pm);
                                            }*/
                                        if(loadtype==LoadType.REFRESH){
                                            if(type==ParamType.GZ){
                                                MViewManager.getInstance().setRSWORKLayout(ctx,mArrayList,LoadType.REFRESH,pm);
                                            }else if(type==ParamType.LS){
                                                MViewManager.getInstance().setRSHISLayout(ctx,mArrayList,LoadType.REFRESH,pm);
                                            }
                                        }else if(loadtype==LoadType.LOADMORE){
                                            if(type==ParamType.GZ){
                                                MViewManager.getInstance().setRSWORKLayout(ctx,mArrayList,LoadType.LOADMORE,pm);
                                            }else if(type==ParamType.LS){
                                                MViewManager.getInstance().setRSHISLayout(ctx,mArrayList,LoadType.LOADMORE,pm);
                                            }
                                        }

                                    }
                                });
                            }else{
/*                                final String err=jsonObject.getString("err");
                                pm.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(mContext,err,Toast.LENGTH_SHORT).show();
                                    }
                                });*/

                                final JSONObject err=jsonObject.getJSONObject("err");//err是否一定是jsonobject?
                                final String msg=err.getString("message");
                                if(msg.equals("token已失效，请重新登录")){//据此判断是否下线
                                    //弹出alert
                                    pm.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            showCloseAlert(pm);
                                        }
                                    });
                                }else{
                                    pm.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(ctx,msg,Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }

                            }
                        }catch (Exception e){
                            Log.e(TAG,"exception info:"+e.getMessage());
                        }
                    }else{
                        Log.i(TAG,"loginCallbackResponse is null or length0");
                    }
                    if(type.equals(ParamType.GZ)){
                        Log.e(TAG,"stopLoad Enter");
                        pm.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                pm.xrv_gz.stopLoadMore();
                            }
                        });
                    }else if(type.equals(ParamType.LS)){
                        Log.e(TAG,"stopLoad Enter");
                        pm.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                pm.xrv_ls.stopLoadMore();
                            }
                        });
                    }
                }
            };
            return resultCallback;

        }
        return null;
    }

    //设置已读Callback
    public Callback getReadCallback(final Context ctx, final Class denstination, final ProcessMain pm, final String tasktype){
        Callback resultCallback=null;
        resultCallback=new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG,"getReadCallbackFAIL/e"+e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i(TAG,"getReadCallbackSUCCESS");
                if(response!=null){
                    String bodystr=response.body().string();
                    Log.i(TAG,"setReadgetCallbackResponsebodyStr:"+bodystr);
                    try{
                        JSONObject jsonObject=new JSONObject(bodystr);
                        boolean success=jsonObject.getBoolean("success");
                        if(success){
                           /* boolean data=jsonObject.getBoolean("data");
                            if(data){//?依据data为true,还是success为true,设置成功
                                pm.startActivity(denstination, DetailIntentType.UNREAD);
                            }*/

                            pm.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //未读条数-1
                                    int counts=UnReadCounts.getCount(tasktype);
                                    UnReadCounts.setCount(tasktype,counts-1);
                                    //CountSetHelper.getInstance(pm).setCount(tasktype,UnReadCounts.getCount(tasktype));
                                    pm.updateCountView();
                                    pm.startActivity(denstination, DetailIntentType.UNREAD);
                                }
                            });

                        }else{
                            final JSONObject err=jsonObject.getJSONObject("err");//err是否一定是jsonobject?
                            final String msg=err.getString("message");
                            if(msg.equals("token已失效，请重新登录")){//据此判断是否下线
                                //弹出alert
                                pm.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        showCloseAlert(pm);
                                    }
                                });
                            }else{
                                pm.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(ctx,msg,Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                        }
                    }catch (Exception e){
                        Log.e(TAG,"exception info:"+e.getMessage());
                    }
                }else{
                    Log.i(TAG,"loginCallbackResponse is null or length0");
                }
            }
        };
        return resultCallback;
    }


    //设置风险提醒Callback
    public Callback getRiskWarmCallback(final Context ctx, final MBaseActivity detailClass){
        Callback resultCallback=null;
        RiskWarm.riskwarmArray=new ArrayList<ContentValues>();//清除riskArray
        resultCallback=new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG,"getRiskWarmCallbackFAIL/e"+e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i(TAG,"getRiskWarmCallbackSUCCESS");
                if(response!=null){
                    String bodystr=response.body().string();
                    Log.i(TAG,"getwarmCallbackResponsebodyStr:"+bodystr);
                    try{
                        JSONObject jsonObject=new JSONObject(bodystr);
                        boolean success=jsonObject.getBoolean("success");
                        if(success){
                            JSONArray datas=jsonObject.getJSONArray("data");
                            if(datas!=null && datas.length()>0){
                                for(int i=0;i<datas.length();i++){
                                    ContentValues values=new ContentValues();
                                    JSONObject mdata=datas.getJSONObject(i);
                                    values.put(RiskWarm.RISKNAME,mdata.getString(RiskWarm.RISKNAME));
                                    values.put(RiskWarm.CONTENT,mdata.getString(RiskWarm.CONTENT));
                                    RiskWarm.riskwarmArray.add(values);
                                }
                            }
                            //在dck,yck,dds,dsz,yds,hp详情页显示riskdialguo并把riskArray填充进去,只有未读任务需要
                            if(detailClass instanceof DetailDCK){
                                final DetailDCK detailDCK= (DetailDCK) detailClass;
                                if(detailDCK.intentType==DetailIntentType.UNREAD){
                                    detailDCK.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            //detailDCK.showRiskDg(RiskWarm.riskwarmArray);
                                            showRiskDg(ctx,RiskWarm.riskwarmArray);
                                        }
                                    });
                                }
                            }

                            if(detailClass instanceof DetailYCK){
                                final DetailYCK detailYCK= (DetailYCK) detailClass;
                                if(detailYCK.intentType==DetailIntentType.UNREAD){
                                    detailYCK.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            showRiskDg(ctx,RiskWarm.riskwarmArray);
                                        }
                                    });
                                }
                            }

                            if(detailClass instanceof DetailDDS){
                                final DetailDDS detailDDS= (DetailDDS) detailClass;
                                if(detailDDS.intentType==DetailIntentType.UNREAD){
                                    detailDDS.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            showRiskDg(ctx,RiskWarm.riskwarmArray);
                                        }
                                    });
                                }
                            }

                            if(detailClass instanceof DetailDSZ){
                                final DetailDSZ detailDSZ= (DetailDSZ) detailClass;
                                if(detailDSZ.intentType==DetailIntentType.UNREAD){
                                    detailDSZ.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            showRiskDg(ctx,RiskWarm.riskwarmArray);
                                        }
                                    });
                                }
                            }

                            if(detailClass instanceof DetailYDS){
                                final DetailYDS detailYDS= (DetailYDS) detailClass;
                                if(detailYDS.intentType==DetailIntentType.UNREAD){
                                    detailYDS.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            showRiskDg(ctx,RiskWarm.riskwarmArray);
                                        }
                                    });
                                }
                            }
                            if(detailClass instanceof DetailHP){
                                final DetailHP detailHP= (DetailHP) detailClass;
                                if(detailHP.intentType==DetailIntentType.UNREAD){
                                    detailHP.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            showRiskDg(ctx,RiskWarm.riskwarmArray);
                                        }
                                    });
                                }
                            }


                            //处理dck,yck,dds,dsz,yds中的风险类型展示
                            if(detailClass instanceof DetailDCK){
                                final DetailDCK detailDCK= (DetailDCK) detailClass;
                                detailDCK.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        LinearLayout risktype_wrapper= (LinearLayout) detailDCK.findViewById(R.id.dck_risktype_wrapper);
                                        risktype_wrapper.removeAllViews();
                                        LayoutAddDanamic.getInstance().addRiskTypeListView(ctx,risktype_wrapper,RiskWarm.riskwarmArray);
                                    }
                                });
                            }else if(detailClass instanceof DetailYCK){
                                final DetailYCK detailYCK= (DetailYCK) detailClass;
                                detailYCK.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        LinearLayout risktype_wrapper= (LinearLayout) detailYCK.findViewById(R.id.yck_risktype_wrapper);
                                        risktype_wrapper.removeAllViews();
                                        LayoutAddDanamic.getInstance().addRiskTypeListView(ctx,risktype_wrapper,RiskWarm.riskwarmArray);
                                    }
                                });
                            }else if(detailClass instanceof DetailDDS){
                                final DetailDDS detailDDS= (DetailDDS) detailClass;
                                detailDDS.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        LinearLayout risktype_wrapper= (LinearLayout) detailDDS.findViewById(R.id.dds_risktype_wrapper);
                                        risktype_wrapper.removeAllViews();
                                        LayoutAddDanamic.getInstance().addRiskTypeListView(ctx,risktype_wrapper,RiskWarm.riskwarmArray);
                                    }
                                });

                            }else if(detailClass instanceof DetailDSZ){
                                final DetailDSZ detailDSZ= (DetailDSZ) detailClass;
                                detailDSZ.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        LinearLayout risktype_wrapper= (LinearLayout) detailDSZ.findViewById(R.id.dsz_risktype_wrapper);
                                        risktype_wrapper.removeAllViews();
                                        LayoutAddDanamic.getInstance().addRiskTypeListView(ctx,risktype_wrapper,RiskWarm.riskwarmArray);
                                    }
                                });

                            }else if(detailClass instanceof DetailYDS){
                                final DetailYDS detailYDS= (DetailYDS) detailClass;
                                detailYDS.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        LinearLayout risktype_wrapper= (LinearLayout) detailYDS.findViewById(R.id.yds_risktype_wrapper);
                                        risktype_wrapper.removeAllViews();
                                        LayoutAddDanamic.getInstance().addRiskTypeListView(ctx,risktype_wrapper,RiskWarm.riskwarmArray);
                                    }
                                });
                            }

                        }else{
                            final JSONObject err=jsonObject.getJSONObject("err");//err是否一定是jsonobject?
                            final String msg=err.getString("message");
                            if(msg.equals("token已失效，请重新登录")){//据此判断是否下线
                                //弹出alert
                                detailClass.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        showCloseAlert(detailClass);
                                    }
                                });
                            }else{
                                detailClass.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(ctx,msg,Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                        }
                    }catch (Exception e){
                        Log.e(TAG,"warm exception info:"+e.getMessage());
                    }
                }else{
                    Log.i(TAG,"loginCallbackResponse is null or length0");
                }
            }
        };
        return resultCallback;
    }

//认领任务回调
    public Callback claimTaskCallback(final Context ctx, final DetailDDS detailDDS){
        Callback resultCallback=null;
        resultCallback=new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG,"claimTaskCallbackFAIL/e"+e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i(TAG,"claimTaskCallbackSUCCESS");
                if(response!=null){
                    String bodystr=response.body().string();
                    Log.i(TAG,"getCallbackResponsebodyStr:"+bodystr);
                    try{
                        JSONObject jsonObject=new JSONObject(bodystr);
                        boolean success=jsonObject.getBoolean("success");
                        if(success){
                           /* boolean data=jsonObject.getBoolean("data");
                            if(data){//?依据data为true,还是success为true,设置成功
                                pm.startActivity(denstination, DetailIntentType.UNREAD);
                            }*/
                            detailDDS.runOnUiThread(new Runnable() {
                               @Override
                               public void run() {
                                   Toast.makeText(ctx,"提交成功",Toast.LENGTH_SHORT).show();
                                   Intent mIntent=new Intent();
                                  /* mIntent.putExtra("sbstate","1");
                                   fxsb.setResult(MResultCode.FXSB,mIntent);*/
                                   AppManager.getAppManager().finishActivity(detailDDS);
                               }
                           });

                        }else{
                            final JSONObject err=jsonObject.getJSONObject("err");//err是否一定是jsonobject?
                            final String msg=err.getString("message");
                            if(msg.equals("token已失效，请重新登录")){//据此判断是否下线
                                //弹出alert
                                detailDDS.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        showCloseAlert(detailDDS);
                                    }
                                });
                            }else{
                                detailDDS.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(ctx,msg,Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                        }
                    }catch (Exception e){
                        Log.e(TAG,"exception info:"+e.getMessage());
                    }
                }else{
                    Log.i(TAG,"claimTaskCallbackResponse is null or length0");
                }
            }
        };
        return resultCallback;
    }
    //超权限上报
    public Callback commitAuthorityRecordCallback(final Context ctx){
        Callback resultCallback=null;
        resultCallback=new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG,"commitRiskRecordCallbackFAIL/e"+e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i(TAG,"commitRiskRecordCallbackSUCCESS");
                if(response!=null){
                    String bodystr=response.body().string();
                    Log.i(TAG,"commitAuthority:"+bodystr);
                    try{
                        JSONObject jsonObject=new JSONObject(bodystr);
                        boolean success=jsonObject.getBoolean("success");
                        if(success){
                            Toast.makeText(ctx,"提交成功",Toast.LENGTH_SHORT).show();
//                            fxsb.runOnUiThread(new Runnable() {
//                                @Override
//                                public void run() {
//                                    Toast.makeText(ctx,"提交成功",Toast.LENGTH_SHORT).show();
//                                    //AppManager.getAppManager().finishActivity(fxsb);
//                                }
//                            });

                        }else{
//                            final JSONObject err=jsonObject.getJSONObject("err");//err是否一定是jsonobject?
//                            final String msg=err.getString("message");
//                            if(msg.equals("token已失效，请重新登录")){//据此判断是否下线
//                                //弹出alert
//                                fxsb.runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        showCloseAlert(fxsb);
//                                    }
//                                });
//                            }else{
//                                fxsb.runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        Toast.makeText(ctx,msg,Toast.LENGTH_SHORT).show();
//                                    }
//                                });
//                            }

                        }
                    }catch (Exception e){
                        Log.e(TAG,"exception info:"+e.getMessage());
                    }
                }else{
                    Log.i(TAG,"loginCallbackResponse is null or length0");
                }
            }
        };
        return resultCallback;
    }
    public Callback getTaskRiskCallback(final Context ctx, final MBaseActivity detailClass){
        Callback resultCallback=null;
        resultCallback=new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG,"getTaskRiskCallbackFAIL/e"+e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i(TAG,"getTaskRiskCallbackSUCCESS");
                if(detailClass instanceof DetailDCK){
                    DetailDCK detailDCK=(DetailDCK) detailClass;
                    detailDCK.riskArray=new ArrayList<ContentValues>();//清空
                }else if(detailClass instanceof DetailYCK){
                    DetailYCK detailYCK=(DetailYCK) detailClass;
                    detailYCK.riskArray=new ArrayList<ContentValues>();//清空
                }else if(detailClass instanceof DetailDDS){
                    DetailDDS detailDDS=(DetailDDS) detailClass;
                    detailDDS.riskArray=new ArrayList<ContentValues>();//清空
                }
                else if(detailClass instanceof DetailDSZ){
                    DetailDSZ detailDSZ=(DetailDSZ) detailClass;
                    detailDSZ.riskArray=new ArrayList<ContentValues>();//清空
                }
                else if(detailClass instanceof DetailYDS){
                    DetailYDS detailYDS=(DetailYDS) detailClass;
                    detailYDS.riskArray=new ArrayList<ContentValues>();//清空
                }
                if(response!=null){
                    String bodystr=response.body().string();
                    Log.i(TAG,"getCallbackResponsebodyStr:"+bodystr);
                    try{
                        JSONObject jsonObject=new JSONObject(bodystr);
                        boolean success=jsonObject.getBoolean("success");
                        if(success){
                            JSONArray datas=jsonObject.getJSONArray("data");
                            Log.i(TAG,"getCallbackdatasLength:"+datas.length());
                            if(datas!=null && datas.length()>0){
                                for(int i=0;i<datas.length();i++){
                                    ContentValues values=new ContentValues();
                                    JSONObject mdata= datas.getJSONObject(i);
                                    values.put(TaskRisk.id,mdata.getString(TaskRisk.id));
                                    values.put(TaskRisk.taskid_ck,mdata.getString(TaskRisk.taskid_ck));
                                    values.put(TaskRisk.taskid_ds,mdata.getString(TaskRisk.taskid_ds));
                                    values.put(TaskRisk.task_from,mdata.getString(TaskRisk.task_from));
                                    values.put(TaskRisk.caseTime,mdata.getString(TaskRisk.caseTime));
                                    values.put(TaskRisk.createtime,mdata.getString(TaskRisk.createtime));
                                    values.put(TaskRisk.finishtime,mdata.getString(TaskRisk.finishtime));
                                    values.put(TaskRisk.outTime,mdata.getString(TaskRisk.outTime));
                                    values.put(TaskRisk.outNumber,mdata.getString(TaskRisk.outNumber));
                                    values.put(TaskRisk.caseNo,mdata.getString(TaskRisk.caseNo));
                                    values.put(TaskRisk.reporter,mdata.getString(TaskRisk.reporter));
                                    values.put(TaskRisk.reporterPhone,mdata.getString(TaskRisk.reporterPhone));
                                    values.put(TaskRisk.licenseno,mdata.getString(TaskRisk.licenseno));
                                    values.put(TaskRisk.risktype,mdata.getString(TaskRisk.risktype));
                                    values.put(TaskRisk.others,mdata.getString(TaskRisk.others));
                                    values.put(TaskRisk.remark,mdata.getString(TaskRisk.remark));
                                    values.put(TaskRisk.state,mdata.getString(TaskRisk.state));
                                    values.put(TaskRisk.auditor,mdata.getString(TaskRisk.auditor));
                                    //values.put(TaskRisk.audit_time,mdata.getString(TaskRisk.audit_time));
                                    values.put(TaskRisk.auditorNo,mdata.getString(TaskRisk.auditorNo));
                                    values.put(TaskRisk.reporter_name,mdata.getString(TaskRisk.reporter_name));
                                    if(detailClass instanceof DetailDCK){//关联的地方太多，如何简化?setRiskListView
                                        DetailDCK detailDCK=(DetailDCK) detailClass;
                                        detailDCK.riskArray.add(values);
                                    }else if(detailClass instanceof DetailYCK){
                                        DetailYCK detailYCK=(DetailYCK) detailClass;
                                        detailYCK.riskArray.add(values);
                                    }else if(detailClass instanceof DetailDDS){
                                        DetailDDS detailDDS=(DetailDDS) detailClass;
                                        detailDDS.riskArray.add(values);
                                    }
                                    else if(detailClass instanceof DetailDSZ){
                                        DetailDSZ detailDSZ=(DetailDSZ) detailClass;
                                        detailDSZ.riskArray.add(values);
                                    }

                                    else if(detailClass instanceof DetailYDS){
                                        DetailYDS detailYDS=(DetailYDS) detailClass;
                                        detailYDS.riskArray.add(values);
                                    }
                                }
                            }else{
                                Log.e(TAG,"getTaskRiskCallback is null or length0");
                            }
                            detailClass.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if(detailClass instanceof DetailDCK){
                                        DetailDCK detailDCK=(DetailDCK) detailClass;
                                        MViewManager.getInstance().setRiskListView(ctx,detailDCK.riskArray,detailDCK);
                                        LinearLayout risklist_wrapper= (LinearLayout) detailDCK.findViewById(R.id.dck_risklist_wrapper);
                                        risklist_wrapper.removeAllViews();//清空原有views
                                        LayoutAddDanamic.getInstance().addTraceListView(ctx,risklist_wrapper,detailDCK.riskArray);

                                    }else if(detailClass instanceof DetailYCK){
                                        DetailYCK detailYCK=(DetailYCK) detailClass;
                                        MViewManager.getInstance().setRiskListView(ctx,detailYCK.riskArray,detailYCK);
                                        LinearLayout risklist_wrapper= (LinearLayout) detailYCK.findViewById(R.id.risklist_wrapper);
                                        risklist_wrapper.removeAllViews();//清空原有views
                                        LayoutAddDanamic.getInstance().addTraceListView(ctx,risklist_wrapper,detailYCK.riskArray);

                                    }
                                    else if(detailClass instanceof DetailDDS){
                                        DetailDDS detailDDS=(DetailDDS) detailClass;
                                        MViewManager.getInstance().setRiskListView(ctx,detailDDS.riskArray,detailDDS);
                                        LinearLayout risklist_wrapper= (LinearLayout) detailDDS.findViewById(R.id.dds_risklist_wrapper);
                                        risklist_wrapper.removeAllViews();//清空原有views
                                        LayoutAddDanamic.getInstance().addTraceListView(ctx,risklist_wrapper,detailDDS.riskArray);

                                    }
                                    else if(detailClass instanceof DetailDSZ){
                                        DetailDSZ detailDSZ=(DetailDSZ) detailClass;
                                        MViewManager.getInstance().setRiskListView(ctx,detailDSZ.riskArray,detailDSZ);
                                        LinearLayout risklist_wrapper= (LinearLayout) detailDSZ.findViewById(R.id.dsz_risklist_wrapper);
                                        risklist_wrapper.removeAllViews();//清空原有views
                                        LayoutAddDanamic.getInstance().addTraceListView(ctx,risklist_wrapper,detailDSZ.riskArray);

                                    }

                                    else if(detailClass instanceof DetailYDS){
                                        DetailYDS detailYDS=(DetailYDS) detailClass;
                                        LinearLayout risklist_wrapper= (LinearLayout) detailYDS.findViewById(R.id.yds_risklist_wrapper);
                                        risklist_wrapper.removeAllViews();//清空原有views
                                        LayoutAddDanamic.getInstance().addTraceListView(ctx,risklist_wrapper,detailYDS.riskArray);
                                        MViewManager.getInstance().setRiskListView(ctx,detailYDS.riskArray,detailYDS);
                                    }
                                }
                            });

                        }else{
                            final JSONObject err=jsonObject.getJSONObject("err");//err是否一定是jsonobject?
                            final String msg=err.getString("message");
                            if(msg.equals("token已失效，请重新登录")){//据此判断是否下线
                                //弹出alert
                                detailClass.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        showCloseAlert(detailClass);
                                    }
                                });
                            }else{
                                detailClass.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(ctx,msg,Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                        }
                    }catch (Exception e){
                        Log.e(TAG,"TaskRisk exception info:"+e.getMessage());
                    }
                }else{
                    Log.i(TAG,"CallbackResponse is null or length0");
                }
            }
        };
        return resultCallback;
    }


    public Callback getAreasCallback(final Context ctx, final DSappointment dSappointment){
        Callback resultCallback=null;
        resultCallback=new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG,"getAreasCallbackFAIL/e"+e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i(TAG,"getAreasCallbackSUCCESS");
                DSArea.dsAreaArray=new ArrayList<ContentValues>();//清空
                if(response!=null){
                    String bodystr=response.body().string();
                    Log.i(TAG,"getCallbackResponsebodyStr:"+bodystr);
                    try{
                        JSONObject jsonObject=new JSONObject(bodystr);
                        boolean success=jsonObject.getBoolean("success");
                        if(success){
                            JSONArray datas=jsonObject.getJSONArray("data");
                            if(datas!=null && datas.length()>0){
                                for(int i=0;i<datas.length();i++){
                                    ContentValues values=new ContentValues();
                                    JSONObject mdata= datas.getJSONObject(i);
                                    values.put(TaskArea.AreaID,mdata.getString(TaskArea.AreaID));
                                    values.put(TaskArea.AreaName,mdata.getString(TaskArea.AreaName));
                                    values.put(TaskArea.Areastate,mdata.getString(TaskArea.Areastate));
                                    values.put(TaskArea.AreaClass,mdata.getString(TaskArea.AreaClass));
                                    values.put(TaskArea.Area1Code,mdata.getString(TaskArea.Area1Code));
                                    values.put(TaskArea.Area2Code,mdata.getString(TaskArea.Area2Code));
                                    values.put(TaskArea.Area3Code,mdata.getString(TaskArea.Area3Code));
                                    values.put(TaskArea.Area4Code,mdata.getString(TaskArea.Area4Code));
                                    values.put(TaskArea.assess_id,mdata.getString(TaskArea.assess_id));
                                    values.put(TaskArea.assess_name,mdata.getString(TaskArea.assess_name));
                                    if(mdata.isNull(TaskArea.group_id)){//非空判断
                                        Log.e(TAG,"group_id is null enter");
                                        values.put(TaskArea.group_id,"");
                                    }else{
                                        values.put(TaskArea.group_id,mdata.getString(TaskArea.group_id));
                                    }


                                    DSArea.dsAreaArray.add(values);
                                    DSArea.area_idMap.put(mdata.getString(TaskArea.AreaName),mdata.getString(TaskArea.AreaID));
                                    DSArea.access_idMap.put(mdata.getString(TaskArea.assess_name),mdata.getString(TaskArea.assess_id));
                                    DSArea.access_group.put(mdata.getString(TaskArea.assess_name),mdata.getString(TaskArea.group_id));
                                }
                            }else{
                                Log.e(TAG,"getTaskRiskCallback is null or length0");
                            }
                            dSappointment.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    MViewManager.getInstance().setSpinnerAdapter(ctx,DSArea.dsAreaArray,dSappointment);
                                }
                            });

                        }else{
                            final JSONObject err=jsonObject.getJSONObject("err");//err是否一定是jsonobject?
                            final String msg=err.getString("message");
                            if(msg.equals("token已失效，请重新登录")){//据此判断是否下线
                                //弹出alert
                                dSappointment.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        showCloseAlert(dSappointment);
                                    }
                                });
                            }else{
                                dSappointment.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(ctx,msg,Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                        }
                    }catch (Exception e){
                        Log.e(TAG,"TaskRisk exception info:"+e.getMessage());
                    }
                }else{
                    Log.i(TAG,"CallbackResponse is null or length0");
                }
            }
        };
        return resultCallback;
    }


    //获取三者车Callback
    public Callback getthirdcarCallback(final Context ctx, final MBaseActivity detailClass){
        Callback resultCallback=null;
        ThirdCars.thirdcarsArray=new ArrayList<ContentValues>();//清除riskArray
        resultCallback=new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG,"getthirdcarCallbackFAIL/e"+e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i(TAG,"getthirdcarCallbackSUCCESS");
                if(response!=null){
                    String bodystr=response.body().string();
                    Log.i(TAG,"getthirdcarCallbackResponsebodyStr:"+bodystr);
                    try{
                        JSONObject jsonObject=new JSONObject(bodystr);
                        boolean success=jsonObject.getBoolean("success");
                        if(success){
                            JSONArray datas=jsonObject.getJSONArray("data");
                            if(datas!=null && datas.length()>0){
                                for(int i=0;i<datas.length();i++){
                                    JSONObject mdata=datas.getJSONObject(i);
                                  /*  String thirdCarNo=mdata.getString(TaskThirdcars.thirdlicenseno);
                                    String selectCar=SelectedTask.getTaskYCK().getAsString(TaskCK.licenseno);
                                    Log.e("OkCallback","thirdCarNo"+thirdCarNo+"/selectCar"+selectCar);*/
                                    if(mdata.getString(TaskThirdcars.thirdlicenseno).equals(SelectedTask.getTaskYCK().getAsString(TaskCK.licenseno))){
                                        //filter去掉三者车数据中的标的车,但需要读取isAppoint字段
                                        ContentValues values=SelectedTask.getTaskYCK();
                                        values.put(TaskCK.isAppoint,mdata.getString(TaskCK.isAppoint));
                                        SelectedTask.storeTaskYCK(values);
                                    }else{
                                        ContentValues values=new ContentValues();
                                        values.put(TaskThirdcars.id,mdata.getString(TaskThirdcars.id));
                                        values.put(TaskThirdcars.taskid_ck,mdata.getString(TaskThirdcars.taskid_ck));
                                        values.put(TaskThirdcars.taskid_ds,mdata.getString(TaskThirdcars.taskid_ds));
                                        values.put(TaskThirdcars.createtime,mdata.getString(TaskThirdcars.createtime));
                                        values.put(TaskThirdcars.isAppoint,mdata.getString(TaskThirdcars.isAppoint));
                                        values.put(TaskThirdcars.thirdlicenseno,mdata.getString(TaskThirdcars.thirdlicenseno));
                                        ThirdCars.thirdcarsArray.add(values);
                                    }

                                }
                            }
                            //在yck详情页显示显示三者车牌号
                            if(detailClass instanceof DetailYCK){
                                final DetailYCK detailYCK= (DetailYCK) detailClass;
                                //yckdetail中动态加载车牌号
                                detailYCK.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        //更新thirdcarAdapter数据
                                        //detailYCK.thirdcarAdapter.notifyDataSetChanged();//无效
                                        detailYCK.isAppoint=detailYCK.selectYCK.getAsString(TaskCK.isAppoint);
                                        if(detailYCK.isAppoint==null){//若三者车记录没有包含标的车
                                            detailYCK.isAppoint="-1";
                                        }
                                        if(detailYCK.isAppoint.equals("0")){//预约
                                            detailYCK.go_detail.setText("定损预约");

                                        }else  if(detailYCK.isAppoint.equals("1")){//详情
                                            detailYCK.go_detail.setText("详情");
                                        }else{
                                            detailYCK.go_detail.setText("error");
                                        }

                                        {
                                            detailYCK.setThirdListAdapter(detailYCK.yck_detail_thirdcars_list);

                                            final LinearLayout thirdcars_linear= (LinearLayout) detailYCK.findViewById(R.id.thirdcars_linear);
                                            thirdcars_linear.removeAllViews();
                                            LayoutAddDanamic.getInstance().addThirdCarListView(ctx,thirdcars_linear,ThirdCars.thirdcarsArray);
                                            LayoutAddDanamic.getInstance().setDanamicItemDetailClick(new LayoutAddDanamic.DanamicItemClickListener() {//详情按钮
                                                @Override
                                                public void onClick() {
                                                    detailYCK.access_detailHttpData(detailYCK.userManager.getUserToken(),detailYCK.userManager.getFrontRole(),detailYCK.selectYCK.getAsString(TaskCK.caseNo), SelectedThirdCar.getThirdCar().getAsString(TaskThirdcars.thirdlicenseno),OkCallbackManager.getInstance().access_detailCallback(ctx,detailYCK));
                                                }
                                            });

                                            LayoutAddDanamic.getInstance().setDanamicItemYuyueClick(new LayoutAddDanamic.DanamicItemClickListener() {
                                                @Override
                                                public void onClick() {
                                                    Intent toDSappointment=new Intent(ctx, DSappointment.class);
                                                    toDSappointment.putExtra("type","YCK");
                                                    toDSappointment.putExtra("licenseno",SelectedThirdCar.getThirdCar().getAsString(TaskCK.thirdlicenseno));
                                                    ctx.startActivity(toDSappointment);
                                                }
                                            });


                                            final LinearLayout thirdcar_editwrap= (LinearLayout) detailYCK.findViewById(R.id.thirdcar_editwrap);
                                            detailYCK.danamic_thirdcars_add.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    LayoutAddDanamic.getInstance().addThirdcarEditItem(ctx,thirdcar_editwrap);
                                                    LayoutAddDanamic.getInstance().setDanamicItemaddClick(new LayoutAddDanamic.DanamicAddClickListener() {
                                                        @Override
                                                        public void onClick(String thirdcarStr) {
                                                            if(thirdcarStr==null || thirdcarStr.length()<=0){
                                                                Toast.makeText(ctx,"车牌号不能为空",Toast.LENGTH_SHORT).show();
                                                            }else if(!MRegex.isRightCarNO(thirdcarStr)){
                                                                Toast.makeText(ctx,"车牌号格式错误",Toast.LENGTH_SHORT).show();
                                                            }
                                                            else{
                                                                detailYCK.addthirdcarHttpData(UserManager.getInstance().getUserToken(),UserManager.getInstance().getFrontRole(),detailYCK.selectYCK.getAsString(TaskCK.caseNo),detailYCK.selectYCK.getAsString(TaskCK.id),thirdcarStr,OkCallbackManager.getInstance().addthirdcarCallback(ctx,detailYCK));
                                                                detailYCK.danamic_thirdcars_add.setEnabled(true);
                                                            }
                                                        }
                                                    });
                                                    LayoutAddDanamic.getInstance().setDanamicItemCancelClick(new LayoutAddDanamic.DanamicCancelClickListener() {
                                                        @Override
                                                        public void onClick() {
                                                            detailYCK.danamic_thirdcars_add.setEnabled(true);
                                                        }
                                                    });
                                                    detailYCK.danamic_thirdcars_add.setEnabled(false);
                                                }
                                            });

                                        }

                                    }
                                });
                            }

                        }else{
                            final JSONObject err=jsonObject.getJSONObject("err");//err是否一定是jsonobject?
                            final String msg=err.getString("message");
                            if(msg.equals("token已失效，请重新登录")){//据此判断是否下线
                                //弹出alert
                                detailClass.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        showCloseAlert(detailClass);
                                    }
                                });
                            }else{
                                detailClass.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(ctx,msg,Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                        }
                    }catch (Exception e){
                        Log.e(TAG,"warm exception info:"+e.getMessage());
                    }
                }else{
                    Log.i(TAG,"loginCallbackResponse is null or length0");
                }
            }
        };
        return resultCallback;
    }

    //添加三者车Callback
    public Callback addthirdcarCallback(final Context ctx, final DetailYCK detailClass){
        Callback resultCallback=null;
        resultCallback=new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG,"addthirdcarCallbackFAIL/e"+e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i(TAG,"addthirdcarCallbackSUCCESS");
                if(response!=null){
                    String bodystr=response.body().string();
                    Log.i(TAG,"getthirdcarCallbackResponsebodyStr:"+bodystr);
                    try{
                        JSONObject jsonObject=new JSONObject(bodystr);
                        boolean success=jsonObject.getBoolean("success");
                        if(success){//添加成功
                            boolean data=jsonObject.getBoolean("data");
                            //隐藏编辑框
                            //刷新thirdcars数据
                            detailClass.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    detailClass.thirdcar_editwrap.removeAllViews();//清空编辑区域的view
                                    detailClass.getthirdcarHttpData(detailClass.userManager.getUserToken(),detailClass.userManager.getFrontRole(),
                                            detailClass.selectYCK.getAsString(TaskCK.caseNo),detailClass.selectYCK.getAsString(TaskCK.licenseno),OkCallbackManager.getInstance().getthirdcarCallback(ctx,detailClass));
                                }
                            });
                        }else{
                            final JSONObject err=jsonObject.getJSONObject("err");//err是否一定是jsonobject?
                            final String msg=err.getString("message");
                            if(msg.equals("token已失效，请重新登录")){//据此判断是否下线
                                //弹出alert
                                detailClass.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        showCloseAlert(detailClass);
                                    }
                                });
                            }else{
                                detailClass.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(ctx,msg,Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                        }
                    }catch (Exception e){
                        Log.e(TAG,"warm exception info:"+e.getMessage());
                    }
                }else{
                    Log.i(TAG,"loginCallbackResponse is null or length0");
                }
            }
        };
        return resultCallback;
    }

    public Callback applyAccessCallback(final Context ctx, final DSappointment detailClass){
        Callback resultCallback=null;
        resultCallback=new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG,"applyAccessCallbackFAIL/e"+e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i(TAG,"applyAccessCallbackSUCCESS");
                if(response!=null){
                    String bodystr=response.body().string();
                    Log.i(TAG,"applyAccessCallbackResponsebodyStr:"+bodystr);
                    try{
                        JSONObject jsonObject=new JSONObject(bodystr);
                        boolean success=jsonObject.getBoolean("success");
                        if(success){//添加成功
                            boolean data=jsonObject.getBoolean("data");
                            //隐藏编辑框
                            detailClass.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(ctx,"提交成功",Toast.LENGTH_SHORT).show();
                                    AppManager.getAppManager().finishActivity(detailClass);
                                }
                            });
                        }else{
                            final JSONObject err=jsonObject.getJSONObject("err");//err是否一定是jsonobject?
                            final String msg=err.getString("message");
                            if(msg.equals("token已失效，请重新登录")){//据此判断是否下线
                                //弹出alert
                                detailClass.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        showCloseAlert(detailClass);
                                    }
                                });
                            }else{
                                detailClass.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(ctx,msg,Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                        }
                    }catch (Exception e){
                        Log.e(TAG,"warm exception info:"+e.getMessage());
                    }
                }else{
                    Log.i(TAG,"loginCallbackResponse is null or length0");
                }
            }
        };
        return resultCallback;
    }

    public Callback urgeDealhuitCallback(final Context ctx, final MBaseActivity detailClass){
        Callback resultCallback=null;
        resultCallback=new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG,"urgeDealhuitCallbackFAIL/e"+e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i(TAG,"urgeDealhuitCallbackSUCCESS");
                if(response!=null){
                    String bodystr=response.body().string();
                    Log.i(TAG,"urgeDealhuitCallbackResponsebodyStr:"+bodystr);
                    try{
                        JSONObject jsonObject=new JSONObject(bodystr);
                        boolean success=jsonObject.getBoolean("success");
                        if(success){//添加成功
                            JSONObject data=jsonObject.getJSONObject("data");
                            UrgeResponseData.realname=data.getString("realname");
                            UrgeResponseData.mobile=data.getString("mobile");
                            detailClass.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    showUrgeAlert(detailClass);
                                    //Toast.makeText(ctx,"催办人伤:"+UrgeResponseData.realname+UrgeResponseData.mobile,Toast.LENGTH_SHORT).show();
                                }
                            });
                        }else{
                            final JSONObject err=jsonObject.getJSONObject("err");//err是否一定是jsonobject?
                            final String msg=err.getString("message");
                            if(msg.equals("token已失效，请重新登录")){//据此判断是否下线
                                //弹出alert
                                detailClass.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        showCloseAlert(detailClass);
                                    }
                                });
                            }else{
                                detailClass.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(ctx,msg,Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                        }
                    }catch (Exception e){
                        Log.e(TAG,"warm exception info:"+e.getMessage());
                    }
                }else{
                    Log.i(TAG,"urgeDealhuitCallbackResponse is null or length0");
                }
            }
        };
        return resultCallback;
    }

    public Callback apply_taskchangeCallback(final Context ctx, final DetailYCK detailClass){
        Callback resultCallback=null;
        resultCallback=new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG,"urgeDealhuitCallbackFAIL/e"+e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i(TAG,"urgeDealhuitCallbackSUCCESS");
                if(response!=null){
                    String bodystr=response.body().string();
                    Log.i(TAG,"urgeDealhuitCallbackResponsebodyStr:"+bodystr);
                    try{
                        JSONObject jsonObject=new JSONObject(bodystr);
                        boolean success=jsonObject.getBoolean("success");
                        if(success){//添加成功
                            JSONObject data=jsonObject.getJSONObject("data");
                            UrgeResponseData.realname=data.getString("realname");
                            UrgeResponseData.mobile=data.getString("mobile");
                            detailClass.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //showUrgeAlert(detailClass);
                                    Toast.makeText(ctx,"催办人伤:"+UrgeResponseData.realname+UrgeResponseData.mobile,Toast.LENGTH_SHORT).show();
                                }
                            });
                        }else{
                            final JSONObject err=jsonObject.getJSONObject("err");//err是否一定是jsonobject?
                            final String msg=err.getString("message");
                            if(msg.equals("token已失效，请重新登录")){//据此判断是否下线
                                //弹出alert
                                detailClass.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        showCloseAlert(detailClass);
                                    }
                                });
                            }else{
                                detailClass.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(ctx,msg,Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                        }
                    }catch (Exception e){
                        Log.e(TAG,"warm exception info:"+e.getMessage());
                    }
                }else{
                    Log.i(TAG,"urgeDealhuitCallbackResponse is null or length0");
                }
            }
        };
        return resultCallback;
    }

    public Callback commitRiskRecordCallback(final Context ctx, final FXSBActivity fxsb){
        Callback resultCallback=null;
        resultCallback=new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG,"commitRiskRecordCallbackFAIL/e"+e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i(TAG,"commitRiskRecordCallbackSUCCESS");
                if(response!=null){
                    String bodystr=response.body().string();
                    Log.i(TAG,"getCallbackResponsebodyStr:"+bodystr);
                    try{
                        JSONObject jsonObject=new JSONObject(bodystr);
                        boolean success=jsonObject.getBoolean("success");
                        if(success){
                           /* boolean data=jsonObject.getBoolean("data");
                            if(data){//?依据data为true,还是success为true,设置成功
                                pm.startActivity(denstination, DetailIntentType.UNREAD);
                            }*/
                            fxsb.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(ctx,"提交成功",Toast.LENGTH_SHORT).show();
                                    Intent mIntent=new Intent();
                                    mIntent.putExtra("sbstate","1");
                                    fxsb.setResult(MResultCode.FXSB,mIntent);
                                    AppManager.getAppManager().finishActivity(fxsb);
                                }
                            });

                        }else{
                            final JSONObject err=jsonObject.getJSONObject("err");//err是否一定是jsonobject?
                            final String msg=err.getString("message");
                            if(msg.equals("token已失效，请重新登录")){//据此判断是否下线
                                //弹出alert
                                fxsb.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        showCloseAlert(fxsb);
                                    }
                                });
                            }else{
                                fxsb.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(ctx,msg,Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                        }
                    }catch (Exception e){
                        Log.e(TAG,"exception info:"+e.getMessage());
                    }
                }else{
                    Log.i(TAG,"loginCallbackResponse is null or length0");
                }
            }
        };
        return resultCallback;
    }

    public Callback supperReportCallback(final Context ctx, final AuthorityActivity authorityActivity){
        Callback resultCallback=null;
        resultCallback=new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG,"supperReportCallbackFAIL/e"+e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i(TAG,"supperReportCallbackSUCCESS");
                if(response!=null){
                    String bodystr=response.body().string();
                    Log.i(TAG,"getCallbackResponsebodyStr:"+bodystr);
                    try{
                        JSONObject jsonObject=new JSONObject(bodystr);
                        boolean success=jsonObject.getBoolean("success");
                        if(success){
                           /* boolean data=jsonObject.getBoolean("data");
                            if(data){//?依据data为true,还是success为true,设置成功
                                pm.startActivity(denstination, DetailIntentType.UNREAD);
                            }*/
                            authorityActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(ctx,"提交成功",Toast.LENGTH_SHORT).show();
                                    AppManager.getAppManager().finishActivity(authorityActivity);
                                }
                            });

                        }else{
                            final JSONObject err=jsonObject.getJSONObject("err");//err是否一定是jsonobject?
                            final String msg=err.getString("message");
                            if(msg.equals("token已失效，请重新登录")){//据此判断是否下线
                                //弹出alert
                                authorityActivity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        showCloseAlert(authorityActivity);
                                    }
                                });
                            }else{
                                authorityActivity.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(ctx,msg,Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                        }
                    }catch (Exception e){
                        Log.e(TAG,"exception info:"+e.getMessage());
                    }
                }else{
                    Log.i(TAG,"loginCallbackResponse is null or length0");
                }
            }
        };
        return resultCallback;
    }

    public Callback verifyTicketCallback(final Context ctx, final DetailHP detailHP){
        Callback resultCallback=null;
        resultCallback=new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG,"verifyTicketCallbackFAIL/e"+e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i(TAG,"verifyTicketCallbackSUCCESS");
                if(response!=null){
                    String bodystr=response.body().string();
                    Log.i(TAG,"getCallbackResponsebodyStr:"+bodystr);
                    try{
                        JSONObject jsonObject=new JSONObject(bodystr);
                        boolean success=jsonObject.getBoolean("success");
                        if(success){
                           /* boolean data=jsonObject.getBoolean("data");
                            if(data){//?依据data为true,还是success为true,设置成功
                                pm.startActivity(denstination, DetailIntentType.UNREAD);
                            }*/
                            detailHP.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(ctx,"提交成功",Toast.LENGTH_SHORT).show();
                                    AppManager.getAppManager().finishActivity(detailHP);
                                }
                            });

                        }else{
                            final JSONObject err=jsonObject.getJSONObject("err");//err是否一定是jsonobject?
                            final String msg=err.getString("message");
                            if(msg.equals("token已失效，请重新登录")){//据此判断是否下线
                                //弹出alert
                                detailHP.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        showCloseAlert(detailHP);
                                    }
                                });
                            }else{
                                detailHP.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(ctx,msg,Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                        }
                    }catch (Exception e){
                        Log.e(TAG,"exception info:"+e.getMessage());
                    }
                }else{
                    Log.i(TAG,"loginCallbackResponse is null or length0");
                }
            }
        };
        return resultCallback;
    }

    public Callback access_detailCallback(final Context ctx, final DetailYCK detailYCK){
        Callback resultCallback=null;
        resultCallback=new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG,"access_detailCallbackFAIL/e"+e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i(TAG,"access_detailCallbackSUCCESS");
                if(response!=null){
                    String bodystr=response.body().string();
                    Log.i(TAG,"getCallbackResponsebodyStr:"+bodystr);
                    try{
                        JSONObject jsonObject=new JSONObject(bodystr);
                        boolean success=jsonObject.getBoolean("success");
                        if(success){
                           /* boolean data=jsonObject.getBoolean("data");
                            if(data){//?依据data为true,还是success为true,设置成功
                                pm.startActivity(denstination, DetailIntentType.UNREAD);
                            }*/
                            JSONObject data=jsonObject.getJSONObject("data");
                            AccessDetailData.id=data.getString(AccessDetailBean.id);
                            AccessDetailData.caseNo=data.getString(AccessDetailBean.caseNo);
                            AccessDetailData.case_from=data.getString(AccessDetailBean.case_from);
                            AccessDetailData.case_state=data.getString(AccessDetailBean.case_state);
                            AccessDetailData.caseTime=data.getString(AccessDetailBean.caseTime);
                            AccessDetailData.outTime=data.getString(AccessDetailBean.outTime);
                            AccessDetailData.appointTime=data.getString(AccessDetailBean.appointTime);
                            AccessDetailData.createtime=data.getString(AccessDetailBean.createtime);
                            AccessDetailData.accept_time=data.getString(AccessDetailBean.accept_time);
                            AccessDetailData.finishtime=data.getString(AccessDetailBean.finishtime);
                            AccessDetailData.outNumber=data.getString(AccessDetailBean.outNumber);
                            AccessDetailData.reporter=data.getString(AccessDetailBean.reporter);
                            AccessDetailData.reporterPhone=data.getString(AccessDetailBean.reporterPhone);
                            AccessDetailData.reporter1=data.getString(AccessDetailBean.reporter1);
                            AccessDetailData.reporterPhone1=data.getString(AccessDetailBean.reporterPhone1);
                            AccessDetailData.licenseno=data.getString(AccessDetailBean.licenseno);
                            AccessDetailData.vehicleBrand=data.getString(AccessDetailBean.vehicleBrand);
                            AccessDetailData.investigatorNo=data.getString(AccessDetailBean.investigatorNo);
                            AccessDetailData.car_role=data.getString(AccessDetailBean.car_role);
                            AccessDetailData.riskstate=data.getString(AccessDetailBean.riskstate);
                            AccessDetailData.risktype=data.getString(AccessDetailBean.risktype);
                            AccessDetailData.assessorNo=data.getString(AccessDetailBean.assessorNo);
                            AccessDetailData.assessor_name=data.getString(AccessDetailBean.assessor_name);
                            AccessDetailData.assessor_mobile=data.getString(AccessDetailBean.assessor_mobile);
                            AccessDetailData.assess_address=data.getString(AccessDetailBean.assess_address);
                            AccessDetailData.assess_amount=data.getString(AccessDetailBean.assess_amount);
                            AccessDetailData.risklevel=data.getString(AccessDetailBean.risklevel);
                            AccessDetailData.ticket_tag=data.getString(AccessDetailBean.ticket_tag);
                            AccessDetailData.ticket_state=data.getString(AccessDetailBean.ticket_state);
                            AccessDetailData.isRead=data.getString(AccessDetailBean.isRead);
                            AccessDetailData.expect_amount=data.getString(AccessDetailBean.expect_amount);
                            AccessDetailData.reparations=data.getString(AccessDetailBean.reparations);
                            AccessDetailData.lianTime=data.getString(AccessDetailBean.lianTime);
                            AccessDetailData.assessTime=data.getString(AccessDetailBean.assessTime);
                            AccessDetailData.ticket_amount=data.getString(AccessDetailBean.ticket_amount);
                            AccessDetailData.ticket_name=data.getString(AccessDetailBean.ticket_name);
                            AccessDetailData.cooperative_name=data.getString(AccessDetailBean.cooperative_name);
                            AccessDetailData.should_ticket_amount=data.getString(AccessDetailBean.should_ticket_amount);
                            AccessDetailData.should_ticket_garage=data.getString(AccessDetailBean.should_ticket_garage);
                            AccessDetailData.real_ticket_garage=data.getString(AccessDetailBean.real_ticket_garage);
                            AccessDetailData.real_ticket_time=data.getString(AccessDetailBean.real_ticket_time);
                            AccessDetailData.assess_id=data.getString(AccessDetailBean.assess_id);
                            AccessDetailData.county_id=data.getString(AccessDetailBean.county_id);
                            AccessDetailData.AuthorityContent=data.getString(AccessDetailBean.AuthorityContent);
                            detailYCK.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //Toast.makeText(ctx,"提交成功",Toast.LENGTH_SHORT).show();
                                    Intent toAccessDetail=new Intent(ctx, DSappointmentDetail.class);
                                    detailYCK.startActivity(toAccessDetail);
                                }
                            });

                        }else{
                            final JSONObject err=jsonObject.getJSONObject("err");//err是否一定是jsonobject?
                            final String msg=err.getString("message");
                            if(msg.equals("token已失效，请重新登录")){//据此判断是否下线
                                //弹出alert
                                detailYCK.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        showCloseAlert(detailYCK);
                                    }
                                });
                            }else{
                                detailYCK.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(ctx,msg,Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                        }
                    }catch (final Exception e){
                        detailYCK.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ctx,"exception info:"+e.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        });
                        Log.e(TAG,"exception info:"+e.getMessage());
                    }
                }else{
                    Log.i(TAG,"loginCallbackResponse is null or length0");
                }
            }
        };
        return resultCallback;
    }


    public Callback getTaskCountCallback(final Context ctx, final String frontRole, final ProcessMain processMain){
        Callback resultCallback=null;
        resultCallback=new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG,"getTaskCountCallbackFAIL/e"+e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i(TAG,"getTaskCountCallbackSUCCESS");
                if(response!=null){
                    String bodystr=response.body().string();
                    Log.i(TAG,"getTaskCountCallbackStr:"+bodystr);
                    try{
                        JSONObject jsonObject=new JSONObject(bodystr);
                        boolean success=jsonObject.getBoolean("success");
                        if(success){
                        JSONObject data=jsonObject.getJSONObject("data");
                        if(frontRole.equals("1")){//查勘员
                            String count_dck=data.getString("count_dck");
                            String count_yck=data.getString("count_yck");
                            String count_dds=data.getString("count_dds");
                            //存储未读任务数量
                            if(count_dck!=null && count_dck.length()>0){
                                UnReadCounts.DCKCount=Integer.valueOf(count_dck);
                                /*processMain.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        CountSetHelper.getInstance(processMain).setCount(ParamType.DCK,UnReadCounts.getCount(ParamType.DCK));
                                    }
                                });*/

                            }
                            if(count_yck!=null && count_yck.length()>0){
                                UnReadCounts.YCKCount=Integer.valueOf(count_yck);
                              /*  processMain.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        CountSetHelper.getInstance(processMain).setCount(ParamType.YCK,UnReadCounts.getCount(ParamType.YCK));
                                    }
                                });*/

                            }
                            if(count_dds!=null && count_dds.length()>0){
                                UnReadCounts.DDSCount=Integer.valueOf(count_dds);
                              /*  processMain.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        CountSetHelper.getInstance(processMain).setCount(ParamType.DDS,UnReadCounts.getCount(ParamType.DDS));
                                    }
                                });*/

                            }


                        }else if(frontRole.equals("2")){//定损员
                            String count_dds=data.getString("count_dds");
                            String count_dsz=data.getString("count_dsz");
                            String count_yds=data.getString("count_yds");
                            String count_hp=data.getString("count_hp");
                            //存储未读任务数量
                            if(count_dds!=null && count_dds.length()>0){
                                UnReadCounts.DDSCount=Integer.valueOf(count_dds);
                               /* processMain.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        CountSetHelper.getInstance(processMain).setCount(ParamType.DDS,UnReadCounts.getCount(ParamType.DDS));
                                    }
                                });*/
                            }
                            if(count_dsz!=null && count_dsz.length()>0){
                                UnReadCounts.DSZCount=Integer.valueOf(count_dsz);
                               /* processMain.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        CountSetHelper.getInstance(processMain).setCount(ParamType.DDS,UnReadCounts.getCount(ParamType.DDS));
                                    }
                                });*/
                            }
                            if(count_yds!=null && count_yds.length()>0){
                                UnReadCounts.YDSCount=Integer.valueOf(count_yds);
                                /*processMain.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        CountSetHelper.getInstance(processMain).setCount(ParamType.YDS,UnReadCounts.getCount(ParamType.YDS));
                                    }
                                });*/

                            }
                            if(count_hp!=null && count_hp.length()>0){
                                UnReadCounts.HPCount=Integer.valueOf(count_hp);
                               /* processMain.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        CountSetHelper.getInstance(processMain).setCount(ParamType.HP,UnReadCounts.getCount(ParamType.HP));
                                    }
                                });*/

                            }
                        }else if(frontRole.equals("12")){//查勘定损员
                            String count_dck=data.getString("count_dck");
                            String count_yck=data.getString("count_yck");
                            String count_dds=data.getString("count_dds");
                            String count_dsz=data.getString("count_dsz");
                            String count_yds=data.getString("count_yds");
                            String count_hp=data.getString("count_hp");
                            //存储未读任务数量
                            if(count_dck!=null && count_dck.length()>0){
                                UnReadCounts.DCKCount=Integer.valueOf(count_dck);
                              /*  processMain.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Log.e("okcallbackManager","unReadCountsDCK"+UnReadCounts.getCount(ParamType.DCK));
                                        CountSetHelper.getInstance(processMain).setCount(ParamType.DCK,UnReadCounts.getCount(ParamType.DCK));
                                    }
                                });*/

                            }
                            if(count_yck!=null && count_yck.length()>0){
                                UnReadCounts.YCKCount=Integer.valueOf(count_yck);
                                /*processMain.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        CountSetHelper.getInstance(processMain).setCount(ParamType.YCK,UnReadCounts.getCount(ParamType.YCK));
                                    }
                                });*/

                            }
                            if(count_dds!=null && count_dds.length()>0){
                                UnReadCounts.DDSCount=Integer.valueOf(count_dds);
                                /*processMain.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        CountSetHelper.getInstance(processMain).setCount(ParamType.DDS,UnReadCounts.getCount(ParamType.DDS));
                                    }
                                });*/

                            }
                            if(count_dsz!=null && count_dsz.length()>0){
                                UnReadCounts.DSZCount=Integer.valueOf(count_dsz);
                               /* processMain.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        CountSetHelper.getInstance(processMain).setCount(ParamType.DDS,UnReadCounts.getCount(ParamType.DDS));
                                    }
                                });*/
                            }
                            if(count_yds!=null && count_yds.length()>0){
                                UnReadCounts.YDSCount=Integer.valueOf(count_yds);
                               /* processMain.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        CountSetHelper.getInstance(processMain).setCount(ParamType.YDS,UnReadCounts.getCount(ParamType.YDS));
                                    }
                                });*/

                            }
                            if(count_hp!=null && count_hp.length()>0){
                                UnReadCounts.HPCount=Integer.valueOf(count_hp);
                               /* processMain.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        CountSetHelper.getInstance(processMain).setCount(ParamType.HP,UnReadCounts.getCount(ParamType.HP));
                                    }
                                });*/

                            }
                        }else if(frontRole.equals("3")){//人伤
                            String count_rs_gz=data.getString("count_rs_1");
                            String count_rs_ls=data.getString("count_rs_2");
                            if(count_rs_gz!=null && count_rs_gz.length()>0){
                                UnReadCounts.GZCount=Integer.valueOf(count_rs_gz);
                            }
                            if(count_rs_ls!=null && count_rs_ls.length()>0){
                                UnReadCounts.LSCount=Integer.valueOf(count_rs_ls);
                            }
                        }


                            processMain.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    processMain.updateCountView();
                                    //Toast.makeText(ctx,"提交成功",Toast.LENGTH_SHORT).show();
                                }
                            });

                        }else{
                            final JSONObject err=jsonObject.getJSONObject("err");//err是否一定是jsonobject?
                            final String msg=err.getString("message");
                            if(msg.equals("token已失效，请重新登录")){//据此判断是否下线
                                //弹出alert
                                processMain.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        showCloseAlert(processMain);
                                    }
                                });
                            }else{
                                processMain.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(ctx,msg,Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                        }
                    }catch (Exception e){
                        Log.e(TAG,"exception info:"+e.getMessage());
                    }
                }else{
                    Log.i(TAG,"loginCallbackResponse is null or length0");
                }
            }
        };
        return resultCallback;
    }

    public Callback getUpdateCallback(final Context ctx,final MBaseActivity detailAct){
        Callback resultCallback=null;
        resultCallback=new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG," getUpdateCallbackFAIL/e"+e.getMessage());
                detailAct.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(ctx,"当前版本已是最新版本",Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.i(TAG," getUpdateCallbackSUCCESS");
                if(response!=null){
                    String bodystr=response.body().string();
                    Log.i(TAG,"getCallbackResponsebodyStr:"+bodystr);
                    try{
                        JSONObject jsonObject=new JSONObject(bodystr);
                        boolean success=jsonObject.getBoolean("success");
                        if(success){
                            JSONObject value=jsonObject.getJSONObject("value");
                            if(value!=null){
                                Log.d(TAG, "value is not null");
                                JSONObject latestVersion=value.getJSONObject("latest_version");
                                if(latestVersion!=null){
                                    Log.d(TAG, "lastVersion is not null");
                                    String versionCode=latestVersion.getString("version_code");
                                    String updateHintMsg=latestVersion.getString("update_hint_msg");
                                    String downLoadUrl=latestVersion.getString("update_url");
                                    String releaseDate=latestVersion.getString("release_date");
                                    MyUpdateObject.versionCode=versionCode;
                                    MyUpdateObject.hintMessage=updateHintMsg;
                                    MyUpdateObject.downLoadUrl=downLoadUrl;
                                    MyUpdateObject.releaseDate=releaseDate;
                                    Log.d(TAG, "servierversionCode"+MyUpdateObject.versionCode);

                                    detailAct.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            final String localVersion= UpdateHelper.getInstance(ctx).getVersionCode();
                                            if(UpdateHelper.getInstance(ctx).isNeedUpdate(localVersion,MyUpdateObject.versionCode)){
                                                if(MyUpdateObject.versionCode!=null && MyUpdateObject.versionCode.length()>0){
                                                    showUpdateAlert(detailAct,MyUpdateObject.versionCode);
                                                }else{
                                                    Toast.makeText(ctx,"版本号为空",Toast.LENGTH_SHORT).show();
                                                }
                                            }else{
                                                Toast.makeText(ctx,"当前版本已是最新版本",Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });


                                }
                            }else{
                                Log.d(TAG, "value is  null");
                            }

                        }else{
                           /* final JSONObject err=jsonObject.getJSONObject("err");//err是否一定是jsonobject?
                            final String msg=err.getString("message");
                            if(msg.equals("token已失效，请重新登录")){//据此判断是否下线
                                //弹出alert
                                detailAct.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        showCloseAlert(detailAct);
                                    }
                                });
                            }else{
                                detailAct.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(ctx,msg,Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }*/
                           detailAct.runOnUiThread(new Runnable() {
                               @Override
                               public void run() {
                                   Toast.makeText(ctx,"当前版本已是最新版本",Toast.LENGTH_SHORT).show();
                               }
                           });

                        }
                    }catch (Exception e){
                        Log.e(TAG,"exception info:"+e.getMessage());
                        detailAct.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ctx,"当前版本已是最新版本",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }else{
                    Log.i(TAG,"loginCallbackResponse is null or length0");
                }
            }
        };
        return resultCallback;
    }


    public void showCloseAlert(final Activity mPm){
            if(!mPm.isFinishing()){//判断ProcessMain是否存在
                dialog_alert=new Dialog_alert(mPm,R.style.RiskTipDialog,R.layout.dialog_alerttip);
                dialog_alert.setCancelable(true);
                dialog_alert.setalertText("账号已在其他端登录");
                dialog_alert.setOnPositiveListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(dialog_alert!=null && dialog_alert.isShowing()){
                            dialog_alert.dismiss();
                            dialog_alert=null;
                            AppManager.getAppManager().AppExit(mPm.getApplicationContext());//context 是否正确
                        }
                    }
                });
                dialog_alert.show();
            }
    }

    public void showRiskDg(Context ctx,ArrayList<ContentValues> riskwarmArray){
        risktipDg=new Dialog_risktip(ctx,R.style.RiskTipDialog,riskwarmArray);
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

    public void showUpdateAlert(final Activity mPm,String versionCode){
        if(!mPm.isFinishing()){//判断ProcessMain是否存在
            dialog_alert=new Dialog_alert(mPm,R.style.RiskTipDialog,R.layout.dialog_alerttip);
            dialog_alert.setCancelable(false);
            dialog_alert.setCanceledOnTouchOutside(false);
            dialog_alert.setalertText("请更新版本至"+versionCode);
            dialog_alert.setOnPositiveListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(dialog_alert!=null && dialog_alert.isShowing()){
                        dialog_alert.dismiss();
                        dialog_alert=null;
                        //下载新版本
                        if( MyUpdateObject.downLoadUrl!=null && MyUpdateObject.downLoadUrl.length()>0){
                            UpdateHelper.getInstance(mPm).downLoadAPK(mPm,MyUpdateObject.downLoadUrl);
                        }
                    }
                }
            });
            dialog_alert.show();
        }
    }

    /**
     * 催办人伤显示
     * @param mPm
     */
    public void showUrgeAlert(final Activity mPm){
        if(!mPm.isFinishing()){//判断ProcessMain是否存在
            dialog_alert=new Dialog_alert(mPm,R.style.RiskTipDialog,R.layout.dialog_cbrs);
            dialog_alert.setCancelable(true);
            dialog_alert.initDialog();
            dialog_alert.setRensha_name(UrgeResponseData.realname);
            dialog_alert.setRensha_phone(UrgeResponseData.mobile);
            dialog_alert.setOnPositiveListener(new View.OnClickListener() {//跳转到拨号界面
                @Override
                public void onClick(View v) {
                    if(dialog_alert!=null && dialog_alert.isShowing()){
                        dialog_alert.dismiss();
                        dialog_alert=null;
                        Intent dialIntent =  new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + UrgeResponseData.mobile));//跳转到拨
                        mPm.startActivity(dialIntent);
                       // MPermissions.requestPermissions(mPm, PermissionCode.CALLPERMISSION, Manifest.permission.CALL_PHONE);
                    }
                }
            });
            dialog_alert.show();
        }
    }

/*    *//**
     * 根据type 获取processMain对应的arraylist

     * @return
     *//*
    public ArrayList<ContentValues> getArrayList(String type,ProcessMain pm){
        if(type.equals(ParamType.DCK)){
           return pm.arrayList_dck;
        }else if(type.equals(ParamType.YCK)){
            return pm.arrayList_yck;
        }else if(type.equals(ParamType.DDS)){
            return pm.arrayList_dds;
        }else if(type.equals(ParamType.DSZ)){
            return pm.arrayList_dsz;
        }else if(type.equals(ParamType.YDS)){
            return pm.arrayList_yds;
        }else if(type.equals(ParamType.HP)){
            return pm.arrayList_hp;
        }else if(type.equals(ParamType.RSWORK)){
            return pm.arrayList_rswork;
        }else if(type.equals(ParamType.RSHIS)){
            return pm.arrayList_rshis;
        }
        return null;
    }*/


}
