package com.project.cx.processcontroljx.ui;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

import com.project.cx.processcontroljx.R;


/**
 * Created by cx on 2017/9/4.
 */

public class Dialog_detailtip extends Dialog {
    static int scrW, scrH;
    private Button confirm_bt;
    private View dialogView;

    public Dialog_detailtip(Context context, int themeResId) {
        super(context, themeResId);
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        scrW = wm.getDefaultDisplay().getWidth();
        scrH = wm.getDefaultDisplay().getHeight();
    }

    public void initDialog() {
        dialogView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_detailtip, null);
        confirm_bt = (Button) dialogView.findViewById(R.id.alert_cancle);
        int diaW = scrW * 9 /10;
        int diaH = scrH * 9 /10;
        super.addContentView(dialogView, new ViewGroup.LayoutParams(diaW, diaH));
    }

    public void setOnPositiveListener(View.OnClickListener listener) {
        confirm_bt.setOnClickListener(listener);
    }
}
