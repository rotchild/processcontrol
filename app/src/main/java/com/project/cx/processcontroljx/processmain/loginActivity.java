package com.project.cx.processcontroljx.processmain;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.project.cx.processcontroljx.R;
import com.project.cx.processcontroljx.net.OkhttpDataHandler;
import com.project.cx.processcontroljx.settings.SystemSetActivity;
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

public class loginActivity extends MBaseActivity implements View.OnClickListener {
    Button loginbtn,settingbtn;
    String username,password,pwMd5;
    Context mContext;
    UserManager userManager;

    EditText usernameET,passwordEt;
    private final String TAG=getClass().getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mContext=this;
        initData();
        initView();
    }

    private void initView() {
        usernameET= (EditText) findViewById(R.id.username);
        passwordEt= (EditText) findViewById(R.id.password);


        usernameET.setText(userManager.getUserName());


        loginbtn= (Button) findViewById(R.id.login);
        settingbtn= (Button) findViewById(R.id.setting);

        loginbtn.setOnClickListener(this);
        settingbtn.setOnClickListener(this);
    }

    private void initData() {
        userManager=UserManager.getInstance();
        userManager.init(mContext);
    }

    public void login(String username,String password){
        pwMd5= new MD5().toMd5(password);
        OkhttpDataHandler okhandler=new OkhttpDataHandler(mContext);
        okhandler.setmIsShowProgressDialog(true);
        okhandler.loginOKhttp(username,pwMd5,loginCallback);
    }

    Callback loginCallback=new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {
            Log.e(TAG,"errorinfo:"+e.getMessage());
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(mContext,"IP或端口设置错误",Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            Log.i(TAG,"loginCallbackSUCCESS");
            if(response!=null){
            String bodystr=response.body().string();
            Log.i(TAG,"loginCallbackResponsebodyStr:"+bodystr);
            try{
                JSONObject jsonObject=new JSONObject(bodystr);
                boolean success=jsonObject.getBoolean("success");
                if(success){
                    JSONObject data=jsonObject.getJSONObject("data");
                    String userid=data.getString("userid");
                    //String password=data.getString("password");
                    String username=data.getString("username");
                    String token=data.getString("token");
                    String frontrole=data.getString("frontrole");
                    String realname=data.getString("realname");
                    String mobile=data.getString("mobile");
                    String jobNo=data.getString("jobNo");//工号,在待定损任务，须比较assessorNo和工号,if不一致,为查勘员dds,一致为定损员dds

                    userManager.saveUserInfo(mContext,userid,username,token,frontrole,realname,mobile,jobNo);
                    userManager.setIsLogin(true);
                    Intent toLogin=new Intent(loginActivity.this,ProcessMain.class);
                    startActivity(toLogin);
                    AppManager.getAppManager().finishActivity();

                }else{
                    JSONObject err=jsonObject.getJSONObject("err");
                    final String message=err.getString("message");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(mContext,message,Toast.LENGTH_SHORT).show();
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
        switch (v.getId()){
            case R.id.login:
                String username=usernameET.getText().toString().trim();
                String password=passwordEt.getText().toString().trim();
                if(TextUtils.isEmpty(username)||TextUtils.isEmpty(password)){
                    Toast.makeText(mContext,"用户名或密码不能为空",Toast.LENGTH_SHORT).show();
                }else{
                    login(username,password);
                }
/*                Intent toLogin=new Intent(loginActivity.this,ProcessMain.class);
                startActivity(toLogin);
                AppManager.getAppManager().finishActivity();*/
                break;
            case R.id.setting:
                //Intent toSetting=new Intent(loginActivity.this,SystemIPSetActivity.class);
                Intent toSetting=new Intent(loginActivity.this,SystemSetActivity.class);
                startActivity(toSetting);
                break;
            default:
                break;
        }
    }

}
