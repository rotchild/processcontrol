package com.project.cx.processcontroljx.utils;

import com.project.cx.processcontroljx.R;
import com.project.cx.processcontroljx.beans.ParamType;
import com.project.cx.processcontroljx.processmain.ProcessMain;

/**
 * Created by Administrator on 2018/1/2 0002.
 */

public class CountSetHelper {
    private static CountSetHelper mCountSetHelper;
    private ProcessMain mActivity;
    private CountSetHelper(ProcessMain act){
        mActivity=act;
    }
    public static CountSetHelper getInstance(ProcessMain activity){
        if(mCountSetHelper==null){
            mCountSetHelper=new CountSetHelper(activity);
        }
        return mCountSetHelper;
    }

  /*  public void setCount(String tasktype,int count){
        int viewId=getViewId(tasktype);
        Log.e("countSetHelper","viewId"+viewId);
        if(viewId!=-1){
            TextView textView= (TextView) mActivity.findViewById(viewId);
            if(textView!=null){
                if(count>0){
                    textView.setVisibility(View.VISIBLE);
                }else{
                    textView.setVisibility(View.GONE);
                }
                textView.setVisibility(View.VISIBLE);
                textView.setText(String.valueOf(count));
                Log.e("countSetHelper","setText Count:"+count);
            }else{
                Log.e("countSetHelper","textView=null");
            }
        }
    }*/

    public int getViewId(String tasktype){
        int viewId=-1;
        switch (tasktype){
            case ParamType.DCK:
                viewId= R.id.bar_num1;
                break;
            case ParamType.YCK:
                viewId= R.id.bar_num2;
                break;
            case ParamType.DDS:
                viewId= R.id.bar_num3;
                break;
            case ParamType.DSZ:
                viewId= R.id.bar_num4;
                break;
            case ParamType.YDS:
                viewId= R.id.bar_num5;
                break;
            case ParamType.HP:
                viewId= R.id.bar_num6;
                break;
            case ParamType.ALL:
                viewId= R.id.bar_num7;
                break;
            case ParamType.GZ:
                viewId= R.id.bar_num8;
                break;
            case ParamType.LS:
                viewId= R.id.bar_num9;
                break;
            default:
                break;
        }
        return viewId;
    }

}
