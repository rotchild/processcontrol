package com.project.cx.processcontroljx.beans;

/**
 * Created by Administrator on 2017/12/6 0006.
 */

import android.content.ContentValues;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * TaskThirdcars 中data json数组中json的结构
 * 管理TaskThirdcars的array数组
 */
public class ThirdCars {
    public static ArrayList<ContentValues> thirdcarsArray=new ArrayList<ContentValues>();
    public static ArrayList<ContentValues> alllicensenoArray=new ArrayList<ContentValues>();
    public static HashMap<Integer,String>  thirdAddMap=new HashMap<Integer,String>();//添加时item输入的内容
}
