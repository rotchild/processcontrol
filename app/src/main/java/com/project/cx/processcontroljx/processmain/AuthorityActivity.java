package com.project.cx.processcontroljx.processmain;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.project.cx.processcontroljx.R;
import com.project.cx.processcontroljx.beans.SelectedTask;
import com.project.cx.processcontroljx.beans.TaskDS;
import com.project.cx.processcontroljx.net.OkhttpDataHandler;
import com.project.cx.processcontroljx.theme.MBaseActivity;
import com.project.cx.processcontroljx.utils.AppManager;
import com.project.cx.processcontroljx.utils.CashierInputFilter;
import com.project.cx.processcontroljx.utils.OkCallbackManager;
import com.project.cx.processcontroljx.utils.UserManager;

import okhttp3.Callback;

/**
 * Created by Administrator on 2017/12/7 0007.
 * 超权限上报
 */
public class AuthorityActivity extends MBaseActivity implements View.OnClickListener{
    private final String TAG=getClass().getSimpleName();
    Context mContext;
    UserManager userManager;
    ImageView setting_back;
    ContentValues selectTask;
    String type;
    EditText authority_expect_amount,authority_vehicleBrand,authority_remarks;

    Button authority_edit_yes,authority_edit_no;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG,"onCreate");
        setContentView(R.layout.activity_authority_edit);
        mContext=this;
        initData();//must before initView
        initView();
    }

    private void initView() {

        authority_expect_amount=(EditText)findViewById(R.id.authority_expect_amount);
        //添加金额过滤器
        InputFilter[] filters={new CashierInputFilter()};
        authority_expect_amount.setFilters(filters);
        authority_vehicleBrand=(EditText)findViewById(R.id.authority_vehicleBrand);
        authority_remarks=(EditText)findViewById(R.id.authority_remarks);

        setting_back= (ImageView) findViewById(R.id.setting_back);
        setting_back.setOnClickListener(this);
        authority_edit_yes= (Button) findViewById(R.id.authority_edit_yes);
        authority_edit_yes.setOnClickListener(this);
        authority_edit_no= (Button) findViewById(R.id.authority_edit_no);
        authority_edit_no.setOnClickListener(this);
    }

    private void initData() {
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        type=bundle.getString("type");
        if(type.equals("DetailDDS")){
            selectTask=SelectedTask.getTaskDDS();
        }else if(type.equals("DetailDSZ")){
            selectTask=SelectedTask.getTaskDSZ();
        }
        userManager=UserManager.getInstance();
        userManager.init(mContext);
    }

    //提交超权限上报数据
    public void supperReportHttpData(String token,String frontrole,String taskid,String expect_amount, String vehicleBrand, String remarks,Callback Callback){
        OkhttpDataHandler okhandler=new OkhttpDataHandler(mContext);
        okhandler.setmIsShowProgressDialog(true);
        okhandler.supperReportHttp(token,frontrole,taskid,expect_amount,vehicleBrand,remarks,Callback);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.setting_back:
                AppManager.getAppManager().finishActivity();
                break;
            case R.id.authority_edit_yes:
                String  authority_expect_amount_str=authority_expect_amount.getText().toString().trim();

                String  authority_vehicleBrand_str=authority_vehicleBrand.getText().toString().trim();
                String  authority_remarks_str=authority_remarks.getText().toString().trim();
                if(TextUtils.isEmpty(authority_expect_amount_str)){
                    Toast.makeText(mContext,"估损金额不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(authority_vehicleBrand_str)){
                    Toast.makeText(mContext,"车辆品牌不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                supperReportHttpData(userManager.getUserToken(),userManager.getFrontRole(),selectTask.getAsString(TaskDS.id),authority_expect_amount_str,
                        authority_vehicleBrand_str,authority_remarks_str, OkCallbackManager.getInstance().supperReportCallback(mContext,AuthorityActivity.this));


                break;

            case R.id.authority_edit_no:
                AppManager.getAppManager().finishActivity();
                break;
            default:
                break;
        }


    }
}
