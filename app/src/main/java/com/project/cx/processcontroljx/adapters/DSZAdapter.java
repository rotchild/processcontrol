package com.project.cx.processcontroljx.adapters;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.cx.processcontroljx.R;
import com.project.cx.processcontroljx.beans.TaskDS;
import com.project.cx.processcontroljx.utils.TimeUtil;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/11/29 0029.
 */

public class DSZAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<ContentValues> mData;
    private LayoutInflater mInflater;
    private int mResourceID=-1;
    ViewHolder_dck holder_dsz=null;

    public interface MOnItemClickListener{
        public void onClick(int i);
    }

    private MOnItemClickListener mOnItemClicklistener;

    public DSZAdapter(Context mContext, ArrayList<ContentValues> mData){
        super();
        this.mContext=mContext;
        this.mData=mData;
        this.mInflater=LayoutInflater.from(mContext);
    }

    public void setMOnItemClickListener(MOnItemClickListener mOnItemClickListener){
        this.mOnItemClicklistener=mOnItemClickListener;
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
            convertView=mInflater.inflate(R.layout.item_dsz,null);
            holder_dsz=new ViewHolder_dck();
            holder_dsz.item_ck= (LinearLayout) convertView.findViewById(R.id.item_dsz);
            holder_dsz.caseNo=(TextView)convertView.findViewById(R.id.dsz_caseNo);
            holder_dsz.licenseno=(TextView)convertView.findViewById(R.id.dsz_licenseno);
            holder_dsz.caseTime=(TextView)convertView.findViewById(R.id.dsz_caseTime);
            //holder_dsz.caseState= (TextView) convertView.findViewById(R.id.dsz_caseState);
            //holder_dsz.hurt_state=(TextView)convertView.findViewById(R.id.dsz_hurt_state);
            //holder_dsz.outNumber=(TextView)convertView.findViewById(R.id.dsz_outNumber);
            holder_dsz.risklevel=(TextView)convertView.findViewById(R.id.dsz_risklevel);
            holder_dsz.riskstate=(TextView)convertView.findViewById(R.id.dsz_riskstate);
            holder_dsz.newTaskTag=(ImageView)convertView.findViewById(R.id.dsz_new_tag);
            String isRead=mData.get(position).getAsString(TaskDS.isRead);
            if(isRead.equals("0")){//是否显示isNewTag
                holder_dsz.newTaskTag.setVisibility(View.VISIBLE);
            }else{
                holder_dsz.newTaskTag.setVisibility(View.GONE);
            }

            RelativeLayout head= (RelativeLayout)convertView.findViewById(R.id.dsz_head_change);
            ImageView image_hurt = (ImageView)convertView.findViewById(R.id.dsz_hurt_state_icon);
            long reparations_long=Long.valueOf(mData.get(position).getAsString(TaskDS.reparations));
            Log.i("dm","reparations_long"+reparations_long);
            if(reparations_long>10000){
                head.setBackground(mContext.getResources().getDrawable(R.drawable.grayhead_background));
            }else {
                long offSet = Long.valueOf(mData.get(position).getAsString(TaskDS.accept_time));
                long today = System.currentTimeMillis() / 1000;
                long intervalTime = (today - offSet) / 3600;
                Log.i("dm","intervalTime"+intervalTime);
                if (intervalTime < 2) {
                    head.setBackground(mContext.getResources().getDrawable(R.drawable.grayhead_background));
                } else if (intervalTime < 4) {
                    head.setBackground(mContext.getResources().getDrawable(R.drawable.bluehead_background));
                } else if (intervalTime < 6) {
                    head.setBackground(mContext.getResources().getDrawable(R.drawable.yellowhead_background));
                } else {
                    head.setBackground(mContext.getResources().getDrawable(R.drawable.redhead_background));
                }
            }
            holder_dsz.caseNo.setText(mData.get(position).getAsString(TaskDS.caseNo));
            holder_dsz.licenseno.setText(mData.get(position).getAsString(TaskDS.licenseno));
            Long Ctime_long=Long.valueOf(mData.get(position).getAsString(TaskDS.caseTime))*1000;
            String Ctime_str=String.valueOf(Ctime_long);
            String caseTime_str= TimeUtil.stampToDate(Ctime_str);
            holder_dsz.caseTime.setText(caseTime_str);
            //holder_dsz.outNumber.setText(mData.get(position).getAsString(TaskDS.outNumber));

        /*    String hurtstate_str="--";
            Long hurtstate_long=Long.valueOf(mData.get(position).getAsString(TaskDS.riskstate));
            if(hurtstate_long==0){
                hurtstate_str="";
            }else if(hurtstate_long==1){
                image_hurt.setImageDrawable(mContext.getResources().getDrawable(R.drawable.red_medical));
                image_hurt.setVisibility(View.VISIBLE);
                hurtstate_str="未完成";
                holder_dsz.hurt_state.setVisibility(View.VISIBLE);
            }else if(hurtstate_long==2){
                image_hurt.setImageDrawable(mContext.getResources().getDrawable(R.drawable.green_medical));
                image_hurt.setVisibility(View.VISIBLE);
                hurtstate_str="已完成";
                holder_dsz.hurt_state.setTextColor(mContext.getResources().getColor(R.color.typegreen));
                holder_dsz.hurt_state.setVisibility(View.VISIBLE);
            }
            holder_dsz.hurt_state.setText(hurtstate_str);*/

            String risklevel_str="--";
            Long risklevel_long=Long.valueOf(mData.get(position).getAsString(TaskDS.riskstate));
            if(risklevel_long==0){
                risklevel_str="无风险";
            }else if(risklevel_long==1){
                risklevel_str="风险案件";
            }
            holder_dsz.risklevel.setText(risklevel_str);
            String riskstate_str="--";
            Long riskstate_long=Long.valueOf(mData.get(position).getAsString(TaskDS.riskstate));
            if(riskstate_long==0){
                riskstate_str="未上报";
            }else if(riskstate_long==1){
                riskstate_str="已上报";
            }
            holder_dsz.riskstate.setText(riskstate_str);


            holder_dsz.item_ck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClicklistener.onClick(position);
                }
            });
            convertView.setTag(holder_dsz);
//        }else{
//            holder_dsz= (ViewHolder_dck) convertView.getTag();
//        }

        return convertView;
    }

    public class ViewHolder_dck{
        TextView caseTime,caseState,outNumber,risklevel,riskstate,caseNo,licenseno;
        //hurt_state;
        LinearLayout item_ck;
        ImageView newTaskTag;
    }
}
