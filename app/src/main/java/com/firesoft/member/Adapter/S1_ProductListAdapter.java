package com.firesoft.member.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.BeeFramework.adapter.BeeBaseAdapter;
import com.firesoft.member.Protocol.SIMPLE_PRODUCT;
import com.firesoft.member.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/10/30.
 */
public class S1_ProductListAdapter extends BeeBaseAdapter {
    public S1_ProductListAdapter(Context c, ArrayList dataList)
    {
        super(c, dataList);
    }

    public class C0_ProductCellHolder extends BeeCellHolder
    {
        TextView c0_mcard_xm;
        TextView    c0_mcard_kh;
        TextView 	c0_mcard_phone;


    }

    @Override
    protected BeeCellHolder createCellHolder(View cellView)
    {
        C0_ProductCellHolder holder = new C0_ProductCellHolder();
        holder.c0_mcard_kh  = (TextView)cellView.findViewById(R.id.c0_mcard_kh);
        holder.c0_mcard_xm  = (TextView)cellView.findViewById(R.id.c0_mcard_xm);
        holder.c0_mcard_phone  	 = (TextView) cellView.findViewById(R.id.c0_mcard_phone);

        return holder;
    }

    @Override
    protected View bindData(int position, View cellView, ViewGroup parent, BeeCellHolder h)
    {
        SIMPLE_PRODUCT member = (SIMPLE_PRODUCT)dataList.get(position);
        C0_ProductCellHolder holder = (C0_ProductCellHolder)h;
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
