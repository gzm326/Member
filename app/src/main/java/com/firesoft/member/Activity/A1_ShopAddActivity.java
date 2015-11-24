package com.firesoft.member.Activity;



import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.view.Gravity;

import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.BusinessResponse;
import com.BeeFramework.view.ToastView;
import com.external.activeandroid.util.Log;
import com.external.androidquery.callback.AjaxStatus;
import com.external.eventbus.EventBus;
import com.firesoft.member.APIErrorCode;
import com.firesoft.member.MemberAppConst;
import com.firesoft.member.MessageConstant;
import com.firesoft.member.Model.ShopModel;
import com.firesoft.member.Protocol.ApiInterface;
import com.firesoft.member.Protocol.SIMPLE_SHOP;
import com.firesoft.member.Protocol.shopaddResponse;
import com.firesoft.member.R;
import com.firesoft.member.Utils.LocationManager;

import org.json.JSONException;
import org.json.JSONObject;


public class A1_ShopAddActivity extends BaseActivity implements BusinessResponse {

    private ShopModel mShopModel;
    private EditText shop_name;
    private EditText lxr;
    private EditText lxdh;
    private EditText address;
    private EditText birth_credit;//生日积分系数
    private EditText birth_discount;//生日打折系数
    private EditText shop_credit;//店铺积分系数
    private EditText shop_discount;//店铺打折系数
    private String nProvince,nCity,nDistrict;
    private String nShopid,nShopname;
    private String nFlag="0";
    private int userId;

    private CompoundButton grade_credit;//会员级别是否积分
    private CompoundButton grade_discount;//会员级别是否打折
    private CompoundButton sale_credit;//消费项目是否积分
    private CompoundButton sale_discount;//消费项目是否打折



    private TextView mAddComplete;

    private SharedPreferences mShared;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a1_shop_add);

        mShared =getSharedPreferences(MemberAppConst.USERINFO, 0);

        shop_name = (EditText) findViewById(R.id.shop_name);
        lxr = (EditText) findViewById(R.id.lxr);
        lxdh = (EditText) findViewById(R.id.lxdh);
        address = (EditText) findViewById(R.id.address);
        birth_credit = (EditText) findViewById(R.id.birth_credit);
        birth_discount = (EditText) findViewById(R.id.birth_discount);
        shop_credit = (EditText) findViewById(R.id.shop_credit);
        shop_discount = (EditText) findViewById(R.id.shop_discount);
        grade_credit = (CompoundButton) findViewById(R.id.grade_credit);
        grade_discount = (CompoundButton) findViewById(R.id.grade_discount);
        sale_credit = (CompoundButton) findViewById(R.id.sale_credit);
        sale_discount = (CompoundButton) findViewById(R.id.sale_discount);

        mAddComplete = (TextView) findViewById(R.id.c0_publish_button);

        mShopModel = new ShopModel(this);
        mShopModel.addResponseListener(this);

        nShopid=mShared.getString("shopid", "0");
        nShopname=mShared.getString("shopname", "");
        userId = mShared.getInt("uid", 0);



        if("0".equals(nShopid)){
            nFlag="0";
        }else if("".equals(nShopid)){
            nShopid="0";
            nFlag="0";
        }else{
            nFlag="1";
        }

        ToastShow(nShopid);
        if(nFlag=="0"){
            address.setText(mShared.getString("naddress", ""));
        }


        nProvince=mShared.getString("nProvince", "");
        nCity=mShared.getString("nCity", "");
        nDistrict=mShared.getString("nDistrict", "");



        if(nFlag=="1"){
            mShopModel.getinfo(Integer.parseInt(nShopid));
          // ToastShow(Integer.toString(mShopModel.dataList.size()));
           // ToastShow(mShopModel.mshop.name);
            //init(mShopModel.shop);
        }


        mAddComplete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                String name = shop_name.getText().toString();
                String str_address = address.getText().toString();
                String str_lxr = lxr.getText().toString();
                String str_lxdh = lxdh.getText().toString();
                String str_birth_credit = birth_credit.getText().toString();
                String str_birth_discount = birth_discount.getText().toString();
                String str_shop_credit = shop_credit.getText().toString();
                String str_shop_discount = shop_discount.getText().toString();

                if("".equals(str_birth_credit)){
                    str_birth_credit="0";
                }

                if("".equals(str_birth_discount)){
                    str_birth_discount="0";
                }

                if("".equals(str_shop_credit)){
                    str_shop_credit="0";
                }

                if("".equals(str_shop_discount)){
                    str_shop_discount="0";
                }

                String str_grade_credit,str_grade_discount,str_sale_credit,str_sale_discount;
                if(grade_credit.isChecked()){
                    str_grade_credit="1";
                }else{
                    str_grade_credit="0";
                }

                if(grade_discount.isChecked()){
                    str_grade_discount="1";
                }else{
                    str_grade_discount="0";
                }

                if(sale_credit.isChecked()){
                    str_sale_credit="1";
                }else{
                    str_sale_credit="0";
                }

                if(sale_discount.isChecked()){
                    str_sale_discount="1";
                }else{
                    str_sale_discount="0";
                }


                if ("".equals(name)) {
                    ToastShow("名称不能为空！");
                    shop_name.setText("");
                    shop_name.requestFocus();
                }else if("".equals(str_lxr)){
                    ToastShow("联系人不能为空！");
                    lxr.requestFocus();

                } else if("".equals(str_lxdh)){
                    ToastShow("联系人电话不能为空！");
                    lxdh.requestFocus();

                }else {
                    ToastShow(Integer.toString(userId));
                    SIMPLE_SHOP shop = new SIMPLE_SHOP();
                    shop.id=Integer.parseInt(nShopid);
                    shop.userid=userId;
                    shop.name=name;
                    shop.lxr=str_lxr;
                    shop.lxdh=str_lxdh;
                    shop.adress_province_name=nProvince;
                    shop.adress_city_name=nCity;
                    shop.adress_county_name=nDistrict;
                    shop.adress_detail=str_address;
                    shop.birth_credit=str_birth_credit;
                    shop.birth_discount=str_birth_discount;
                    shop.shop_credit=str_shop_credit;
                    shop.shop_discount=str_shop_discount;
                    shop.grade_credit=str_grade_credit;
                    shop.grade_discount=str_grade_discount;
                    shop.sale_credit=str_sale_credit;
                    shop.sale_discount=str_sale_discount;
                    if(nFlag=="1"){
                        mShopModel.update(shop);
                    }else {
                        mShopModel.add(shop);
                    }


                }

            }
        });
    }

    private void init(SIMPLE_SHOP nshop){
        shop_name.setText(nshop.name);
        lxr.setText(nshop.lxr);
        lxdh.setText(nshop.lxdh);
        address.setText(nshop.adress_detail);
        birth_credit.setText(nshop.birth_credit);
        birth_discount.setText(nshop.birth_discount);
        shop_credit.setText(nshop.shop_credit);
        shop_discount.setText(nshop.shop_discount);
        if(nshop.grade_credit=="1"){
            grade_credit.setChecked(true);
        }else{
            grade_credit.setChecked(false);
        }

        if(nshop.grade_discount=="1"){
            grade_discount.setChecked(true);
        }else{
            grade_discount.setChecked(false);
        }

        if(nshop.sale_credit=="1"){
            sale_credit.setChecked(true);
        }else{
            sale_credit.setChecked(false);
        }

        if(nshop.sale_discount=="1"){
            sale_discount.setChecked(true);
        }else{
            sale_discount.setChecked(false);
        }

    }

    public  void ToastShow(String atr){
        ToastView toast = new ToastView(A1_ShopAddActivity.this, atr);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
            throws JSONException {
        // TODO Auto-generated method stub
        if (url.endsWith(ApiInterface.SHOP_ADD)) {
            shopaddResponse response = new shopaddResponse();
            response.fromJson(jo);
            if (response.succeed == 1) {
                /*Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();*/
                ToastShow("保存成功");
                Message msg = new Message();
                msg.what = MessageConstant.REFRESH_LIST;
                EventBus.getDefault().post(msg);
            }else {
                if (response.error_code == APIErrorCode.NICKNAME_EXIST) {
                    shop_name.requestFocus();
                }
            }
        }else if (url.endsWith(ApiInterface.SHOP_UPDATE)) {
            shopaddResponse response = new shopaddResponse();
            response.fromJson(jo);
            if (response.succeed == 1) {
                /*Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();*/
                ToastShow("保存成功");
                Message msg = new Message();
                msg.what = MessageConstant.REFRESH_LIST;
                EventBus.getDefault().post(msg);
            }else {
                if (response.error_code == APIErrorCode.NICKNAME_EXIST) {
                    shop_name.requestFocus();
                }
            }
        }else if (url.endsWith(ApiInterface.SHOP_INFO)) {
            shopaddResponse response = new shopaddResponse();
            response.fromJson(jo);
            if (response.succeed == 1) {
                /*Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();*/
                //ToastShow(mShopModel.mshop.name);

                init(mShopModel.mshop);
                Message msg = new Message();
                msg.what = MessageConstant.REFRESH_LIST;
                EventBus.getDefault().post(msg);
            }else {
                if (response.error_code == APIErrorCode.NICKNAME_EXIST) {
                    shop_name.requestFocus();
                }
            }
        }
        /*else if (url.endsWith(ApiInterface.LOCATION_INFO)) {
            address.setText(mLocationInfoModel.publicLocationName);
        }*/

    }


}
