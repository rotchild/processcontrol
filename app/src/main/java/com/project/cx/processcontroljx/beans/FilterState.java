package com.project.cx.processcontroljx.beans;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.project.cx.processcontroljx.R;

/**
 * Created by Administrator on 2017/12/15 0015.
 */

public class FilterState {
    public static int lian_yes=0;
    public static int lian_no=0;
    public static int shangbao_yes=0;
    public static int shangbao_no=0;
    public static int fengxian_yes=0;
    public static int fengxian_no=0;

    /**
     * 根据页面选择状态转为上传所需参数
     * @param state_yes
     * @param state_no
     * @return
     */
    public static String stateconvert(int state_yes,int state_no){
        String paramState="";
        if(state_yes==0 && state_no==0){

        }else if(state_yes==1 && state_no==1){

        }else if(state_yes==1 && state_no==0){
            paramState="1";
        }else if(state_yes==0 && state_no==1){
            paramState="0";
        }
        return paramState;
    }

    public static void initState(Context ctx, View parentView, int viewId){
        TextView tv= (TextView) parentView.findViewById(viewId);
        switch (viewId){
            case R.id.typetip_lian_yes:
                if(FilterState.lian_yes==0){
                    tv.setTextColor(ctx.getResources().getColor(R.color.maintext));
                }else if(FilterState.lian_yes==1){
                    tv.setTextColor(Color.RED);
                }
                break;
            case R.id.typetip_lian_no:
                if(FilterState.lian_no==0){
                    tv.setTextColor(ctx.getResources().getColor(R.color.maintext));
                }else if(FilterState.lian_no==1){
                    tv.setTextColor(Color.RED);
                }
                break;
            case R.id.typetip_shangbao_yes:
                if(FilterState.shangbao_yes==0){
                    tv.setTextColor(ctx.getResources().getColor(R.color.maintext));
                }else if(FilterState.shangbao_yes==1){
                    tv.setTextColor(Color.RED);
                }
                break;
            case R.id.typetip_shangbao_no:
                if(FilterState.shangbao_no==0){
                    tv.setTextColor(ctx.getResources().getColor(R.color.maintext));
                }else if(FilterState.shangbao_no==1){
                    tv.setTextColor(Color.RED);
                }
                break;
            case R.id.typetip_fengxian_yes:
                if(FilterState.fengxian_yes==0){
                    tv.setTextColor(ctx.getResources().getColor(R.color.maintext));
                }else if(FilterState.fengxian_yes==1){
                    tv.setTextColor(Color.RED);
                }
                break;
            case R.id.typetip_fengxian_no:
                if(FilterState.fengxian_no==0){
                    tv.setTextColor(ctx.getResources().getColor(R.color.maintext));
                }else if(FilterState.fengxian_no==1){
                    tv.setTextColor(Color.RED);
                }
                break;
            default:
                break;
        }
    }
    //重置选中状态
    public static void reset(Context ctx, View parentView){
        FilterState.lian_yes=0;
        FilterState.lian_no=0;
        FilterState.shangbao_yes=0;
        FilterState.shangbao_no=0;
        FilterState.fengxian_yes=0;
        FilterState.fengxian_no=0;

        initState(ctx,parentView,R.id.typetip_lian_yes);
        initState(ctx,parentView,R.id.typetip_lian_no);
        initState(ctx,parentView,R.id.typetip_shangbao_yes);
        initState(ctx,parentView,R.id.typetip_shangbao_no);
        initState(ctx,parentView,R.id.typetip_fengxian_yes);
        initState(ctx,parentView,R.id.typetip_fengxian_no);
    }
}
