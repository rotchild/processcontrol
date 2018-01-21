package com.project.cx.processcontroljx.beans;

/**
 * Created by Administrator on 2017/12/13 0013.
 */

import java.util.ArrayList;

/**
 * 用于表示区域和定损点关系的结构
 */
public class AreaObj {
    private String a_name;
    private ArrayList<String> assess_arraylist=new ArrayList<String>();

    public void setName(String name){
        a_name=name;
    }
    public void setAssess_arraylist(ArrayList<String> assessList){
        assess_arraylist=assessList;
    }

    public String getName(){
        return a_name;
    }

    public ArrayList<String> getAssess_arraylist(){
        return assess_arraylist;
    }
}
