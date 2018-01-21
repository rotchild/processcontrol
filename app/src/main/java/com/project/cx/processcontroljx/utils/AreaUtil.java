package com.project.cx.processcontroljx.utils;

import android.content.ContentValues;

import com.project.cx.processcontroljx.beans.AreaObj;
import com.project.cx.processcontroljx.beans.TaskArea;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/12/13 0013.
 */

public class AreaUtil {
    public static ArrayList<String> getAreaNames(ArrayList<ContentValues> areaDatas){//要有id和name
        ArrayList<String> areaNames=new ArrayList<String>();
        for(int i=0;i<areaDatas.size();i++){
            ContentValues areadata=areaDatas.get(i);
            String areaNameStr= areadata.getAsString(TaskArea.AreaName);
            if(areaNames.contains(areaNameStr)){//去重

            }else{
                areaNames.add(areaNameStr);
            }
        }
        return areaNames;
    }

    public static ArrayList<AreaObj> formatAreaData(ArrayList<ContentValues> areaDatas){
        ArrayList<AreaObj> areaObjList=new ArrayList<AreaObj>();
        //ArrayList<String> areaNames=getAreaNames(areaDatas);
        for(int i=0;i<areaDatas.size();i++){
            ContentValues areadata=areaDatas.get(i);
            String areaName=areadata.getAsString(TaskArea.AreaName);
            boolean found=false;
            for(int j=0;j<areaObjList.size();j++){
                AreaObj areaObj=areaObjList.get(j);
                if(areaObj!=null && areaObj.getName().equals(areaName)){
                    areaObj.getAssess_arraylist().add(areadata.getAsString(TaskArea.assess_name));
                    found=true;
                    break;
                }else{

                }

            }
            if(!found){
                AreaObj areaObjnew=new AreaObj();
                ArrayList<String> assess_arraylist=new ArrayList<String>();
                assess_arraylist.add(areadata.getAsString(TaskArea.assess_name));
                areaObjnew.setName(areaName);
                areaObjnew.setAssess_arraylist(assess_arraylist);
                areaObjList.add(areaObjnew);
            }

        }
        return areaObjList;
    }
}
