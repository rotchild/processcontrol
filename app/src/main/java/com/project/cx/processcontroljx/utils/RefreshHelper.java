package com.project.cx.processcontroljx.utils;

import com.project.cx.processcontroljx.beans.CurrentBottom;
import com.project.cx.processcontroljx.beans.LoadType;
import com.project.cx.processcontroljx.beans.ParamType;
import com.project.cx.processcontroljx.processmain.ProcessMain;

/**
 * Created by Administrator on 2017/12/19 0019.
 */

public class RefreshHelper {
    private RefreshHelper(){}
    private static RefreshHelper mRefreshHelper;
    public static RefreshHelper getRefreshHelper(){
        if(mRefreshHelper!=null){
            mRefreshHelper=new RefreshHelper();
        }
        return mRefreshHelper;
    }
    public void refreshtask(String bottom, ProcessMain pm){//貌似在resume中调用会有问题，暂时不用
        if(bottom.equals(CurrentBottom.CK_DDS)){
            pm.getTaskCKData(pm.userManager.getUserToken(),pm.userManager.getFrontRole(), ParamType.DCK,"","","",
                    "",pm.refreshStart,pm.refreshLimit,
                    OkCallbackManager.getInstance().getCallback(LoadType.REFRESH,pm,ParamType.DCK,pm));
        }else if(bottom.equals(CurrentBottom.CK_DDS)){
            pm.getTaskCKData(pm.userManager.getUserToken(),pm.userManager.getFrontRole(), ParamType.YCK,"","","",
                    "",pm.refreshStart,pm.refreshStart,
                    OkCallbackManager.getInstance().getCallback(LoadType.REFRESH,pm,ParamType.YCK,pm));
        }
    }
}
