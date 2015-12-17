package com.firesoft.member.Activity;

import android.app.Activity;
import android.content.Intent;
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
import com.external.alertview.AlertView;
import com.external.alertview.OnDismissListener;
import com.external.alertview.OnItemClickListener;
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

public class S3_GuideUpdateActivity extends BaseActivity implements BusinessResponse, View.OnClickListener,OnItemClickListener, OnDismissListener {
    private AlertView mAlertView;//避免创建重复View，先创建View，然后需要的时候show出来，推荐这个做法
    private GuideModel mGuideModel;
    private EditText guide_name;
    private EditText guide_phone;
    private EditText password,re_password;
    private CompoundButton user_flag;
    private LinearLayout layout_user;
    private TextView userID;

    private LinearLayout  mDelComplete;
    private TextView mUpdateComplete;

    private SharedPreferences mShared;
    private SIMPLE_GUIDE mGuide;
    private String guideId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.s3_guide_update);

        guide_name = (EditText) findViewById(R.id.guide_name);
        guide_phone = (EditText) findViewById(R.id.guide_phone);
        password = (EditText) findViewById(R.id.user_password);
        re_password = (EditText) findViewById(R.id.user_repassword);
        user_flag = (CompoundButton) findViewById(R.id.user_flag);
        layout_user = (LinearLayout) findViewById(R.id.layout_user);
        mUpdateComplete = (TextView) findViewById(R.id.c0_publish_button);
        mDelComplete = (LinearLayout) findViewById(R.id.c0_del_button);
        userID=(TextView) findViewById(R.id.userId);

        Intent intent = getIntent();
        guideId=intent.getStringExtra("guideId");

        mShared =getSharedPreferences(MemberAppConst.USERINFO, 0);
        mAlertView = new AlertView("信息提示", "确定删除该条记录！", "取消", new String[]{"确定"}, null, this, AlertView.Style.Alert, this).setCancelable(true).setOnDismissListener(this);

        mGuideModel = new GuideModel(this);
        mGuideModel.addResponseListener(this);
        mGuideModel.getinfo(Integer.parseInt(guideId));

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

    }

    private void init(SIMPLE_GUIDE obj){

        guide_name.setText(obj.name);
        guide_phone.setText(obj.phone);
        password.setText(obj.password);
        re_password.setText(obj.password);

        if(obj.user_flag=="1"){
            user_flag.setChecked(true);
            layout_user.setVisibility(View.VISIBLE);
        }else{
            user_flag.setChecked(false);
            layout_user.setVisibility(View.GONE);
        }

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.top_view_back:
                finish();
                break;
            case R.id.top_member_upate:
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
                        guide.id=mGuide.id;
                        mGuideModel.update(guide);
                    }
                }

                break;

            case R.id.c0_del_button:{
                mAlertView.show();
                break;
            }
        }
    }

    public  void ToastShow(String atr){
        ToastView toast = new ToastView(S3_GuideUpdateActivity.this, atr);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
            throws JSONException {
        // TODO Auto-generated method stub
        if (url.endsWith(ApiInterface.GUIDE_DEL)) {
            guideaddResponse response = new guideaddResponse();
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
                    guide_name.requestFocus();
                }
            }
        }else if(url.endsWith(ApiInterface.GUIDE_UPDATE)){
            guideaddResponse response = new guideaddResponse();
            response.fromJson(jo);
            if (response.succeed == 1) {
                ToastShow("保存成功！");
                finish();
                Message msg = new Message();
                msg.what = MessageConstant.REFRESH_LIST;
                EventBus.getDefault().post(msg);

            } else {
                if (response.error_code == APIErrorCode.NICKNAME_EXIST) {
                    guide_name.requestFocus();
                }
            }
        }else if(url.endsWith(ApiInterface.GUIDE_INFO)){
            if (null != jo)
            {
                guideaddResponse response = new guideaddResponse();
                response.fromJson(jo);
                mGuide = mGuideModel.guide;
                if (null != mGuide) {
                    //init_member(member);
                    if(null !=mGuide.name) {
                        init(mGuide);
                    } else {
                        ToastShow("无此员工信息！");
                        finish();
                    }
                } else {
                    ToastShow("无此员工信息！");
                    finish();
                }
            }
        }
    }

    @Override
    public void onItemClick(Object o,int position) {
        if(position!=AlertView.CANCELPOSITION){
            mGuideModel.del(mGuide.id);
        }
    }

    @Override
    public void onDismiss(Object o) {

    }
}
