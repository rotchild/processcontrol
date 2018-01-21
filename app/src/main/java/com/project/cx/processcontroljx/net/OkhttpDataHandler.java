package com.project.cx.processcontroljx.net;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/11/30 0030.
 */

public class OkhttpDataHandler {
    private Context mContext;
    private OkHttpClient okhttpClient=new OkHttpClient.Builder()
            .connectTimeout(MHttpParams.DEFAULT_TIME_OUT, TimeUnit.MILLISECONDS)
            .readTimeout(MHttpParams.DEFAULT_TIME_OUT,TimeUnit.MILLISECONDS)
            .build();
    private ProgressDialog mProgressDialog;
    private boolean mIsShowProgressDialog=true;
    private Callback mCallback=null;
    private final String TAG=getClass().getSimpleName();
    //private volatile int counts=0;//并发请求遮罩处理问题,volatile 实现同步

    public OkhttpDataHandler(Context ctx){
        mContext=ctx;
        initProgressDialog(mContext);
    }

    private void initProgressDialog(Context context) {
        mProgressDialog=new ProgressDialog(context);
        String loading="正在请求网络...";
        mProgressDialog.setMessage(loading);
        mProgressDialog.setCanceledOnTouchOutside(false);
    }

    public void setmIsShowProgressDialog(boolean isShow) {
        mIsShowProgressDialog=isShow;
        if (!mIsShowProgressDialog && mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

 /*   public void setCounts(int mcounts){//并发请求
        counts=mcounts;
    }
    public int getCounts(){
        return counts;
    }*/


    /**
     * login
     * @param username
     * @param password
     * @param Callback
     */
    public void loginOKhttp(String username,String password, Callback Callback){
        Log.i(TAG,"loginOKhttp enter");
        if(mIsShowProgressDialog) mProgressDialog.show();
        mCallback=Callback;
        String url= MHttpParams.USERLOGIN;
        RequestBody requestBody=new FormBody.Builder().add("username",username).add("password",password).build();
        Request request=new Request.Builder().url(url).post(requestBody).build();
        Log.i(TAG,"loginOKhttp params:"+"url:"+url+"?username="+username+"&"+"password="+password);
        Call call=okhttpClient.newCall(request);
        call.enqueue(pcallback);
    }

    /**
     * 修改密码
     * @param token
     * @param frontrole
     * @param oldpassword
     * @param newpassword
     * @param Callback
     */
    public void modifyPsdhttp(String token,String frontrole,String oldpassword,String newpassword, Callback Callback){
        Log.i(TAG,"modifyPsdhttp enter");
        if(mIsShowProgressDialog) mProgressDialog.show();
        mCallback=Callback;
        String url= MHttpParams.MODIFYPSD;
        RequestBody requestBody=new FormBody.Builder().add("token",token).add("frontrole",frontrole).
                add("oldpassword",oldpassword).add("newpassword",newpassword).build();
        Log.i(TAG,"modifyPsdhttp params:"+"url:"+url+"?token="+token+"&"+"frontrole="+frontrole+"&"+"oldpassword="+oldpassword+"&"+"newpassword="+newpassword);
        Request request=new Request.Builder().url(url).post(requestBody).build();
        Call call=okhttpClient.newCall(request);
        call.enqueue(pcallback);
    }

    /**
     * 查勘任务数据
     * @param token
     * @param frontrole
     * @param type
     * @param start
     * @param limit
     * @param Callback
     */
    public void getTaskCKHttp(String token,String frontrole, String type,String lian_state,String riskstate,String risklevel,String keyword,
                              int start,int limit,Callback Callback){
        Log.i(TAG,"getTaskCKHttp enter");
        String startStr=String.valueOf(start);
        String limitStr=String.valueOf(limit);

        if(mIsShowProgressDialog) mProgressDialog.show();
        mCallback=Callback;
        String url= MHttpParams.GETTASK_ck;
        RequestBody requestBody=new FormBody.Builder().add("token",token).add("frontrole",frontrole).
                add("type",type).add("lian_state",lian_state).add("riskstate",riskstate).
                add("risklevel",risklevel).add("keyword",keyword).add("start",startStr).add("limit",limitStr).build();
        Log.i(TAG,"getTaskCKHttp params:"+"url:"+url+"?token="+token+"&"+"frontrole="+frontrole+"&"+"type="+type+"&"+"lian_state="+lian_state+"&"+"riskstate="+riskstate+
                "&"+"risklevel="+risklevel+"&"+"keyword="+keyword+"&"+"start="+startStr+"&"+"limit="+limitStr);
        Request request=new Request.Builder().url(url).post(requestBody).build();
        Call call=okhttpClient.newCall(request);
        call.enqueue(pcallback);
    }

    /**
     * 定损任务数据
     * @param token
     * @param frontrole
     * @param type
     * @param start
     * @param limit
     * @param Callback
     */
    public void getTaskDSHttp(String token,String frontrole, String type,String keyword,int start,int limit,Callback Callback){
        Log.i(TAG,"getTaskDSHttp enter");
        String startStr=String.valueOf(start);
        String limitStr=String.valueOf(limit);

        if(mIsShowProgressDialog) mProgressDialog.show();
        mCallback=Callback;
        String url= MHttpParams.GETTASK_ds;
        RequestBody requestBody=new FormBody.Builder().add("token",token).add("frontrole",frontrole).
                add("type",type).add("keyword",keyword).add("start",startStr).add("limit",limitStr).build();
        Log.i(TAG,"getTaskDSHttp params:"+"url:"+url+"?token="+token+"&"+"frontrole="+frontrole+"&"+"type="+type+"&"+"keyword="+keyword+"&"+"start="
                +start+"&"+"limit="+limit);
        Request request=new Request.Builder().url(url).post(requestBody).build();
        Call call=okhttpClient.newCall(request);
        call.enqueue(pcallback);
    }

    public void getTaskhurtHttp(String token,String frontrole, String type,String keyword,int start,int limit,Callback Callback){
        Log.i(TAG,"getTaskDSHttp enter");
        String startStr=String.valueOf(start);
        String limitStr=String.valueOf(limit);

        if(mIsShowProgressDialog) mProgressDialog.show();
        mCallback=Callback;
        String url= MHttpParams.GETTASK_hurt;
        RequestBody requestBody=new FormBody.Builder().add("token",token).add("frontrole",frontrole).
                add("type",type).add("keyword",keyword).add("start",startStr).add("limit",limitStr).build();
        Log.i(TAG,"getTaskhurtHttp params:"+"url:"+url+"?token="+token+"&"+"frontrole="+frontrole+"&"+"type="+type+"&"+"keyword="+keyword+"&"+"start="
                +start+"&"+"limit="+limit);
        Request request=new Request.Builder().url(url).post(requestBody).build();
        Call call=okhttpClient.newCall(request);
        call.enqueue(pcallback);
    }

    /**
     * 风险预警
     * @param token
     * @param frontrole
     * @param taskid
     * @param task_role
     * @param Callback
     */
    public void getRisksWarnHttp(String token,String frontrole,String taskid,String task_role,Callback Callback){
        Log.i(TAG,"getRisksWarnHttp enter");
        if(mIsShowProgressDialog) mProgressDialog.show();
        mCallback=Callback;
        String url= MHttpParams.GetRisks_warn;
        RequestBody requestBody=new FormBody.Builder().add("token",token).add("frontrole",frontrole).
                add("taskid",taskid).add("task_role",task_role).build();
        Log.i(TAG,"getTaskDSHttp params:"+"url:"+url+"?token="+token+"&"+"frontrole="+frontrole+"&"+"taskid="+taskid+"&"+"task_role="+task_role);
        Request request=new Request.Builder().url(url).post(requestBody).build();
        Call call=okhttpClient.newCall(request);
        call.enqueue(pcallback);
    }

    /**
     * 设置任务已读
     * @param token
     * @param frontrole
     * @param taskid
     * @param Callback
     */
    public void setTaskReadHttp(String token,String frontrole, String taskid,String task_role,Callback Callback){
        Log.i(TAG,"getRisksWarnHttp enter");
        if(mIsShowProgressDialog) mProgressDialog.show();
        mCallback=Callback;
        String url= MHttpParams.SetTask_isread;
        RequestBody requestBody=new FormBody.Builder().add("token",token).add("frontrole",frontrole).
                add("taskid",taskid).add("task_role",task_role).build();
        Log.i(TAG,"getTaskDSHttp params:"+"url:"+url+"?token="+token+"&"+"frontrole="+frontrole+"&"+"taskid="+taskid+"&"+"task_role="+task_role);
        Request request=new Request.Builder().url(url).post(requestBody).build();
        Call call=okhttpClient.newCall(request);
        call.enqueue(pcallback);
    }

    /**
     * 提交风险上报
     * @param token
     * @param frontrole
     * @param taskid
     * @param task_role
     * @param risktype
     * @param others
     * @param remark
     * @param Callback
     */
    public void commitRiskRecordHttp(String token,String frontrole,String taskid,String task_role,String risktype,String risktype_sys,String risktype_man,String others,
                                      String remark,Callback Callback){
        Log.i(TAG,"comimitRiskRecordHttp enter");
        if(mIsShowProgressDialog) mProgressDialog.show();
        mCallback=Callback;
        String url= MHttpParams.CommitRiskRecord;
        RequestBody requestBody=new FormBody.Builder().add("token",token).add("frontrole",frontrole).
                add("taskid",taskid).add("task_role",task_role).add("risktype",risktype).add("risktype_sys",risktype_sys).add("risktype_man",risktype_man)
                .add("others",others).add("remark",remark).build();
        Log.i(TAG,"comimitRiskRecordHttp params:"+"url:"+url+"?token="+token+"&"+"frontrole="+frontrole+"&"+"taskid="+taskid+"&"+"task_role="+task_role
        +"&"+"risktype="+risktype +"&"+"risktype_sys="+risktype_sys+"&"+"risktype_man="+risktype_man+"&"+"others="+others+"&"+"remark="+remark);
        Request request=new Request.Builder().url(url).post(requestBody).build();
        Call call=okhttpClient.newCall(request);
        call.enqueue(pcallback);
    }

    /**
     * 风险追踪
     * @param token
     * @param frontrole
     * @param task_role
     * @param caseNo
     * @param licenseNo
     * @param start
     * @param limit
     * @param Callback
     */
    public void getTaskRiskHttp(String token,String frontrole, String task_role,String caseNo,String licenseNo,int start,int limit,Callback Callback){
        Log.i(TAG,"getTaskRiskHttp enter");
        String startStr=String.valueOf(start);
        String limitStr=String.valueOf(limit);

        if(mIsShowProgressDialog) mProgressDialog.show();
        mCallback=Callback;
        String url= MHttpParams.GETTASK_risk;
        RequestBody requestBody=new FormBody.Builder().add("token",token).add("frontrole",frontrole).
                add("task_role",task_role).add("caseNo",caseNo).add("licenseno",licenseNo)
                .add("start",startStr).add("limit",limitStr).build();
        Log.i(TAG,"getTaskRiskHttp params:"+"url:"+url+"?token="+token+"&"+"frontrole="+frontrole+"&"+"task_role="+task_role+"&"+"caseNo="+caseNo+"&"+"licenseno="+licenseNo+"&"+"start="
                +start+"&"+"limit="+limit);
        Request request=new Request.Builder().url(url).post(requestBody).build();
        Call call=okhttpClient.newCall(request);
        call.enqueue(pcallback);
    }

    /**
     * 定损预约
     * @param token
     * @param frontrole
     * @param taskid
     * @param licenseno
     * @param car_role
     * @param reporter1
     * @param reporterPhone1
     * @param assess_address
     * @param appointTime
     * @param case_from
     * @param Callback
     */
    public void applyAccessHttp(String token,String frontrole, String taskid,String licenseno,String car_role, String reporter1,
                                String reporterPhone1,String assess_address,String appointTime,String case_from,String assess_id,String county_id,String vehicleBrand,String group_id,Callback Callback){
        Log.i(TAG,"getRisksWarnHttp enter");
        if(mIsShowProgressDialog) mProgressDialog.show();
        mCallback=Callback;
        String url= MHttpParams.Apply_access;
        RequestBody requestBody=new FormBody.Builder().add("token",token).add("frontrole",frontrole).
                add("taskid",taskid).add("licenseno",licenseno).add("car_role",car_role).add("reporter1",reporter1).
                add("reporterPhone1",reporterPhone1).add("assess_address",assess_address).add("appointTime",appointTime).add("case_from",case_from).
                add("assess_id",assess_id).add("county_id",county_id).add("vehicleBrand",vehicleBrand).add("group_id",group_id).build();
        Log.i(TAG,"applyAccessHttp params:"+"url:"+url+"?token="+token+"&"+"frontrole="+frontrole+"&"+"taskid="+taskid+"&"+"licenseno="+licenseno+"&"+"car_role="+car_role
                +"&"+"reporter1="+reporter1+"&"+"reporterPhone1="+reporterPhone1+"&"+"assess_address="+assess_address+"&"+"appointTime="+appointTime+"&"+"case_from="+case_from+"&"+"assess_id="+assess_id
        +"&"+"county_id="+county_id+"&"+"vehicleBrand="+vehicleBrand+"&"+"group_id="+group_id);
        Request request=new Request.Builder().url(url).post(requestBody).build();
        Call call=okhttpClient.newCall(request);
        call.enqueue(pcallback);
    }

    /**
     * 获取定损点和区域
     * @param token
     * @param frontrole
     * @param Callback
     */
    public void getAreasHttp(String token,String frontrole,String taskid,Callback Callback){
        Log.i(TAG,"getAreasHttp enter");
        if(mIsShowProgressDialog) mProgressDialog.show();
        mCallback=Callback;
        String url= MHttpParams.Getareas;
        RequestBody requestBody=new FormBody.Builder().add("token",token).add("frontrole",frontrole).
                add("taskid",taskid).build();
        Log.i(TAG,"getAreasHttp params:"+"url:"+url+"?token="+token+"&"+"frontrole="+frontrole+"&"+"taskid="+taskid);
        Request request=new Request.Builder().url(url).post(requestBody).build();
        Call call=okhttpClient.newCall(request);
        call.enqueue(pcallback);
    }

    /**
     * 添加三者车
     * @param token
     * @param frontrole
     * @param taskid
     * @param thirdlicenseno
     * @param Callback
     */
    public void addthirdcarHttp(String token,String frontrole,String caseNo,String taskid,String thirdlicenseno,Callback Callback){
        Log.i(TAG,"addthirdcarHttp enter");
        if(mIsShowProgressDialog) mProgressDialog.show();
        mCallback=Callback;
        String url= MHttpParams.Addthirdcar;
        RequestBody requestBody=new FormBody.Builder().add("token",token).add("frontrole",frontrole).
                add("caseNo",caseNo).add("taskid",taskid).add("thirdlicenseno",thirdlicenseno).build();
        Log.i(TAG,"addthirdcarHttp params:"+"url:"+url+"?token="+token+"&"+"frontrole="+frontrole+"&"+"caseNo="+caseNo+"&"+"thirdlicenseno="+thirdlicenseno);
        Request request=new Request.Builder().url(url).post(requestBody).build();
        Call call=okhttpClient.newCall(request);
        call.enqueue(pcallback);
    }

    /**
     * 获取三者车
     * @param token
     * @param frontrole
     * @param caseNo
     * @param licenseno
     * @param Callback
     */
    public void getthirdcarHttp(String token,String frontrole,String caseNo,String licenseno,Callback Callback){
        Log.i(TAG,"getthirdcarHttp enter");
        if(mIsShowProgressDialog) mProgressDialog.show();
        mCallback=Callback;
        String url= MHttpParams.Getthirdcar;
        RequestBody requestBody=new FormBody.Builder().add("token",token).add("frontrole",frontrole).
                add("caseNo",caseNo).add("licenseno",licenseno).build();
        Log.i(TAG,"addthirdcarHttp params:"+"url:"+url+"?token="+token+"&"+"frontrole="+frontrole+"&"+"caseNo="+caseNo+"&"+"licenseno="+licenseno);
        Request request=new Request.Builder().url(url).post(requestBody).build();
        Call call=okhttpClient.newCall(request);
        call.enqueue(pcallback);
    }

    /**
     * 催办人伤
     * @param token
     * @param frontrole
     * @param taskid
     * @param Callback
     */
    public void urgeDealHurtHttp(String token,String frontrole,String taskid,Callback Callback){
        Log.i(TAG,"urgeDealHurtHttp enter");
        if(mIsShowProgressDialog) mProgressDialog.show();
        mCallback=Callback;
        String url= MHttpParams.UrgeDealHurt;
        RequestBody requestBody=new FormBody.Builder().add("token",token).add("frontrole",frontrole).
                add("taskid",taskid).build();
        Log.i(TAG,"addthirdcarHttp params:"+"url:"+url+"?token="+token+"&"+"frontrole="+frontrole+"&"+"taskid="+taskid);
        Request request=new Request.Builder().url(url).post(requestBody).build();
        Call call=okhttpClient.newCall(request);
        call.enqueue(pcallback);
    }

    /**
     * 任务改派
     * @param token
     * @param frontrole
     * @param taskid
     * @param assess_address
     * @param appointTime
     * @param assess_id
     * @param county_id
     * @param Callback
     */
    public void apply_taskchangeHttp(String token,String frontrole,String taskid,String assess_address,String appointTime,
                                     String assess_id,String county_id,String group_id,Callback Callback){
        Log.i(TAG,"apply_taskchangeHttp enter");
        if(mIsShowProgressDialog) mProgressDialog.show();
        mCallback=Callback;
        String url= MHttpParams.Apply_taskchange;
        RequestBody requestBody=new FormBody.Builder().add("token",token).add("frontrole",frontrole).
                add("taskid",taskid).add("assess_address",assess_address).add("appointTime",appointTime).
                add("assess_id",assess_id).add("county_id",county_id).add("group_id",group_id).build();
        Log.i(TAG,"addthirdcarHttp params:"+"url:"+url+"?token="+token+"&"+"frontrole="+frontrole+"&"+"taskid="+taskid+"&"+"assess_address="+assess_address
                +"&"+"appointTime="+appointTime+"&"+"assess_id="+assess_id+"&"+"county_id="+county_id+"&"+"group_id="+group_id);
        Request request=new Request.Builder().url(url).post(requestBody).build();
        Call call=okhttpClient.newCall(request);
        call.enqueue(pcallback);
    }


    /**
     * claimTaskHttp
     * @param token
     * @param frontrole
     * @param taskid
     * @param Callback
     */
    public void claimTaskHttp(String token,String frontrole,String taskid,Callback Callback){
        Log.i(TAG,"claimTaskHttp enter");
        if(mIsShowProgressDialog) mProgressDialog.show();
        mCallback=Callback;
        String url= MHttpParams.ClaimTask;
        RequestBody requestBody=new FormBody.Builder().add("token",token).add("frontrole",frontrole).
                add("taskid",taskid).build();
        Log.i(TAG,"addthirdcarHttp params:"+"url:"+url+"?token="+token+"&"+"frontrole="+frontrole+"&"+"taskid="+taskid);
        Request request=new Request.Builder().url(url).post(requestBody).build();
        Call call=okhttpClient.newCall(request);
        call.enqueue(pcallback);
    }

    /**
     * 超权限上报
     * @param token
     * @param frontrole
     * @param taskid
     * @param expect_amount
     * @param vehicleBrand
     * @param remark
     * @param Callback
     */
    public void supperReportHttp(String token,String frontrole,String taskid,String expect_amount, String vehicleBrand, String remark,Callback Callback){
        Log.i(TAG,"supperReportHttp enter");
        if(mIsShowProgressDialog) mProgressDialog.show();
        mCallback=Callback;
        String url= MHttpParams.SuperReport;
        RequestBody requestBody=new FormBody.Builder().add("token",token).add("frontrole",frontrole).
                add("taskid",taskid).add("expect_amount",expect_amount).add("vehicleBrand",vehicleBrand).add("remark",remark).build();
        Log.i(TAG,"addthirdcarHttp params:"+"url:"+url+"?token="+token+"&"+"frontrole="+frontrole+"&"+"taskid="+taskid+"&"+"expect_amount="+expect_amount
                +"&"+"vehicleBrand="+vehicleBrand+"&"+"remark="+remark);
        Request request=new Request.Builder().url(url).post(requestBody).build();
        Call call=okhttpClient.newCall(request);
        call.enqueue(pcallback);
    }

    /**
     * 上报审批
     * @param token
     * @param frontrole
     * @param taskid
     * @param remarks
     * @param Callback
     */
    public void TicketReportHttp(String token,String frontrole,String taskid,String remarks,Callback Callback){
        Log.i(TAG,"TicketReportHttp enter");
        if(mIsShowProgressDialog) mProgressDialog.show();
        mCallback=Callback;
        String url= MHttpParams.TicketReport;
        RequestBody requestBody=new FormBody.Builder().add("token",token).add("frontrole",frontrole).
                add("taskid",taskid).add("remark",remarks).build();
        Log.i(TAG,"addthirdcarHttp params:"+"url:"+url+"?token="+token+"&"+"frontrole="+frontrole+"&"+"taskid="+taskid
               +"&"+"remark="+remarks);
        Request request=new Request.Builder().url(url).post(requestBody).build();
        Call call=okhttpClient.newCall(request);
        call.enqueue(pcallback);
    }

    /**
     * 定损预约详情
     * @param token
     * @param frontrole
     * @param caseNo
     * @param licenseno
     * @param Callback
     */
    public void access_detailHttp(String token,String frontrole,String caseNo,String licenseno,Callback Callback){
        Log.i(TAG,"TicketReportHttp enter");
        if(mIsShowProgressDialog) mProgressDialog.show();
        mCallback=Callback;
        String url= MHttpParams.Access_detail;
        RequestBody requestBody=new FormBody.Builder().add("token",token).add("frontrole",frontrole).
                add("caseNo",caseNo).add("licenseno",licenseno).build();
        Log.i(TAG,"addthirdcarHttp params:"+"url:"+url+"?token="+token+"&"+"frontrole="+frontrole+"&"+"caseNo="+caseNo
                +"&"+"licenseno="+licenseno);
        Request request=new Request.Builder().url(url).post(requestBody).build();
        Call call=okhttpClient.newCall(request);
        call.enqueue(pcallback);
    }

    /**
     * 获取木块任务数量
     * @param token
     * @param frontrole
     * @param Callback
     */
    public void getTaskCountHttp(String token,String frontrole,Callback Callback){
        Log.i(TAG,"getTaskCount enter");
        if(mIsShowProgressDialog) mProgressDialog.show();
        mCallback=Callback;
        String url= MHttpParams.GetTaskCount;
        RequestBody requestBody=new FormBody.Builder().add("token",token).add("frontrole",frontrole).build();
        Log.i(TAG,"addthirdcarHttp params:"+"url:"+url+"?token="+token+"&"+"frontrole="+frontrole);
        Request request=new Request.Builder().url(url).post(requestBody).build();
        Call call=okhttpClient.newCall(request);
        call.enqueue(pcallback);
    }

    /**
     * 检测版本更新
     * @param SystemOS
     * @param ProjectName
     * @param Callback
     */
    public void checkupdateHttp(String SystemOS, String ProjectName, Callback Callback){
        Log.i(TAG,"getTaskCount enter");
        if(mIsShowProgressDialog) mProgressDialog.show();
        mCallback=Callback;
        String url= MHttpParams.Update;
        RequestBody requestBody=new FormBody.Builder().add("SystemOS",SystemOS).add("ProjectName",ProjectName).build();
        Log.i(TAG,"checkupdateHttp params:"+"url:"+url+"?SystemOS="+SystemOS+"&"+"ProjectName="+ProjectName);
        Request request=new Request.Builder().url(url).post(requestBody).build();
        Call call=okhttpClient.newCall(request);
        call.enqueue(pcallback);
    }

    private Callback pcallback=new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            if(mProgressDialog!=null && mIsShowProgressDialog && mProgressDialog.isShowing()){
                mProgressDialog.dismiss();
            }

            if(mCallback!=null){
                mCallback.onFailure(call,e);
            }
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            if(mProgressDialog!=null && mIsShowProgressDialog && mProgressDialog.isShowing()){
                mProgressDialog.dismiss();
            }
            if(mCallback!=null){
                mCallback.onResponse(call,response);
            }
        }
    };
}
