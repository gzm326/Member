package com.firesoft.member.Activity;


import android.os.Bundle;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.BusinessResponse;
import com.external.androidquery.callback.AjaxStatus;


import com.firesoft.member.R;

import org.json.JSONException;
import org.json.JSONObject;

public class McardAddActivity extends BaseActivity implements BusinessResponse{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.d1_mcardcc);


    }

    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException {

    }
}
