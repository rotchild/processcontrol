package com.project.cx.processcontroljx.adapters;

import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.cx.processcontroljx.R;
import com.project.cx.processcontroljx.beans.TaskRiskWarm;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/11/29 0029.
 */

public class RiskWarmAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<ContentValues> mData;
    private LayoutInflater mInflater;
    private int mResourceID=-1;
    ViewHolder_riskwarm holder_riskwarm=null;

    public interface MOnItemClickListener{
        public void onClick(int i);
    }

    private MOnItemClickListener mOnItemClicklistener,mOnItemDetailClicklistener;

    public RiskWarmAdapter(Context mContext, ArrayList<ContentValues> mData){
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
    //第一次是显示，第二次是隐藏
    public void setContentVisible(View view){
       ViewHolder_riskwarm riskwarm= (ViewHolder_riskwarm) view.getTag();

        if(riskwarm.riskwarm_content_detail.getVisibility()==View.VISIBLE){
            riskwarm.riskwarm_content_detail.setVisibility(View.GONE);
        }else{
            riskwarm.riskwarm_content_detail.setVisibility(View.VISIBLE);
        }
    }

    public void setContentGone(View view){
        ViewHolder_riskwarm riskwarm= (ViewHolder_riskwarm) view.getTag();
        if(riskwarm.riskwarm_content_detail.getVisibility()==View.VISIBLE){
            riskwarm.riskwarm_content_detail.setVisibility(View.GONE);
        }else{
            riskwarm.riskwarm_content_detail.setVisibility(View.VISIBLE);
        }

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
            convertView=mInflater.inflate(R.layout.item_riskwarm,null);
            holder_riskwarm=new ViewHolder_riskwarm();
            //标题
            holder_riskwarm.riskwarm_title= (RelativeLayout) convertView.findViewById(R.id.riskwarm_title);
            holder_riskwarm.riskwarm_riskname= (TextView) convertView.findViewById(R.id.riskwarm_riskname);
            holder_riskwarm.riskwarm_info=(RelativeLayout) convertView.findViewById(R.id.riskwarm_title_big);

            holder_riskwarm.riskwarm_riskname.setText(mData.get(position).getAsString(TaskRiskWarm.riskname));

            final View finalConvertView = convertView;
            holder_riskwarm.riskwarm_title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //mOnItemClicklistener.onClick(position);
                    setContentVisible(finalConvertView);
                }
            });
            holder_riskwarm.riskwarm_info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //mOnItemDetailClicklistener.onClick(position);
                    setContentVisible(finalConvertView);
                }
            });

            //内容
            holder_riskwarm.riskwarm_content= (RelativeLayout) convertView.findViewById(R.id.riskwarm_content);
            holder_riskwarm.riskwarm_content_detail= (TextView) convertView.findViewById(R.id.riskwarm_content_detail);

            holder_riskwarm.riskwarm_content_detail.setText(mData.get(position).getAsString(TaskRiskWarm.content));
            holder_riskwarm.riskwarm_content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setContentGone(finalConvertView);
                }
            });

            convertView.setTag(holder_riskwarm);
//        }else{
//            holder_riskwarm= (ViewHolder_riskwarm) convertView.getTag();
//        }

        return convertView;
    }

    public class ViewHolder_riskwarm{
        TextView riskwarm_riskname;
        RelativeLayout riskwarm_info;
        RelativeLayout riskwarm_title;
        RelativeLayout riskwarm_content;
        TextView riskwarm_content_detail;
    }
}
