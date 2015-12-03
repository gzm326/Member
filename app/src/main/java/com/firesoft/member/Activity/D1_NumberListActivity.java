package com.firesoft.member.Activity;


import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
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
import com.firesoft.member.Adapter.D1_NumberListAdapter;
import com.firesoft.member.MessageConstant;
import com.firesoft.member.Model.NumberListModel;
import com.firesoft.member.Protocol.ApiInterface;
import com.firesoft.member.Protocol.ENUM_SEARCH_ORDER;
import com.firesoft.member.Protocol.SERVICE_TYPE;
import com.firesoft.member.Protocol.SIMPLE_NUMBER;
import com.firesoft.member.Protocol.numberlistResponse;
import com.firesoft.member.R;
import com.firesoft.member.Utils.LocationManager;

import org.json.JSONException;
import org.json.JSONObject;

public class D1_NumberListActivity extends BaseActivity implements BusinessResponse, IXListViewListener {

    D1_NumberListAdapter mListWithServiceAdapter;
    XListView mListView;
    NumberListModel mDataModel;
    SERVICE_TYPE mServiceType;
    ImageView mBackButton;
    TextView mTitleTextView;

    ENUM_SEARCH_ORDER search_order = ENUM_SEARCH_ORDER.location_asc;
    View footView               ;
    public static final int REQUESTCODE1 = 1;
    private SIMPLE_NUMBER number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.d1_number_list);

        number = new SIMPLE_NUMBER();

        Intent intent = getIntent();
        number.shopid = intent.getStringExtra("shopid");
        number.member_no=intent.getStringExtra("member_no");


        mTitleTextView = (TextView)findViewById(R.id.top_view_title);

        mListView = (XListView)findViewById(R.id.c0_user_list);
        mListView.setXListViewListener(this, 0);
        mListView.setPullLoadEnable(true);
        mListView.setPullRefreshEnable(true);



        mBackButton = (ImageView)findViewById(R.id.top_view_back);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mDataModel = new NumberListModel(this);
        mDataModel.addResponseListener(this);

        mTitleTextView.setText("项目剩余次数查询");

       mDataModel.fetPreService(number, ENUM_SEARCH_ORDER.location_asc);


       /*  EventBus.getDefault().register(this);

        LocationManager.getInstance().refreshLocation();*/
    }
    public void onEvent(Object event) {

       /* Message message = (Message) event;
        if (message.what == MessageConstant.REFRESH_LIST) {
            mDataModel.fetPreService(number, ENUM_SEARCH_ORDER.location_asc);
        }*/
    }


    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException
    {

        mListView.stopRefresh();
        mListView.stopLoadMore();
        if(url.endsWith(ApiInterface.NUMBER_LIST))
        {
            if (null != jo)
            {
                numberlistResponse response = new numberlistResponse();
                response.fromJson(jo);

                if (null == mListWithServiceAdapter)
                {
                    mListWithServiceAdapter = new D1_NumberListAdapter(this, mDataModel.dataList);
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
        mDataModel.fetPreService(number, search_order);
    }

    @Override
    public void onLoadMore(int id) {
        mDataModel.fetNextService(number, search_order);
    }
}
