package com.project.cx.processcontroljx.utils;

import android.content.ContentValues;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018/1/12 0012.
 */

public class RisTypeHelper {
    public static RisTypeHelper mRisTypeHelper;
    public ArrayList<ContentValues> mArrayList;
    private RisTypeHelper(){
        init();
    }

    private void init() {//初始化所有类型
        mArrayList=new ArrayList<ContentValues>();
        for(int i=1;i<10;i++){
            ContentValues value=new ContentValues();
            value.put(String.valueOf(i),"风险类型"+String.valueOf(i));
            mArrayList.add(value);
        }
    }

    public static RisTypeHelper getInstance(){
        if(mRisTypeHelper==null){
            mRisTypeHelper=new RisTypeHelper();
        }
        return mRisTypeHelper;
    }
    //根据typeStr来获取arraylist<contentvalues>
//    public ArrayList<ContentValues> getRistTypeArray(String typeStr){
//        String[] typeStrArray=typeStr.split(",");
//
//    }





}
