package com.project.cx.processcontroljx.processmain;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.project.cx.processcontroljx.R;
import com.project.cx.processcontroljx.beans.AreaObj;
import com.project.cx.processcontroljx.beans.DSArea;
import com.project.cx.processcontroljx.beans.SelectedTask;
import com.project.cx.processcontroljx.beans.SelectedThirdCar;
import com.project.cx.processcontroljx.beans.TaskCK;
import com.project.cx.processcontroljx.beans.TaskDS;
import com.project.cx.processcontroljx.beans.TaskThirdcars;
import com.project.cx.processcontroljx.beans.ThirdCars;
import com.project.cx.processcontroljx.net.OkhttpDataHandler;
import com.project.cx.processcontroljx.theme.MBaseActivity;
import com.project.cx.processcontroljx.utils.AppManager;
import com.project.cx.processcontroljx.utils.OkCallbackManager;
import com.project.cx.processcontroljx.utils.TelphoneUtil;
import com.project.cx.processcontroljx.utils.TimeUtil;
import com.project.cx.processcontroljx.utils.UserManager;

import java.util.ArrayList;
import java.util.Calendar;

import okhttp3.Callback;

/**
 * Created by Administrator on 2017/12/6 0006.
 */

public class DSappointment extends MBaseActivity implements View.OnClickListener{
    private final String TAG=getClass().getSimpleName();
    Context mContext;
    ContentValues selectTask;
    ContentValues selectThirdCar;

    TextView book_edit_title;
    TextView dsyy_edit_caseNo;
    TextView dsyy_edit_caseTime;
    TextView dsyy_edit_outTime;
    TextView dsyy_edit_reporter;
    TextView dsyy_edit_reporterPhone;
    TextView dsyy_edit_vehicleBrand;
    TextView dsyy_edit_car_role;
    TextView dsyy_edit_assessor_name;
    TextView dsyy_edit_assessor_mobile;
    TextView dsyy_edit_appointTime;//预约时间控件
    TextView dsyy_detail_assessor_name;
    TextView dsyy_detail_assessor_mobile;
    TextView dsyy_detail_licenseno;

    Button dsyy_edit_yes;
    Button go_back;
    Button dsyy_edit_no;
    LinearLayout book_edit_layout;//编辑
    LinearLayout book_detail_layout;//详情

    final int APPOINTTime_DIALOG=1;//预约日期


    ArrayAdapter<?> case_from_spinnerAdapter;
    public ArrayAdapter<String> areaname_spinnerAdapter,assess_name_spinnerAdapter;
    Spinner dsyy_edit_case_from;
    public Spinner dsyy_edit_areaname_spinner,dsyy_edit_assess_name_spinner;
    public ArrayAdapter<String> licenseno_spinnerAdapter;
    public Spinner dsyy_edit_licenseno_spinner;
    public ArrayList<String> areanameArrayList=new ArrayList<String>();
    public ArrayList<String> assess_nameArrayList=new ArrayList<String>();
    public ArrayList<String> licensenoArrayList=new ArrayList<String>();
    public ArrayList<String> methodArray=new ArrayList<String>();//派发方式
    public int licenseno_Select_int=0;
    public int areaname_Select_int = 0;
    public int assess_name_Select_int = 0;
    private int mcase_from_Select_int = 0;

    private String licenseno_selected="";
    public String assess_selected="";
    public String areaname_selected="";
    private String areaname_selected_id="";
    private String assess_selected_id="";
    private String group_id="";
    public String case_from_seleced="";
    private String car_role="";

    String type="";//YCK,DDS,YDS
    String showType="";//预约，任务改派

    UserManager mUsermanager=null;
    int mSYear,mSMonth,mSDay;
    private String timestampSelected="";
    String appointtimestr;
    LinearLayout book_edit_brand;

    private ListView yds_detail_risklist;

    private DatePickerDialog.OnDateSetListener mDateListener=new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            mSYear=year;
            mSMonth=month;
            mSDay=dayOfMonth;
            //appointtimestr=mSYear+"-"+(mSMonth+1)+"-"+mSDay;
            //startTimeStamp= TimeUtil.getTime(timestr);
            setTimeandTimeStamp();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG,"onCreate");
        setContentView(R.layout.activity_book_edit);
        mContext=this;
        initData();//must before initView
        initView();
        //需要等实例化完成
            getAreasHttp(mUsermanager.getUserToken(),mUsermanager.getFrontRole(),selectTask.getAsString(TaskCK.id), OkCallbackManager.getInstance().getAreasCallback(mContext,DSappointment.this));
    }

    private void initData() {

        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        type=bundle.getString("type");
        if(type.equals("YCK")){
            licenseno_selected=bundle.getString("licenseno");
            showType="yuyue";
            selectTask=SelectedTask.getTaskYCK();
            selectThirdCar= SelectedThirdCar.getThirdCar();
            //licenseno_selected=selectThirdCar.getAsString(TaskThirdcars.thirdlicenseno);

        }else if(type.equals("DDS")){
            showType="gaipai";
            selectTask=SelectedTask.getTaskDDS();
        }else if(type.equals("DSZ")){
            showType="gaipai";
            selectTask=SelectedTask.getTaskDSZ();
        }

        mUsermanager=UserManager.getInstance();
        methodArray.add("GIS调度");

        int expectAmount=Integer.valueOf(selectTask.getAsString(TaskCK.expect_amount));
        if(UserManager.getInstance().getFrontRole().equals("12") && expectAmount<=2000 && (!showType.equals("gaipai")) ){
            methodArray.add("派发给自己");
        }

    }
    private void initView() {
        go_back= (Button) findViewById(R.id.go_back);
        go_back.setOnClickListener(this);

        dsyy_edit_no= (Button) findViewById(R.id.dsyy_edit_no);
        dsyy_edit_no.setOnClickListener(this);

        Calendar c = Calendar.getInstance();
        mSYear=c.get(Calendar.YEAR);
        mSMonth=c.get(Calendar.MONTH);
        mSDay=c.get(Calendar.DAY_OF_MONTH);
        //这里有点奇怪mSMonth比当前少一个月
        //appointtimestr=mSYear+"-"+(mSMonth+1)+"-"+mSDay;
        Log.i(TAG,"mSYear"+mSYear+"/mSMonth"+mSMonth+"/mSDay"+mSDay);


        yds_detail_risklist= (ListView) findViewById(R.id.yds_detail_risklist);
        dsyy_edit_yes=(Button) findViewById(R.id.dsyy_edit_yes);
        dsyy_edit_yes.setOnClickListener(this);
        book_edit_title= (TextView) findViewById(R.id.book_edit_title);//任务改派，定损预约
        if(showType.equals("yuyue")){
            book_edit_title.setText("定损预约");
        }else if(showType.equals("gaipai")){
            book_edit_title.setText("任务改派");
        }

        dsyy_edit_caseNo= (TextView) findViewById(R.id.dsyy_edit_caseNo);
        dsyy_edit_caseTime= (TextView) findViewById(R.id.dsyy_edit_caseTime);
        dsyy_edit_outTime= (TextView) findViewById(R.id.dsyy_edit_outTime);
        dsyy_edit_reporter= (TextView) findViewById(R.id.dsyy_edit_reporter);
        dsyy_edit_reporterPhone= (TextView) findViewById(R.id.dsyy_edit_reporterPhone);

        dsyy_edit_assessor_name=(TextView) findViewById(R.id.dsyy_edit_assessor_name);//定损联系人


        dsyy_edit_assessor_mobile=(TextView) findViewById(R.id.dsyy_edit_assessor_mobile);//定损联系人电话
        dsyy_edit_licenseno_spinner= (Spinner) findViewById(R.id.dsyy_edit_licenseno_spinner);

        dsyy_detail_assessor_name=(TextView) findViewById(R.id.dsyy_detail_assessor_name);//定损联系人
        dsyy_detail_assessor_mobile=(TextView) findViewById(R.id.dsyy_detail_assessor_mobile);//定损联系人电话
        dsyy_detail_licenseno= (TextView) findViewById(R.id.dsyy_detail_licenseno);

        book_edit_layout= (LinearLayout) findViewById(R.id.book_edit_layout);
        book_detail_layout= (LinearLayout) findViewById(R.id.book_detail_layout);
        if(type.equals("YCK")){
            book_edit_layout.setVisibility(View.VISIBLE);
            book_detail_layout.setVisibility(View.GONE);
        }else if(type.equals("DDS") || type.equals("DSZ")){
            book_edit_layout.setVisibility(View.GONE);
            book_detail_layout.setVisibility(View.VISIBLE);
            //填充detail中的数据//TaskDS独有
            dsyy_detail_assessor_name.setText(selectTask.getAsString(TaskDS.reporter1));
            dsyy_detail_assessor_mobile.setText(selectTask.getAsString(TaskDS.reporterPhone1));
            dsyy_detail_licenseno.setText(selectTask.getAsString(TaskDS.licenseno));
        }

        book_edit_brand= (LinearLayout) findViewById(R.id.book_edit_brand);
        //如果是三者车则隐藏车辆品牌
        if(selectTask.getAsString(TaskCK.car_role).equals("2")){
            book_edit_brand.setVisibility(View.GONE);
        }else if(selectTask.getAsString(TaskCK.car_role).equals("1")){
            book_edit_brand.setVisibility(View.VISIBLE);
        }
        dsyy_edit_vehicleBrand= (TextView) findViewById(R.id.dsyy_edit_vehicleBrand);
        dsyy_edit_car_role= (TextView) findViewById(R.id.dsyy_edit_car_role);
        dsyy_edit_case_from= (Spinner) findViewById(R.id.dsyy_edit_case_from_spinner);
        dsyy_edit_areaname_spinner= (Spinner) findViewById(R.id.dsyy_edit_areaname_spinner);
        dsyy_edit_assess_name_spinner= (Spinner) findViewById(R.id.dsyy_edit_assess_name_spinner);


        dsyy_edit_appointTime=(TextView) findViewById(R.id.dsyy_edit_appointTime);
        //dsyy_edit_appointTime.setText(appointtimestr);
        setTimeandTimeStamp();//需要在dsyy_edit_appointTime实例化以后
        dsyy_edit_appointTime.setOnClickListener(this);

        //获取车牌数据同时设置被默认的三者车
        setCarLicenseno(licensenoArrayList);
       // setSpinnerAdapter(mContext,dsyy_edit_licenseno_spinner,licenseno_spinnerAdapter,licensenoArrayList);//设置车牌下拉框
        licenseno_spinnerAdapter=new ArrayAdapter<String>(mContext, R.layout.hc_simple_list_item,licensenoArrayList);
        dsyy_edit_licenseno_spinner.setAdapter(licenseno_spinnerAdapter);
        dsyy_edit_licenseno_spinner.setOnItemSelectedListener(licensenoListener);
        dsyy_edit_licenseno_spinner.setSelection(licenseno_Select_int);

      /*  setSpinnerAdapter(mContext,dsyy_edit_areaname_spinner,areaname_spinnerAdapter,areanameArrayList);//设置区域下拉框
        setSpinnerAdapter(mContext,dsyy_edit_assess_name_spinner,assess_name_spinnerAdapter,assess_nameArrayList);//设置定损点下拉框*/
        areaname_spinnerAdapter=new ArrayAdapter<String>(mContext,R.layout.hc_simple_list_item,areanameArrayList);
        dsyy_edit_areaname_spinner.setAdapter(areaname_spinnerAdapter);

        dsyy_edit_areaname_spinner.setOnItemSelectedListener(areanameOnItemSelectedListener);

        assess_name_spinnerAdapter=new ArrayAdapter<String>(mContext, R.layout.hc_simple_list_item,assess_nameArrayList);
        dsyy_edit_assess_name_spinner.setAdapter(assess_name_spinnerAdapter);
        dsyy_edit_assess_name_spinner.setOnItemSelectedListener(assess_nameOnItemSelectedListener);

        dsyy_edit_caseNo.setText(selectTask.getAsString(TaskCK.caseNo));

        String caseTimeSr= TimeUtil.formatTime(selectTask.getAsString(TaskCK.caseTime));
        dsyy_edit_caseTime.setText(caseTimeSr);
        String outTimeSr= TimeUtil.formatTime(selectTask.getAsString(TaskCK.outTime));
        dsyy_edit_outTime.setText(outTimeSr);
        dsyy_edit_reporter.setText(selectTask.getAsString(TaskCK.reporter));
        dsyy_edit_reporterPhone.setText(selectTask.getAsString(TaskCK.reporterPhone));
        dsyy_edit_reporterPhone.setOnClickListener(this);

        dsyy_edit_assessor_name.setText(selectTask.getAsString(TaskCK.reporter));//定损联系人自动带入报案人
        dsyy_edit_assessor_mobile.setText(selectTask.getAsString(TaskCK.reporterPhone));

        dsyy_edit_vehicleBrand.setText(selectTask.getAsString(TaskCK.vehicleBrand));
        if(selectTask.getAsString(TaskCK.car_role).equals("1")){
            dsyy_edit_car_role.setText("标的车");
        }else if(selectTask.getAsString(TaskCK.car_role).equals("2")){
            dsyy_edit_car_role.setText("三者车");
        }



        //case_from_spinnerAdapter=ArrayAdapter.createFromResource(mContext,R.array.case_from_spinner_entryvalues,android.R.layout.simple_spinner_item);
        case_from_spinnerAdapter=new ArrayAdapter<String>(mContext,R.layout.hc_simple_list_item, methodArray);
        //设置下拉列表的风格
        case_from_spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dsyy_edit_case_from.setAdapter(case_from_spinnerAdapter);
        dsyy_edit_case_from.setSelection(mcase_from_Select_int, true);
        dsyy_edit_case_from.setOnItemSelectedListener(case_fromListener);

    }

    AdapterView.OnItemSelectedListener licensenoListener = new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            // TODO Auto-generated method stub
            licenseno_Select_int = arg2;
            licenseno_selected=licenseno_spinnerAdapter.getItem(licenseno_Select_int);
            if(licenseno_selected.equals(selectTask.getAsString(TaskCK.licenseno))){//是否为标的车
                car_role="1";
                dsyy_edit_car_role.setText("标的车");
                book_edit_brand.setVisibility(View.VISIBLE);
            }else{
                car_role="2";
                dsyy_edit_car_role.setText("三者车");
                book_edit_brand.setVisibility(View.GONE);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub

        }
    };

    AdapterView.OnItemSelectedListener case_fromListener = new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            // TODO Auto-generated method stub
            mcase_from_Select_int = arg2;
            if(mcase_from_Select_int==0){//GIS调度
                case_from_seleced="1";
            }else if(mcase_from_Select_int==1){//派发给自己
                case_from_seleced="2";
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub

        }
    };
//区域选择
    public AdapterView.OnItemSelectedListener areanameOnItemSelectedListener = new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {//进入页面不会调用onItemSelected
            // TODO Auto-generated method stub
            Log.e(TAG,"onItemSelected");
            areaname_Select_int = arg2;
            int count=areanameArrayList.size();
            areaname_selected=areanameArrayList.get(areaname_Select_int);
            String area_name=areanameArrayList.get(areaname_Select_int);
            assess_nameArrayList=new ArrayList<String>();
            for(int i=0;i< DSArea.dsAreaObjArray.size();i++){
                AreaObj areaObj=DSArea.dsAreaObjArray.get(i);
                if(areaObj.getName().equals(area_name)){
                    assess_nameArrayList=areaObj.getAssess_arraylist();
                    break;
                }
            }
            //setSpinnerAdapter(mContext,dsyy_edit_assess_name_spinner,assess_name_spinnerAdapter,assess_nameArrayList);
            assess_name_spinnerAdapter=new ArrayAdapter<String>(mContext,R.layout.hc_simple_list_item, assess_nameArrayList);
            dsyy_edit_assess_name_spinner.setAdapter(assess_name_spinnerAdapter);
            dsyy_edit_assess_name_spinner.setOnItemSelectedListener(assess_nameOnItemSelectedListener);
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub
            Log.e(TAG,"onNothingSelected");
        }

    };
//定损点选择
    public AdapterView.OnItemSelectedListener assess_nameOnItemSelectedListener = new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            // TODO Auto-generated method stub
            assess_name_Select_int = arg2;
            int count=assess_nameArrayList.size();
            assess_selected=assess_nameArrayList.get(assess_name_Select_int);
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            // TODO Auto-generated method stub

        }
    };

    //获取定损点区域数据
    public void getAreasHttp(String token,String frontrole,String taskid,Callback Callback){
        OkhttpDataHandler okhandler=new OkhttpDataHandler(mContext);
        okhandler.setmIsShowProgressDialog(true);
        okhandler.getAreasHttp(token,frontrole,taskid,Callback);
    }

//为mSpinner设置adapter,配置数据,貌似arrayAdapter有问题,暂时不用
    public void setSpinnerAdapter(Context ctx,Spinner mSpinner, ArrayAdapter<String> arrayAdapter, ArrayList<String> datas){
        arrayAdapter=new ArrayAdapter<String>(ctx,R.layout.hc_simple_list_item, datas);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(arrayAdapter);
    }
//获取标的车牌和三者车牌
    public void setCarLicenseno(ArrayList<String> mlicensenoList){
    ArrayList<ContentValues> thirdcarsList=new ArrayList<ContentValues>();
    for(int i=0;i< ThirdCars.thirdcarsArray.size();i++){//添加三者车牌
        ContentValues values=ThirdCars.thirdcarsArray.get(i);
        String thirdcarLicenseno=values.getAsString(TaskThirdcars.thirdlicenseno);
        mlicensenoList.add(thirdcarLicenseno);
    }
    mlicensenoList.add(selectTask.getAsString(TaskCK.licenseno));//标的车牌
        //获取YCK中选中的三者车的index
        if(type.equals("YCK")){
/*
            String licensenothird=selectThirdCar.getAsString(TaskThirdcars.thirdlicenseno);
            if(mlicensenoList.contains(selectThirdCar.getAsString(TaskThirdcars.thirdlicenseno))){
                licenseno_Select_int=mlicensenoList.indexOf(selectThirdCar.getAsString(TaskThirdcars.thirdlicenseno));
                Log.e(TAG,"contains enter,licenseno_Select_int:"+licenseno_Select_int+"/licensenothird:"+licensenothird);
            }
*/


            if(mlicensenoList.contains(licenseno_selected)){
                licenseno_Select_int=mlicensenoList.indexOf(licenseno_selected);
                Log.e(TAG,"contains enter,licenseno_Select_int:"+licenseno_Select_int+"/licenseno_selected:"+licenseno_selected);
            }
        }
    }

    @Override
    protected Dialog onCreateDialog(int id){
        switch(id){
            case APPOINTTime_DIALOG:
                return new DatePickerDialog(mContext,mDateListener,mSYear,mSMonth,mSDay);
            default:
                break;
        }
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.go_back:
                AppManager.getAppManager().finishActivity(DSappointment.this);
                break;
            case R.id.dsyy_edit_no:
                AppManager.getAppManager().finishActivity(DSappointment.this);
                break;
            case R.id.dsyy_edit_yes:
                String assessor_nameStr=dsyy_edit_assessor_name.getText().toString().trim();
                String assessor_mobileStr=dsyy_edit_assessor_mobile.getText().toString().trim();
              /*  String car_role="";
                if(licenseno_selected.equals(selectYCK.getAsString(TaskCK.licenseno))){//是否为标的车
                    car_role="1";

                }else{
                    car_role="2";
                }*/
                //areaname_selected

                areaname_selected_id=DSArea.area_idMap.get(areaname_selected);
                assess_selected_id=DSArea.access_idMap.get(assess_selected);
                group_id=DSArea.access_group.get(assess_selected);

                if(type.equals("YCK")){
                    if(car_role.equals("1")){//标的车,有车牌
                        applyAccessHttpData(mUsermanager.getUserToken(),mUsermanager.getFrontRole(),selectTask.getAsString(TaskCK.id),licenseno_selected,car_role,
                                assessor_nameStr,assessor_mobileStr,assess_selected,timestampSelected,case_from_seleced,assess_selected_id,areaname_selected_id,selectTask.getAsString(TaskCK.vehicleBrand),group_id,OkCallbackManager.getInstance().applyAccessCallback(mContext,DSappointment.this));
                    }else if(car_role.equals("2")){//三者车,无车牌
                        applyAccessHttpData(mUsermanager.getUserToken(),mUsermanager.getFrontRole(),selectTask.getAsString(TaskCK.id),licenseno_selected,car_role,
                                assessor_nameStr,assessor_mobileStr,assess_selected,timestampSelected,case_from_seleced,assess_selected_id,areaname_selected_id,"",group_id,OkCallbackManager.getInstance().applyAccessCallback(mContext,DSappointment.this));
                    }

                }else if(type.equals("DDS") || type.equals("DSZ")){
                    apply_taskchangeHttpData(mUsermanager.getUserToken(),mUsermanager.getFrontRole(),selectTask.getAsString(TaskCK.id),assess_selected,timestampSelected,assess_selected_id,areaname_selected_id,group_id,OkCallbackManager.getInstance().applyAccessCallback(mContext,DSappointment.this));
                }
                break;
            case R.id.dsyy_edit_appointTime:
                showDialog(APPOINTTime_DIALOG);
                break;
            case R.id.dsyy_edit_reporterPhone:
                TelphoneUtil.toDial(mContext,selectTask.getAsString(TaskCK.reporterPhone));
                break;
            default:
                break;
        }
    }
    //显示设置时间
    public void displayappointDate(){
        dsyy_edit_appointTime.setText(appointtimestr);
    }
    //定损预约
    public void applyAccessHttpData(String token,String frontrole, String taskid,String licenseno,String Car_role, String reporter1,
                    String reporterPhone1,String assess_address,String appointTime,String case_from,String assess_id,String country_id,String vehicleBrand,String group_id,Callback Callback){
        OkhttpDataHandler okhandler=new OkhttpDataHandler(mContext);
        okhandler.setmIsShowProgressDialog(true);
        okhandler.applyAccessHttp(token,frontrole,taskid,licenseno,Car_role,reporter1,reporterPhone1,assess_address,appointTime,case_from,assess_id,country_id
        ,vehicleBrand,group_id,Callback);
    }


    //任务改派
    public void apply_taskchangeHttpData(String token,String frontrole,String taskid,String assess_address,String appointTime,
                                     String assess_id,String county_id,String group_id,Callback Callback){
        OkhttpDataHandler okhandler=new OkhttpDataHandler(mContext);
        okhandler.setmIsShowProgressDialog(true);
        okhandler.apply_taskchangeHttp(token,frontrole,taskid,assess_address,appointTime,assess_id,county_id,group_id, Callback);
    }

    /**
     * 设置显示时间和对应时间戳
     */
    public void setTimeandTimeStamp(){
        appointtimestr=mSYear+"-"+(mSMonth+1)+"-"+mSDay;
        timestampSelected=String.valueOf(TimeUtil.timeStamp(appointtimestr,"yyyy-MM-dd"));
        displayappointDate();
    }



}
