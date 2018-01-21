package com.project.cx.processcontroljx.adapters;

import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.cx.processcontroljx.R;
import com.project.cx.processcontroljx.beans.TaskCK;
import com.project.cx.processcontroljx.utils.TimeUtil;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/11/29 0029.
 */

public class YCKAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<ContentValues> mData;
    private LayoutInflater mInflater;
    private int mResourceID=-1;
    ViewHolder_yck holder_yck=null;

    public interface MOnItemClickListener{
        public void onClick(int i);
    }

    private MOnItemClickListener mOnItemClicklistener;

    public YCKAdapter(Context mContext, ArrayList<ContentValues> mData){
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
            convertView=mInflater.inflate(R.layout.item_yck,null);
            holder_yck=new ViewHolder_yck();
            holder_yck.item_yck= (LinearLayout) convertView.findViewById(R.id.item_yck);
            holder_yck.caseNo=(TextView)convertView.findViewById(R.id.yck_caseNo);
            holder_yck.licenseno= (TextView) convertView.findViewById(R.id.yck_licenseno);
            holder_yck.caseTime=(TextView)convertView.findViewById(R.id.yck_caseTime);
            holder_yck.hurt_state=(TextView)convertView.findViewById(R.id.yck_hurt_state);
            //holder_yck.outNumber=(TextView)convertView.findViewById(R.id.yck_outNumber);
            //holder_yck.lian_state=(TextView)convertView.findViewById(R.id.yck_lian_state);
            holder_yck.risklevel=(TextView)convertView.findViewById(R.id.yck_risklevel);
            holder_yck.riskstate=(TextView)convertView.findViewById(R.id.yck_riskstate);
            holder_yck.lian_state=(TextView)convertView.findViewById(R.id.yck_lian_state);

            holder_yck.newTaskTag=(ImageView)convertView.findViewById(R.id.yck_new_tag);
            String isRead=mData.get(position).getAsString(TaskCK.isRead);
            if(isRead.equals("0")){//是否显示isNewTag
                holder_yck.newTaskTag.setVisibility(View.VISIBLE);
            }else{
                holder_yck.newTaskTag.setVisibility(View.GONE);
            }

            RelativeLayout head= (RelativeLayout)convertView.findViewById(R.id.yck_head_change);
            ImageView image_hurt = (ImageView)convertView.findViewById(R.id.yck_hurt_state_icon);

            holder_yck.item_yck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClicklistener.onClick(position);
                }
            });
            long reparations_long=Long.valueOf(mData.get(position).getAsString(TaskCK.reparations));
            //Log.i("dm","reparations_long"+reparations_long);
            if(reparations_long>10000){
                head.setBackground(mContext.getResources().getDrawable(R.drawable.grayhead_background));
            }else {
                long offSet = Long.valueOf(mData.get(position).getAsString(TaskCK.lianTime));
                long today = System.currentTimeMillis() / 1000;
                long intervalTime = (today - offSet) / 3600;
                if (intervalTime < 24) {
                    head.setBackground(mContext.getResources().getDrawable(R.drawable.grayhead_background));
                } else if (intervalTime < 48) {
                    head.setBackground(mContext.getResources().getDrawable(R.drawable.bluehead_background));
                } else if (intervalTime < 72) {
                    head.setBackground(mContext.getResources().getDrawable(R.drawable.yellowhead_background));
                } else {
                    head.setBackground(mContext.getResources().getDrawable(R.drawable.redhead_background));
                }
            }
            holder_yck.caseNo.setText(mData.get(position).getAsString(TaskCK.caseNo));
            holder_yck.licenseno.setText(mData.get(position).getAsString(TaskCK.licenseno));
            //holder_yck.outNumber.setText(mData.get(position).getAsString(TaskCK.outNumber));
            Long Ctime_long=Long.valueOf(mData.get(position).getAsString(TaskCK.caseTime))*1000;
            String Ctime_str=String.valueOf(Ctime_long);
            String caseTime_str= TimeUtil.stampToDate(Ctime_str);
            holder_yck.caseTime.setText(caseTime_str);
            String hurtstate_str="--";
            Long hurtstate_long=Long.valueOf(mData.get(position).getAsString(TaskCK.hurt_state));
            if(hurtstate_long==0){
                hurtstate_str="";
            }else if(hurtstate_long==1){
                image_hurt.setImageDrawable(mContext.getResources().getDrawable(R.drawable.red_medical));
                image_hurt.setVisibility(View.VISIBLE);
                hurtstate_str="未完成";
                holder_yck.hurt_state.setTextColor(mContext.getResources().getColor(R.color.typered));
                holder_yck.hurt_state.setVisibility(View.VISIBLE);
            }else if(hurtstate_long==2){
                image_hurt.setImageDrawable(mContext.getResources().getDrawable(R.drawable.green_medical));
                image_hurt.setVisibility(View.VISIBLE);
                hurtstate_str="已完成";
                holder_yck.hurt_state.setTextColor(mContext.getResources().getColor(R.color.typegreen));
                holder_yck.hurt_state.setVisibility(View.VISIBLE);
            }
            holder_yck.hurt_state.setText(hurtstate_str);
            //holder_yck.lian_state.setText(mData.get(position).getAsString(TaskCK.lian_state));
            String risklevel_str="--";
            Long risklevel_long=Long.valueOf(mData.get(position).getAsString(TaskCK.riskstate));
            if(risklevel_long==0){
                risklevel_str="无风险";
                holder_yck.risklevel.setTextColor(mContext.getResources().getColor(R.color.typegreen));
            }else if(risklevel_long==1){
                risklevel_str="风险案件";
                holder_yck.risklevel.setTextColor(mContext.getResources().getColor(R.color.typered));
            }
            holder_yck.risklevel.setText(risklevel_str);
            String riskstate_str="--";
            Long riskstate_long=Long.valueOf(mData.get(position).getAsString(TaskCK.riskstate));
            if(riskstate_long==0){
                riskstate_str="未上报";
            }else if(riskstate_long==1){
                riskstate_str="已上报";
            }
            holder_yck.riskstate.setText(riskstate_str);
            String lianstate_str="--";
            Long lianstate_long=Long.valueOf(mData.get(position).getAsString(TaskCK.lian_state));
            if(lianstate_long==0){
                lianstate_str="未立案";
                holder_yck.lian_state.setTextColor(mContext.getResources().getColor(R.color.typered));
            }else if(lianstate_long==1){
                lianstate_str="已立案";
            }
            holder_yck.lian_state.setText(lianstate_str);


            convertView.setTag(holder_yck);
//        }else{
//            holder_yck= (ViewHolder_yck) convertView.getTag();
//        }

        return convertView;
    }

    public class ViewHolder_yck{
        TextView caseNo,licenseno,caseTime,hurt_state,outNumber,
                 risklevel,riskstate,lian_state;
        LinearLayout item_yck;
        ImageView newTaskTag;
    }
}
