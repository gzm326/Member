package com.firesoft.member.Adapter;

/**
 * Created by Administrator on 2015/9/16.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.BeeFramework.adapter.BeeBaseAdapter;
import com.firesoft.member.Model.Product;
import com.firesoft.member.R;
import com.firesoft.member.View.AddDelView;


import java.util.ArrayList;

public class ProductxfAdapter extends BeeBaseAdapter{
    private Context mContext;
    public ProductxfAdapter(Context c, ArrayList dataList) {
        super(c, dataList);
        mContext = c;
    }

    public class C0_ProductCellHolder extends BeeCellHolder
    {
        TextView productid;
        TextView productname;
        TextView productnum;
        AddDelView addview;
    }

    @Override
    protected BeeCellHolder createCellHolder(View cellView) {
        C0_ProductCellHolder holder = new C0_ProductCellHolder();
        holder.productid  = (TextView)cellView.findViewById(R.id.txv_id);
        holder.productname  = (TextView)cellView.findViewById(R.id.txv_name);
        holder.productnum  	 = (TextView) cellView.findViewById(R.id.txv_times);
        holder.addview=(AddDelView)cellView.findViewById(R.id.addview);

        return holder;
    }

    @Override
    protected View bindData(int position, View cellView, ViewGroup parent, BeeCellHolder h) {
        Product product= (Product)dataList.get(position);
        C0_ProductCellHolder holder=(C0_ProductCellHolder)h;
        holder.productid.setText(product.str_productid);
        holder.productname.setText(product.str_productname);
        holder.productnum.setText(Integer.toString(product.it_maxnum)+"/"+Integer.toString(product.it_num));
        holder.addview.maxNum=product.it_maxnum-product.it_num;
        return null;
    }

    @Override
    public View createCellView() {
        return LayoutInflater.from(mContext).inflate(R.layout.c0_product_cell, null);
    }
}
