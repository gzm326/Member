package com.firesoft.member.Activity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Message;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.Utils.ImageUtil;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.BusinessResponse;
import com.BeeFramework.view.ToastView;
import com.external.androidquery.callback.AjaxStatus;
import com.external.eventbus.EventBus;
import com.external.maxwin.view.IXListViewListener;
import com.external.maxwin.view.XListView;

import com.firesoft.member.Adapter.S2_GradeListAdapter;
import com.firesoft.member.MemberAppConst;
import com.firesoft.member.MessageConstant;
import com.firesoft.member.Model.GradeListModel;
import com.firesoft.member.Protocol.ApiInterface;
import com.firesoft.member.Protocol.ENUM_SEARCH_ORDER;
import com.firesoft.member.Protocol.SERVICE_TYPE;
import com.firesoft.member.Protocol.SIMPLE_GRADE;
import com.firesoft.member.Protocol.gradelistResponse;
import com.firesoft.member.R;
import com.firesoft.member.Utils.LocationManager;

import org.json.JSONException;
import org.json.JSONObject;

public class S2_GradeListActivity extends BaseActivity implements BusinessResponse, IXListViewListener {

    S2_GradeListAdapter mListWithServiceAdapter;
    XListView mListView;
    GradeListModel  mDataModel;
    ImageView mBackButton;
    TextView mTitleTextView;
    TextView                        mPublishButton;
    ENUM_SEARCH_ORDER search_order = ENUM_SEARCH_ORDER.location_asc;
    View footView               ;
    public static final int REQUESTCODE1 = 1;
    private SharedPreferences mShared;
    private String nShopid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.s2_grade_list);

        mTitleTextView = (TextView)findViewById(R.id.top_view_title);

        mListView = (XListView)findViewById(R.id.c0_user_list);
        mListView.setXListViewListener(this, 0);
        mListView.setPullLoadEnable(true);
        mListView.setPullRefreshEnable(true);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position - 1 >= 0 && position - 1 < mDataModel.dataList.size()) {
                    SIMPLE_GRADE grade = mDataModel.dataList.get(position - 1);

                    Intent intent_profile = new Intent(S2_GradeListActivity.this, S2_GradeUpdateActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("grade", grade);
                    intent_profile.putExtras(bundle);

                    startActivityForResult(intent_profile, REQUESTCODE1);
                    S2_GradeListActivity.this.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                }
            }
        });


        mBackButton = (ImageView)findViewById(R.id.top_view_back);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        mShared =getSharedPreferences(MemberAppConst.USERINFO, 0);
        nShopid=mShared.getString("shopid", "0");
        mDataModel = new GradeListModel(this);
        mDataModel.addResponseListener(this);

        mTitleTextView.setText("会员分类维护");

        mDataModel.fetPreService(nShopid, ENUM_SEARCH_ORDER.location_asc);


        mPublishButton = (TextView)findViewById(R.id.c0_publish_button1);
        mPublishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_profile = new Intent(S2_GradeListActivity.this, S2_GradeAddActivity.class);
                startActivity(intent_profile);
                S2_GradeListActivity.this.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
            }
        });

        EventBus.getDefault().register(this);

        LocationManager.getInstance().refreshLocation();


    }


    public void onEvent(Object event) {

        Message message = (Message) event;
        if (message.what == MessageConstant.REFRESH_LIST) {
            mDataModel.fetPreService(nShopid, ENUM_SEARCH_ORDER.location_asc);
        }
    }


    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException
    {

        mListView.stopRefresh();
        mListView.stopLoadMore();
        if(url.endsWith(ApiInterface.GRADE_LIST))
        {
            if (null != jo)
            {
                gradelistResponse response = new gradelistResponse();
                response.fromJson(jo);

                if (null == mListWithServiceAdapter)
                {

                    mListWithServiceAdapter = new S2_GradeListAdapter(this, mDataModel.dataList);
                    mListView.setAdapter(mListWithServiceAdapter);
                    footView = new View(this);
                    footView.setEnabled(true);
                    footView.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ImageUtil.Dp2Px(this, 60)));
                    mListView.addFooterView(footView);
                }
                else
                {
                    mListWithServiceAdapter.notifyDataSetChanged();
                }
                mListView.stopLoadMore();
                if (0 == response.more)
                {
                    mListView.setPullLoadEnable(false);
                }

                Rect frame = new Rect();
                getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
                int statusBarHeight = frame.top;

                int listHeight = mListView.getHeight();
                int screenHeight = getResources().getDisplayMetrics().heightPixels - ImageUtil.Dp2Px(this, 50) - statusBarHeight;

                if (listHeight >= screenHeight)
                {
                    footView.setVisibility(View.VISIBLE);
                }
                else
                {
                    footView.setVisibility(View.GONE);
                    mListView.removeFooterView(footView);
                }
            }


        }

    }

    public  void ToastShow(String atr){
        ToastView toast = new ToastView(this, atr);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
    @Override
    public void onRefresh(int id) {
        mDataModel.fetPreService(nShopid, search_order);
    }

    @Override
    public void onLoadMore(int id) {
        mDataModel.fetNextService(nShopid, search_order);
    }

}
