package com.project.cx.processcontroljx.net;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2017/11/30 0030.
 */

public class MHttpParams {
    private final static String TAG = MHttpParams.class.getSimpleName();
    private static SharedPreferences HTTP_SP;
    public static MHttpParams mHttpParams;
    private Context mContext;
    /** http 存储*/
    public final static String PREFERENCES_HTTP = "JXhttp_preferences";

    /** 默认编码 */
    public static String DEFAULT_CHARSET = "UTF-8";
    /** 默认超时时间 */
    public static  int DEFAULT_TIME_OUT = 15 * 1000;

    /** 默认IP端口 */
    public static String DEFAULT_IP="192.168.1.63";
    public static String DEFAULT_PORT="1010";
    public static String BASEURL = "http://192.168.1.63:1010/";

    //login
    public static String USERLOGIN="";
    //修改密码
    public static String MODIFYPSD="";

    //获取查勘任务数据
    public static String GETTASK_ck="";

    //获取定损任务数据
    public static String GETTASK_ds="";

    //获取人伤任务数据
    public static String GETTASK_hurt="";

    //获取风险上报任务数据
    public static String GETTASK_risk="";

    //风险预警
    public static String GetRisks_warn="";

    //风险预警
    public static String CommitRiskRecord="";

    //设置已读
    public static String SetTask_isread="";

    //定损预约
    public static String Apply_access="";

    //获取定损点和区域
    public static String Getareas="";

    //添加三者车
    public static String Addthirdcar="";

    //获取三者车
    public static String Getthirdcar="";

    //催办人伤
    public static String UrgeDealHurt="";
    //任务改派
    public static String Apply_taskchange="";
    //定损认领
    public static String ClaimTask="";
    //超权限上报
    public static String SuperReport="";
    //上报审批
    public static String TicketReport="";
    //定损预约详情
    public static String Access_detail="";
    //获取摸快任务数量
    public static String GetTaskCount="";
    //自动更新
    public static String Update="";

    private MHttpParams(){}

    public static MHttpParams getInstance(){
        if(mHttpParams==null){
            mHttpParams=new MHttpParams();
        }
        return mHttpParams;
    }

    public  void init(Context ctx){
        mContext=ctx;
        HTTP_SP=getSharedPeferences();
        BASEURL=getURL();//must below HTTP_SP;

        USERLOGIN=BASEURL+"api/AuthSystem/Loginin";
        MODIFYPSD=BASEURL+"api/AuthSystem/ModifyPsd";

        GETTASK_ck=BASEURL+"api/AuthSystem/GetTask_ck";
        GETTASK_ds=BASEURL+"api/AuthSystem/GetTask_ds";
        GETTASK_hurt=BASEURL+"api/AuthSystem/GetTask_hurt";
        GETTASK_risk=BASEURL+"api/AuthSystem/GetTask_risk";

        GetRisks_warn=BASEURL+"api/AuthSystem/GetRisks_warn";
        SetTask_isread=BASEURL+"api/AuthSystem/SetTask_isread";
        CommitRiskRecord=BASEURL+"api/AuthSystem/CommitRiskRecord";

        Apply_access=BASEURL+"api/AuthSystem/Apply_access";
        Getareas=BASEURL+"api/AuthSystem/Getareas";

        Addthirdcar=BASEURL+"api/AuthSystem/Add_thirdcar";
        Getthirdcar=BASEURL+"api/AuthSystem/Get_thirdcars";

        UrgeDealHurt=BASEURL+"api/AuthSystem/UrgeDealHurt";
        Apply_taskchange=BASEURL+"api/AuthSystem/Apply_taskchange";
        ClaimTask=BASEURL+"api/AuthSystem/ClaimTask";
        SuperReport=BASEURL+"api/AuthSystem/SuperReport";
        TicketReport=BASEURL+"api/AuthSystem/TicketReport";
        Access_detail=BASEURL+"api/AuthSystem/Access_detail";
        GetTaskCount=BASEURL+"api/AuthSystem/GetTaskCount";
        Update=BASEURL+"api/Versions/GetVersionInfo";
    }

    public SharedPreferences getSharedPeferences(){
        return mContext.getSharedPreferences(PREFERENCES_HTTP,Context.MODE_PRIVATE);
    }

    public boolean setIP(String ip){
        SharedPreferences sp=mContext.getSharedPreferences(PREFERENCES_HTTP,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString("ip",ip);
        boolean saveSucc=editor.commit();
        return saveSucc;
    }

    public String getIP(){
        return HTTP_SP.getString("ip",DEFAULT_IP);
    }

    public boolean setPort(String port){
        SharedPreferences sp=mContext.getSharedPreferences(PREFERENCES_HTTP,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString("port",port);
        boolean saveSucc=editor.commit();
        return saveSucc;
    }

    public String getPort(){
        return HTTP_SP.getString("port",DEFAULT_PORT);
    }
    //URL e.g. http://192.168.1.13:1010/
    public String getURL(){
        String URL="http://"+getIP()+":"+getPort()+"/";
        return URL;
    }

}
