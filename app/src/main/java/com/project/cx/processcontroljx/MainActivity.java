package com.project.cx.processcontroljx;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.project.cx.processcontroljx.processmain.ProcessMain;
import com.project.cx.processcontroljx.processmain.loginActivity;
import com.project.cx.processcontroljx.theme.MBaseActivity;
import com.project.cx.processcontroljx.utils.UserManager;

public class MainActivity extends MBaseActivity {
Context mContext;
UserManager userManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext=this;
        initData();
        if(userManager.isLogin()){
            Intent toProcess=new Intent(MainActivity.this,ProcessMain.class);
            startActivity(toProcess);
            MainActivity.this.finish();
        }else{
            initView();
        }


    }

    private void initData() {
        userManager= UserManager.getInstance();
        userManager.init(mContext);
    }

    private void initView() {
        Intent toLogin=new Intent(MainActivity.this,loginActivity.class);
        startActivity(toLogin);
        MainActivity.this.finish();
    }
}
