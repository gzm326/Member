package com.firesoft.member.Activity;


import android.content.Intent;
import android.os.Bundle;


import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.BeeFramework.model.BusinessResponse;
import com.BeeFramework.view.ToastView;

import com.external.androidquery.callback.AjaxStatus;
import com.external.eventbus.EventBus;
import com.firesoft.member.Fragment.A0_HomeFragment;

import com.firesoft.member.MemberAppConst;
import com.firesoft.member.MessageConstant;
import com.firesoft.member.Model.UserModel;
import com.firesoft.member.Protocol.ApiInterface;
import com.firesoft.member.Protocol.userchange_profileRequest;
import com.firesoft.member.Protocol.userchange_profileResponse;
import com.firesoft.member.Protocol.usersigninResponse;
import com.firesoft.member.R;
import android.content.SharedPreferences;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firesoft.member.Fragment.Fragment_Profile;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends FragmentActivity implements BusinessResponse {

    private A0_HomeFragment mA0HomeFragment;
    private Fragment[] fragments;
    private Fragment_Profile profilefragment;

    private TextView txt_title;
    private ImageView[] imagebuttons;
    private TextView[] textviews;
    private int index;
    private int currentTabIndex;// ��ǰfragment��index
    private UserModel mUserModel;



    private     SharedPreferences mShared;
    private     SharedPreferences.Editor mEditor;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        mUserModel= new UserModel(this);
        mUserModel.addResponseListener(this);

        mUserModel.getinfo();

        mShared =getSharedPreferences(MemberAppConst.USERINFO, 0);
        mEditor = mShared.edit();


       /* mEditor = mShared.edit();
        EventBus.getDefault().register(this);*/
    }

    public  void ToastShow(String atr){
        ToastView toast = new ToastView(this, atr);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    private void init() {

        txt_title = (TextView) findViewById(R.id.txt_title);

        //FragmentTransaction t = this.getSupportFragmentManager().beginTransaction();

        mA0HomeFragment = new A0_HomeFragment();
        //mHomeFragment = new HomeFragment();
        profilefragment = new Fragment_Profile();

        fragments = new Fragment[] { mA0HomeFragment, mA0HomeFragment,
                mA0HomeFragment, profilefragment };
        imagebuttons = new ImageView[4];
        imagebuttons[0] = (ImageView) findViewById(R.id.ib_weixin);
        imagebuttons[1] = (ImageView) findViewById(R.id.ib_contact_list);
        imagebuttons[2] = (ImageView) findViewById(R.id.ib_find);
        imagebuttons[3] = (ImageView) findViewById(R.id.ib_profile);

        imagebuttons[0].setSelected(true);
        textviews = new TextView[4];
        textviews[0] = (TextView) findViewById(R.id.tv_weixin);
        textviews[1] = (TextView) findViewById(R.id.tv_contact_list);
        textviews[2] = (TextView) findViewById(R.id.tv_find);
        textviews[3] = (TextView) findViewById(R.id.tv_profile);
        textviews[0].setTextColor(0xFF45C01A);

        //
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, mA0HomeFragment)
                .add(R.id.fragment_container, profilefragment)
                .hide(profilefragment)
                .show(mA0HomeFragment).commit();


        /*t.replace(R.id.fragment_container, mA0HomeFragment);
        t.commit();*/

        //FragmentManager fm = getFragmentManager();
        /*FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        mHomeFragment = new HomeFragment();
        transaction.replace(R.id.fragment_container, mHomeFragment);
        transaction.commit();*/

    }

    public void onTabClicked(View view) {
        switch (view.getId()) {
            case R.id.re_weixin:
                index = 0;
                txt_title.setText("会员");
                break;
            case R.id.re_contact_list:
                index = 1;
                txt_title.setText("报表");
                break;
            case R.id.re_find:
                index = 2;
                txt_title.setText("发现");
                break;
            case R.id.re_profile:
                index = 3;
                txt_title.setText("设置");
                break;
        }
        if (currentTabIndex != index) {
            FragmentTransaction trx = getSupportFragmentManager()
                    .beginTransaction();
            trx.hide(fragments[currentTabIndex]);
            if (!fragments[index].isAdded()) {
                trx.add(R.id.fragment_container, fragments[index]);
            }
            trx.show(fragments[index]).commit();
        }
        imagebuttons[currentTabIndex].setSelected(false);
        //
        imagebuttons[index].setSelected(true);
        textviews[currentTabIndex].setTextColor(0xFF999999);
        textviews[index].setTextColor(0xFF45C01A);
        currentTabIndex = index;
    }

    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
            throws JSONException {

       /* if (url.endsWith(ApiInterface.USER_SIGNIN)) {
            userchange_profileResponse response = new userchange_profileResponse();
            response.fromJson(jo);
            if (response.succeed == 1) {

                mEditor.putString("shopid", response.user.shopid);
                mEditor.putString("shopname", response.user.shopname);
                mEditor.commit();
                ToastShow(response.user.shopid);
                Message msg = new Message();
                msg.what = MessageConstant.SIGN_IN_SUCCESS;
                EventBus.getDefault().post(msg);

            }else{

            }

        }*/
    }



}
