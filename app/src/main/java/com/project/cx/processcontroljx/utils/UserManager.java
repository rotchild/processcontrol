package com.project.cx.processcontroljx.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by cx on 2017/8/14.
 */

public class UserManager {
    private final String TAG=this.getClass().getSimpleName();
    public static final String PREFERENCE_USER="JXpreference_user";
    private SharedPreferences mSP;
    private Context mContext;
    private static UserManager instance;
    private UserManager(){}

    public static  UserManager getInstance(){
        if(instance==null){
            instance=new UserManager();
        }
        return instance;
    }

    public void init(Context ctx){
        mContext=ctx;
        mSP=getUserPreferences();

    }

    public SharedPreferences getUserPreferences(){
        return mContext.getSharedPreferences(PREFERENCE_USER,Context.MODE_PRIVATE);
    }

    /**
     * 获取登录用户
     * @return
     */
    public String getUserName(){
        return mSP.getString("username","");
    }


    /**
     * 获取用户登录状态
     * @return
     */
    public boolean isLogin(){
        return mSP.getBoolean("isLogin",false);
    }

    /**
     * 设置用户登录状态，退出时用
     * @param isLogin
     * @return
     */
    public boolean setIsLogin(boolean isLogin){
        SharedPreferences sp=mContext.getSharedPreferences(PREFERENCE_USER,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putBoolean("isLogin",isLogin);
        boolean saveSucc=editor.commit();
        if(saveSucc==false){
            Log.e(TAG,"saveUserInfo error  context:");
        }
        return saveSucc;
    }

    /**
     * save loginInfo
     * @param ctx
     * @param userid
     * @param username
     * @param token
     * @param frontrole
     * @param realname
     * @param mobile
     * @return
     */
    public boolean saveUserInfo(Context ctx,String userid,String username,String token,String frontrole,
                                String realname,String mobile,String jobNo){
        SharedPreferences sp=mContext.getSharedPreferences(PREFERENCE_USER,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString("userid",userid);
        editor.putString("username",username);
        editor.putString("token",token);
        editor.putString("frontrole",frontrole);
        editor.putString("realname",realname);
        editor.putString("mobile",mobile);
        editor.putString("jobNo",jobNo);
        boolean saveSucc=editor.commit();
        if(saveSucc==false){
            Log.e(TAG,"saveUserInfoERROR");
        }
        return saveSucc;
    }

    /**
     * when login act
     * @param ctx
     * @param UserName
     * @param DeviceNo
     * @param stationId
     * @param accid
     * @param token
     * @return
     */

    /**
     * 设置登录状态
     * @param isLogin
     * @return
     */
    public boolean saveIsLogin(boolean isLogin){
        SharedPreferences sp=mContext.getSharedPreferences(PREFERENCE_USER,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putBoolean("isLogin",isLogin);
        boolean saveSucc=editor.commit();
        return saveSucc;
    }

    public int getUserType(){
        return Integer.valueOf(mSP.getString("frontrole","0"));
    }

    public String getUserToken(){
        return mSP.getString("token","");
    }

    public String getFrontRole(){
        return mSP.getString("frontrole","");
    }
    //获取登录工号
    public String getJobNo(){
        return mSP.getString("jobNo","");
    }

}
