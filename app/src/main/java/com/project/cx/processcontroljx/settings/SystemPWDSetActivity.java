package com.project.cx.processcontroljx.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.project.cx.processcontroljx.R;
import com.project.cx.processcontroljx.net.MHttpParams;
import com.project.cx.processcontroljx.net.OkhttpDataHandler;
import com.project.cx.processcontroljx.processmain.loginActivity;
import com.project.cx.processcontroljx.theme.MBaseActivity;
import com.project.cx.processcontroljx.utils.AppManager;
import com.project.cx.processcontroljx.utils.MD5;
import com.project.cx.processcontroljx.utils.UserManager;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/11/27 0027.
 */

public class SystemPWDSetActivity extends MBaseActivity implements View.OnClickListener {
    EditText old_input,new_input,confirm_input;
    ImageView changePWD_back;
    Button savePWD_btn,cancelPWD_btn;
    Context mContext;
    MHttpParams mHttpParams;
    UserManager userManager;
    private String TAG="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pwd);
        mContext=this;
        TAG=mContext.getClass().getSimpleName();
        initData();
        initView();//must below initData();
    }

    private void initView() {
        old_input= (EditText) findViewById(R.id.old_pwd_et);
        new_input= (EditText) findViewById(R.id.new_pwd_et);
        confirm_input= (EditText) findViewById(R.id.confirm_pwd_et);
        changePWD_back= (ImageView) findViewById(R.id.changePWD_back);
        savePWD_btn= (Button) findViewById(R.id.changepwd_save);
        //cancelPWD_btn= (Button) findViewById(R.id.cancelPWD_btn);


        savePWD_btn.setOnClickListener(this);
        changePWD_back.setOnClickListener(this);
        //cancelPWD_btn.setOnClickListener(this);
    }

    private void initData() {
        mHttpParams=MHttpParams.getInstance();
        mHttpParams.init(mContext);
        userManager=UserManager.getInstance();
        userManager.init(mContext);
    }

    public void modifyPSDHttp(String token,String frontrole,String oldpassword,String newpassword, Callback Callback){
        String oldpasswordMD5= new MD5().toMd5(oldpassword);
        String newpasswordMD5= new MD5().toMd5(newpassword);
        OkhttpDataHandler okhandler=new OkhttpDataHandler(mContext);
        okhandler.setmIsShowProgressDialog(true);
        okhandler.modifyPsdhttp(token,frontrole,oldpasswordMD5,newpasswordMD5,Callback);
    }

    Callback modifyPSDCallback=new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            Log.e(TAG,"errorinfo:"+e.getMessage());
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            Log.i(TAG,"modifyPSDSUCCESS");
            if(response!=null){
                String bodystr=response.body().string();
                Log.i(TAG,"modifyPSDResponsebodyStr:"+bodystr);
                try{
                    JSONObject jsonObject=new JSONObject(bodystr);
                    boolean success=jsonObject.getBoolean("success");
                    if(success){//success为判断标准
                      boolean data=jsonObject.getBoolean("data");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(mContext,"修改成功",Toast.LENGTH_SHORT).show();
                                AppManager.getAppManager().AppExit(mContext);
                                Intent toLogin=new Intent(SystemPWDSetActivity.this,loginActivity.class);
                                startActivity(toLogin);
                            }
                        });
                    }else{
                        final String err=jsonObject.getString("err");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(mContext,err,Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }catch(Exception e){
                    Log.e(TAG,"exception info:"+e.getMessage());
                }
            }else{
                Log.i(TAG,"loginCallbackResponse is null or length0");
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.changepwd_save:
                //http请求
                String old_pwd=old_input.getText().toString().trim();
                String new_pwd=new_input.getText().toString().trim();
                String confirm_pwd=confirm_input.getText().toString().trim();
                if(!new_pwd.equals(confirm_pwd)){
                    Toast.makeText(mContext,"两次输入密码不一致",Toast.LENGTH_SHORT).show();
                    return;
                }
                modifyPSDHttp(userManager.getUserToken(),userManager.getFrontRole(),old_pwd,new_pwd,modifyPSDCallback);
                break;
/*            case R.id.cancelPWD_btn:
                AppManager.getAppManager().finishActivity();
                break;*/
            case R.id.changePWD_back:
                AppManager.getAppManager().finishActivity();
                break;
            default:
                break;
        }

    }
}
