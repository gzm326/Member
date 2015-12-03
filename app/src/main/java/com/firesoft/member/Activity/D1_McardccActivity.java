package com.firesoft.member.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;


import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.BusinessResponse;
import com.BeeFramework.view.ToastView;
import com.external.androidquery.callback.AjaxStatus;
import com.external.eventbus.EventBus;
import com.firesoft.member.APIErrorCode;
import com.firesoft.member.MemberAppConst;
import com.firesoft.member.MessageConstant;
import com.firesoft.member.Model.MemberModel;
import com.firesoft.member.Model.NumberHistoryModel;
import com.firesoft.member.Model.Product;
import com.firesoft.member.Protocol.ApiInterface;
import com.firesoft.member.Protocol.SIMPLE_MEMBER;
import com.firesoft.member.Protocol.SIMPLE_NUMBERHISTORY;
import com.firesoft.member.Protocol.SIMPLE_PRODUCT;
import com.firesoft.member.Protocol.numberhistoryaddResponse;
import com.firesoft.member.Protocol.memberaddResponse;
import com.firesoft.member.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;


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
    private EditText edt_ccnum;
    private TextView txv_ccmoney;
    private TextView txv_yh_ccmoney;
    private EditText edt_sf_ccmoney;
    private MemberModel mDataModel;
    private NumberHistoryModel mNumberModel;
    private SharedPreferences mShared;
    private SIMPLE_PRODUCT product;
    private TextView   mTitleTextView;
    private DecimalFormat df;
    private RelativeLayout rl_number;

    private TextView mAddComplete;
    private String nShopid;
    private String nShopname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.d1_mcardcc);
        mShared =getSharedPreferences(MemberAppConst.USERINFO, 0);
        nShopid = mShared.getString("shopid", "0");
        nShopname = mShared.getString("shopname", "");
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
        edt_ccnum=(EditText)findViewById(R.id.cc_num);
        txv_ccmoney=(TextView)findViewById(R.id.cc_money);
        txv_yh_ccmoney=(TextView)findViewById(R.id.cc_yh_money);
        edt_sf_ccmoney=(EditText)findViewById(R.id.cc_sf_money);
        edt_keyword=(EditText)findViewById(R.id.edt_keyword);
        mAddComplete = (TextView) findViewById(R.id.c0_publish_button1);
        mTitleTextView = (TextView)findViewById(R.id.top_view_title);
        rl_number = (RelativeLayout)findViewById(R.id.number_sy);
        df = new DecimalFormat("#.00");
        mTitleTextView.setText("会员充次");

        edt_ccnum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {


            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String str_num = edt_ccnum.getText().toString();
                if (null != product) {
                    Double dSum = Integer.parseInt(str_num) * Double.valueOf(product.price);
                    Double dtj = Integer.parseInt(str_num) * Double.valueOf(product.specia_price);
                    if (product.special == "1") {
                        txv_yh_ccmoney.setText(String.valueOf(df.format(dSum - dtj)));
                        edt_sf_ccmoney.setText(String.valueOf(df.format(dtj)));
                    } else {
                        txv_yh_ccmoney.setText("0.00");
                        edt_sf_ccmoney.setText(String.valueOf(df.format(dSum)));
                    }

                    txv_ccmoney.setText(String.valueOf(df.format(dSum)));

                }
            }
        });

        btn_qd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String tmp = edt_keyword.getText().toString();
                String str = "00000000" + tmp;
                String member_no = str.substring(str.length() - 8, str.length());


                if ("".equals(member_no)) {
                    ToastShow("请输入会员卡号！");
                    edt_keyword.setText("");
                    edt_keyword.requestFocus();
                }
                mDataModel.getinfo(member_no, nShopid);


            }
        });
        txv_cc_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(D1_McardccActivity.this, D1_ProductChooseActivity.class);
                intent.putExtra("title", "充次项目选择");
                intent.putExtra("state",2);
                startActivityForResult(intent, 1);
                overridePendingTransition(R.anim.my_scale_action, R.anim.my_alpha_action);
            }
        });
        txv_pay_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(D1_McardccActivity.this, F3_RegionPickActivity.class);
                intent.putExtra("title", "支付类别选择");
                intent.putExtra("state",3);
                intent.putExtra("shopid","0");
                intent.putExtra("type_id","1");
                startActivityForResult(intent, 1);
                overridePendingTransition(R.anim.my_scale_action, R.anim.my_alpha_action);
            }
        });

        rl_number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String member_no = txv_no.getText().toString();
                if ("--".equals(member_no)) {
                    ToastShow("请选择会员信息！");
                    edt_keyword.setText("");
                    edt_keyword.requestFocus();
                }else{
                    Intent intent = new Intent(D1_McardccActivity.this, D1_NumberListActivity.class);
                    intent.putExtra("shopid",nShopid);
                    intent.putExtra("member_no",member_no);
                    startActivity(intent);
                    D1_McardccActivity.this.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                }

            }
        });
        mDataModel = new MemberModel(this);
        mDataModel.addResponseListener(this);

        mNumberModel = new NumberHistoryModel(this);
        mNumberModel.addResponseListener(this);

        mAddComplete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                String member_no = txv_no.getText().toString();
                String real = edt_ccnum.getText().toString();
                String give = edt_sf_ccmoney.getText().toString();
                String ys = txv_ccmoney.getText().toString();
                String yh = txv_yh_ccmoney.getText().toString();
                String payid = txv_payid_item.getText().toString();
                String pay_wx="0.00",pay_ali="0.00",pay_cash="0.00",pay_card="0.00",pay_balance="0.00";


                if(payid=="5"){
                    pay_wx=give;
                }else if(payid=="4"){
                    pay_ali=give;
                } else if(payid=="3"){
                    pay_card=give;
                }else if(payid=="2"){
                    pay_balance=give;
                }else{
                    pay_cash=give;
                }

                int userid = mShared.getInt("uid", 0);


                if ("--".equals(member_no)) {
                    ToastShow("请选择会员信息！");
                    edt_keyword.setText("");
                    edt_keyword.requestFocus();
                }  else if ("".equals(real)) {
                    ToastShow("次数不能为空");
                    txv_pay_item.requestFocus();

                } else if ("".equals(payid)) {
                    ToastShow("支付方式不能为空");
                    txv_pay_item.requestFocus();

                } else if ("".equals(give)) {
                    ToastShow("实收金额不能为空");
                    edt_sf_ccmoney.setText("0");
                    edt_sf_ccmoney.requestFocus();

                } else if (product==null) {
                    ToastShow("请选择充次项目");
                    txv_cc_item.requestFocus();

                }else {
                    SIMPLE_NUMBERHISTORY obj = new SIMPLE_NUMBERHISTORY();
                    obj.member_no = member_no;
                    obj.product_id = Integer.toString(product.id);
                    obj.product_name = product.name;
                    obj.type_id = Integer.toString(product.type_id);
                    obj.type_name = product.type_name;
                    obj.num = real;
                    obj.sale_price = product.price;
                    obj.sum =ys ;
                    obj.pay_ali=pay_ali ;
                    obj.pay_balance=pay_balance ;
                    obj.pay_card=pay_card;
                    obj.pay_cash=pay_cash;
                    obj.pay_wx=pay_wx;
                    obj.product_price = product.price;
                    obj.special = product.special;
                    obj.specia_price = product.specia_price;
                    obj.unit = product.unit;
                    obj.special_discount = product.special_discount;
                    obj.special_credit = product.special_credit;
                    obj.oper = Integer.toString(userid);
                    obj.opername = Integer.toString(userid);
                    obj.shopid = nShopid;
                    obj.shopname = nShopname;
                    mNumberModel.add(obj);
                    //mChargeModel.add(charge);

                }

            }
        });
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
            }else if (url.endsWith(ApiInterface.NUMBERHISTORY_ADD)) {
                numberhistoryaddResponse response = new numberhistoryaddResponse();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ArrayList<Product> productArrayList;
        super.onActivityResult(requestCode, resultCode, data);
        String choose_name,choose_id,pay_name,pay_id;

        if(requestCode == 1){
            switch(resultCode)
            {
                case 2:
                    product=(SIMPLE_PRODUCT)data.getSerializableExtra("product");
                    choose_name=product.name;
                    choose_id=Integer.toString(product.id);
                    txv_cc_item.setText(choose_name);
                    txv_ccid_item.setText(choose_id);
                    break;
                case 3:
                    pay_id=data.getStringExtra("uid");
                    pay_name=data.getStringExtra("name");

                    txv_pay_item.setText(pay_name);
                    txv_payid_item.setText(pay_id);
                    break;

            }
        }
    }

}
