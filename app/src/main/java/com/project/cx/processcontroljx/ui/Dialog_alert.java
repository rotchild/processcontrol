package com.project.cx.processcontroljx.ui;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.project.cx.processcontroljx.R;


/**
 * Created by cx on 2017/9/4.
 */

/**
 * 所有alert的模板
 */
public class Dialog_alert extends Dialog {
    static int scrW, scrH;
    private Button confirm_bt;
    private TextView rensha_name;
    private TextView rensha_phone;
    private TextView dialog_alert_content;
    private View dialogView;
    private int layoutId;

    public Dialog_alert(Context context, int themeResId,int resourcelayoutId) {
        super(context, themeResId);
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        scrW = wm.getDefaultDisplay().getWidth();
        scrH = wm.getDefaultDisplay().getHeight();
        layoutId=resourcelayoutId;
        initDialog();
    }

    public void initDialog() {
        int diaW=0;
        int diaH=0;
        dialogView = LayoutInflater.from(getContext()).inflate(layoutId, null);
        confirm_bt = (Button) dialogView.findViewById(R.id.risktip_confirm_bt);
        if(layoutId==R.layout.dialog_cbrs){
            rensha_name= (TextView) dialogView.findViewById(R.id.dialog_rs_name);
            rensha_phone= (TextView) dialogView.findViewById(R.id.dialog_rs_phone);
            diaW = scrW * 9 /10;
            diaH = scrH * 2/5;
        }else if(layoutId==R.layout.dialog_alerttip){
            dialog_alert_content=(TextView) dialogView.findViewById(R.id.dialog_alert_content);
            diaW = scrW * 3 / 4;
              diaH = diaW * 2 / 3;
        }

        super.addContentView(dialogView, new ViewGroup.LayoutParams(diaW, diaH));
    }

    public void setOnPositiveListener(View.OnClickListener listener) {
        confirm_bt.setOnClickListener(listener);
    }

    public void setRensha_name(String rensha_nameStr){
        if(rensha_name!=null){
            rensha_name.setText(rensha_nameStr);
        }
    }
    public void setRensha_phone(String rensha_phoneStr){
        if(rensha_phone!=null){
            rensha_phone.setText(rensha_phoneStr);
        }
    }

    public void setalertText(String alertText){
        if(dialog_alert_content!=null){
            dialog_alert_content.setText(alertText);
        }
    }

}
