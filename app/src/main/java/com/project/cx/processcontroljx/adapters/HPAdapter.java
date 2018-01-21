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
import com.project.cx.processcontroljx.beans.TaskDS;
import com.project.cx.processcontroljx.utils.TimeUtil;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/11/29 0029.
 */

public class HPAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<ContentValues> mData;
    private LayoutInflater mInflater;
    private int mResourceID=-1;
    ViewHolder_hp holder_hp=null;
    public interface MOnItemClickListener{
        public void onClick(int i);
    }

    private MOnItemClickListener mOnItemClicklistener;

    public HPAdapter(Context mContext, ArrayList<ContentValues> mData){
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
            convertView=mInflater.inflate(R.layout.item_hp,null);
            holder_hp=new ViewHolder_hp();
            holder_hp.item_hp= (LinearLayout) convertView.findViewById(R.id.item_hp);
            holder_hp.caseNo=(TextView)convertView.findViewById(R.id.hp_caseNo);
            holder_hp.licenseno=(TextView)convertView.findViewById(R.id.hp_licenseno);
            holder_hp.caseTime=(TextView)convertView.findViewById(R.id.hp_caseTime);
            //holder_dsz.caseState= (TextView) convertView.findViewById(R.id.dsz_caseState);
            //holder_hp.hurt_state=(TextView)convertView.findViewById(R.id.hp_hurt_state);
            holder_hp.risklevel=(TextView)convertView.findViewById(R.id.hp_risklevel);
           // holder_hp.riskstate=(TextView)convertView.findViewById(R.id.hp_riskstate);
            holder_hp.hp_tickestate=(TextView)convertView.findViewById(R.id.hp_tickestate);

            holder_hp.newTaskTag=(ImageView)convertView.findViewById(R.id.hp_new_tag);
            String isRead=mData.get(position).getAsString(TaskDS.isRead);
            if(isRead.equals("0")){//是否显示isNewTag
                holder_hp.newTaskTag.setVisibility(View.VISIBLE);
            }else{
                holder_hp.newTaskTag.setVisibility(View.GONE);
            }

            holder_hp.hp_head_change = (RelativeLayout)convertView.findViewById(R.id.hp_head_change);
            ImageView image_hurt = (ImageView)convertView.findViewById(R.id.hp_hurt_state_icon);
            long reparations_long=Long.valueOf(mData.get(position).getAsString(TaskCK.reparations));
            //Log.i("dm","reparations_long"+reparations_long);
//            if(reparations_long>10000){
//                holder_hp.hp_head_change.setBackground(mContext.getResources().getDrawable(R.drawable.grayhead_background));
//            }else if(reparations_long==0){
//                holder_hp.hp_head_change.setBackground(mContext.getResources().getDrawable(R.drawable.grayhead_background));
//            }else {
//                long offSet = Long.valueOf(mData.get(position).getAsString(TaskCK.caseTime));
//                long today = System.currentTimeMillis() / 1000;
//                long intervalTime = (today - offSet) / 3600;
//                if (intervalTime < 24) {
//                    holder_hp.hp_head_change.setBackground(mContext.getResources().getDrawable(R.drawable.grayhead_background));
//                } else if (intervalTime < 48) {
//                    holder_hp.hp_head_change.setBackground(mContext.getResources().getDrawable(R.drawable.bluehead_background));
//                } else if (intervalTime < 72) {
//                    holder_hp.hp_head_change.setBackground(mContext.getResources().getDrawable(R.drawable.yellowhead_background));
//                } else {
//                    holder_hp.hp_head_change.setBackground(mContext.getResources().getDrawable(R.drawable.redhead_background));
//                }
//            }
            String hp_ticketstate=mData.get(position).getAsString(TaskDS.ticket_state);
            String hp_ticketstateStr="--";
            if(hp_ticketstate.equals("0")){

            }else if(hp_ticketstate.equals("-1")){
                hp_ticketstateStr="未获票";
            } if(hp_ticketstate.equals("1")){
                hp_ticketstateStr="已获票";
            }
            holder_hp.hp_tickestate.setText(hp_ticketstateStr);
            holder_hp.caseNo.setText(mData.get(position).getAsString(TaskDS.caseNo));
            holder_hp.licenseno.setText(mData.get(position).getAsString(TaskDS.licenseno));
            Long Ctime_long=Long.valueOf(mData.get(position).getAsString(TaskDS.caseTime))*1000;
            String Ctime_str=String.valueOf(Ctime_long);
            String caseTime_str= TimeUtil.stampToDate(Ctime_str);
            holder_hp.caseTime.setText(caseTime_str);


/*            String hurtstate_str="--";
            Long hurtstate_long=Long.valueOf(mData.get(position).getAsString(TaskCK.hurt_state));
            if(hurtstate_long==0){
                hurtstate_str="";
            }else if(hurtstate_long==1){
                image_hurt.setImageDrawable(mContext.getResources().getDrawable(R.drawable.red_medical));
                image_hurt.setVisibility(View.VISIBLE);
                hurtstate_str="未完成";
                holder_hp.hurt_state.setVisibility(View.VISIBLE);
            }else if(hurtstate_long==2){
                image_hurt.setImageDrawable(mContext.getResources().getDrawable(R.drawable.green_medical));
                image_hurt.setVisibility(View.VISIBLE);
                hurtstate_str="已完成";
                holder_hp.hurt_state.setTextColor(mContext.getResources().getColor(R.color.typegreen));
                holder_hp.hurt_state.setVisibility(View.VISIBLE);
            }
            holder_hp.hurt_state.setText(hurtstate_str);*/

            String risklevel_str="--";
            Long risklevel_long=Long.valueOf(mData.get(position).getAsString(TaskCK.riskstate));
            if(risklevel_long==0){
                risklevel_str="无风险";
            }else if(risklevel_long==1){
                risklevel_str="风险案件";
            }
            holder_hp.risklevel.setText(risklevel_str);
/*            String riskstate_str="--";
            Long riskstate_long=Long.valueOf(mData.get(position).getAsString(TaskCK.riskstate));
            if(riskstate_long==0){
                riskstate_str="无风险";
            }else if(riskstate_long==1){
                riskstate_str="风险案件";
            }
            holder_hp.riskstate.setText(riskstate_str);*/

            holder_hp.item_hp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClicklistener.onClick(position);
                }
            });

            convertView.setTag(holder_hp);
//        }else{
//            holder_hp= (ViewHolder_hp) convertView.getTag();
//        }

        return convertView;
    }

    public class ViewHolder_hp{
        TextView caseTime,caseState,risklevel,licenseno,caseNo,hp_tickestate;
        LinearLayout item_hp;
        RelativeLayout hp_head_change;
        ImageView newTaskTag;
    }
}
