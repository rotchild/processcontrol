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
import com.project.cx.processcontroljx.beans.Taskhurt;
import com.project.cx.processcontroljx.utils.TimeUtil;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/11/29 0029.
 */

public class RSHISAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<ContentValues> mData;
    private LayoutInflater mInflater;
    private int mResourceID=-1;
    ViewHolder_rsls holder_rsls=null;

    public interface MOnItemClickListener{
        public void onClick(int i);
    }

    private MOnItemClickListener mOnItemClicklistener;

    public RSHISAdapter(Context mContext, ArrayList<ContentValues> mData){
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
            convertView=mInflater.inflate(R.layout.item_rshis,null);
            holder_rsls=new ViewHolder_rsls();
            holder_rsls.item_rsls= (LinearLayout) convertView.findViewById(R.id.item_rsls);
            holder_rsls.caseNo=(TextView)convertView.findViewById(R.id.lsgz_caseNo);
            holder_rsls.licenseno=(TextView)convertView.findViewById(R.id.lsgz_licenseno);
            holder_rsls.caseTime=(TextView)convertView.findViewById(R.id.lsgz_caseTime);
            holder_rsls.contactName=(TextView)convertView.findViewById(R.id.lsgz_contactName);
            holder_rsls.contactPhone=(TextView)convertView.findViewById(R.id.lsgz_contactPhone);
            RelativeLayout head= (RelativeLayout)convertView.findViewById(R.id.lsgz_head_change);
            holder_rsls.item_rsls.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClicklistener.onClick(position);
                }
            });
            holder_rsls.caseNo.setText(mData.get(position).getAsString(Taskhurt.caseNo));
            holder_rsls.licenseno.setText(mData.get(position).getAsString(Taskhurt.licenseno));
            holder_rsls.newTaskTag=(ImageView)convertView.findViewById(R.id.rshis_new_tag);
            String isRead=mData.get(position).getAsString(Taskhurt.isRead);
            if(isRead.equals("0")){//是否显示isNewTag
                holder_rsls.newTaskTag.setVisibility(View.VISIBLE);
            }else{
                holder_rsls.newTaskTag.setVisibility(View.GONE);
            }

                long offSet = Long.valueOf(mData.get(position).getAsString(Taskhurt.caseTime));
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
            holder_rsls.caseTime.setText(caseTime_str);


            holder_rsls.contactName.setText(mData.get(position).getAsString(Taskhurt.contactName));
            holder_rsls.contactPhone.setText(mData.get(position).getAsString(Taskhurt.contactPhone));


            convertView.setTag(holder_rsls);
//        }else{
//            holder_rsls= (ViewHolder_rsls) convertView.getTag();
//        }

        return convertView;
    }

    public class ViewHolder_rsls{
        TextView caseNo,licenseno,caseTime,contactName,contactPhone;
        LinearLayout item_rsls;
        ImageView newTaskTag;
    }
}
