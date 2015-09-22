package com.firesoft.member.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.BusinessResponse;
import com.external.androidquery.callback.AjaxStatus;
import com.external.eventbus.EventBus;
import com.firesoft.member.R;

import org.json.JSONException;
import org.json.JSONObject;

public class C2_BuyDetailActivity extends BaseActivity implements BusinessResponse, View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c2__buy_detail);

        EventBus.getDefault().register(this);
    }

    @Override
    protected void onResume() {

        super.onResume();
    }

    private JSONObject myServiceListJo;
    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException {

    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.top_view_back:
                finish();
                break;

            /*case R.id.refresh:{
                if(mUserId ==SESSION.getInstance().uid){
                    mUserBalance.get();
                }
                mUserBalance.getProfile(mUserId);
                break;

            }*/
        }
    }


    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
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
}
