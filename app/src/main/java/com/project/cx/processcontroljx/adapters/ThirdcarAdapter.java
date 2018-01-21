package com.project.cx.processcontroljx.adapters;

import android.content.ContentValues;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.project.cx.processcontroljx.R;
import com.project.cx.processcontroljx.beans.TaskThirdcars;
import com.project.cx.processcontroljx.beans.ThirdCars;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/11/29 0029.
 */

public class ThirdcarAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<ContentValues> mData;
    private LayoutInflater mInflater;
    private int mResourceID=-1;
    public static String thirdcarlicneseno="";
    ViewHolder_thirdcars holder_thirdcars=null;

    public interface MOnItemClickListener{
        public void onClick(int i);
    }

    private MOnItemClickListener mOnItemyyClicklistener,mOnItemaddClicklistener,mOnItemdetailClicklistener;
    private MOnItemClickListener mOnItemokClicklistener,mOnItemcancelClicklistener;

    public ThirdcarAdapter(Context mContext, ArrayList<ContentValues> mData){
        super();
        this.mContext=mContext;
        this.mData=mData;
        this.mInflater=LayoutInflater.from(mContext);
    }

    public void setItemyyClickListener(MOnItemClickListener mOnItemClickListener){
        this.mOnItemyyClicklistener=mOnItemClickListener;
    }

    public void setItemdetailClickListener(MOnItemClickListener mOnItemClickListener){
        this.mOnItemdetailClicklistener=mOnItemClickListener;
    }

    public void setItemaddClickListener(MOnItemClickListener mOnItemClickListener){
        this.mOnItemaddClicklistener=mOnItemClickListener;
    }
    //确认添加
    public void setItemaokClickListener(MOnItemClickListener mOnItemClickListener){
        this.mOnItemokClicklistener=mOnItemClickListener;
    }
    //取消添加
    public void setItemcancelClickListener(MOnItemClickListener mOnItemClickListener){
        this.mOnItemcancelClicklistener=mOnItemClickListener;
    }
    //获取输入的车牌号
    public  String getThirdcarlicneseno(){
        return holder_thirdcars.item_thirdcars_edit.getText().toString().trim();
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
            convertView=mInflater.inflate(R.layout.item_thirdcars,null);
            holder_thirdcars=new ViewHolder_thirdcars();
            //holder_thirdcars.item_thirdcars= (LinearLayout) convertView.findViewById(R.id.item_fxsb);
            holder_thirdcars.thirdcarlicneseno=(TextView)convertView.findViewById(R.id.item_thirdcars_licenseno);
            holder_thirdcars.thirdcar_dsyy=(Button)convertView.findViewById(R.id.item_thirdcars_dsappointment);
            holder_thirdcars.item_thirdcars_detail=(Button) convertView.findViewById(R.id.item_thirdcars_detail);

            if(mData.get(position).getAsString(TaskThirdcars.isAppoint).equals("1")){//是否为预约
                holder_thirdcars.thirdcar_dsyy.setVisibility(View.GONE);
                holder_thirdcars.item_thirdcars_detail.setVisibility(View.VISIBLE);
            }else{
                holder_thirdcars.thirdcar_dsyy.setVisibility(View.VISIBLE);
                holder_thirdcars.item_thirdcars_detail.setVisibility(View.GONE);
            }


            holder_thirdcars.thirdcar_add=(Button) convertView.findViewById(R.id.item_thirdcars_add);

            holder_thirdcars.editLayout=(LinearLayout) convertView.findViewById(R.id.item_thirdcars_editlayout);

            holder_thirdcars.item_thirdcars_edit=(EditText) convertView.findViewById(R.id.item_thirdcars_edit);

            holder_thirdcars.item_thirdcars_edit.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    ThirdCars.thirdAddMap.put(position,s.toString());//存储每次输入的值
                }
            });

            holder_thirdcars.item_thirdcars_addok=(Button) convertView.findViewById(R.id.item_thirdcars_addok);

            holder_thirdcars.item_thirdcars_addcancel=(Button) convertView.findViewById(R.id.item_thirdcars_addcancel);

            holder_thirdcars.thirdcarlicneseno.setText(mData.get(position).getAsString(TaskThirdcars.thirdlicenseno));

            //定损预约
            holder_thirdcars.thirdcar_dsyy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("thirdcar","you click yy");
                    mOnItemyyClicklistener.onClick(position);
                }
            });
            //详情
            holder_thirdcars.item_thirdcars_detail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("thirdcar","you click");
                    mOnItemdetailClicklistener.onClick(position);
                }
            });

            //添加车牌
            holder_thirdcars.thirdcar_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {//显示添加页面
                    holder_thirdcars.editLayout.setVisibility(View.VISIBLE);
                    mOnItemaddClicklistener.onClick(position);
                }
            });

            holder_thirdcars.item_thirdcars_addok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    mOnItemokClicklistener.onClick(position);
                }
            });

            holder_thirdcars.item_thirdcars_addcancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder_thirdcars.editLayout.setVisibility(View.GONE);
                    mOnItemcancelClicklistener.onClick(position);
                }
            });

            convertView.setTag(holder_thirdcars);
//        }else{
//            holder_thirdcars= (ViewHolder_thirdcars) convertView.getTag();
//        }

        return convertView;
    }

    public class ViewHolder_thirdcars{
        TextView thirdcarlicneseno;
        Button thirdcar_dsyy,thirdcar_add;
        //LinearLayout item_thirdcars;
        LinearLayout editLayout;
        EditText item_thirdcars_edit;
        Button item_thirdcars_addok,item_thirdcars_addcancel;
        Button item_thirdcars_detail;
    }
}
