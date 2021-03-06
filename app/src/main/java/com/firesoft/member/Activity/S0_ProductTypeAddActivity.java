package com.firesoft.member.Activity;



import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.view.Gravity;

import android.view.View;
import android.widget.EditText;

import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.BusinessResponse;
import com.BeeFramework.view.ToastView;
import com.external.androidquery.callback.AjaxStatus;
import com.external.eventbus.EventBus;

import com.firesoft.member.APIErrorCode;
import com.firesoft.member.MemberAppConst;
import com.firesoft.member.MessageConstant;
import com.firesoft.member.Model.ProductTypeModel;
import com.firesoft.member.Protocol.ApiInterface;
import com.firesoft.member.Protocol.SIMPLE_PRODUCTTYPE;
import com.firesoft.member.Protocol.producttypeaddResponse;
import com.firesoft.member.R;

import org.json.JSONException;
import org.json.JSONObject;



public class S0_ProductTypeAddActivity extends BaseActivity implements BusinessResponse {




    private ProductTypeModel mProductTypeModel;
    private EditText type_name;
    private EditText type_bz;


    private TextView mAddComplete;

    private SharedPreferences mShared;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.s0_product_type_add);

        type_name = (EditText) findViewById(R.id.type_name);
        type_bz = (EditText) findViewById(R.id.type_bz);
        mAddComplete = (TextView) findViewById(R.id.c0_publish_button);

        mShared =getSharedPreferences(MemberAppConst.USERINFO, 0);

        mProductTypeModel = new ProductTypeModel(this);
        mProductTypeModel.addResponseListener(this);


        mAddComplete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                String name = type_name.getText().toString();
                String bz = type_bz.getText().toString();
                String nShopid=mShared.getString("shopid", "0");
                String nShopname=mShared.getString("shopname", "");


                if ("".equals(name)) {
                    ToastView toast = new ToastView(S0_ProductTypeAddActivity.this, "项目分类名称不能为空");
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    type_name.setText("");
                    type_name.requestFocus();
                } else {
                    SIMPLE_PRODUCTTYPE mProductTye = new SIMPLE_PRODUCTTYPE();
                    mProductTye.name=name;
                    mProductTye.bz=bz;
                    mProductTye.shopid=nShopid;
                    mProductTye.shopname=nShopname;
                    mProductTypeModel.add(mProductTye);
                }

            }
        });

    }


    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
            throws JSONException {
        // TODO Auto-generated method stub
        if (url.endsWith(ApiInterface.PRODUCTTYPE_ADD)) {
            producttypeaddResponse response = new producttypeaddResponse();
            response.fromJson(jo);
            if (response.succeed == 1) {
                /*Intent intent = new Intent(this, S0_ProductTypeListActivity.class);
                startActivity(intent);*/
                finish();
                Message msg = new Message();
                msg.what = MessageConstant.REFRESH_LIST;
                EventBus.getDefault().post(msg);
            }else {
                if (response.error_code == APIErrorCode.NICKNAME_EXIST) {
                    type_name.requestFocus();
                }
            }
        }

    }

}
