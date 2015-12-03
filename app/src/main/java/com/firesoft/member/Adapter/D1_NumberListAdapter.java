package com.firesoft.member.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.BeeFramework.adapter.BeeBaseAdapter;
import com.firesoft.member.Protocol.SIMPLE_NUMBER;
import com.firesoft.member.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/12/3.
 */
public class D1_NumberListAdapter extends BeeBaseAdapter {
    public D1_NumberListAdapter(Context c, ArrayList dataList)
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
        SIMPLE_NUMBER member = (SIMPLE_NUMBER)dataList.get(position);
        C0_ProductCellHolder holder = (C0_ProductCellHolder)h;
        holder.c0_mcard_kh.setText(member.product_name);
        holder.c0_mcard_xm.setText(member.num);
        holder.c0_mcard_phone.setText(member.sale_price);

        return null;
    }

    @Override
    public View createCellView()
    {
        return mInflater.inflate(R.layout.d1_number_cell,null);
    }
}
