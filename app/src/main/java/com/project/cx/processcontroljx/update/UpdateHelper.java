package com.project.cx.processcontroljx.update;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.Toast;

import com.project.cx.processcontroljx.R;
import com.project.cx.processcontroljx.net.OkhttpDataHandler;
import com.zhouyou.http.EasyHttp;
import com.zhouyou.http.callback.DownloadProgressCallBack;
import com.zhouyou.http.exception.ApiException;
import com.zhouyou.http.utils.HttpLog;

import java.io.File;

import okhttp3.Callback;


/**
 * Created by Administrator on 2017/9/27 0027.
 */

public class UpdateHelper {
    private static UpdateHelper _instance;
    private String TAG="updateHelper";
    private Context mContext;
    private ProgressDialog progressbar;
    boolean stopThread=false;
    private String fileName;
    private final int percentStep=5;
    private final int SHOWUPDATEDIAG=1;
    private final int UPDATEPROGRESS=2;
    private final int UPDATEWRONG=3;


    Handler dealerHandler=new Handler(){

        @Override
        public void handleMessage(Message msg) {
            // TODO 自动生成的方法存根
            super.handleMessage(msg);
            int msgId=msg.what;
            switch(msgId){
                case SHOWUPDATEDIAG://显示下载dialog
                   // showUpdateDialog();
                    break;
                case UPDATEPROGRESS:
                    Bundle mBundle=msg.getData();
                    int percentCount=mBundle.getInt("percentCount");
                    if(percentCount>0){
                        if(progressbar!=null){
                            progressbar.setProgress(percentCount);
                        }
                    }

                    if(percentCount==100){
                        if(progressbar!=null){
                            if(progressbar.isShowing()){
                                progressbar.dismiss();
                                progressbar=null;
                            }
                            inStallAPK(fileName);
                        }

                    }
                    break;
                default:
                    break;
            }
        }

    };

    public UpdateHelper(Context context){
            mContext=context;
    }
    public static UpdateHelper getInstance(Context ctx){
       /* if(_instance==null){
            _instance=new UpdateHelper(ctx);
        }*/
        _instance=new UpdateHelper(ctx);
        return _instance;
    }

    public void checkupdateHttpData(String SystemOs, String ProjectName, Callback Callback){
        OkhttpDataHandler okhandler=new OkhttpDataHandler(mContext);
        okhandler.setmIsShowProgressDialog(true);
        okhandler.checkupdateHttp(SystemOs,ProjectName,Callback);
    }

    /**
     * 获取当前程序的版本名称
     * @return
     */
    public String getVersionCode(){
        String localVersionName="-1";
        try {
            localVersionName=mContext.getPackageManager().getPackageInfo(mContext.getPackageName(),0).versionName;
        }catch (Exception e){
            // localVersionName="1.0";
            e.printStackTrace();
        }
        return localVersionName;
    }

    /**
     * 安装apk
     * @param fileName
     */
    public void inStallAPK(String fileName){

        File apkFile=null;
        //fileName=mContext.getString(R.string.app_name)+MyUpdateObject.versionCode+".apk";
        try{
            apkFile=new File(Environment.getExternalStorageDirectory()+"/ProcessControlJX/",fileName);
        }catch (Exception e){
            Log.e("apkFile","apkFile except"+e.getMessage());
        }

        Intent installIntent = new Intent(Intent.ACTION_VIEW);
        Uri uri = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Log.e("settings","codeN enter");
            installIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            uri = FileProvider.getUriForFile(mContext.getApplicationContext(), mContext.getApplicationContext().getPackageName() + ".fileprovider", apkFile);
        } else {
            uri = Uri.fromFile(apkFile);
        }

        installIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // installIntent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        installIntent.setAction(Intent.ACTION_VIEW);
        installIntent.setDataAndType(uri,
                "application/vnd.android.package-archive");
        mContext.startActivity(installIntent);
        //android.os.Process.killProcess(android.os.Process.myPid());
    }

    /**
     * 检测是否需要更新
     * @param serverVersionCode
     * @return
     */
    public boolean isNeedUpdate(String localVersion,String serverVersionCode){
        Log.d(TAG, "localVersion"+localVersion);
        if(localVersion.equals("-1")){
            Log.d(TAG, "本地版本namenofound");
            return false;
        }else if(localVersion.compareTo(MyUpdateObject.versionCode)==0){
            Log.d(TAG, "本地版本不需要更新");
            Toast.makeText(mContext, "当前版本已是最新版本", Toast.LENGTH_SHORT).show();
            return false;
        }else if(localVersion.compareTo(MyUpdateObject.versionCode)<0){
            return true;
        }else{
            Log.d(TAG, "本地版大于新版本");
            return false;
        }

    }


    public void setDownLoadDialog(Context ctx){
        progressbar = new ProgressDialog(ctx);
        progressbar.setCanceledOnTouchOutside(false);
        progressbar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);// 设置进度条的形式为圆形转动的进度条
        progressbar.setMessage("正在下载...");
        // 设置提示的title的图标，默认是没有的，如果没有设置title的话只设置Icon是不会显示图标的
        progressbar.setTitle("下载文件");
        progressbar.setMax(100);
    }

    public void downLoadAPK(final Context ctx, final String url){
        setDownLoadDialog(ctx);
        if(!(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))){
            Toast.makeText(ctx, "SD卡不可用", Toast.LENGTH_SHORT).show();
        }else{
            fileName=ctx.getString(R.string.app_name)+MyUpdateObject.versionCode+".apk";
            EasyHttp.downLoad(url).saveName(fileName).savePath(Environment.getExternalStorageDirectory().getPath()+"/ProcessControlJX/").execute(new DownloadProgressCallBack<String>() {

                @Override
                public void onStart() {
                    progressbar.show();
                }

                @Override
                public void onError(ApiException e) {
                    // Toast.makeText(mContext,e.getMessage(),Toast.LENGTH_SHORT).show();
                    Log.e("updateHelper","error:"+e.getMessage());
                    Toast.makeText(ctx,"下载错误",Toast.LENGTH_SHORT).show();
                    progressbar.dismiss();
                }

                @Override
                public void update(long bytesRead, long contentLength, boolean done) {
                    int progress=(int)(bytesRead*100/contentLength);
                    HttpLog.e(progress+"%");
                    progressbar.setProgress(progress);
                    if(done){
                        progressbar.setMessage("下载完成");
                    }
                }

                @Override
                public void onComplete(String path) {
                   /* Toast.makeText(mContext,"文件保存路径："+path,Toast.LENGTH_LONG).show();

                    progressbar.dismiss();*/
                    if(progressbar!=null && progressbar.isShowing()){
                        progressbar.dismiss();
                        progressbar=null;
                    }
                    inStallAPK(fileName);
                }
            });
        }
    }




}
