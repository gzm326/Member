package com.firesoft.member.Activity;


import android.content.SharedPreferences;

import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;

import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.BusinessResponse;
import com.BeeFramework.view.ToastView;
import com.external.androidquery.callback.AjaxStatus;
import com.external.eventbus.EventBus;
import com.firesoft.member.APIErrorCode;
import com.firesoft.member.MemberAppConst;
import com.firesoft.member.MessageConstant;
import com.firesoft.member.Model.GuideModel;
import com.firesoft.member.Protocol.ApiInterface;
import com.firesoft.member.Protocol.SIMPLE_GUIDE;
import com.firesoft.member.Protocol.guideaddResponse;
import com.firesoft.member.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class S3_GuideAddActivity  extends BaseActivity implements BusinessResponse {
    private GuideModel mGuideModel;
    private EditText guide_name;
    private EditText guide_phone;
    private EditText password,re_password;
    private CompoundButton user_flag;
    private LinearLayout layout_user;
    private TextView mAddComplete,userID;

    private SharedPreferences mShared;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.s3_guide_add);
        guide_name = (EditText) findViewById(R.id.guide_name);
        guide_phone = (EditText) findViewById(R.id.guide_phone);
        password = (EditText) findViewById(R.id.user_password);
        re_password = (EditText) findViewById(R.id.user_repassword);
        user_flag = (CompoundButton) findViewById(R.id.user_flag);
        layout_user = (LinearLayout) findViewById(R.id.layout_user);
        mAddComplete = (TextView) findViewById(R.id.c0_publish_button);
        userID=(TextView) findViewById(R.id.userId);

        mShared =getSharedPreferences(MemberAppConst.USERINFO, 0);

        mGuideModel = new GuideModel(this);
        mGuideModel.addResponseListener(this);

        guide_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String str_userid = guide_phone.getText().toString();
                userID.setText(str_userid);

            }
        });

        user_flag.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    layout_user.setVisibility(View.VISIBLE);
                } else {
                    layout_user.setVisibility(View.GONE);
                }
            }
        });


        mAddComplete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                String name = guide_name.getText().toString();
                String phone = guide_phone.getText().toString();
                String nShopid = mShared.getString("shopid", "0");
                String nShopname = mShared.getString("shopname", "");
                String str_password = "", str_repassword = "", str_userId = "";
                String user_falg = "0", flag = "0";


                Pattern pf = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
                Matcher mf = pf.matcher(phone);


                if ("".equals(name)) {
                    ToastShow("店员名称不能为空");
                    guide_name.setText("");
                    guide_name.requestFocus();
                } else if ("".equals(phone)) {
                    ToastShow("手机号码不能为空");
                    guide_phone.setText("");
                    guide_phone.requestFocus();
                } else if (!mf.matches()) {
                    ToastShow("手机号码输入有误，请重新输入！");
                    guide_phone.requestFocus();
                } else {
                    if (user_flag.isChecked()) {
                        user_falg = "1";
                        str_userId = userID.getText().toString();
                        str_password = password.getText().toString();
                        str_repassword = re_password.getText().toString();

                        if ("".equals(str_userId)) {
                            ToastShow("用户名不能为空");
                            userID.requestFocus();
                            flag = "1";
                        } else if ("".equals(str_password)) {
                            ToastShow(getString(R.string.please_input_password));
                            password.requestFocus();
                            flag = "1";
                        } else if (str_password.length() < 6 || str_password.length() > 20) {
                            ToastShow(getString(R.string.password_wrong_format_hint));
                            password.requestFocus();
                        } else if (!str_password.equals(str_repassword)) {
                            //ToastShow(str_password + "||" + str_repassword);
                            ToastShow(getString(R.string.two_passwords_differ_hint));
                            password.requestFocus();
                            flag = "1";
                        }
                    }
                    if (flag == "0") {
                        SIMPLE_GUIDE guide = new SIMPLE_GUIDE();
                        guide.name = name;
                        guide.phone = phone;
                        guide.shopid = nShopid;
                        guide.shopname = nShopname;
                        guide.password = str_password;
                        guide.user_flag=user_falg;
                        mGuideModel.add(guide);
                    }
                }

            }
        });

    }

    public  void ToastShow(String atr){
        ToastView toast = new ToastView(S3_GuideAddActivity.this, atr);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }


    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
            throws JSONException {
        // TODO Auto-generated method stub
        if (url.endsWith(ApiInterface.GUIDE_ADD)) {
            guideaddResponse response = new guideaddResponse();
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
                    guide_name.requestFocus();
                }
            }
        }

    }
}
