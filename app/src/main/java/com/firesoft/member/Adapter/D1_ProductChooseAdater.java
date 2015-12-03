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
 * Created by Administrator on 2015/12/2.
 */
public class D1_ProductChooseAdater extends BeeBaseAdapter{
    public D1_ProductChooseAdater(Context c, ArrayList dataList) {
        super(c, dataList);
    }

    public class C0_ChooseCellHolder extends BeeBaseAdapter.BeeCellHolder
    {
        TextView c0_name;
        TextView    c0_id;
    }

    @Override
    protected BeeBaseAdapter.BeeCellHolder createCellHolder(View cellView)
    {
        C0_ChooseCellHolder holder = new C0_ChooseCellHolder();
        holder.c0_id  = (TextView)cellView.findViewById(R.id.choose_item_id);
        holder.c0_name  = (TextView)cellView.findViewById(R.id.choose_item_name);
        return holder;
    }

    @Override
    protected View bindData(int position, View cellView, ViewGroup parent, BeeBaseAdapter.BeeCellHolder h)
    {
        SIMPLE_PRODUCT choose = (SIMPLE_PRODUCT)dataList.get(position);
        C0_ChooseCellHolder holder = (C0_ChooseCellHolder)h;
        holder.c0_name.setText(choose.name);
        holder.c0_id.setText(Integer.toString(choose.id));
        return null;
    }

    @Override
    public View createCellView()
    {
        return mInflater.inflate(R.layout.f3_region_pick_cell,null);
    }

}
