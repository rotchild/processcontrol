package com.project.cx.processcontroljx.adapters;

import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/11/29 0029.
 */

public class ALLAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<ContentValues> mData;
    private LayoutInflater mInflater;
    private int mResourceID=-1;
    ViewHolder_dck holder_dck=null;

    public interface MOnItemClickListener{
        public void onClick(int i);
    }

    private MOnItemClickListener mOnItemClicklistener;

    public ALLAdapter(Context mContext, ArrayList<ContentValues> mData){
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
        if(convertView==null){

/*            convertView=mInflater.inflate(R.layout.item_dck,null);
            holder_dck=new ViewHolder_dck();
            holder_dck.item_ck= (LinearLayout) convertView.findViewById(R.id.item_ck);
            holder_dck.caseTime=(TextView)convertView.findViewById(R.id.caseTime);
            //holder_dck.caseState= (TextView) convertView.findViewById(R.id.caseState);
            holder_dck.outNumber=(TextView)convertView.findViewById(R.id.outNumber);
            holder_dck.risklevel=(TextView)convertView.findViewById(R.id.risklevel);
            holder_dck.riskstate=(TextView)convertView.findViewById(R.id.riskstate);

            holder_dck.item_ck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClicklistener.onClick(position);
                }
            });
            holder_dck.caseTime.setText(mData.get(position).getAsString(TaskCK.caseTime));
            holder_dck.caseState.setText(mData.get(position).getAsString(TaskCK.case_state));
            holder_dck.outNumber.setText(mData.get(position).getAsString(TaskCK.outNumber));
            holder_dck.risklevel.setText(mData.get(position).getAsString(TaskCK.risklevel));
            holder_dck.riskstate.setText(mData.get(position).getAsString(TaskCK.riskstate));
            convertView.setTag(holder_dck);*/
        }else{
            holder_dck= (ViewHolder_dck) convertView.getTag();
        }

        return convertView;
    }

    public class ViewHolder_dck{
        TextView caseTime,caseState,outNumber,risklevel,riskstate;
        LinearLayout item_ck;
    }
}
