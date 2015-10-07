package com.firesoft.member.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.BusinessResponse;
import com.BeeFramework.view.ToastView;
import com.external.androidquery.callback.AjaxStatus;
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
import java.util.Timer;

public class D1_McardccActivity extends BaseActivity implements BusinessResponse {

    private TextView txv_no;
    private TextView txv_name;
    private TextView txv_type;
    private TextView txv_state;
    private TextView txv_money;
    private TextView txv_integrals;
    private TextView txv_phone;
    private TextView txv_cc_item,txv_ccid_item;
    private TextView txv_pay_item,txv_payid_item;
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
        txv_cc_item=(TextView)findViewById(R.id.txv_cc_item);
        txv_pay_item=(TextView)findViewById(R.id.txv_pay_item);
        txv_ccid_item=(TextView)findViewById(R.id.txv_ccid_item);
        txv_payid_item=(TextView)findViewById(R.id.txv_payid_item);
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
        txv_cc_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(D1_McardccActivity.this, F3_RegionPickActivity.class);
                intent.putExtra("title", "充次项目选择");
                intent.putExtra("str_url",ApiInterface.COMMENT_LIST);
                intent.putExtra("state",2);
                startActivityForResult(intent, 1);
                overridePendingTransition(R.anim.my_scale_action,R.anim.my_alpha_action);
            }
        });
        txv_pay_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(D1_McardccActivity.this, F3_RegionPickActivity.class);
                intent.putExtra("title", "支付类别选择");
                intent.putExtra("str_url",ApiInterface.COMMENT_LIST);
                intent.putExtra("state",3);
                startActivityForResult(intent, 1);
                overridePendingTransition(R.anim.my_scale_action, R.anim.my_alpha_action);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ArrayList<Product> productArrayList;
        super.onActivityResult(requestCode, resultCode, data);
        String choose_name,choose_id;
        choose_name=data.getStringExtra("name");
        choose_id=data.getStringExtra("uid");
        if(requestCode == 1){
            switch(resultCode)
            {
                case 2:

                    txv_cc_item.setText(choose_name);
                    txv_ccid_item.setText(choose_id);
                    break;
                case 3:
                    txv_pay_item.setText(choose_name);
                    txv_payid_item.setText(choose_id);
                    break;

            }
        }
    }

}
