package com.project.cx.processcontroljx.beans;

import android.content.ContentValues;

/**
 * Created by Administrator on 2017/12/17 0017.
 */

public class SelectedThirdCar {
    private static ContentValues mthirdCar;
    public static void storeThirdCar(ContentValues thirdCar){
        mthirdCar=thirdCar;
    }
    public static ContentValues getThirdCar(){
        return mthirdCar;
    }
}
