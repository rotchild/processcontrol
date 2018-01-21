package com.project.cx.processcontroljx.beans;

/**
 * Created by Administrator on 2018/1/2 0002.
 */

/**
 * 未读消息数量
 */
public class UnReadCounts {
    public static int DCKCount=0;
    public static int YCKCount=0;
    public static int DDSCount=0;
    public static int DSZCount=0;
    public static int YDSCount=0;
    public static int HPCount=0;
    public static int GZCount=0;
    public static int LSCount=0;

    public static int getCount(String tasktype){
        switch (tasktype){
            case ParamType.DCK:
                return DCKCount;

            case ParamType.YCK:
                return YCKCount;

            case ParamType.DDS:
                return DDSCount;

            case ParamType.DSZ:
                return DSZCount;

            case ParamType.YDS:
                return YDSCount;

            case ParamType.HP:
                return HPCount;

            case ParamType.ALL:
                break;

            case ParamType.GZ:
                return GZCount;

            case ParamType.LS:
                return LSCount;

            default:
                break;
        }
        return -1;
    }

    public static void setCount(String tasktype,int count){
        switch (tasktype){
            case ParamType.DCK:
                DCKCount=count;
                break;

            case ParamType.YCK:
                YCKCount=count;
                break;

            case ParamType.DDS:
                DDSCount=count;
                break;

            case ParamType.DSZ:
                DSZCount=count;
                break;

            case ParamType.YDS:
                YDSCount=count;
                break;

            case ParamType.HP:
                HPCount=count;
                break;

            case ParamType.ALL:
                break;

            case ParamType.GZ:
                GZCount=count;
                break;

            case ParamType.LS:
                LSCount=count;
                break;

            default:
                break;
        }

    }

}
