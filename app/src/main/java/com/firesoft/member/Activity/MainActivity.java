package com.firesoft.member.Activity;


import android.os.Bundle;


import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import com.firesoft.member.Fragment.A0_HomeFragment;
import com.firesoft.member.R;
import android.content.SharedPreferences;


public class MainActivity extends FragmentActivity {

    private A0_HomeFragment mA0HomeFragment;



    private     SharedPreferences mShared;
    private     SharedPreferences.Editor mEditor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        /*mShared = this.getSharedPreferences(MemberAppConst.USERINFO, 0);
        mEditor = mShared.edit();
        EventBus.getDefault().register(this);*/
    }
    private void init() {

        FragmentTransaction t = this.getSupportFragmentManager().beginTransaction();

        mA0HomeFragment = new A0_HomeFragment();
        //mHomeFragment = new HomeFragment();
        t.replace(R.id.fragment_container, mA0HomeFragment);
        t.commit();

        //FragmentManager fm = getFragmentManager();
        /*FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        mHomeFragment = new HomeFragment();
        transaction.replace(R.id.fragment_container, mHomeFragment);
        transaction.commit();*/

    }



}
