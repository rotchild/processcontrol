package com.project.cx.processcontroljx.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.cx.processcontroljx.R;
import com.project.cx.processcontroljx.processmain.loginActivity;
import com.project.cx.processcontroljx.theme.MBaseActivity;
import com.project.cx.processcontroljx.update.MyUpdateObject;
import com.project.cx.processcontroljx.update.UpdateHelper;
import com.project.cx.processcontroljx.utils.AppManager;
import com.project.cx.processcontroljx.utils.MViewManager;
import com.project.cx.processcontroljx.utils.OkCallbackManager;
import com.project.cx.processcontroljx.utils.UserManager;

/**
 * Created by Administrator on 2017/11/27 0027.
 */

public class SystemSetActivity extends MBaseActivity implements View.OnClickListener {
    Context mContext;
    RelativeLayout system_ipsetting_layout,system_changePWD_layout;
    ImageView checkupdate_iv;
    ImageView setting_back;
    Button system_exit;
    TextView setting_version;
    TextView settingTitle;
    public UserManager userManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_systemsetting);
        mContext=this;
        initData();
        initView();//must below initData();
        Log.e("systemSetActivity","onCreate Enter");
    }

    private void initView() {
        setting_version= (TextView) findViewById(R.id.setting_version);
        String versionName="V"+ UpdateHelper.getInstance(mContext).getVersionCode();//V1.1
        setting_version.setText(versionName);
        checkupdate_iv= (ImageView) findViewById(R.id.checkupdate_iv);
        checkupdate_iv.setOnClickListener(this);

        system_ipsetting_layout= (RelativeLayout) findViewById(R.id.system_ipsetting_layout);
        system_changePWD_layout= (RelativeLayout) findViewById(R.id.system_changePWD_layout);

        setting_back= (ImageView) findViewById(R.id.setting_back);
        system_exit=(Button) findViewById(R.id.system_exit);

        system_ipsetting_layout.setOnClickListener(this);
        system_changePWD_layout.setOnClickListener(this);
        checkupdate_iv.setOnClickListener(this);
        setting_back.setOnClickListener(this);
        system_exit.setOnClickListener(this);
        if(userManager.isLogin()){
            system_changePWD_layout.setVisibility(View.VISIBLE);
            system_exit.setText("注销");
        }else{
            system_changePWD_layout.setVisibility(View.GONE);
            system_exit.setText("保存");
        }

    }

    private void initData() {
        userManager=UserManager.getInstance();
        userManager.init(mContext);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.system_ipsetting_layout:
                Intent toipset=new Intent(SystemSetActivity.this,SystemIPSetActivity.class);
                startActivity(toipset);
                break;
            case R.id.system_changePWD_layout:
                Intent topwd=new Intent(SystemSetActivity.this,SystemPWDSetActivity.class);
                startActivity(topwd);
                break;

            case R.id.checkupdate_iv://检测更新
                Log.e("checkupdate","checkupdate enter");
                    UpdateHelper.getInstance(mContext).checkupdateHttpData(MyUpdateObject.SYSTEMOS,MyUpdateObject.ProjectName, OkCallbackManager.getInstance().getUpdateCallback(mContext,SystemSetActivity.this));
                break;
            case R.id.setting_back:
                AppManager.getAppManager().finishActivity();
                break;
            case R.id.system_exit:
                MViewManager.getInstance().finishAllAdapter();//销毁所有adapter
                AppManager.getAppManager().AppExit(mContext);
                Intent toLogin=new Intent(SystemSetActivity.this,loginActivity.class);
                startActivity(toLogin);
                break;
            default:
                break;
        }

    }
}
