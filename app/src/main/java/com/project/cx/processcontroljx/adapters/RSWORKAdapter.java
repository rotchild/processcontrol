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
import com.project.cx.processcontroljx.beans.TaskCK;
import com.project.cx.processcontroljx.beans.Taskhurt;
import com.project.cx.processcontroljx.utils.TimeUtil;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/11/29 0029.
 */

public class RSWORKAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<ContentValues> mData;
    private LayoutInflater mInflater;
    private int mResourceID=-1;
    ViewHolder_rswork holder_rswork=null;

    public interface MOnItemClickListener{
        public void onClick(int i);
    }

    private MOnItemClickListener mOnItemClicklistener;

    public RSWORKAdapter(Context mContext, ArrayList<ContentValues> mData){
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
            convertView=mInflater.inflate(R.layout.item_rswork,null);
            holder_rswork=new ViewHolder_rswork();
            holder_rswork.item_rslb= (LinearLayout) convertView.findViewById(R.id.item_rslb);
            holder_rswork.caseNo=(TextView)convertView.findViewById(R.id.rslb_caseNo);
            holder_rswork.licenseno= (TextView) convertView.findViewById(R.id.rslb_licenseno);
            holder_rswork.caseTime=(TextView)convertView.findViewById(R.id.rslb_caseTime);
            holder_rswork.contactName=(TextView)convertView.findViewById(R.id.rslb_contactName);
            holder_rswork.contactPhone=(TextView)convertView.findViewById(R.id.rslb_contactPhone);
            RelativeLayout head= (RelativeLayout)convertView.findViewById(R.id.rslb_head_change);
            holder_rswork.item_rslb.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClicklistener.onClick(position);
                }
            });
            holder_rswork.caseNo.setText(mData.get(position).getAsString(Taskhurt.caseNo));
            holder_rswork.licenseno.setText(mData.get(position).getAsString(Taskhurt.licenseno));
            holder_rswork.newTaskTag=(ImageView)convertView.findViewById(R.id.rswork_new_tag);
            String isRead=mData.get(position).getAsString(Taskhurt.isRead);
            if(isRead.equals("0")){//是否显示isNewTag
                holder_rswork.newTaskTag.setVisibility(View.VISIBLE);
            }else{
                holder_rswork.newTaskTag.setVisibility(View.GONE);
            }
            //Log.i("dm","reparations_long"+reparations_long);

                Log.i("TaskCK.licenseno", "TaskCK.licenseno:" + mData.get(position).getAsString(Taskhurt.licenseno));
                long offSet = Long.valueOf(mData.get(position).getAsString(TaskCK.caseTime));
//                long today = System.currentTimeMillis() / 1000;
//                long intervalTime = (today - offSet) / 3600;
//                if (intervalTime < 24) {
//                    head.setBackground(mContext.getResources().getDrawable(R.drawable.grayhead_background));
//                } else if (intervalTime < 48) {
//                    head.setBackground(mContext.getResources().getDrawable(R.drawable.bluehead_background));
//                } else if (intervalTime < 72) {
//                    head.setBackground(mContext.getResources().getDrawable(R.drawable.yellowhead_background));
//                } else {
//                    head.setBackground(mContext.getResources().getDrawable(R.drawable.redhead_background));
//                }


            Long Ctime_long=Long.valueOf(mData.get(position).getAsString(Taskhurt.caseTime))*1000;
            String Ctime_str=String.valueOf(Ctime_long);
            String caseTime_str= TimeUtil.stampToDate(Ctime_str);
            holder_rswork.caseTime.setText(caseTime_str);


            holder_rswork.contactName.setText(mData.get(position).getAsString(Taskhurt.contactName));
            holder_rswork.contactPhone.setText(mData.get(position).getAsString(Taskhurt.contactPhone));
            convertView.setTag(holder_rswork);
//        }else{
//            holder_rswork= (ViewHolder_rswork) convertView.getTag();
//        }

        return convertView;
    }

    public class ViewHolder_rswork{
        TextView caseNo,licenseno,caseTime,contactName,contactPhone;
        LinearLayout item_rslb;
        ImageView newTaskTag;
    }
}
