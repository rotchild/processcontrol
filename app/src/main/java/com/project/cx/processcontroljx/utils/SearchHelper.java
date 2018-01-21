package com.project.cx.processcontroljx.utils;

import android.content.ContentValues;

import com.project.cx.processcontroljx.beans.CurrentBottom;
import com.project.cx.processcontroljx.beans.ParamType;

/**
 * Created by Administrator on 2017/12/16 0016.
 */

/**
 * 根据当前bottom,决定search的type参数
 */
public class SearchHelper {
    public static ContentValues getType(String currentBottom){
        ContentValues paramValues=new ContentValues();
        String httpType="";
        String paramType="";
        if(currentBottom.equals(CurrentBottom.CK_DCK) || currentBottom.equals(CurrentBottom.CD_DCK)){
            httpType="CK";
            paramType= ParamType.DCK;
        }else if(currentBottom.equals(CurrentBottom.CK_YCK) || currentBottom.equals(CurrentBottom.CD_YCK)){
            httpType="CK";
            paramType=ParamType.YCK;
        }else if(currentBottom.equals(CurrentBottom.CK_DDS) || currentBottom.equals(CurrentBottom.CD_DDS)){
            httpType="DS";
            paramType=ParamType.DDS;
        }else if(currentBottom.equals(CurrentBottom.DS_DSZ) || currentBottom.equals(CurrentBottom.CD_DSZ)){
            httpType="DS";
            paramType=ParamType.DSZ;
        }else if(currentBottom.equals(CurrentBottom.DS_YDS) || currentBottom.equals(CurrentBottom.CD_YDS)){
            httpType="DS";
            paramType=ParamType.YDS;
        }else if(currentBottom.equals(CurrentBottom.DS_HP) || currentBottom.equals(CurrentBottom.CD_HP)){
            httpType="DS";
            paramType=ParamType.HP;
        }else if(currentBottom.equals(CurrentBottom.RS_WORK)){
            httpType="RS";
            paramType=ParamType.GZ;
        }else if(currentBottom.equals(CurrentBottom.RS_HISTROY)){
            httpType="RS";
            paramType=ParamType.LS;
        }
        paramValues.put(httpType,paramType);
        return paramValues;
    }
}
