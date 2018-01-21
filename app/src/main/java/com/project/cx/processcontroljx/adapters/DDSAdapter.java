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

public class DDSAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<ContentValues> mData;
    private LayoutInflater mInflater;
    private int mResourceID=-1;
    ViewHolder_dck holder_dds=null;

    public interface MOnItemClickListener{
        public void onClick(int i);
    }

    private MOnItemClickListener mOnItemClicklistener;

    public DDSAdapter(Context mContext, ArrayList<ContentValues> mData){
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
            convertView=mInflater.inflate(R.layout.item_dds,null);
            holder_dds=new ViewHolder_dck();
            holder_dds.item_dds= (LinearLayout) convertView.findViewById(R.id.item_dds);
            holder_dds.caseNo=(TextView)convertView.findViewById(R.id.dds_caseNo);
            holder_dds.licenseno=(TextView)convertView.findViewById(R.id.dds_licenseno);
            holder_dds.caseTime=(TextView)convertView.findViewById(R.id.dds_caseTime);
            //holder_dds.outNumber=(TextView)convertView.findViewById(R.id.dds_outNumber);
            //holder_dds.hurt_state=(TextView)convertView.findViewById(R.id.dds_hurt_state);
            holder_dds.risklevel=(TextView)convertView.findViewById(R.id.dds_risklevel);
            holder_dds.riskstate=(TextView)convertView.findViewById(R.id.dds_riskstate);
            holder_dds.newTaskTag=(ImageView)convertView.findViewById(R.id.dds_new_tag);
            String isRead=mData.get(position).getAsString(TaskDS.isRead);
            if(isRead.equals("0")){//是否显示isNewTag
                holder_dds.newTaskTag.setVisibility(View.VISIBLE);
            }else{
                holder_dds.newTaskTag.setVisibility(View.GONE);
            }

            RelativeLayout head= (RelativeLayout)convertView.findViewById(R.id.dds_head_change);
            ImageView image_hurt = (ImageView)convertView.findViewById(R.id.dds_hurt_state_icon);
            long reparations_long=Long.valueOf(mData.get(position).getAsString(TaskDS.reparations));
            //Log.i("dm","reparations_long"+reparations_long);
            if(reparations_long>10000){
                head.setBackground(mContext.getResources().getDrawable(R.drawable.grayhead_background));
            }else {
                long offSet = Long.valueOf(mData.get(position).getAsString(TaskDS.lianTime));
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
            holder_dds.caseNo.setText(mData.get(position).getAsString(TaskDS.caseNo));
            holder_dds.licenseno.setText(mData.get(position).getAsString(TaskDS.licenseno));
            Long Ctime_long=Long.valueOf(mData.get(position).getAsString(TaskDS.caseTime))*1000;
            String Ctime_str=String.valueOf(Ctime_long);
            String caseTime_str= TimeUtil.stampToDate(Ctime_str);
            holder_dds.caseTime.setText(caseTime_str);
            //holder_dds.outNumber.setText(mData.get(position).getAsString(TaskCK.outNumber));

            String hurtstate_str="--";
            Long hurtstate_long=Long.valueOf(mData.get(position).getAsString(TaskDS.riskstate));
            if(hurtstate_long==0){
                hurtstate_str="";
            }else if(hurtstate_long==1){//dds无人伤
//                image_hurt.setImageDrawable(mContext.getResources().getDrawable(R.drawable.red_medical));
//                image_hurt.setVisibility(View.VISIBLE);
                hurtstate_str="未完成";
               // holder_dds.hurt_state.setVisibility(View.VISIBLE);
            }else if(hurtstate_long==2){
//                image_hurt.setImageDrawable(mContext.getResources().getDrawable(R.drawable.green_medical));
//                image_hurt.setVisibility(View.VISIBLE);
                hurtstate_str="已完成";
               // holder_dds.hurt_state.setTextColor(mContext.getResources().getColor(R.color.typegreen));
               // holder_dds.hurt_state.setVisibility(View.VISIBLE);
            }
           // holder_dds.hurt_state.setText(hurtstate_str);

            String risklevel_str="--";
            Long risklevel_long=Long.valueOf(mData.get(position).getAsString(TaskDS.riskstate));
            if(risklevel_long==0){
                risklevel_str="无风险";
                holder_dds.risklevel.setTextColor(mContext.getResources().getColor(R.color.typegreen));
            }else if(risklevel_long==1){
                risklevel_str="风险案件";
                holder_dds.risklevel.setTextColor(mContext.getResources().getColor(R.color.typered));
            }
            holder_dds.risklevel.setText(risklevel_str);
            String riskstate_str="--";
            Long riskstate_long=Long.valueOf(mData.get(position).getAsString(TaskDS.riskstate));
            if(riskstate_long==0){
                riskstate_str="未上报";
            }else if(riskstate_long==1){
                riskstate_str="已上报";
            }
            holder_dds.riskstate.setText(riskstate_str);

            holder_dds.item_dds.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClicklistener.onClick(position);
                }
            });
            convertView.setTag(holder_dds);
//        }else{
//            holder_dds= (ViewHolder_dck) convertView.getTag();
//        }

        return convertView;
    }

    public class ViewHolder_dck{
        TextView caseNo,licenseno, caseTime,risklevel,riskstate;
        //hurt_state,outNumber;
        LinearLayout item_dds;
        ImageView newTaskTag;
    }
}
