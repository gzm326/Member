package com.firesoft.member.Activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.BeeQuery;
import com.BeeFramework.model.BusinessResponse;
import com.BeeFramework.view.ToastView;
import com.external.androidquery.callback.AjaxStatus;


import com.external.anyversion.AnyVersion;
import com.external.anyversion.NotifyStyle;
import com.firesoft.member.Protocol.ApiInterface;
import com.firesoft.member.R;

import org.json.JSONException;
import org.json.JSONObject;

public class McardAddActivity extends BaseActivity implements BusinessResponse{

    private TextView mAddComplete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.d1_mcardcc);
        mAddComplete = (TextView) findViewById(R.id.c0_publish_button1);
        mAddComplete.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                AnyVersion version = AnyVersion.getInstance();
                version.setURL(BeeQuery.serviceUrl()+ ApiInterface.SYSTEM_UPDATE);
                version.check(NotifyStyle.Dialog);
            }
        });



    }
    public  void ToastShow(String atr){
        ToastView toast = new ToastView(McardAddActivity.this, atr);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException {

    }
}
