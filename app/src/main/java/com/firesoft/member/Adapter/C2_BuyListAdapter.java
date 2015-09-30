package com.firesoft.member.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.BeeFramework.adapter.BeeBaseAdapter;
import com.firesoft.member.Protocol.SIMPLE_MEMBER;
import com.firesoft.member.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2015/9/29.
 */
public class C2_BuyListAdapter extends BeeBaseAdapter {
    public C2_BuyListAdapter(Context c, ArrayList dataList)
    {
        super(c, dataList);
    }

    public class C0_MemberCellHolder extends BeeCellHolder
    {
        TextView c0_mcard_xm;
        TextView    c0_mcard_kh;
        TextView 	c0_mcard_phone;


    }

    @Override
    protected BeeCellHolder createCellHolder(View cellView)
    {
        C0_MemberCellHolder holder = new C0_MemberCellHolder();
        holder.c0_mcard_kh  = (TextView)cellView.findViewById(R.id.c0_mcard_kh);
        holder.c0_mcard_xm  = (TextView)cellView.findViewById(R.id.c0_mcard_xm);
        holder.c0_mcard_phone  	 = (TextView) cellView.findViewById(R.id.c0_mcard_phone);

        return holder;
    }

    @Override
    protected View bindData(int position, View cellView, ViewGroup parent, BeeCellHolder h)
    {
        SIMPLE_MEMBER member = (SIMPLE_MEMBER)dataList.get(position);
        C0_MemberCellHolder holder = (C0_MemberCellHolder)h;
        holder.c0_mcard_xm.setText(member.member_name);
        holder.c0_mcard_kh.setText(member.member_no);
        holder.c0_mcard_phone.setText(member.mobile_no);

        return null;
    }

    @Override
    public View createCellView()
    {
        return mInflater.inflate(R.layout.c0_user_cell,null);
    }
}
