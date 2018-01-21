package com.project.cx.processcontroljx.beans;

/**
 * Created by Administrator on 2017/12/6 0006.
 */

import android.content.ContentValues;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * GetAreas 中data json数组中json的结构原始数据和格式化后的数据
 * 管理content的array数组
 */
public class DSArea {

    public static ArrayList<ContentValues> dsAreaArray=new ArrayList<ContentValues>();
    public static ArrayList<AreaObj> dsAreaObjArray=new ArrayList<AreaObj>();
    public static HashMap<String,String> area_idMap=new HashMap<String, String>();
    public static HashMap<String,String> access_idMap=new HashMap<String, String>();
    public static HashMap<String,String> access_group=new HashMap<String, String>();
}
