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
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.BeeFramework.adapter.BeeBaseAdapter;
import com.firesoft.member.Model.Product;
import com.firesoft.member.Model.ProductDetail;
import com.firesoft.member.R;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class E4_HistoryAdapter extends BeeBaseAdapter {

	private Context mContext;
	public E4_HistoryAdapter(Context context,ArrayList dataList) {
		super(context, dataList);
		mContext=context;
	}

	class ViewHolder extends BeeCellHolder{
		private TextView sno;
		private TextView time;
		private LinearLayout body;
		private TextView fee;
		private TextView red_paper;
		private TextView score;
		private TextView total;
		private Button check;
		private Button ok;
	}

	@Override
	protected BeeCellHolder createCellHolder(View cellView)
	{
		ViewHolder holder = new ViewHolder();
		holder.sno = (TextView) cellView.findViewById(R.id.trade_item_sno);
		holder.time = (TextView) cellView.findViewById(R.id.trade_item_time);
		holder.body = (LinearLayout) cellView.findViewById(R.id.trade_item_body);
		holder.fee = (TextView) cellView.findViewById(R.id.trade_item_fee);
		holder.red_paper = (TextView) cellView.findViewById(R.id.trade_item_redPaper);
		holder.score = (TextView) cellView.findViewById(R.id.trade_item_score);
		holder.total = (TextView) cellView.findViewById(R.id.trade_item_total);


		return holder;
	}

	@Override
	public View bindData(int position, View convertView, ViewGroup parent,BeeCellHolder h) {
		//final Product product= (Product)dataList.get(position);
		final ViewHolder holder=(ViewHolder)h;
		//final ArrayList<ProductDetail> productDetails= product.productDetails;
		for(int i=0;i<3;i++) {
			View view = mInflater.inflate(R.layout.trade_body, null);
			TextView text = (TextView) view.findViewById(R.id.body_goods_name);
			TextView total = (TextView) view.findViewById(R.id.body_goods_total);
			TextView num = (TextView) view.findViewById(R.id.body_goods_num);
			holder.body.addView(view);

			/*text.setText(productDetails.get(i).name);
			total.setText(productDetails.get(i).total.toString());
			num.setText("X " + productDetails.get(i).num.toString());*/
			text.setText("面部护理");
			num.setText("*2");
			total.setText("10");

		}
		holder.sno.setText("201509220180");
		holder.time.setText("2015年9月22日 15:29:47");
		holder.fee.setText("10.00");
		holder.red_paper.setText("5.00");
		holder.score.setText("15");
		holder.total.setText("1000.00");


		
		return null;
	}

	@Override
	public View createCellView()
	{
		return mInflater.from(mContext).inflate(R.layout.e4_history_cell,null);
	}

}
