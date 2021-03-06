//
//       _/_/_/                      _/            _/_/_/_/_/
//    _/          _/_/      _/_/    _/  _/              _/      _/_/      _/_/
//   _/  _/_/  _/_/_/_/  _/_/_/_/  _/_/              _/      _/    _/  _/    _/
//  _/    _/  _/        _/        _/  _/          _/        _/    _/  _/    _/
//   _/_/_/    _/_/_/    _/_/_/  _/    _/      _/_/_/_/_/    _/_/      _/_/
//
//
//  Copyright (c) 2015-2016, Geek Zoo Studio
//  http://www.geek-zoo.com
//
//
//  Permission is hereby granted, free of charge, to any person obtaining a
//  copy of this software and associated documentation files (the "Software"),
//  to deal in the Software without restriction, including without limitation
//  the rights to use, copy, modify, merge, publish, distribute, sublicense,
//  and/or sell copies of the Software, and to permit persons to whom the
//  Software is furnished to do so, subject to the following conditions:
//
//  The above copyright notice and this permission notice shall be included in
//  all copies or substantial portions of the Software.
//
//  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
//  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
//  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
//  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
//  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
//  FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
//  IN THE SOFTWARE.
//

package com.firesoft.member.Activity;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.BusinessResponse;
import com.BeeFramework.view.ToastView;
import com.external.androidquery.callback.AjaxStatus;

import com.external.eventbus.EventBus;
import com.external.timepicker.ScreenInfo;
import com.external.timepicker.WheelMain;
import com.firesoft.member.APIErrorCode;
import com.firesoft.member.Adapter.ProductxfAdapter;
import com.firesoft.member.MemberAppConst;
import com.firesoft.member.MessageConstant;
import com.firesoft.member.Model.MemberModel;
import com.firesoft.member.Model.Product;
import com.firesoft.member.Protocol.ApiInterface;
import com.firesoft.member.Protocol.SIMPLE_MEMBER;
import com.firesoft.member.Protocol.memberaddResponse;
import com.firesoft.member.R;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Timer;

public class C1_PublishOrderActivity extends BaseActivity implements BusinessResponse {

    private TextView tv_xtkz;
    private LinearLayout mKzView;


    private MemberModel mMemberModel;
    private EditText member_no;
    private EditText member_name;
    private EditText mobile_no;
    private TextView mAddComplete;
    private TextView mcard_jb;
    private TextView mcard_jbid;
    private Timer mTimer;
    private TextView mTime;
    private SimpleDateFormat mFormat;
    private WheelMain mWheelMain;

    private SharedPreferences mShared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.c1_publish_order);
        mFormat = new SimpleDateFormat("yyyy-MM-dd");

        tv_xtkz =(TextView)findViewById(R.id.mcard_xtkz);
        mKzView = (LinearLayout) findViewById(R.id.mcard_xtkz_view);
        member_no = (EditText) findViewById(R.id.mcard_kh);
        member_name = (EditText) findViewById(R.id.mcard_xm);
        mobile_no = (EditText) findViewById(R.id.mcard_phone);
        mAddComplete = (TextView) findViewById(R.id.c0_publish_button);
        mcard_jb=(TextView) findViewById(R.id.mcard_jb);
        mcard_jbid=(TextView) findViewById(R.id.mcard_jb_id);
        mTime =(TextView)findViewById(R.id.mcard_sr);

        mShared =getSharedPreferences(MemberAppConst.USERINFO, 0);


        mMemberModel = new MemberModel(this);
        mMemberModel.addResponseListener(this);

        initData();

        mcard_jb.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(C1_PublishOrderActivity.this, F3_RegionPickActivity.class);
                intent.putExtra("title", "会员级别选择");
                intent.putExtra("str_url",ApiInterface.COMMENT_LIST);
                intent.putExtra("state",1);
                startActivityForResult(intent, 1);
                overridePendingTransition(R.anim.my_scale_action,R.anim.my_alpha_action);
            }
        });



        tv_xtkz.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (mKzView.getVisibility() == View.GONE) {
                    mKzView.setVisibility(View.VISIBLE);
                } else {
                    mKzView.setVisibility(View.GONE);
                   /* Handler mHandler = new Handler() {
                        @Override
                        public void handleMessage(Message msg) {
                            super.handleMessage(msg);
                            mKzView.setVisibility(View.GONE);
                        }
                    };
                    mHandler.sendEmptyMessageDelayed(0, 200);*/
                }
            }
        });

        mAddComplete.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String no = member_no.getText().toString().trim();
                String name = member_name.getText().toString();
                String phone = mobile_no.getText().toString();
                String nShopid=mShared.getString("shopid", "0");
                String nShopname=mShared.getString("shopname", "");

                if ("".equals(name)) {
                    ToastView toast = new ToastView(C1_PublishOrderActivity.this, "会员名称不能为空！");
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    member_name.setText("");
                    member_name.requestFocus();
                }else if ("".equals(no)) {
                    ToastView toast = new ToastView(C1_PublishOrderActivity.this,"会员卡号不能为空！");
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    member_no.requestFocus();
                } else if ("".equals(phone)) {
                    ToastView toast = new ToastView(C1_PublishOrderActivity.this,"手机号不能为空！");
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    mobile_no.requestFocus();
                } else {
                    SIMPLE_MEMBER member= new SIMPLE_MEMBER();
                    member.member_no=no;
                    member.member_name=name;
                    member.mobile_no=phone;
                    member.shopid=nShopid;
                    member.shopname=nShopname;
                    mMemberModel.add(member);
                    CloseKeyBoard();
                }

            }
        });
    }

    public void initData() {
        Date date = new Date();
        mTime.setText(mFormat.format(date));
        mTime.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                CloseKeyBoard();
                LayoutInflater inflater = LayoutInflater.from(C1_PublishOrderActivity.this);
                final View timepickerview = inflater.inflate(R.layout.timepicker, null);
                ScreenInfo screenInfo = new ScreenInfo(C1_PublishOrderActivity.this);
                mWheelMain = new WheelMain(timepickerview);
                mWheelMain.screenheight = screenInfo.getHeight();
                Calendar calendar = Calendar.getInstance();
                try {
                    calendar.setTime(mFormat.parse(mTime.getText().toString()));
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int min = calendar.get(Calendar.MINUTE);
                mWheelMain.initDateTimePicker(year, month, day, hour, min);
                new AlertDialog.Builder(C1_PublishOrderActivity.this)
                        .setTitle("选择时间")
                        .setView(timepickerview)
                        .setPositiveButton(getString(R.string.confirm), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mTime.setText(mWheelMain.getTime());
                            }
                        })
                        .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        })
                        .show();
            }
        });
    }


    // 关闭键盘
    private void CloseKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(member_no.getWindowToken(), 0);
    }
    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
            throws JSONException {
        // TODO Auto-generated method stub
        if (url.endsWith(ApiInterface.MEMBER_ADD)) {
            memberaddResponse response = new memberaddResponse();
            response.fromJson(jo);
            if (response.succeed == 1) {
                /*Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);*/
                ToastShow("保存成功");
                finish();
                Message msg = new Message();
                msg.what = MessageConstant.REFRESH_LIST;
                EventBus.getDefault().post(msg);
            }else {
                if (response.error_code == APIErrorCode.NICKNAME_EXIST) {
                    member_no.requestFocus();
                }
            }
        }

    }

    public  void ToastShow(String atr){
        ToastView toast = new ToastView(C1_PublishOrderActivity.this, atr);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
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
                    mcard_jb.setText(choose_name);
                    mcard_jbid.setText(choose_id);
                    break;
                case 2:

                    break;

            }
        }
    }
    /*@Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }*/
}
