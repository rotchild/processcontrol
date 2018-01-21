package com.project.cx.processcontroljx.adapters;

import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.cx.processcontroljx.R;
import com.project.cx.processcontroljx.beans.TaskRisk;
import com.project.cx.processcontroljx.utils.TimeUtil;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/11/29 0029.
 */

/**
 * 流程追踪列表
 */
public class RiskAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<ContentValues> mData;
    private LayoutInflater mInflater;
    private int mResourceID=-1;
    ViewHolder_risk holder_risk=null;

    public interface MOnItemClickListener{
        public void onClick(int i);
    }

    private MOnItemClickListener mOnItemClicklistener,mOnItemDetailClicklistener;

    public RiskAdapter(Context mContext, ArrayList<ContentValues> mData){
        super();
        this.mContext=mContext;
        this.mData=mData;
        this.mInflater=LayoutInflater.from(mContext);
    }

    public void setMOnItemClickListener(MOnItemClickListener mOnItemClickListener){
        this.mOnItemClicklistener=mOnItemClickListener;
    }
    public void setMOnItemdetailClickListener(MOnItemClickListener mOnItemClickListener){
        this.mOnItemDetailClicklistener=mOnItemClickListener;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return  mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
//        if(convertView==null){
            convertView=mInflater.inflate(R.layout.item_fxsb,null);
            holder_risk=new ViewHolder_risk();
            holder_risk.item_fxsb= (LinearLayout) convertView.findViewById(R.id.item_fxsb);
            holder_risk.createtime=(TextView)convertView.findViewById(R.id.item_fxsb_createtime);
            holder_risk.state=(TextView)convertView.findViewById(R.id.item_fxsb_state);
            holder_risk.detail_btn=(Button) convertView.findViewById(R.id.item_fxsb_detail);



            Long Ctime_long=Long.valueOf(mData.get(position).getAsString(TaskRisk.createtime))*1000;
            String Ctime_str=String.valueOf(Ctime_long);
            String Ctime_result= TimeUtil.stampToDate(Ctime_str);
            //holder_risk.createtime.setText(mData.get(position).getAsString(TaskRisk.createtime));
            holder_risk.createtime.setText(Ctime_result);

            String mstate=mData.get(position).getAsString(TaskRisk.state);
            String statesStr="";
            if(mstate.equals("0")){
                statesStr="审核中";
            }else if(mstate.equals("1")){
                statesStr="审核通过";
            }if(mstate.equals("2")){
                statesStr="驳回";
            }
           // holder_risk.state.setText(mData.get(position).getAsString(TaskRisk.state));
            holder_risk.state.setText(statesStr);

            holder_risk.item_fxsb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClicklistener.onClick(position);
                }
            });

            //详情按钮
            holder_risk.detail_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemDetailClicklistener.onClick(position);
                }
            });
            convertView.setTag(holder_risk);
//        }else{
//            holder_risk= (ViewHolder_risk) convertView.getTag();
//        }

        return convertView;
    }

    public class ViewHolder_risk{
        TextView createtime,state;
        Button detail_btn;
        LinearLayout item_fxsb;
    }
}
