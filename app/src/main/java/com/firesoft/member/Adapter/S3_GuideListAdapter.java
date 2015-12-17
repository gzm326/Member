package com.firesoft.member.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.BeeFramework.adapter.BeeBaseAdapter;
import com.firesoft.member.Protocol.SIMPLE_GUIDE;
import com.firesoft.member.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/12/12.
 */
public class S3_GuideListAdapter extends BeeBaseAdapter {


    public S3_GuideListAdapter(Context c, ArrayList dataList)
    {
        super(c, dataList);
    }

    public class C0_GuideCellHolder extends BeeCellHolder
    {
        TextView c0_mcard_xm;
        TextView    c0_mcard_kh;
        TextView 	c0_mcard_phone;


    }

    @Override
    protected BeeCellHolder createCellHolder(View cellView)
    {
        C0_GuideCellHolder holder = new C0_GuideCellHolder();
        holder.c0_mcard_kh  = (TextView)cellView.findViewById(R.id.c0_mcard_kh);
        holder.c0_mcard_xm  = (TextView)cellView.findViewById(R.id.c0_mcard_xm);
        holder.c0_mcard_phone  	 = (TextView) cellView.findViewById(R.id.c0_mcard_phone);

        return holder;
    }

    @Override
    protected View bindData(int position, View cellView, ViewGroup parent, BeeCellHolder h)
    {
        SIMPLE_GUIDE obj = (SIMPLE_GUIDE)dataList.get(position);
        C0_GuideCellHolder holder = (C0_GuideCellHolder)h;

        holder.c0_mcard_kh.setText(Integer.toString(obj.id));
        holder.c0_mcard_xm.setText(obj.name);
        holder.c0_mcard_phone.setText(obj.phone);

        return null;
    }

    @Override
    public View createCellView()
    {
        return mInflater.inflate(R.layout.c0_user_cell,null);
    }
}
