package com.project.cx.processcontroljx.net;

import com.project.cx.processcontroljx.beans.ParamType;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;

/**
 * Created by Administrator on 2017/12/29 0029.
 */

public class MSocketHelper {
    private static MSocketHelper mSocketHelper;
    private io.socket.client.Socket mSocket=null;
    private boolean hasInit=false;
    private MSocketHelper(){}
    public static MSocketHelper getInstance(){
        if(mSocketHelper==null){
            mSocketHelper=new MSocketHelper();
        }
        return mSocketHelper;
    }

    /**
     * 初始化mSocket
     * @param token
     */
    public void init(String socketUrl,String token){
        if(mSocket==null){
            connect(socketUrl,token);
        }
    }

    /**
     * 连接服务器
     * @param token
     */
    public void connect(String socketUrl,String token){
        try{
            if(mSocket==null){
                IO.Options opts = new IO.Options();
                opts.query = "token=" +token;//传参数
                //mSocket = IO.socket("http://192.168.1.13:1017/", opts);
                mSocket = IO.socket(socketUrl, opts);
                hasInit=true;
            }
            mSocket.connect();
            mSocket.emit("token",token);
        }catch (URISyntaxException e){
            e.printStackTrace();
        }
    }

    /**
     * 是否已经初始化
     * @return
     */
    public boolean hasInit(){
        return hasInit;
    }

    public void logout(){
        if(mSocket!=null){
            mSocket.disconnect();
            mSocket.off("new_msg");
            mSocket.off("disconnect");
            mSocket.off("connect");
            mSocket=null;
        }
        hasInit=false;
    }

    /**
     * 获取socket
     * @return
     */
    public Socket getMSocket(){
        if(hasInit){
            return mSocket;
        }
        return null;
    }

    public static String getTaskType(String eventType){
        String taskType="";
        switch (eventType){
            case "1":
                taskType= ParamType.DCK;
                break;
            case "2":
                taskType= ParamType.YCK;
                break;
            case "3":
                taskType= ParamType.DDS;
                break;
            case "4":
                taskType= ParamType.DSZ;
                break;
            case "5":
                taskType= ParamType.YDS;
                break;
            case "6":
                taskType= ParamType.HP;
                break;
            case "7":
                taskType= ParamType.GZ;
                break;
            case "8":
                taskType= ParamType.LS;
                break;
            default:
                break;
        }
        return taskType;
    }

    public static String getTaskTypeName(String eventType){
        String taskTypeName="";
        switch (eventType){
            case "1":
                taskTypeName= "待查勘";
                break;
            case "2":
                taskTypeName= "已查勘";
                break;
            case "3":
                taskTypeName= "待定损";
                break;
            case "4":
                taskTypeName= "定损中";
                break;
            case "5":
                taskTypeName= "已定损";
                break;
            case "6":
                taskTypeName="获票";
                break;
            case "7":
                taskTypeName= "人伤";
                break;
            case "8":
                taskTypeName= "历史";
                break;
            default:
                break;
        }
        return taskTypeName;
    }

}
