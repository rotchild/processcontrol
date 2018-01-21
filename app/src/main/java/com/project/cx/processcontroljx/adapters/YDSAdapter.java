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
import com.project.cx.processcontroljx.beans.TaskDS;
import com.project.cx.processcontroljx.utils.TimeUtil;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/11/29 0029.
 */

public class YDSAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<ContentValues> mData;
    private LayoutInflater mInflater;
    private int mResourceID=-1;
    ViewHolder_yds holder_yds=null;

    public interface MOnItemClickListener{
        public void onClick(int i);
    }

    private MOnItemClickListener mOnItemClicklistener;

    public YDSAdapter(Context mContext, ArrayList<ContentValues> mData){
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
            convertView=mInflater.inflate(R.layout.item_yds,null);
            holder_yds=new ViewHolder_yds();
            holder_yds.item_yds= (LinearLayout) convertView.findViewById(R.id.item_yds);
            holder_yds.caseNo=(TextView)convertView.findViewById(R.id.yds_caseNo);
            holder_yds.licenseno=(TextView)convertView.findViewById(R.id.yds_licenseno);
            holder_yds.caseTime=(TextView)convertView.findViewById(R.id.yds_caseTime);
            //holder_dsz.caseState= (TextView) convertView.findViewById(R.id.dsz_caseState);
            //holder_yds.hurt_state=(TextView)convertView.findViewById(R.id.yds_hurt_state);
           // holder_yds.outNumber=(TextView)convertView.findViewById(R.id.yds_outNumber);
            holder_yds.risklevel=(TextView)convertView.findViewById(R.id.yds_risklevel);
            holder_yds.riskstate=(TextView)convertView.findViewById(R.id.yds_riskstate);
            holder_yds.newTaskTag=(ImageView)convertView.findViewById(R.id.yds_new_tag);
            holder_yds.yds_reporter1=(TextView)convertView.findViewById(R.id.yds_reporter1);
            holder_yds.yds_assess_amount=(TextView)convertView.findViewById(R.id.yds_assess_amount);
            String isRead=mData.get(position).getAsString(TaskDS.isRead);
            if(isRead.equals("0")){//是否显示isNewTag
                holder_yds.newTaskTag.setVisibility(View.VISIBLE);
            }else{
                holder_yds.newTaskTag.setVisibility(View.GONE);
            }

            holder_yds.yds_head_change = (RelativeLayout)convertView.findViewById(R.id.yds_head_change);
            ImageView image_hurt = (ImageView)convertView.findViewById(R.id.yds_hurt_state_icon);
            long reparations_long=Long.valueOf(mData.get(position).getAsString(TaskDS.reparations));
            //Log.i("dm","reparations_long"+reparations_long);
            if(reparations_long>10000){
                holder_yds.yds_head_change.setBackground(mContext.getResources().getDrawable(R.drawable.grayhead_background));
            }else {
                long offSet = Long.valueOf(mData.get(position).getAsString(TaskDS.finishtime));
                long today = System.currentTimeMillis() / 1000;
                long intervalTime = (today - offSet) / 3600;

                if (intervalTime < 2) {
                    holder_yds.yds_head_change.setBackground(mContext.getResources().getDrawable(R.drawable.grayhead_background));
                } else if (intervalTime < 4) {
                    holder_yds.yds_head_change.setBackground(mContext.getResources().getDrawable(R.drawable.bluehead_background));
                } else if (intervalTime < 6) {
                    holder_yds.yds_head_change.setBackground(mContext.getResources().getDrawable(R.drawable.yellowhead_background));
                } else {
                    holder_yds.yds_head_change.setBackground(mContext.getResources().getDrawable(R.drawable.redhead_background));
                }
            }
            holder_yds.caseNo.setText(mData.get(position).getAsString(TaskDS.caseNo));
            holder_yds.licenseno.setText(mData.get(position).getAsString(TaskDS.licenseno));
            Long Ctime_long=Long.valueOf(mData.get(position).getAsString(TaskDS.caseTime))*1000;
            String Ctime_str=String.valueOf(Ctime_long);
            String caseTime_str= TimeUtil.stampToDate(Ctime_str);
            holder_yds.caseTime.setText(caseTime_str);

            holder_yds.yds_reporter1.setText(mData.get(position).getAsString(TaskDS.assessor_name));
            holder_yds.yds_assess_amount.setText(mData.get(position).getAsString(TaskDS.assess_amount)+"元");


          /*  String hurtstate_str="--";
            Long hurtstate_long=Long.valueOf(mData.get(position).getAsString(TaskCK.riskstate));
            if(hurtstate_long==0){
                hurtstate_str="";
            }else if(hurtstate_long==1){
                image_hurt.setImageDrawable(mContext.getResources().getDrawable(R.drawable.red_medical));
                image_hurt.setVisibility(View.VISIBLE);
                hurtstate_str="未完成";
                holder_yds.hurt_state.setVisibility(View.VISIBLE);
            }else if(hurtstate_long==2){
                image_hurt.setImageDrawable(mContext.getResources().getDrawable(R.drawable.green_medical));
                image_hurt.setVisibility(View.VISIBLE);
                hurtstate_str="已完成";
                holder_yds.hurt_state.setTextColor(mContext.getResources().getColor(R.color.typegreen));
                holder_yds.hurt_state.setVisibility(View.VISIBLE);
            }
            holder_yds.hurt_state.setText(hurtstate_str);*/

            String risklevel_str="--";
            Long risklevel_long=Long.valueOf(mData.get(position).getAsString(TaskDS.riskstate));
            if(risklevel_long==0){
                risklevel_str="无风险";
            }else if(risklevel_long==1){
                risklevel_str="风险案件";
            }
            holder_yds.risklevel.setText(risklevel_str);
            String riskstate_str="--";
            Long riskstate_long=Long.valueOf(mData.get(position).getAsString(TaskDS.riskstate));
            if(riskstate_long==0){
                riskstate_str="未上报";
            }else if(riskstate_long==1){
                riskstate_str="已上报";
            }
            holder_yds.riskstate.setText(riskstate_str);

            holder_yds.item_yds.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClicklistener.onClick(position);
                }
            });

            convertView.setTag(holder_yds);
//        }else{
//            holder_yds= (ViewHolder_yds) convertView.getTag();
//        }

        return convertView;
    }

    public class ViewHolder_yds{
        TextView caseTime,risklevel,riskstate,caseNo,licenseno,yds_reporter1,yds_assess_amount;
                //hurt_state;
        LinearLayout item_yds;
        RelativeLayout yds_head_change;
        ImageView newTaskTag;
    }
}
