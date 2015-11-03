package com.firesoft.member.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.BeeFramework.adapter.BeeBaseAdapter;
import com.firesoft.member.Protocol.SIMPLE_PRODUCTTYPE;
import com.firesoft.member.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/10/30.
 */
public class S0_ProductTypeListAdapter extends BeeBaseAdapter {



    public S0_ProductTypeListAdapter(Context c, ArrayList dataList)
    {
        super(c, dataList);
    }

    public class C0_ProductTypeCellHolder extends BeeCellHolder
    {
        TextView c0_mcard_xm;
        TextView    c0_mcard_kh;
        TextView 	c0_mcard_phone;


    }

    @Override
    protected BeeCellHolder createCellHolder(View cellView)
    {
        C0_ProductTypeCellHolder holder = new C0_ProductTypeCellHolder();
        holder.c0_mcard_kh  = (TextView)cellView.findViewById(R.id.c0_mcard_kh);
        holder.c0_mcard_xm  = (TextView)cellView.findViewById(R.id.c0_mcard_xm);
        holder.c0_mcard_phone  	 = (TextView) cellView.findViewById(R.id.c0_mcard_phone);

        return holder;
    }

    @Override
    protected View bindData(int position, View cellView, ViewGroup parent, BeeCellHolder h)
    {
        SIMPLE_PRODUCTTYPE member = (SIMPLE_PRODUCTTYPE)dataList.get(position);
        C0_ProductTypeCellHolder holder = (C0_ProductTypeCellHolder)h;

        holder.c0_mcard_kh.setText(Integer.toString(member.id));
        holder.c0_mcard_xm.setText(member.name);
        holder.c0_mcard_phone.setText("");

        return null;
    }

    @Override
    public View createCellView()
    {
        return mInflater.inflate(R.layout.c0_user_cell,null);
    }
}
