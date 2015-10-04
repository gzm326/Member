package com.firesoft.member.Adapter;
//
//                       __
//                      /\ \   _
//    ____    ____   ___\ \ \_/ \           _____    ___     ___
//   / _  \  / __ \ / __ \ \    <     __   /\__  \  / __ \  / __ \
//  /\ \_\ \/\  __//\  __/\ \ \\ \   /\_\  \/_/  / /\ \_\ \/\ \_\ \
//  \ \____ \ \____\ \____\\ \_\\_\  \/_/   /\____\\ \____/\ \____/
//   \/____\ \/____/\/____/ \/_//_/         \/____/ \/___/  \/___/
//     /\____/
//     \/___/
//
//  Powered by BeeFramework
//

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.BeeFramework.adapter.BeeBaseAdapter;
import com.firesoft.member.Protocol.SIMPLE_CHOOSE;
import com.firesoft.member.R;
import java.util.ArrayList;


public class F3_RegionPickAdapter extends BeeBaseAdapter {

	public F3_RegionPickAdapter(Context c, ArrayList dataList) {
		super(c, dataList);
	}

	public class C0_ChooseCellHolder extends BeeCellHolder
	{
		TextView c0_name;
		TextView    c0_id;
	}

	@Override
	protected BeeCellHolder createCellHolder(View cellView)
	{
		C0_ChooseCellHolder holder = new C0_ChooseCellHolder();
		holder.c0_id  = (TextView)cellView.findViewById(R.id.choose_item_id);
		holder.c0_name  = (TextView)cellView.findViewById(R.id.choose_item_name);
		return holder;
	}

	@Override
	protected View bindData(int position, View cellView, ViewGroup parent, BeeCellHolder h)
	{
		SIMPLE_CHOOSE choose = (SIMPLE_CHOOSE)dataList.get(position);
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
