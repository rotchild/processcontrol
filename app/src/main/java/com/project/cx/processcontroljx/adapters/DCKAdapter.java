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
import com.project.cx.processcontroljx.utils.TimeUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/29 0029.
 */

public class DCKAdapter extends BaseAdapter {
    private Context mContext;
    private List<ContentValues> mData;
    private LayoutInflater mInflater;
    private int mResourceID=-1;
    ViewHolder_dck holder_dck=null;

  /*  public DCKAdapter(@NonNull Context context,@NonNull List<ContentValues> objects) {
        super(context, 0, objects);
        this.mContext=context;
        this.mData=objects;
        this.mInflater=LayoutInflater.from(mContext);
    }*/


    public interface MOnItemClickListener{
        public void onClick(int i);
    }

    private MOnItemClickListener mOnItemClicklistener;


    public DCKAdapter(Context mContext, ArrayList<ContentValues> mData){
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
    public ContentValues getItem(int position) {
        return  mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Log.i("dckadapterText","size:"+mData.size());
//        if(convertView==null){
            convertView=mInflater.inflate(R.layout.item_dck,null);
            holder_dck=new ViewHolder_dck();
            holder_dck.item_ck= (LinearLayout) convertView.findViewById(R.id.item_dck);
            holder_dck.caseNo=(TextView)convertView.findViewById(R.id.dck_caseNo);
            holder_dck.licenseno=(TextView)convertView.findViewById(R.id.dck_licenseno);
            holder_dck.caseTime=(TextView)convertView.findViewById(R.id.dck_caseTime);
            //holder_dck.caseState= (TextView) convertView.findViewById(R.id.caseState);
            holder_dck.outNumber=(TextView)convertView.findViewById(R.id.dck_outNumber);
            holder_dck.risklevel=(TextView)convertView.findViewById(R.id.dck_risklevel);
            holder_dck.riskstate=(TextView)convertView.findViewById(R.id.dck_riskstate);
            holder_dck.hurt_state=(TextView)convertView.findViewById(R.id.dck_hurt_state);
            holder_dck.newTaskTag=(ImageView)convertView.findViewById(R.id.dck_new_tag);
            String isRead=mData.get(position).getAsString(TaskCK.isRead);
            if(isRead.equals("0")){//是否显示isNewTag
                holder_dck.newTaskTag.setVisibility(View.VISIBLE);
            }else{
                holder_dck.newTaskTag.setVisibility(View.GONE);
            }

            RelativeLayout head= (RelativeLayout)convertView.findViewById(R.id.dck_head_change);
            ImageView image_hurt = (ImageView)convertView.findViewById(R.id.dck_hurt_state_icon);
            holder_dck.item_ck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClicklistener.onClick(position);
                }
            });
            long reparations_long=Long.valueOf(mData.get(position).getAsString(TaskCK.reparations));
            //Log.i("dm","reparations_long"+reparations_long);
//            if(reparations_long>10000){
//                head.setBackground(mContext.getResources().getDrawable(R.drawable.grayhead_background));
//            }else{
//                long offSet = Long.valueOf(mData.get(position).getAsString(TaskCK.lianTime));
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
//            }
            holder_dck.caseNo.setText(mData.get(position).getAsString(TaskCK.caseNo));
            Log.i("dckadapterText","position:"+position+"caseNo:"+mData.get(position).getAsString(TaskCK.caseNo));
            holder_dck.licenseno.setText(mData.get(position).getAsString(TaskCK.licenseno));

            Long Ctime_long=Long.valueOf(mData.get(position).getAsString(TaskCK.caseTime))*1000;
            String Ctime_str=String.valueOf(Ctime_long);
            String caseTime_str=TimeUtil.stampToDate(Ctime_str);
            holder_dck.caseTime.setText(caseTime_str);
            //holder_dck.caseState.setText(mData.get(position).getAsString(TaskCK.case_state));


            holder_dck.outNumber.setText(mData.get(position).getAsString(TaskCK.outNumber)+"次");

            String hurtstate_str="--";
            Long hurtstate_long=Long.valueOf(mData.get(position).getAsString(TaskCK.hurt_state));
            if(hurtstate_long==0){
                hurtstate_str="";
            }else if(hurtstate_long==1){
                image_hurt.setImageDrawable(mContext.getResources().getDrawable(R.drawable.red_medical));
                image_hurt.setVisibility(View.VISIBLE);
                hurtstate_str="未完成";
                holder_dck.hurt_state.setVisibility(View.VISIBLE);
            }else if(hurtstate_long==2){
                image_hurt.setImageDrawable(mContext.getResources().getDrawable(R.drawable.green_medical));
                image_hurt.setVisibility(View.VISIBLE);
                hurtstate_str="已完成";
                holder_dck.hurt_state.setTextColor(mContext.getResources().getColor(R.color.typegreen));
                holder_dck.hurt_state.setVisibility(View.VISIBLE);
            }
            holder_dck.hurt_state.setText(hurtstate_str);

            String risklevel_str="--";
            Long risklevel_long=Long.valueOf(mData.get(position).getAsString(TaskCK.riskstate));
            if(risklevel_long==0){
                risklevel_str="无风险";
                holder_dck.risklevel.setTextColor(mContext.getResources().getColor(R.color.typegreen));
            }else if(risklevel_long==1){
                risklevel_str="风险案件";
                holder_dck.risklevel.setTextColor(mContext.getResources().getColor(R.color.typered));
            }
            holder_dck.risklevel.setText(risklevel_str);
            String riskstate_str="--";
            Long riskstate_long=Long.valueOf(mData.get(position).getAsString(TaskCK.riskstate));
            if(riskstate_long==0){
                riskstate_str="未上报";
            }else if(riskstate_long==1){
                riskstate_str="已上报";
            }
            holder_dck.riskstate.setText(riskstate_str);

            convertView.setTag(holder_dck);
//        }else{
//            holder_dck= (ViewHolder_dck) convertView.getTag();
//        }

        return convertView;
    }

    public class ViewHolder_dck{
        TextView caseNo,licenseno,caseTime,caseState,outNumber,risklevel,riskstate,hurt_state;
        LinearLayout item_ck;
        ImageView newTaskTag;
    }
}
