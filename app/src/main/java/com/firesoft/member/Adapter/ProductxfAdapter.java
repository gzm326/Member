package com.firesoft.member.Adapter;

/**
 * Created by Administrator on 2015/9/16.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.BeeFramework.adapter.BeeBaseAdapter;
import com.firesoft.member.Model.Product;
import com.firesoft.member.Protocol.SIMPLE_NUMBER;
import com.firesoft.member.R;
import com.firesoft.member.View.AddDelView;


import java.util.ArrayList;
import java.util.HashMap;

public class ProductxfAdapter extends BeeBaseAdapter{
    private Context mContext;
    private HashMap<String,SIMPLE_NUMBER> hm_product =new HashMap<String,SIMPLE_NUMBER>();
    private HashMap<String,SIMPLE_NUMBER> hm_check =new HashMap<String,SIMPLE_NUMBER>();
    private int num=1;
    public ProductxfAdapter(Context c, ArrayList dataList) {
        super(c, dataList);
        mContext = c;
    }

    public class C0_ProductCellHolder extends BeeCellHolder
    {
        TextView productid;
        TextView productname;
        TextView productnum;
        TextView num;
        AddDelView addview;
        ImageView minusImageView;
        ImageView addImageView;
        CheckBox checkBox;
    }

    public HashMap<String,SIMPLE_NUMBER> getHm(){
        return  hm_product;
    }

    public HashMap<String,SIMPLE_NUMBER> getCheckedHm(){
        return  hm_check;
    }

    @Override
    protected BeeCellHolder createCellHolder(View cellView) {
        C0_ProductCellHolder holder = new C0_ProductCellHolder();
        holder.productid  = (TextView)cellView.findViewById(R.id.txv_id);
        holder.productname  = (TextView)cellView.findViewById(R.id.txv_name);
        holder.productnum  	 = (TextView) cellView.findViewById(R.id.txv_times);
        holder.checkBox   = (CheckBox) cellView.findViewById(R.id.select_checkbox);
        holder.addview=(AddDelView)cellView.findViewById(R.id.addview);
        holder.num=(TextView)holder.addview.findViewById(R.id.shop_car_item_editNum);
        holder.minusImageView = (ImageView)holder.addview.findViewById(R.id.shop_car_item_min);
        holder.addImageView = (ImageView)holder.addview.findViewById(R.id.shop_car_item_sum);

        return holder;
    }

    @Override
    protected View bindData(int position, View cellView, ViewGroup parent, BeeCellHolder h) {
        final SIMPLE_NUMBER number= (SIMPLE_NUMBER)dataList.get(position);
        final C0_ProductCellHolder holder=(C0_ProductCellHolder)h;
        holder.productid.setText(number.product_id);
        holder.productname.setText(number.product_name);
        holder.productnum.setText(number.num+"/"+number.donum);
        holder.addview.maxNum=Integer.parseInt(number.num)-Integer.parseInt(number.donum);
        holder.num.setText(Integer.toString(number.lnum));

        holder.minusImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (number.lnum - 1 > 0) {
                    number.lnum--;
                    if (hm_product.containsKey(number.product_id)) {
                        hm_product.remove(number.product_id);
                    }
                    hm_product.put(number.product_id, number);
                    holder.num.setText(String.valueOf(number.lnum));
                }
            }
        });

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.checkBox.isChecked()){
                    hm_check.put(number.product_id,number);
                }
                else{
                    if(hm_check.containsKey(number.product_id)){
                        //holder.productnum.setText("1");
                        hm_check.remove(number.product_id);
                    }
                }
            }
        });

        holder.addImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (number.lnum < Integer.parseInt(number.num) - Integer.parseInt(number.donum)) {
                    number.lnum++;
                    if(hm_product.containsKey(number.product_id)){
                        hm_product.remove(number.product_id);
                    }
                    hm_product.put(number.product_id,number);
                    holder.num.setText(String.valueOf(number.lnum));
                }
            }
        });
        return null;
    }

    @Override
    public View createCellView() {
        return LayoutInflater.from(mContext).inflate(R.layout.c0_product_cell, null);
    }
}
