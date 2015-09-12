package com.firesoft.member.Activity;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.BeeFramework.activity.BaseActivity;
import com.external.viewpagerindicator.CirclePageIndicator;
import com.firesoft.member.Adapter.LeadAdapter;
import com.firesoft.member.MemberAppConst;
import com.firesoft.member.R;

public class LeadActivity extends BaseActivity {
    private ViewPager leadViewPager;
    private CirclePageIndicator indicator;


    private int[] imageBg = {R.mipmap.guide1, R.mipmap.guide2, R.mipmap.guide3};

    private SharedPreferences mShared;
    private SharedPreferences.Editor mEditor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lead);
        mShared = getSharedPreferences(MemberAppConst.USERINFO, 0);
        mEditor = mShared.edit();

        leadViewPager = (ViewPager) findViewById(R.id.lead_viewPager);
        indicator = (CirclePageIndicator)findViewById(R.id.indicator);

        leadViewPager.setAdapter(new LeadAdapter(this, imageBg));
        indicator.setViewPager(leadViewPager,0);


        leadViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                // TODO Auto-generated method stub
                indicator.setCurrentItem(arg0);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub
            }
        });

    }

    @Override
    public void finish() {
        super.finish();

    }
}
