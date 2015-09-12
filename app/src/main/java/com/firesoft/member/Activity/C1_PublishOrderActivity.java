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


import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.BusinessResponse;
import com.external.androidquery.callback.AjaxStatus;

import com.firesoft.member.R;
import org.json.JSONException;
import org.json.JSONObject;

public class C1_PublishOrderActivity extends BaseActivity implements BusinessResponse {

    private TextView tv_xtkz;
    private LinearLayout mKzView;
    private LinearLayout lv_xtkz;
    private EditText et_kh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.c1_publish_order);

        tv_xtkz =(TextView)findViewById(R.id.mcard_xtkz);
        //lv_xtkz =(LinearLayout)findViewById(R.id.mcard_xtkz_layout);
        mKzView = (LinearLayout) findViewById(R.id.mcard_xtkz_view);

        //et_kh=(EditText)findViewById(R.id.mcard_kh);


        tv_xtkz.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(getApplicationContext(), "默认Toast样式",
                        Toast.LENGTH_SHORT).show();
                if (mKzView.getVisibility() == View.GONE) {
                    mKzView.setVisibility(View.VISIBLE);
                } else {
                    mKzView.setVisibility(View.GONE);
                   /* Handler mHandler = new Handler() {
                        @Override
                        public void handleMessage(Message msg) {
                            super.handleMessage(msg);
                            mKzView.setVisibility(View.GONE);
                        }
                    };
                    mHandler.sendEmptyMessageDelayed(0, 200);*/
                }
            }
        });


    }

    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
            throws JSONException {
        // TODO Auto-generated method stub

    }


    /*@Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }*/
}
