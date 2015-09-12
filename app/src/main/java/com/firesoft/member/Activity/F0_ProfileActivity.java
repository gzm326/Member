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

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BeeFramework.Utils.ImageUtil;
import com.BeeFramework.Utils.Utils;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.BusinessResponse;
import com.BeeFramework.view.RoundedWebImageView;
import com.external.androidquery.callback.AjaxStatus;
import com.external.eventbus.EventBus;
import com.firesoft.member.Adapter.F0_ProfileServiceListAdapter;
import com.firesoft.member.Adapter.F0_ProfileServiceListGridAdapter;
import com.firesoft.member.Member;
import com.firesoft.member.MemberAppConst;
import com.firesoft.member.MessageConstant;
import com.firesoft.member.Model.UserBalanceModel;
import com.firesoft.member.Protocol.ApiInterface;
import com.firesoft.member.Protocol.ENUM_USER_GROUP;
import com.firesoft.member.Protocol.SERVICE_TYPE;
import com.firesoft.member.Protocol.USER;
import com.firesoft.member.Protocol.userprofileResponse;
import com.firesoft.member.R;
import com.firesoft.member.SESSION;
import com.firesoft.member.View.MyGridView;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.json.JSONException;
import org.json.JSONObject;


public class F0_ProfileActivity extends BaseActivity implements BusinessResponse, View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.c1_publish_order);

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
