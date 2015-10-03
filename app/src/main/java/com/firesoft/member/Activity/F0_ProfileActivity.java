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
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
import com.firesoft.member.Model.MemberModel;
import com.firesoft.member.Protocol.ApiInterface;
import com.firesoft.member.Protocol.SIMPLE_MEMBER;
import com.firesoft.member.Protocol.memberaddResponse;
import com.firesoft.member.R;

import org.json.JSONException;
import org.json.JSONObject;


public class F0_ProfileActivity extends BaseActivity implements BusinessResponse, View.OnClickListener {


    private SIMPLE_MEMBER member;
    private EditText member_no;
    private EditText member_name;
    private EditText mobile_no;
    private TextView mDelComplete;
    private LinearLayout mUpdateComplete;
    private MemberModel mMemberModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.c0_member_update);

        Intent intent = getIntent();
        member=(SIMPLE_MEMBER)intent.getSerializableExtra("member");
        init(member);

        mMemberModel = new MemberModel(this);
        mMemberModel.addResponseListener(this);

        EventBus.getDefault().register(this);
    }

    private void init(SIMPLE_MEMBER member){
        member_no = (EditText) findViewById(R.id.mcard_kh);
        member_name = (EditText) findViewById(R.id.mcard_xm);
        mobile_no = (EditText) findViewById(R.id.mcard_phone);
        mDelComplete = (TextView) findViewById(R.id.c0_del_button);
        mUpdateComplete = (LinearLayout) findViewById(R.id.top_member_upate);

        mUpdateComplete.setOnClickListener(this);
        mDelComplete.setOnClickListener(this);

        member_no.setText(member.member_no);
        member_name.setText(member.member_name);
        mobile_no.setText(member.mobile_no);
    }
    @Override
    protected void onResume() {

        super.onResume();
    }



    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.top_view_back:
                finish();
                break;
            case R.id.top_member_upate:
                String no = member_no.getText().toString().trim();
                String name = member_name.getText().toString();
                String phone = mobile_no.getText().toString();

                if ("".equals(name)) {
                    ToastView toast = new ToastView(F0_ProfileActivity.this, "会员名称不能为空！");
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    member_name.setText("");
                    member_name.requestFocus();
                }else if ("".equals(no)) {
                    ToastView toast = new ToastView(F0_ProfileActivity.this,"会员卡号不能为空！");
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    member_no.requestFocus();
                } else if ("".equals(phone)) {
                    ToastView toast = new ToastView(F0_ProfileActivity.this,"手机号不能为空！");
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    mobile_no.requestFocus();
                } else {
                    mMemberModel.update(no, name, phone, member.id);
                    CloseKeyBoard();
                }
                break;

            case R.id.c0_del_button:{
                mMemberModel.del(member.id);
                break;


            }
        }
    }

        // �رռ���
    private void CloseKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(member_no.getWindowToken(), 0);
    }
    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
    public  void ToastShow(String atr){
        ToastView toast = new ToastView(F0_ProfileActivity.this, atr);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
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
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
            throws JSONException {
        // TODO Auto-generated method stub
        if (url.endsWith(ApiInterface.MEMBER_DEL)) {
            memberaddResponse response = new memberaddResponse();
            response.fromJson(jo);
            if (response.succeed == 1) {
                Intent intent = new Intent(this, C0_ServiceListActivity.class);
                //startActivity(intent);
                setResult(Activity.RESULT_OK, intent);
                finish();
                Message msg = new Message();
                msg.what = MessageConstant.SIGN_UP_SUCCESS;
                EventBus.getDefault().post(msg);
            } else {
                if (response.error_code == APIErrorCode.NICKNAME_EXIST) {
                    member_no.requestFocus();
                }
            }
        }else if(url.endsWith(ApiInterface.MEMBER_UPDATE)){
            memberaddResponse response = new memberaddResponse();
            response.fromJson(jo);
            if (response.succeed == 1) {
                Message msg = new Message();
                msg.what = MessageConstant.SIGN_UP_SUCCESS;
                EventBus.getDefault().post(msg);
            } else {
                if (response.error_code == APIErrorCode.NICKNAME_EXIST) {
                    member_name.requestFocus();
                }
            }
        }
    }
}
