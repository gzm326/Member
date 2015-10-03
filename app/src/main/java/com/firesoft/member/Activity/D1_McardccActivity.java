package com.firesoft.member.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.BusinessResponse;
import com.BeeFramework.view.ToastView;
import com.external.androidquery.callback.AjaxStatus;
import com.firesoft.member.Model.MemberModel;
import com.firesoft.member.Protocol.ApiInterface;
import com.firesoft.member.Protocol.SIMPLE_MEMBER;
import com.firesoft.member.Protocol.memberaddResponse;
import com.firesoft.member.R;

import org.json.JSONException;
import org.json.JSONObject;

public class D1_McardccActivity extends BaseActivity implements BusinessResponse {

    private TextView txv_no;
    private TextView txv_name;
    private TextView txv_type;
    private TextView txv_state;
    private TextView txv_money;
    private TextView txv_integrals;
    private TextView txv_phone;
    private Button btn_qd;
    private EditText edt_keyword;
    private MemberModel mDataModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.d1_mcardcc);
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
        btn_qd=(Button)findViewById(R.id.btn_qd);
        edt_keyword=(EditText)findViewById(R.id.edt_keyword);
        btn_qd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String member_no = edt_keyword.getText().toString();
                if ("".equals(member_no)) {
                    ToastShow("请输入会员卡号！");
                    edt_keyword.setText("");
                    edt_keyword.requestFocus();
                }
                mDataModel.getinfo(member_no);


            }
        });
        mDataModel = new MemberModel(this);
        mDataModel.addResponseListener(this);
    }
    private  void init_null(){
        txv_no.setText("--");
        txv_name.setText("--");
        txv_type.setText("--");
        txv_state.setText("--");
        txv_money.setText("--");
        txv_integrals.setText("--");
        txv_phone.setText("--");
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
        ToastView toast = new ToastView(D1_McardccActivity.this, atr);
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
                member = mDataModel.member;
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
        }
    }

}
