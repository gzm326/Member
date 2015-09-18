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
import android.os.DropBoxManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.BeeFramework.adapter.BeeBaseAdapter;
import com.firesoft.member.Model.Product;
import com.firesoft.member.R;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SpecificationAdapter extends BeeBaseAdapter
{
    private Context mContext;
    private  ArrayList<Product> productA = new ArrayList<Product>();
    private HashMap<String,Product> hm_product =new HashMap<String,Product>();
    public SpecificationAdapter(Context c, ArrayList dataList) {
        super(c, dataList);
        mContext = c;
    }

    public class C0_ProductCellHolder extends BeeCellHolder
    {
        TextView productid;
        TextView productname;
        TextView productnum;
        CheckBox chk_good;
    }

    @Override
    protected BeeCellHolder createCellHolder(View cellView) {
        C0_ProductCellHolder holder = new C0_ProductCellHolder();
        holder.productid  = (TextView)cellView.findViewById(R.id.txv_productid);
        holder.productname  = (TextView)cellView.findViewById(R.id.txv_name);
        holder.productnum  	 = (TextView) cellView.findViewById(R.id.txv_cs);

        holder.chk_good=(CheckBox) cellView.findViewById(R.id.select_checkbox);

        return holder;
    }

    public HashMap<String,Product> getHm(){
        return  hm_product;
    }
    public ArrayList<Product> getCheckedAll(){

        Iterator iter = hm_product.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            productA.add((Product)entry.getValue());
        }
        return productA;
    }

    @Override
    protected View bindData(final int position, View cellView, ViewGroup parent, BeeCellHolder h) {
        final Product product= (Product)dataList.get(position);

        final C0_ProductCellHolder holder=(C0_ProductCellHolder)h;
        holder.productid.setText(product.str_productid);
        holder.productname.setText(product.str_productname);
        holder.productnum.setText(Integer.toString(product.it_maxnum)+"/"+Integer.toString(product.it_num));

        holder.chk_good.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.chk_good.isChecked()){
                    hm_product.put(product.str_productid,product);
                }
                else{
                   if(hm_product.containsKey(product.str_productid)){
                       //holder.productnum.setText("1");
                       hm_product.remove(product.str_productid);
                   }
                }
            }
        });
        return null;
    }

    @Override
    public View createCellView() {
        return LayoutInflater.from(mContext).inflate(R.layout.c0_item_cell, null);
    }
}
