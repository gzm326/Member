package com.firesoft.member.Activity;



import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.view.Gravity;
import android.view.View;

import android.widget.Button;
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
import com.firesoft.member.Model.ChargeModel;
import com.firesoft.member.Model.MemberModel;
import com.firesoft.member.Protocol.ApiInterface;
import com.firesoft.member.Protocol.SIMPLE_CHARGE;
import com.firesoft.member.Protocol.SIMPLE_MEMBER;
import com.firesoft.member.Protocol.memberaddResponse;
import com.firesoft.member.Protocol.chargeaddResponse;
import com.firesoft.member.R;

import org.json.JSONException;
import org.json.JSONObject;

public class D0_McardycActivity extends BaseActivity implements BusinessResponse {

    private TextView txv_no;
    private TextView txv_name;
    private TextView txv_type;
    private TextView txv_state;
    private TextView txv_money;
    private TextView txv_integrals;
    private TextView txv_phone;
    private TextView txv_real;
    private TextView txv_give;
    private Button btn_qd;
    private EditText edt_keyword;
    private MemberModel mMemberModel;
    private ChargeModel mChargeModel;
    private SharedPreferences mShared;
    private TextView   mTitleTextView;

    private TextView mAddComplete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.c2_mcard_yjje);
        mShared =getSharedPreferences(MemberAppConst.USERINFO, 0);
        init_qd();
    }

    private void init_qd(){
        txv_no=(TextView)findViewById(R.id.txv_kh);
        txv_name=(TextView)findViewById(R.id.txv_name);
        txv_type=(TextView)findViewById(R.id.txv_type);
        txv_state=(TextView)findViewById(R.id.txv_state);
        txv_money=(TextView)findViewById(R.id.txv_money);
        txv_integrals=(TextView)findViewById(R.id.txv_integrals);
        txv_phone=(TextView)findViewById(R.id.txv_phone);

        txv_real=(TextView)findViewById(R.id.edt_money);
        txv_give=(TextView)findViewById(R.id.txv_present);

        btn_qd=(Button)findViewById(R.id.btn_qd);
        edt_keyword=(EditText)findViewById(R.id.edt_keyword);
        mAddComplete = (TextView) findViewById(R.id.c0_publish_button1);
        mTitleTextView = (TextView)findViewById(R.id.top_view_title);

        mTitleTextView.setText("会员预存");

        btn_qd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tmp = edt_keyword.getText().toString();
                String str = "00000000"+tmp;
                String member_no=str.substring(str.length()-8,str.length());


                String nShopid=mShared.getString("shopid", "0");
                if ("".equals(member_no)) {
                    ToastShow("请输入会员卡号！");
                    edt_keyword.setText("");
                    edt_keyword.requestFocus();
                }
                mMemberModel.getinfo(member_no,nShopid);


            }
        });

        mAddComplete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                String member_no = txv_no.getText().toString();
                String real = txv_real.getText().toString();
                String give=txv_give.getText().toString();

                int userid = mShared.getInt("uid", 0);
                String nShopid=mShared.getString("shopid", "0");
                String nShopname=mShared.getString("shopname", "");

                if ("".equals(real)) {
                    ToastShow("充值金额不能为空");
                    txv_real.setText("0");
                    txv_real.requestFocus();
                } else if("".equals(give)){
                    ToastShow("赠送金额不能为空");
                    txv_give.setText("0");
                    txv_give.requestFocus();

                }else {
                    SIMPLE_CHARGE charge = new SIMPLE_CHARGE();
                    charge.member_no=member_no;
                    charge.real_money=real;
                    charge.give_money=give;
                    charge.oper=Integer.toString(userid);
                    charge.opername=Integer.toString(userid);
                    charge.shopid=nShopid;
                    charge.shopname=nShopname;
                    mChargeModel.add(charge);

                }

            }
        });
        mMemberModel = new MemberModel(this);
        mMemberModel.addResponseListener(this);

        mChargeModel = new ChargeModel(this);
        mChargeModel.addResponseListener(this);
    }
    private  void init_null(){
        txv_no.setText("--");
        txv_name.setText("--");
        txv_type.setText("--");
        txv_state.setText("--");
        txv_money.setText("--");
        txv_integrals.setText("--");
        txv_phone.setText("--");
        txv_real.setText("0");
        txv_give.setText("0");
    }
    private  void init_member(SIMPLE_MEMBER member){
        txv_no.setText(member.member_no);
        txv_name.setText(member.member_name);
        txv_type.setText("普通会员");
        txv_state.setText("正常");
        txv_money.setText("00");
        txv_integrals.setText("00");
        txv_phone.setText(member.mobile_no);
    }

    public  void ToastShow(String atr){
        ToastView toast = new ToastView(D0_McardycActivity.this, atr);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException {

        if(url.endsWith(ApiInterface.MEMBER_MINFO))
        {
            if (null != jo)
            {
                memberaddResponse response = new memberaddResponse();
                response.fromJson(jo);
                //ToastShow(response.member.member_name);
                SIMPLE_MEMBER member;
                member = mMemberModel.member;
                if (null != member) {
                    //init_member(member);
                    if(null !=member.member_name) {
                        init_member(member);
                    }else {
                        ToastShow("会员卡号输入有误，无此会员信息！");
                        init_null();
                        edt_keyword.setText("");
                        edt_keyword.requestFocus();
                    }
                } else {
                    ToastShow("会员卡号输入有误，无此会员信息！");
                    init_null();
                    edt_keyword.setText("");
                    edt_keyword.requestFocus();
                }
            }
        }else if (url.endsWith(ApiInterface.CHARGE_ADD)) {
            chargeaddResponse response = new chargeaddResponse();
            response.fromJson(jo);
            if (response.succeed == 1) {
                init_null();
                Message msg = new Message();
                msg.what = MessageConstant.REFRESH_LIST;
                EventBus.getDefault().post(msg);
            }else {
                if (response.error_code == APIErrorCode.NICKNAME_EXIST) {

                }
            }
        }
    }
}
