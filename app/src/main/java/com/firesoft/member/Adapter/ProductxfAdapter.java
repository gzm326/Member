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
import com.firesoft.member.R;
import com.firesoft.member.View.AddDelView;


import java.util.ArrayList;
import java.util.HashMap;

public class ProductxfAdapter extends BeeBaseAdapter{
    private Context mContext;
    private HashMap<String,Product> hm_product =new HashMap<String,Product>();
    private HashMap<String,Product> hm_check =new HashMap<String,Product>();
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

    public HashMap<String,Product> getHm(){
        return  hm_product;
    }

    public HashMap<String,Product> getCheckedHm(){
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
        final Product product= (Product)dataList.get(position);
        final C0_ProductCellHolder holder=(C0_ProductCellHolder)h;
        holder.productid.setText(product.str_productid);
        holder.productname.setText(product.str_productname);
        holder.productnum.setText(Integer.toString(product.it_maxnum)+"/"+Integer.toString(product.it_num));
        holder.addview.maxNum=product.it_maxnum-product.it_num;
        holder.num.setText(Integer.toString(product.num));

        holder.minusImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (product.num - 1 > 0) {
                    product.num--;
                    if (hm_product.containsKey(product.str_productid)) {
                        hm_product.remove(product.str_productid);
                    }
                    hm_product.put(product.str_productid, product);
                    holder.num.setText(String.valueOf(product.num));
                }
            }
        });

        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.checkBox.isChecked()){
                    hm_check.put(product.str_productid,product);
                }
                else{
                    if(hm_check.containsKey(product.str_productid)){
                        //holder.productnum.setText("1");
                        hm_check.remove(product.str_productid);
                    }
                }
            }
        });

        holder.addImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (product.num < product.it_maxnum-product.it_num) {
                    product.num++;
                    if(hm_product.containsKey(product.str_productid)){
                        hm_product.remove(product.str_productid);
                    }
                    hm_product.put(product.str_productid,product);
                    holder.num.setText(String.valueOf(product.num));
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
