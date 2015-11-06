package com.firesoft.member.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.BusinessResponse;
import com.BeeFramework.view.ToastView;
import com.external.androidquery.callback.AjaxStatus;
import com.external.eventbus.EventBus;
import com.firesoft.member.APIErrorCode;
import com.firesoft.member.MessageConstant;
import com.firesoft.member.Model.Product;
import com.firesoft.member.Model.ProductModel;
import com.firesoft.member.Protocol.ApiInterface;
import com.firesoft.member.Protocol.SIMPLE_PRODUCT;
import com.firesoft.member.Protocol.productaddResponse;
import com.firesoft.member.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class S1_ProductAddActivity extends BaseActivity implements BusinessResponse {

    private ProductModel mProductModel;
    private EditText product_name;
    private EditText product_price;
    private EditText product_sprice;
    private TextView product_type,product_typeid;
    private TextView product_unit,product_unitid;
    private RadioButton rb_special_true,rb_special_false;
    private RadioButton rb_discount_true,rb_discount_false;
    private RadioButton rb_credit_true,rb_credit_false;

    private TextView mAddComplete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.s1_product_add);

        product_name = (EditText) findViewById(R.id.product_name);
        product_price = (EditText) findViewById(R.id.product_price);
        product_sprice = (EditText) findViewById(R.id.specia_price);
        product_type = (TextView) findViewById(R.id.product_type);
        product_typeid = (TextView) findViewById(R.id.product_type_id);
        rb_special_true = (RadioButton) findViewById(R.id.rb_special_true);
        rb_special_false = (RadioButton) findViewById(R.id.rb_special_false);
        rb_discount_true = (RadioButton) findViewById(R.id.rb_discount_true);
        rb_discount_false = (RadioButton) findViewById(R.id.rb_discount_false);
        rb_credit_true = (RadioButton) findViewById(R.id.rb_credit_true);
        rb_credit_false = (RadioButton) findViewById(R.id.rb_credit_false);
        mAddComplete = (TextView) findViewById(R.id.c0_publish_button);

        mProductModel = new ProductModel(this);
        mProductModel.addResponseListener(this);


        product_type.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(S1_ProductAddActivity.this, F3_RegionPickActivity.class);
                intent.putExtra("title", "项目分类选择");
                intent.putExtra("str_url", ApiInterface.COMMENT_LIST);
                intent.putExtra("state", 1);
                startActivityForResult(intent, 1);
                overridePendingTransition(R.anim.my_scale_action, R.anim.my_alpha_action);
            }
        });


        mAddComplete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                String name = product_name.getText().toString();
                String type_id = product_typeid.getText().toString();
                String price = product_price.getText().toString();
                String sprice = product_sprice.getText().toString();
                String special,discount,credit;
                if(rb_special_true.isChecked()){
                    special="1";
                }else{
                    special="0";
                }

                if(rb_discount_true.isChecked()){
                    discount="1";
                }else{
                    discount="0";
                }

                if(rb_credit_true.isChecked()){
                    credit="1";
                }else{
                    credit="0";
                }


                if ("".equals(name)) {
                    ToastShow("项目名称不能为空");
                    product_name.setText("");
                    product_name.requestFocus();
                } else if("".equals(type_id)){
                    ToastShow("项目分类不能为空");
                    product_type.requestFocus();

                }else {
                    SIMPLE_PRODUCT product = new SIMPLE_PRODUCT();
                    product.name=name;
                    product.price=price;
                    product.type_id=Integer.parseInt(type_id);
                    product.specia_price=sprice;
                    product.special=special;
                    product.special_discount=discount;
                    product.special_credit=credit;
                    mProductModel.add(product);

                }

            }
        });
    }

    public  void ToastShow(String atr){
        ToastView toast = new ToastView(S1_ProductAddActivity.this, atr);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
            throws JSONException {
        // TODO Auto-generated method stub
        if (url.endsWith(ApiInterface.PRODUCT_ADD)) {
            productaddResponse response = new productaddResponse();
            response.fromJson(jo);
            if (response.succeed == 1) {
                /*Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);*/
                finish();
                Message msg = new Message();
                msg.what = MessageConstant.REFRESH_LIST;
                EventBus.getDefault().post(msg);
            }else {
                if (response.error_code == APIErrorCode.NICKNAME_EXIST) {
                    product_name.requestFocus();
                }
            }
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ArrayList<Product> productArrayList;
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            switch(resultCode)
            {
                case 1:
                    String choose_name,choose_id;
                    choose_name=data.getStringExtra("name");
                    choose_id=data.getStringExtra("uid");
                    product_type.setText(choose_name);
                    product_typeid.setText(choose_id);

                    break;
                case 2:

                    break;

            }
        }
    }

}
