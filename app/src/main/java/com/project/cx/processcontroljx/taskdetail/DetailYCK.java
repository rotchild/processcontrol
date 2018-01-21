package com.project.cx.processcontroljx.taskdetail;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.cx.processcontroljx.R;
import com.project.cx.processcontroljx.adapters.ThirdcarAdapter;
import com.project.cx.processcontroljx.beans.DetailIntentType;
import com.project.cx.processcontroljx.beans.MRequestCode;
import com.project.cx.processcontroljx.beans.MResultCode;
import com.project.cx.processcontroljx.beans.SelectedTask;
import com.project.cx.processcontroljx.beans.SelectedThirdCar;
import com.project.cx.processcontroljx.beans.TaskCK;
import com.project.cx.processcontroljx.beans.TaskRole;
import com.project.cx.processcontroljx.beans.TaskThirdcars;
import com.project.cx.processcontroljx.beans.ThirdCars;
import com.project.cx.processcontroljx.net.OkhttpDataHandler;
import com.project.cx.processcontroljx.processmain.DSappointment;
import com.project.cx.processcontroljx.processmain.FXSBActivity;
import com.project.cx.processcontroljx.theme.MBaseActivity;
import com.project.cx.processcontroljx.utils.AppManager;
import com.project.cx.processcontroljx.utils.LayoutAddDanamic;
import com.project.cx.processcontroljx.utils.OkCallbackManager;
import com.project.cx.processcontroljx.utils.TelphoneUtil;
import com.project.cx.processcontroljx.utils.TimeUtil;
import com.project.cx.processcontroljx.utils.UserManager;

import java.util.ArrayList;

import okhttp3.Callback;

/**
 * Created by Administrator on 2017/11/30 0030.
 */

public class DetailYCK extends MBaseActivity implements View.OnClickListener {
    Context mContext;
    public int intentType;//已读未读
    public String TAG;
    public ContentValues selectYCK;
    ImageView setting_back;
    TextView yck_detail_caseNo, yck_detail_caseTime, yck_detail_outTime, yck_detail_outNumber, yck_detail_reporter,
            yck_detail_reporterPhone, yck_detail_licenseno, yck_detail_riskstate, yck_detail_risktype;

    public Button yck_detail_cbrs_btn;
    public Button safe_book;//风险上报
    public Button go_detail;//标的车 详情/定损预约
    public String isAppoint="";
    public ListView yck_detail_thirdcars_list;
    public ListView yck_detail_risklist;//流程追踪
    public ThirdcarAdapter thirdcarAdapter;
    public UserManager userManager = null;

    int risktask_start = 0;
    int risktask_limit = 100;//?如何设置
    public LinearLayout thirdcars_linear;
    public ArrayList<ContentValues> riskArray = new ArrayList<ContentValues>();//上报任务列表数据
    public LayoutAddDanamic layoutAddDanamic;
    public Button danamic_thirdcars_add;
    public LinearLayout thirdcar_editwrap;
    public LinearLayout yck_risktype_wrapper;//风险类型动态加载

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_yck);
        mContext = this;
        TAG = getClass().getSimpleName();
        initData();
       // getthirdcarHttpData(userManager.getUserToken(),userManager.getFrontRole(),selectYCK.getAsString(TaskCK.id), OkCallbackManager.getInstance().getthirdcarCallback(mContext,DetailYCK.this));
        initView();
        //获取风险类型,为风险上报提供数据,如果获取失败如何处理
        getRisksWarnHttpData(userManager.getUserToken(), userManager.getFrontRole(), TaskRole.ck,
                selectYCK.getAsString(TaskCK.id), OkCallbackManager.getInstance().getRiskWarmCallback(mContext, DetailYCK.this));
        //获取三者车数据,转移到resume中使用
        //getthirdcarHttpData(userManager.getUserToken(),userManager.getFrontRole(),selectYCK.getAsString(TaskCK.id), OkCallbackManager.getInstance().getthirdcarCallback(mContext,DetailYCK.this));
    }

    private void initView() {
        danamic_thirdcars_add= (Button) findViewById(R.id.danamic_thirdcars_add);


        //first_thirdcars_editlayout= (LinearLayout) findViewById(R.id.first_thirdcars_editlayout);
        thirdcar_editwrap= (LinearLayout) findViewById(R.id.thirdcar_editwrap);
        yck_risktype_wrapper= (LinearLayout) findViewById(R.id.yck_risktype_wrapper);

        thirdcars_linear= (LinearLayout) findViewById(R.id.thirdcars_linear);
        yck_detail_risklist = (ListView) findViewById(R.id.yck_detail_risklist);

        go_detail = (Button) findViewById(R.id.go_detail);
        go_detail.setOnClickListener(this);


        safe_book = (Button) findViewById(R.id.safe_book);
        safe_book.setOnClickListener(this);

        setting_back = (ImageView) findViewById(R.id.setting_back);
        setting_back.setOnClickListener(this);

        yck_detail_caseNo = (TextView) findViewById(R.id.yck_detail_caseNo);
        yck_detail_caseTime = (TextView) findViewById(R.id.yck_detail_caseTime);
        yck_detail_outTime = (TextView) findViewById(R.id.yck_detail_outTime);
        //yck_detail_outNumber= (TextView) findViewById(R.id.yck_detail_outNumber);
        yck_detail_reporter = (TextView) findViewById(R.id.yck_detail_reporter);
        yck_detail_reporterPhone = (TextView) findViewById(R.id.yck_detail_reporterPhone);
        yck_detail_licenseno = (TextView) findViewById(R.id.yck_detail_licenseno);
        yck_detail_cbrs_btn = (Button) findViewById(R.id.yck_detail_cbrs_btn);
        yck_detail_cbrs_btn.setOnClickListener(this);

        if(selectYCK.getAsString(TaskCK.hurt_state).equals("1")){//只有hurt_state为1显示催办人伤
            yck_detail_cbrs_btn.setVisibility(View.VISIBLE);
        }else{
            yck_detail_cbrs_btn.setVisibility(View.GONE);
        }

        yck_detail_riskstate = (TextView) findViewById(R.id.yck_detail_riskstate);
        yck_detail_caseNo.setText(selectYCK.getAsString(TaskCK.caseNo));
        Long Ctime_long = Long.valueOf(selectYCK.getAsString(TaskCK.caseTime)) * 1000;
        String Ctime_str = String.valueOf(Ctime_long);
        String Ctime_result = TimeUtil.stampToDate(Ctime_str);
        yck_detail_caseTime.setText(Ctime_result);
        Long Otime_long = Long.valueOf(selectYCK.getAsString(TaskCK.outTime)) * 1000;
        String Otime_str = String.valueOf(Otime_long);
        String Otime_result = TimeUtil.stampToDate(Otime_str);
        yck_detail_outTime.setText(Otime_result);
        //yck_detail_outNumber.setText(selectYCK.getAsString(TaskCK.outNumber));
        yck_detail_reporter.setText(selectYCK.getAsString(TaskCK.reporter));
        yck_detail_reporterPhone.setText(selectYCK.getAsString(TaskCK.reporterPhone));
        yck_detail_reporterPhone.setOnClickListener(this);
        yck_detail_licenseno.setText(selectYCK.getAsString(TaskCK.licenseno));

        yck_detail_thirdcars_list = (ListView) findViewById(R.id.yck_detail_thirdcars_list);
        thirdcarAdapter = new ThirdcarAdapter(mContext, ThirdCars.thirdcarsArray);

        yck_detail_thirdcars_list.setAdapter(thirdcarAdapter);
        String riskstate_str = "--";
        Long riskstate_long = Long.valueOf(selectYCK.getAsString(TaskCK.riskstate));
        if (riskstate_long == 0) {
            riskstate_str = "未上报";
        } else if (riskstate_long == 1) {
            riskstate_str = "已上报";
        }
        yck_detail_riskstate.setText(riskstate_str);


    }

    private void initData() {
        Intent intent = getIntent();
        intentType = intent.getIntExtra("intentType", DetailIntentType.UNREAD);
        selectYCK = SelectedTask.getTaskYCK();
        userManager = UserManager.getInstance();
        layoutAddDanamic=LayoutAddDanamic.getInstance();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.yck_detail_cbrs_btn:
                //催办人伤
                urgeDealHurtHttpData(userManager.getUserToken(), userManager.getFrontRole(), selectYCK.getAsString(TaskCK.id), OkCallbackManager.getInstance().urgeDealhuitCallback(mContext, DetailYCK.this));
                break;

            case R.id.safe_book:
                //风险上报
                Intent intent = new Intent(DetailYCK.this, FXSBActivity.class);
                intent.putExtra("from", "DetailYCK");
                //startActivity(intent);
                startActivityForResult(intent, MRequestCode.YCK);
                break;
            case R.id.go_detail:
               if(isAppoint!=null && isAppoint.equals("1")){//详情
                   access_detailHttpData(userManager.getUserToken(),userManager.getFrontRole(),selectYCK.getAsString(TaskCK.caseNo),selectYCK.getAsString(TaskCK.licenseno),OkCallbackManager.getInstance().access_detailCallback(mContext,DetailYCK.this));
               }else if(isAppoint!=null && isAppoint.equals("0")){//定损预约
                   Intent toDSappointment=new Intent(mContext, DSappointment.class);
                   toDSappointment.putExtra("type","YCK");
                   toDSappointment.putExtra("licenseno",selectYCK.getAsString(TaskCK.licenseno));//区分表弟和三者车
                   startActivity(toDSappointment);
               }
                break;
            case R.id.setting_back:
                AppManager.getAppManager().finishActivity();
                break;
                //yck_detail_reporterPhone
            case R.id.yck_detail_reporterPhone:
                TelphoneUtil.toDial(mContext,selectYCK.getAsString(TaskCK.reporterPhone));
                break;
            default:
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case MRequestCode.YCK:
                if(resultCode== MResultCode.FXSB){
                    if(data!=null){
                        Bundle b=data.getExtras();
                        String result=b.getString("sbstate");
                        if(result.equals("1")){//调用FXSB接口成功,局部改变上报状态
                            yck_detail_riskstate.setText("已上报");
                    }
                    }
                }

                break;
            default:
                break;

        }
    }

    //获取风险上报任务数据
    public void getTaskRiskHttpData(String token,String frontrole, String task_role,String caseNo,String licenseNo,int start,int limit,Callback Callback){
        OkhttpDataHandler okhandler=new OkhttpDataHandler(mContext);
        okhandler.setmIsShowProgressDialog(true);
        okhandler.getTaskRiskHttp(token,frontrole,task_role,caseNo,licenseNo,start,limit,Callback);
    }

    //获取风险提示数据
    public void getRisksWarnHttpData(String token, String frontrole, String task_role, String taskid, Callback Callback){
        OkhttpDataHandler okhandler=new OkhttpDataHandler(mContext);
        okhandler.setmIsShowProgressDialog(true);
        okhandler.getRisksWarnHttp(token,frontrole,task_role,taskid,Callback);
    }

    //获取三者车数据
    public void getthirdcarHttpData(String token,String frontrole,String caseNo,String licenseno,Callback Callback){
        OkhttpDataHandler okhandler=new OkhttpDataHandler(mContext);
        okhandler.setmIsShowProgressDialog(true);
        okhandler.getthirdcarHttp(token,frontrole,caseNo,licenseno,Callback);
    }

    //添加三者车数据
    public void addthirdcarHttpData(String token,String frontrole,String caseNo,String taskid,String thirdlicenseno,Callback Callback){
        OkhttpDataHandler okhandler=new OkhttpDataHandler(mContext);
        okhandler.setmIsShowProgressDialog(true);
        okhandler.addthirdcarHttp(token,frontrole,caseNo,taskid,thirdlicenseno,Callback);
    }

    //催办人伤数据
    public void urgeDealHurtHttpData(String token,String frontrole,String taskid,Callback Callback){
        OkhttpDataHandler okhandler=new OkhttpDataHandler(mContext);
        okhandler.setmIsShowProgressDialog(true);
        okhandler.urgeDealHurtHttp(token,frontrole,taskid,Callback);
    }

    //定损预约详情
    public void access_detailHttpData(String token,String frontrole,String caseNo,String licenseno,Callback Callback){
        OkhttpDataHandler okhandler=new OkhttpDataHandler(mContext);
        okhandler.setmIsShowProgressDialog(true);
        okhandler.access_detailHttp(token,frontrole,caseNo,licenseno,Callback);
    }

//设置三者车适配器
    public void setThirdListAdapter(final ListView listview){

                thirdcarAdapter=new ThirdcarAdapter(mContext, ThirdCars.thirdcarsArray);
       // mthirdcarAdapter=new ThirdcarAdapter(mContext, ThirdCars.alllicensenoArray);
        listview.setAdapter(thirdcarAdapter);
        thirdcarAdapter.setItemyyClickListener(new ThirdcarAdapter.MOnItemClickListener() {
            @Override
            public void onClick(int i) {
                ContentValues thirdCarSelected=ThirdCars.thirdcarsArray.get(i);
                //ContentValues thirdCarSelected=ThirdCars.alllicensenoArray.get(i);
                Log.e(TAG,"thirdCarClick:"+thirdCarSelected.getAsString(TaskThirdcars.thirdlicenseno));
                SelectedThirdCar.storeThirdCar(thirdCarSelected);
                Intent toDSappointment=new Intent(mContext, DSappointment.class);
                toDSappointment.putExtra("type","YCK");
                toDSappointment.putExtra("licenseno",SelectedThirdCar.getThirdCar().getAsString(TaskCK.thirdlicenseno));
                startActivity(toDSappointment);
            }
        });

        thirdcarAdapter.setItemdetailClickListener(new ThirdcarAdapter.MOnItemClickListener() {
            @Override
            public void onClick(int i) {
                Log.e(TAG,"thirdCarClickdetail:");
                ContentValues thirdCarSelected=ThirdCars.thirdcarsArray.get(i);
                //ContentValues thirdCarSelected=ThirdCars.alllicensenoArray.get(i);
                Log.e(TAG,"thirdCarClick:"+thirdCarSelected.getAsString(TaskThirdcars.thirdlicenseno));
                SelectedThirdCar.storeThirdCar(thirdCarSelected);
                //
                access_detailHttpData(userManager.getUserToken(),userManager.getFrontRole(),selectYCK.getAsString(TaskCK.caseNo),thirdCarSelected.getAsString(TaskThirdcars.thirdlicenseno),OkCallbackManager.getInstance().access_detailCallback(mContext,DetailYCK.this));
               /* Intent toDSappointmentDetail=new Intent(mContext, DSappointmentDetail.class);
                //toDSappointmentDetail.putExtra("type","YCK");
                startActivity(toDSappointmentDetail);*/
            }
        });

        thirdcarAdapter.setItemaddClickListener(new ThirdcarAdapter.MOnItemClickListener() {
            @Override
            public void onClick(int i) {
                //刷新listview的高度，貌似有问题
                //ListViewUtil.setListViewHeightBasedOnChildren(listview);
            }
        });
        final ThirdcarAdapter finalMthirdcarAdapter = thirdcarAdapter;
        thirdcarAdapter.setItemaokClickListener(new ThirdcarAdapter.MOnItemClickListener() {
            @Override
            public void onClick(int i) {
                //String thirdlicensenoStr= finalMthirdcarAdapter.getThirdcarlicneseno();
                String thirdlicensenoStr= ThirdCars.thirdAddMap.get(i);
                if(thirdlicensenoStr!=null && thirdlicensenoStr.length()>0){
                    Log.i(TAG,"thirdlicensenoStr:"+thirdlicensenoStr);
                    addthirdcarHttpData(UserManager.getInstance().getUserToken(),UserManager.getInstance().getFrontRole(),selectYCK.getAsString(TaskCK.caseNo),selectYCK.getAsString(TaskCK.id),thirdlicensenoStr,OkCallbackManager.getInstance().addthirdcarCallback(mContext,DetailYCK.this));
                }else {
                    Toast.makeText(mContext,"车牌号不能为空",Toast.LENGTH_SHORT).show();
                }

            }
        });
        thirdcarAdapter.setItemcancelClickListener(new ThirdcarAdapter.MOnItemClickListener() {
            @Override
            public void onClick(int i) {

            }
        });
        //ListViewUtil.setListViewHeightBasedOnChildren(listview);
    }

    public void startActivity(Class activity,int intentType){
        Intent intent=new Intent(mContext,activity);
        intent.putExtra("intentType",intentType);//intentType 0:未读 1:已读
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //为了定损预约返回时刷新（定损预约/详情状态）
        if(userManager==null)
            return;
        getthirdcarHttpData(userManager.getUserToken(),userManager.getFrontRole(),selectYCK.getAsString(TaskCK.caseNo),selectYCK.getAsString(TaskCK.licenseno), OkCallbackManager.getInstance().getthirdcarCallback(mContext,DetailYCK.this));
        getTaskRiskHttpData(userManager.getUserToken(), userManager.getFrontRole(), TaskRole.ck,
                selectYCK.getAsString(TaskCK.caseNo), selectYCK.getAsString(TaskCK.licenseno),risktask_start, risktask_limit, OkCallbackManager.getInstance().getTaskRiskCallback(mContext, DetailYCK.this));
    }

}
