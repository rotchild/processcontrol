package com.project.cx.processcontroljx.ui;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.project.cx.processcontroljx.R;
import com.project.cx.processcontroljx.adapters.RiskWarmAdapter;

import java.util.ArrayList;


/**
 * Created by cx on 2017/9/4.
 */

public class Dialog_risktip extends Dialog {
    static int scrW, scrH;
    private Button confirm_bt;
    private Button confirm_no;

    private EditText risktip_reason;
    private View dialogView;
    private ListView riskwarm_list;
    private Context mContext;
    private ArrayList<ContentValues> mdata;
    private int dialogType=0;

//上报审批
    public Dialog_risktip(Context context, int themeResId) {
        super(context, themeResId);
        mContext=context;
        mdata=null;
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        scrW = wm.getDefaultDisplay().getWidth();
        scrH = wm.getDefaultDisplay().getHeight();
        dialogType=1;
    }
    //风险提醒
    public Dialog_risktip(Context context, int themeResId, ArrayList<ContentValues> dataArray) {
        super(context, themeResId);
        mContext=context;
        mdata=dataArray;
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        scrW = wm.getDefaultDisplay().getWidth();
        scrH = wm.getDefaultDisplay().getHeight();
        dialogType=2;
    }

    public void initDialog( ) {
        if(dialogType==1){//上报审批
            dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_risktip, null);
            risktip_reason= (EditText) dialogView.findViewById(R.id.risktip_reason);
            confirm_bt = (Button) dialogView.findViewById(R.id.risktip_confirm_bt);
            confirm_no = (Button) dialogView.findViewById(R.id.risktip_confirm_no);
        }else if(dialogType==2){//风险提醒
            dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_risktype, null);
            confirm_bt = (Button) dialogView.findViewById(R.id.confirm_bt_single);
            riskwarm_list= (ListView) dialogView.findViewById(R.id.riskwarm_list);
            RiskWarmAdapter riskWarmAdapter=new RiskWarmAdapter(mContext,mdata);
            riskwarm_list.setAdapter(riskWarmAdapter);
        }
        int diaW = scrW * 3 / 4;
        int diaH =scrW * 3 /4;
        super.addContentView(dialogView, new ViewGroup.LayoutParams(diaW, diaH));
    }
    public void Notice_Dialog() {
        dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_righttip, null);
        confirm_bt = (Button) dialogView.findViewById(R.id.Notice_confirm_bt);
        int diaW = scrW * 19/20;
        int diaH = scrH * 7/8;
        super.addContentView(dialogView, new ViewGroup.LayoutParams(diaW, diaH));
    }

    public void setOnPositiveListener(View.OnClickListener listener) {
        confirm_bt.setOnClickListener(listener);
    }

    public void setOnNagetiveListener(View.OnClickListener listener) {
        confirm_no.setOnClickListener(listener);
    }

/*    public void setRistipReason(String reasoncontent){
        risktip_reason.setText(reasoncontent);
    }*/

    public String  getRistipReason(){
        return risktip_reason.getText().toString();
    }
}
