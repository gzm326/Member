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
import com.firesoft.member.Protocol.SIMPLE_NUMBER;
import com.firesoft.member.R;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SpecificationAdapter extends BeeBaseAdapter
{
    private Context mContext;
    private  ArrayList<SIMPLE_NUMBER> numbers = new ArrayList<SIMPLE_NUMBER>();
    private HashMap<String,SIMPLE_NUMBER> hm_product =new HashMap<String,SIMPLE_NUMBER>();
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

    public HashMap<String,SIMPLE_NUMBER> getHm(){
        return  hm_product;
    }
    public ArrayList<SIMPLE_NUMBER> getCheckedAll(){

        Iterator iter = hm_product.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            numbers.add((SIMPLE_NUMBER)entry.getValue());
        }
        return numbers;
    }

    @Override
    protected View bindData(final int position, View cellView, ViewGroup parent, BeeCellHolder h) {
        final SIMPLE_NUMBER number= (SIMPLE_NUMBER)dataList.get(position);

        final C0_ProductCellHolder holder=(C0_ProductCellHolder)h;
        holder.productid.setText(number.product_id);
        holder.productname.setText(number.product_name);
        holder.productnum.setText(number.num+"/"+number.donum);

        holder.chk_good.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.chk_good.isChecked()){
                    hm_product.put(number.product_id,number);
                }
                else{
                   if(hm_product.containsKey(number.product_id)){
                       //holder.productnum.setText("1");
                       hm_product.remove(number.product_id);
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
