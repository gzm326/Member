package com.firesoft.member.Activity;


import android.content.Intent;
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
import com.firesoft.member.MessageConstant;
import com.firesoft.member.Model.Product;
import com.firesoft.member.Model.GradeModel;
import com.firesoft.member.Protocol.ApiInterface;
import com.firesoft.member.Protocol.SIMPLE_GRADE;
import com.firesoft.member.Protocol.gradeaddResponse;
import com.firesoft.member.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class S2_GradeAddActivity extends BaseActivity implements BusinessResponse {

    private GradeModel mGradeModel;
    private EditText grade_name;
    private EditText discount_percent;
    private EditText credit_percent,validity;
    private EditText beg_money;
    private EditText beg_credit;
    private EditText rec_credit;
    private EditText rem;
    private TextView validity_unit,validity_unitid;


    private TextView mAddComplete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.s2_grade_add);

        grade_name = (EditText) findViewById(R.id.grade_name);
        discount_percent = (EditText) findViewById(R.id.discount_percent);
        credit_percent = (EditText) findViewById(R.id.credit_percent);
        validity = (EditText) findViewById(R.id.validity);
        validity_unit = (TextView) findViewById(R.id.validity_unit);
        validity_unitid = (TextView) findViewById(R.id.validity_unit_id);
        beg_money = (EditText) findViewById(R.id.beg_money);
        beg_credit = (EditText) findViewById(R.id.beg_credit);
        rec_credit = (EditText) findViewById(R.id.rec_credit);
        rem = (EditText) findViewById(R.id.rem);
        mAddComplete = (TextView) findViewById(R.id.c0_publish_button);

        mGradeModel = new GradeModel(this);
        mGradeModel.addResponseListener(this);


        validity_unit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent intent = new Intent(S2_GradeAddActivity.this, F3_RegionPickActivity.class);
                intent.putExtra("title", "有效期单位选择");
                intent.putExtra("str_url", ApiInterface.COMMENT_LIST);
                intent.putExtra("state", 1);
                startActivityForResult(intent, 1);
                overridePendingTransition(R.anim.my_scale_action, R.anim.my_alpha_action);
            }
        });


        mAddComplete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                String name = grade_name.getText().toString();
                String discount = discount_percent.getText().toString();
                String credit = credit_percent.getText().toString();
                String val = validity.getText().toString();
                String validity_unit_id = validity_unitid.getText().toString();
                String begMoney = beg_money.getText().toString();
                String begCredit = beg_credit.getText().toString();
                String recCredit = rec_credit.getText().toString();
                String rems = rem.getText().toString();



                if ("".equals(name)) {
                    ToastShow("会员分类名称不能为空");
                    grade_name.setText("");
                    grade_name.requestFocus();
                } else if("".equals(validity_unit_id)){
                    ToastShow("有效期单位不能为空");
                    validity_unit.requestFocus();

                }else {
                    SIMPLE_GRADE grade = new SIMPLE_GRADE();
                    grade.name=name;
                    grade.discount_percent=discount;
                    grade.validity_unit=validity_unit_id;
                    grade.credit_percent=credit;
                    grade.validity=val;
                    grade.beg_money=begMoney;
                    grade.beg_credit=begCredit;
                    grade.rec_credit=recCredit;
                    grade.rem=rems;
                    mGradeModel.add(grade);

                }

            }
        });
    }
    public  void ToastShow(String atr){
        ToastView toast = new ToastView(S2_GradeAddActivity.this, atr);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
            throws JSONException {
        // TODO Auto-generated method stub
        if (url.endsWith(ApiInterface.PRODUCT_ADD)) {
            gradeaddResponse response = new gradeaddResponse();
            response.fromJson(jo);
            if (response.succeed == 1) {
                /*Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);*/
                finish();
                Message msg = new Message();
                msg.what = MessageConstant.REFRESH_LIST;
                EventBus.getDefault().post(msg);
            }else {
                if (response.error_code == APIErrorCode.NICKNAME_EXIST) {
                    grade_name.requestFocus();
                }
            }
        }

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
                    validity_unit.setText(choose_name);
                    validity_unitid.setText(choose_id);

                    break;
                case 2:

                    break;

            }
        }
    }
}
