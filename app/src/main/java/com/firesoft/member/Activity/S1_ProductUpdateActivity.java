package com.firesoft.member.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.BusinessResponse;
import com.BeeFramework.view.ToastView;
import com.external.alertview.AlertView;
import com.external.alertview.OnDismissListener;
import com.external.alertview.OnItemClickListener;
import com.external.androidquery.callback.AjaxStatus;
import com.external.eventbus.EventBus;
import com.firesoft.member.APIErrorCode;
import com.firesoft.member.MemberAppConst;
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

public class S1_ProductUpdateActivity extends BaseActivity implements BusinessResponse, View.OnClickListener,OnItemClickListener, OnDismissListener {
    private AlertView mAlertView;//避免创建重复View，先创建View，然后需要的时候show出来，推荐这个做法
    private ProductModel mProductModel;
    private EditText product_name;
    private EditText product_price;
    private EditText product_sprice;
    private TextView product_type,product_typeid;
    private TextView product_unit,product_unitid;
    private RadioButton rb_special_true,rb_special_false;
    private RadioButton rb_discount_true,rb_discount_false;
    private RadioButton rb_credit_true,rb_credit_false;

    private LinearLayout  mDelComplete;
    private TextView mUpdateComplete;
    private SIMPLE_PRODUCT mProduct;

    private SharedPreferences mShared;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.s1_product_update);

        Intent intent = getIntent();
        mProduct=(SIMPLE_PRODUCT)intent.getSerializableExtra("product");
        init(mProduct);
        mShared =getSharedPreferences(MemberAppConst.USERINFO, 0);
        mAlertView = new AlertView("信息提示", "确定删除该条记录！", "取消", new String[]{"确定"}, null, this, AlertView.Style.Alert, this).setCancelable(true).setOnDismissListener(this);

        mProductModel = new ProductModel(this);
        mProductModel.addResponseListener(this);

        EventBus.getDefault().register(this);
    }

    private void init(SIMPLE_PRODUCT produt){
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

        mDelComplete = (LinearLayout) findViewById(R.id.c0_del_button);
        mUpdateComplete =(TextView)  findViewById(R.id.top_member_upate);

        mUpdateComplete.setOnClickListener(this);
        mDelComplete.setOnClickListener(this);

        product_name.setText(produt.name);
        product_price.setText(produt.price);
        product_sprice.setText(produt.specia_price);
        product_type.setText(produt.type_name);
        product_typeid.setText(Integer.toString(produt.type_id));
        if(produt.special=="1"){
            rb_special_true.setChecked(true);
            rb_special_false.setChecked(false);
        }else{
            rb_special_true.setChecked(false);
            rb_special_false.setChecked(true);
        }

        if(produt.special_discount=="1"){
            rb_discount_true.setChecked(true);
            rb_discount_false.setChecked(false);
        }else{
            rb_discount_true.setChecked(false);
            rb_discount_false.setChecked(true);
        }

        if(produt.special_credit=="1"){
            rb_credit_true.setChecked(true);
            rb_credit_false.setChecked(false);
        }else{
            rb_credit_true.setChecked(false);
            rb_credit_false.setChecked(true);
        }

        product_type.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(S1_ProductUpdateActivity.this, S0_ProductTypeChooseActivity.class);
                intent.putExtra("title", "项目分类选择");
                intent.putExtra("state", 1);
                startActivityForResult(intent, 1);
                overridePendingTransition(R.anim.my_scale_action, R.anim.my_alpha_action);
            }
        });


    }

    public void onEvent(Object event) {
        /*SharedPreferences shared = getSharedPreferences(MemberAppConst.USERINFO, 0);
        Message message = (Message) event;
        if (message.what == MessageConstant.LOGINOUT) {
            finish();
        }

        if (message.what == MessageConstant.Change_Seivice) {
            mUserBalance.getServiceList(mUser.id);
        }*/


    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.top_view_back:
                finish();
                break;
            case R.id.top_member_upate:
                String name = product_name.getText().toString();
                String type_id = product_typeid.getText().toString();
                String type_name=product_type.getText().toString();
                String price = product_price.getText().toString();
                String sprice = product_sprice.getText().toString();
                String nShopid=mShared.getString("shopid", "0");
                String nShopname=mShared.getString("shopname", "");
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
                    product.type_name=type_name;
                    product.specia_price=sprice;
                    product.special=special;
                    product.special_discount=discount;
                    product.special_credit=credit;
                    product.id=mProduct.id;
                    product.shopid=nShopid;
                    product.shopname=nShopname;
                    mProductModel.update(product);

                }
                break;

            case R.id.c0_del_button:{
                mAlertView.show();
                break;
            }
        }
    }

    public  void ToastShow(String atr){
        ToastView toast = new ToastView(S1_ProductUpdateActivity.this, atr);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
            throws JSONException {
        // TODO Auto-generated method stub
        if (url.endsWith(ApiInterface.PRODUCT_DEL)) {
            productaddResponse response = new productaddResponse();
            response.fromJson(jo);
            if (response.succeed == 1) {
                Intent intent = new Intent(this, S0_ProductTypeListActivity.class);
                //startActivity(intent);
                setResult(Activity.RESULT_OK, intent);
                ToastShow("删除成功！");
                finish();
                Message msg = new Message();
                msg.what = MessageConstant.REFRESH_LIST;
                EventBus.getDefault().post(msg);
            } else {
                if (response.error_code == APIErrorCode.NICKNAME_EXIST) {
                    product_name.requestFocus();
                }
            }
        }else if(url.endsWith(ApiInterface.PRODUCT_UPDATE)){
            productaddResponse response = new productaddResponse();
            response.fromJson(jo);
            if (response.succeed == 1) {
                ToastShow("保存成功！");
                finish();
                Message msg = new Message();
                msg.what = MessageConstant.REFRESH_LIST;
                EventBus.getDefault().post(msg);

            } else {
                if (response.error_code == APIErrorCode.NICKNAME_EXIST) {
                    product_name.requestFocus();
                }
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

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
    @Override
    public void onItemClick(Object o,int position) {
        if(position!=AlertView.CANCELPOSITION){
            mProductModel.del(mProduct.id);
        }
    }

    @Override
    public void onDismiss(Object o) {

    }
}
