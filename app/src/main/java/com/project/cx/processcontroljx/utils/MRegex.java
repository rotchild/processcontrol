package com.project.cx.processcontroljx.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by cx on 2017/9/7.
 */

public class MRegex {
/*    public static boolean isRightCaseNO(String caseNo){
        String express="[A-Z0-9]{22}";
        Pattern p=Pattern.compile(express);
        Matcher m=p.matcher(caseNo);
        return m.matches();
    }*/
    public static boolean isRightCarNO(String carNo){
        //String express = "^[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1}$";
        String express="[\\u4e00-\\u9fa5]{1}[A-Z]{1}[A-Z0-9\\u4e00-\\u9fa5]{5}";
        Pattern p= Pattern.compile(express);
        Matcher m=p.matcher(carNo);
        return m.matches();
    }
    //如果是navis.3322.org如何处理
    public static boolean isRightIP(String IP){
        String express="^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
                +"(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                +"(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                +"(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
        Pattern p=Pattern.compile(express);
        Matcher m=p.matcher(IP);
        //return m.matches();
        return true;
    }
    public static boolean isRightPort(String Port){
        String express="([0-9]|[1-9]\\d{1,3}|[1-5]\\d{4}|6[0-5]{2}[0-3][0-5])";
        Pattern p=Pattern.compile(express);
        Matcher m=p.matcher(Port);
        return m.matches();
    }
}
