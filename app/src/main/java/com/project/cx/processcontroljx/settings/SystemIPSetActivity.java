package com.project.cx.processcontroljx.settings;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.project.cx.processcontroljx.R;
import com.project.cx.processcontroljx.net.MHttpParams;
import com.project.cx.processcontroljx.theme.MBaseActivity;
import com.project.cx.processcontroljx.utils.AppManager;
import com.project.cx.processcontroljx.utils.MRegex;

/**
 * Created by Administrator on 2017/11/27 0027.
 */

public class SystemIPSetActivity extends MBaseActivity implements View.OnClickListener {
    Context mContext;
    EditText ip_et,port_et;
    Button saveYes_btn,saveNo_btn;
    MHttpParams mHttpParams;
    ImageView ipsetting_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ipsetting);
        mContext=this;
        initData();
        initView();//must below initData();
    }

    private void initView() {
        ipsetting_back= (ImageView) findViewById(R.id.ipsetting_back);

        ip_et=(EditText)findViewById(R.id.ip_et);
        port_et=(EditText)findViewById(R.id.port_et);
        saveYes_btn=(Button)findViewById(R.id.save_yes);
        saveNo_btn=(Button)findViewById(R.id.save_no);

        ip_et.setText(mHttpParams.getIP());
        port_et.setText(mHttpParams.getPort());
        saveYes_btn.setOnClickListener(this);
        saveNo_btn.setOnClickListener(this);
        ipsetting_back.setOnClickListener(this);
    }

    private void initData() {
        mHttpParams=MHttpParams.getInstance();
        mHttpParams.init(mContext);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.save_yes:
                String ipStr=ip_et.getText().toString().trim();
                String portStr=port_et.getText().toString().trim();
                if(MRegex.isRightIP(ipStr)){
                    if(MRegex.isRightPort(portStr)){
                        mHttpParams.setIP(ipStr);
                        mHttpParams.setPort(portStr);
                        mHttpParams.init(mContext);//修改及时更新
                        Toast.makeText(mContext,"保存成功",Toast.LENGTH_SHORT).show();
                        AppManager.getAppManager().finishActivity();
                    }else{
                        Toast.makeText(mContext,"端口格式错误",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(mContext,"ip格式错误",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.save_no:
                AppManager.getAppManager().finishActivity();
                break;
            case R.id.ipsetting_back:
                AppManager.getAppManager().finishActivity();
                break;
            default:
                break;
        }

    }
}
