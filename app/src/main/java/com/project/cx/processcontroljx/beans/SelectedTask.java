package com.project.cx.processcontroljx.beans;

import android.content.ContentValues;

/**
 * Created by Administrator on 2017/12/5 0005.
 */
//管理选中的task
public class SelectedTask {
    private static ContentValues mtaskDCK,mtaskYCK,mtaskDDS,mtaskDSZ,mtaskYDS,mtaskHP,mtaskRSWORK,mtaskRSHIS,mtaskRisk;

    public static void storeTaskDCK(ContentValues taskDCK){
        mtaskDCK=taskDCK;
    }
    public static void storeTaskYCK(ContentValues taskYCK){
        mtaskYCK=taskYCK;
    }
    public static void storeTaskDDS(ContentValues taskDDS){
        mtaskDDS=taskDDS;
    }
    public static void storeTaskDSZ(ContentValues taskDSZ){
        mtaskDSZ=taskDSZ;
    }
    public static void storeTaskYDS(ContentValues taskYDS){
        mtaskYDS=taskYDS;
    }
    public static void storeTaskHP(ContentValues taskHP){
        mtaskHP=taskHP;
    }
    public static void storeTaskRSWORK(ContentValues taskRSWORK){
        mtaskRSWORK=taskRSWORK;
    }
    public static void storeTaskRSHIS(ContentValues taskRSHIS){
        mtaskRSHIS=taskRSHIS;
    }
    public static void storeTaskRisk(ContentValues taskRisk){
        mtaskRisk=taskRisk;
    }

    public static ContentValues getTaskDCK(){
        return mtaskDCK;
    }
    public static ContentValues getTaskYCK(){
        return mtaskYCK;
    }
    public static ContentValues getTaskDDS(){
        return mtaskDDS;
    }
    public static ContentValues getTaskDSZ(){
        return mtaskDSZ;
    }
    public static ContentValues getTaskYDS(){
        return mtaskYDS;
    }
    public static ContentValues getTaskHP(){
        return mtaskHP;
    }
    public static ContentValues getTaskRSWORK(){
        return mtaskRSWORK;
    }
    public static ContentValues getTaskRSHIS(){
        return mtaskRSHIS;
    }
    public static ContentValues getTaskRisk(){
        return mtaskRisk;
    }
}
