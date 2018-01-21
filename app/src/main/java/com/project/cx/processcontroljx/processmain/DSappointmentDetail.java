package com.project.cx.processcontroljx.processmain;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.cx.processcontroljx.R;
import com.project.cx.processcontroljx.beans.AccessDetailData;
import com.project.cx.processcontroljx.theme.MBaseActivity;
import com.project.cx.processcontroljx.utils.AppManager;
import com.project.cx.processcontroljx.utils.TelphoneUtil;
import com.project.cx.processcontroljx.utils.TimeUtil;

/**
 * Created by Administrator on 2017/12/6 0006.
 */

public class DSappointmentDetail extends MBaseActivity implements View.OnClickListener{
    Context mContext;
    TextView book_detail_caseNo,book_detail_caseTime,book_detail_outTime,book_detail_reporter,book_detail_reporterPhone,
            book_detail_assessor_name,book_detail_assessor_mobile,dsyy_edit_licenseno,book_detail_vehicleBrand,book_detail_car_role,
            book_detail_dingsundian,book_detail_appointTime,book_detail_case_from,book_detail_reporter1,book_detail_reporterPhone1;
    Button go_back;
    LinearLayout book_detail_brandlinear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        mContext=this;
        initData();//must before initView
        initView();
    }

    private void initData() {
    }
    private void initView() {
        book_detail_brandlinear= (LinearLayout) findViewById(R.id.book_detail_brandlinear);

        go_back= (Button) findViewById(R.id.go_back);
        go_back.setOnClickListener(this);

        book_detail_caseNo= (TextView) findViewById(R.id.book_detail_caseNo);
        book_detail_caseTime= (TextView) findViewById(R.id.book_detail_caseTime);
        book_detail_outTime= (TextView) findViewById(R.id.book_detail_outTime);
        book_detail_reporter= (TextView) findViewById(R.id.book_detail_reporter);
        book_detail_reporterPhone= (TextView) findViewById(R.id.book_detail_reporterPhone);
        book_detail_assessor_name= (TextView) findViewById(R.id.book_detail_assessor_name);
        book_detail_assessor_mobile= (TextView) findViewById(R.id.book_detail_assessor_mobile);
        book_detail_assessor_mobile.setOnClickListener(this);

        dsyy_edit_licenseno= (TextView) findViewById(R.id.dsyy_edit_licenseno);
        book_detail_vehicleBrand= (TextView) findViewById(R.id.book_detail_vehicleBrand);
        book_detail_car_role= (TextView) findViewById(R.id.book_detail_car_role);
        book_detail_dingsundian= (TextView) findViewById(R.id.book_detail_dingsundian);
        book_detail_appointTime= (TextView) findViewById(R.id.book_detail_appointTime);
        book_detail_case_from= (TextView) findViewById(R.id.book_detail_case_from);
        book_detail_reporter1= (TextView) findViewById(R.id.book_detail_reporter1);
        book_detail_reporterPhone1= (TextView) findViewById(R.id.book_detail_reporterPhone1);

        book_detail_caseNo.setText(AccessDetailData.caseNo);
        String caseTimeSr= TimeUtil.formatTime(AccessDetailData.caseTime);
        book_detail_caseTime.setText(caseTimeSr);
        String outTimeSr= TimeUtil.formatTime(AccessDetailData.outTime);
        book_detail_outTime.setText(outTimeSr);
        book_detail_reporter.setText(AccessDetailData.reporter);//notice reporter
        book_detail_reporterPhone.setText(AccessDetailData.reporterPhone);
        book_detail_reporterPhone.setOnClickListener(this);

        book_detail_assessor_name.setText(AccessDetailData.assessor_name);
        book_detail_assessor_mobile.setText(AccessDetailData.assessor_mobile);
        book_detail_assessor_mobile.setOnClickListener(this);
        dsyy_edit_licenseno.setText(AccessDetailData.licenseno);
        book_detail_vehicleBrand.setText(AccessDetailData.vehicleBrand);

        String car_roleStr="--";
        if(AccessDetailData.car_role.equals("1")){
            car_roleStr="标的车";
            book_detail_brandlinear.setVisibility(View.VISIBLE);
        }else if(AccessDetailData.car_role.equals("2")){
            car_roleStr="三者车";
            book_detail_brandlinear.setVisibility(View.GONE);
        }
        book_detail_car_role.setText(car_roleStr);
        book_detail_dingsundian.setText(AccessDetailData.assess_address);
        String appointTimeSr= TimeUtil.formatTime(AccessDetailData.appointTime);
        book_detail_appointTime.setText(appointTimeSr);
        book_detail_caseNo.setText(AccessDetailData.caseNo);

        String case_fromStr="--";
        if(AccessDetailData.case_from.equals("1")){
            case_fromStr="GIS调度";
        }else if(AccessDetailData.car_role.equals("2")){
            case_fromStr="派发给自己";
        }
        book_detail_case_from.setText(case_fromStr);
        book_detail_reporter1.setText(AccessDetailData.reporter1);
        book_detail_reporterPhone1.setText(AccessDetailData.reporterPhone1);
        book_detail_reporterPhone1.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.go_back:
                AppManager.getAppManager().finishActivity(DSappointmentDetail.this);
                break;
            case R.id.book_detail_reporterPhone:
                TelphoneUtil.toDial(mContext,AccessDetailData.reporterPhone);
                break;
            case R.id.book_detail_reporterPhone1:
                TelphoneUtil.toDial(mContext,AccessDetailData.reporterPhone1);
                break;
            case R.id.book_detail_assessor_mobile:
                TelphoneUtil.toDial(mContext,AccessDetailData.assessor_mobile);
                break;
            default:
                break;
        }
    }
}
