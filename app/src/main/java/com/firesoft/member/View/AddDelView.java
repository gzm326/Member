package com.firesoft.member.View;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firesoft.member.R;


/**
 * Created by Administrator on 2015/9/15.
 */

public class AddDelView extends LinearLayout{

    private EditText quantityEditText;
    private TextView et_goodsy;
    private ImageView minusImageView;
    private ImageView addImageView;
    private int num=1;
    public int maxNum=10;

    public AddDelView(Context context){
        super(context, null);
    }

    public AddDelView(Context context,AttributeSet attr){
        super(context,attr);
        LayoutInflater.from(context).inflate(R.layout.add_item_component, this, true);
        init();
    }

    private void init(){
        minusImageView = (ImageView)findViewById(R.id.shop_car_item_min);
        addImageView = (ImageView)findViewById(R.id.shop_car_item_sum);
        quantityEditText = (EditText)findViewById(R.id.shop_car_item_editNum);
        et_goodsy = (TextView)findViewById(R.id.good_sy);
        quantityEditText.setText(String.valueOf(num));

       /* minusImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (num - 1 > 0) {
                    num--;
                    quantityEditText.setText(String.valueOf(num));
                }
            }
        });

        addImageView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(num<maxNum) {
                    num++;
                    quantityEditText.setText(String.valueOf(num));
                }
            }
        });*/
    }
}
