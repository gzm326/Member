package com.firesoft.member.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.BusinessResponse;
import com.BeeFramework.view.ToastView;
import com.external.androidquery.callback.AjaxStatus;
import com.external.eventbus.EventBus;
import com.firesoft.member.APIErrorCode;
import com.firesoft.member.MessageConstant;

import com.firesoft.member.Model.ProductTypeModel;
import com.firesoft.member.Protocol.ApiInterface;
import com.firesoft.member.Protocol.SIMPLE_PRODUCTTYPE;
import com.firesoft.member.Protocol.producttypeaddResponse;
import com.firesoft.member.R;

import org.json.JSONException;
import org.json.JSONObject;

public class S0_ProductTypeUpdateActivity extends BaseActivity implements BusinessResponse, View.OnClickListener {

    private ProductTypeModel mProductTypeModel;
    private EditText type_name;
    private EditText type_bz;
    private EditText type_id;
    private SIMPLE_PRODUCTTYPE mProductType;
    private TextView mDelComplete;
    private LinearLayout mUpdateComplete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.s0_product_type_update);

        Intent intent = getIntent();
        mProductType=(SIMPLE_PRODUCTTYPE)intent.getSerializableExtra("producttype");
        init(mProductType);

       mProductTypeModel = new ProductTypeModel(this);
        mProductTypeModel.addResponseListener(this);

         EventBus.getDefault().register(this);
    }

    private void init(SIMPLE_PRODUCTTYPE produttype){
        type_name = (EditText) findViewById(R.id.type_name);
        type_bz = (EditText) findViewById(R.id.type_bz);
        type_id = (EditText) findViewById(R.id.type_id);

        mDelComplete = (TextView) findViewById(R.id.c0_del_button);
        mUpdateComplete = (LinearLayout) findViewById(R.id.top_member_upate);

        mUpdateComplete.setOnClickListener(this);
        mDelComplete.setOnClickListener(this);

        type_name.setText(produttype.name);
        type_bz.setText(produttype.bz);
        type_id.setText(Integer.toString(produttype.id));

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.top_view_back:
                finish();
                break;
            case R.id.top_member_upate:
                String name = type_name.getText().toString();
                String bz = type_bz.getText().toString();

                if ("".equals(name)) {
                    ToastView toast = new ToastView(S0_ProductTypeUpdateActivity.this, "项目分类名称不能为空！");
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    type_name.setText("");
                    type_name.requestFocus();
                }
                else {
                    mProductTypeModel.update(name, mProductType.id, bz);
                }
                break;

            case R.id.c0_del_button:{
                mProductTypeModel.del(mProductType.id);
                break;
            }
        }
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

    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
            throws JSONException {
        // TODO Auto-generated method stub
        if (url.endsWith(ApiInterface.PRODUCTTYPE_DEL)) {
            producttypeaddResponse response = new producttypeaddResponse();
            response.fromJson(jo);
            if (response.succeed == 1) {
                Intent intent = new Intent(this, S0_ProductTypeListActivity.class);
                //startActivity(intent);
                setResult(Activity.RESULT_OK, intent);
                finish();
                Message msg = new Message();
                msg.what = MessageConstant.SIGN_UP_SUCCESS;
                EventBus.getDefault().post(msg);
            } else {
                if (response.error_code == APIErrorCode.NICKNAME_EXIST) {
                    type_name.requestFocus();
                }
            }
        }else if(url.endsWith(ApiInterface.PRODUCTTYPE_UPDATE)){
            producttypeaddResponse response = new producttypeaddResponse();
            response.fromJson(jo);
            if (response.succeed == 1) {
                Message msg = new Message();
                msg.what = MessageConstant.SIGN_UP_SUCCESS;
                EventBus.getDefault().post(msg);
            } else {
                if (response.error_code == APIErrorCode.NICKNAME_EXIST) {
                    type_name.requestFocus();
                }
            }
        }
    }
}
