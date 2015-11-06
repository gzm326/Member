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
import com.firesoft.member.Model.GradeModel;
import com.firesoft.member.Protocol.ApiInterface;
import com.firesoft.member.Protocol.SIMPLE_GRADE;
import com.firesoft.member.Protocol.gradeaddResponse;
import com.firesoft.member.R;

import org.json.JSONException;
import org.json.JSONObject;

public class S2_GradeUpdateActivity extends BaseActivity implements BusinessResponse, View.OnClickListener {

    private GradeModel mGradeModel;
    private EditText grade_name;
    private EditText discount_percent;
    private EditText credit_percent,validity;
    private EditText beg_money;
    private EditText beg_credit;
    private EditText rec_credit;
    private EditText rem;
    private TextView validity_unit,validity_unitid;

    private TextView mDelComplete;
    private LinearLayout mUpdateComplete;
    private SIMPLE_GRADE mGrade;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.s2_grade_update);

        Intent intent = getIntent();
        mGrade=(SIMPLE_GRADE)intent.getSerializableExtra("grade");
        init(mGrade);

        mGradeModel = new GradeModel(this);
        mGradeModel.addResponseListener(this);
    }

    private void init(SIMPLE_GRADE grade){
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

        mDelComplete = (TextView) findViewById(R.id.c0_del_button);
        mUpdateComplete = (LinearLayout) findViewById(R.id.top_member_upate);

        mUpdateComplete.setOnClickListener(this);
        mDelComplete.setOnClickListener(this);

        grade_name.setText(grade.name);
        discount_percent.setText(grade.discount_percent);
        credit_percent.setText(grade.credit_percent);
        validity.setText(grade.validity);
        validity_unit.setText(grade.validity_unit);
        validity_unitid.setText(grade.validity_unit);
        beg_money.setText(grade.beg_money);
        beg_credit.setText(grade.beg_credit);
        rec_credit.setText(grade.rec_credit);
        rem.setText(grade.rem);


    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.top_view_back:
                finish();
                break;
            case R.id.top_member_upate:
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
                    grade.id=mGrade.id;
                    mGradeModel.update(grade);

                }
                break;

            case R.id.c0_del_button:{
                mGradeModel.del(mGrade.id);
                break;
            }
        }
    }

    public  void ToastShow(String atr){
        ToastView toast = new ToastView(S2_GradeUpdateActivity.this, atr);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
            throws JSONException {
        // TODO Auto-generated method stub
        if (url.endsWith(ApiInterface.GRADE_DEL)) {
            gradeaddResponse response = new gradeaddResponse();
            response.fromJson(jo);
            if (response.succeed == 1) {
                Intent intent = new Intent(this, S0_ProductTypeListActivity.class);
                //startActivity(intent);
                setResult(Activity.RESULT_OK, intent);
                finish();
                Message msg = new Message();
                msg.what = MessageConstant.REFRESH_LIST;
                EventBus.getDefault().post(msg);
            } else {
                if (response.error_code == APIErrorCode.NICKNAME_EXIST) {
                    grade_name.requestFocus();
                }
            }
        }else if(url.endsWith(ApiInterface.GRADE_UPDATE)){
            gradeaddResponse response = new gradeaddResponse();
            response.fromJson(jo);
            if (response.succeed == 1) {
                Message msg = new Message();
                msg.what = MessageConstant.REFRESH_LIST;
                EventBus.getDefault().post(msg);
                ToastShow("保存成功");
            } else {
                if (response.error_code == APIErrorCode.NICKNAME_EXIST) {
                    grade_name.requestFocus();
                }
            }
        }
    }
}
