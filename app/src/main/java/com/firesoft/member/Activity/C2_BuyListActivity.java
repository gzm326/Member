package com.firesoft.member.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View.OnClickListener;

import com.BeeFramework.Utils.ImageUtil;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.BusinessResponse;
import com.BeeFramework.view.ToastView;
import com.external.androidquery.callback.AjaxStatus;
import com.external.maxwin.view.IXListViewListener;
import com.external.maxwin.view.XListView;
import com.firesoft.member.Adapter.C0_ServiceListAdapter;
import com.firesoft.member.Model.UserListModel;
import com.firesoft.member.Protocol.ApiInterface;
import com.firesoft.member.Protocol.ENUM_SEARCH_ORDER;
import com.firesoft.member.Protocol.SERVICE_TYPE;
import com.firesoft.member.Protocol.SIMPLE_USER;
import com.firesoft.member.Protocol.userlistResponse;
import com.firesoft.member.R;
import com.firesoft.member.Utils.LocationManager;

import org.json.JSONException;
import org.json.JSONObject;

public class C2_BuyListActivity extends BaseActivity implements BusinessResponse, IXListViewListener {

    C0_ServiceListAdapter mListWithServiceAdapter;
    XListView mListView;
    UserListModel mDataModel;
    SERVICE_TYPE mServiceType;
    ImageView mBackButton;
    TextView mTitleTextView;
    TextView                        mPublishButton;
    ENUM_SEARCH_ORDER search_order = ENUM_SEARCH_ORDER.location_asc;
    View footView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c2__buy_list);

        mTitleTextView = (TextView)findViewById(R.id.top_view_title);
        mListView = (XListView)findViewById(R.id.c0_user_list);
        mListView.setXListViewListener(this, 0);
        mListView.setPullLoadEnable(true);
        mListView.setPullRefreshEnable(true);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position - 1 >= 0 && position - 1 < mDataModel.dataList.size()) {
                    SIMPLE_USER user = mDataModel.dataList.get(position - 1);
                    Intent intent_profile = new Intent(C2_BuyListActivity.this, E4_HistoryActivity.class);
                    startActivity(intent_profile);
                    C2_BuyListActivity.this.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
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

       /* mMaskView = (View)findViewById(R.id.c0_mask_view);
        mMaskView.setClickable(true);
        mMaskView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //hideFilter();
            }
        });*/

        mDataModel = new UserListModel(this);
        mDataModel.addResponseListener(this);
        mServiceType =  new SERVICE_TYPE();
        //mServiceType = (SERVICE_TYPE) getIntent().getSerializableExtra(MemberAppConst.SERVICE_TYPE);
        mServiceType.id=5;
        mServiceType.title="会员档案";

        if (null != mServiceType.title)
        {
            mTitleTextView.setText(mServiceType.title);
        }

        mDataModel.fetPreService(mServiceType.id, ENUM_SEARCH_ORDER.location_asc);

       /* mFilterLayout = (LinearLayout)findViewById(R.id.c0_filter_layout);
        mFilterButton = (ImageView)findViewById(R.id.top_view_right_image);
        mFilterButton.setImageResource(R.drawable.b1_icon_filter);
        mFilterButton.setVisibility(View.VISIBLE);

        mFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showFilter();
            }
        });*/

        mPublishButton = (TextView)findViewById(R.id.c0_publish_button1);
        mPublishButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent it = new Intent(C0_ServiceListActivity.this, F0_ProfileActivity.class);
                it.putExtra(MemberAppConst.SERVICE_TYPE, mServiceType);
                startActivity(it);*/
            }
        });


        // initFilter();

        LocationManager.getInstance().refreshLocation();
    }


    public  void ToastShow(String atr){
        ToastView toast = new ToastView(C2_BuyListActivity.this, atr);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException
    {

        mListView.stopRefresh();
        mListView.stopLoadMore();
        if(url.endsWith(ApiInterface.USER_LIST))
        {
            if (null != jo)
            {
                userlistResponse response = new userlistResponse();
                response.fromJson(jo);

                if (null == mListWithServiceAdapter)
                {
                    mListWithServiceAdapter = new C0_ServiceListAdapter(this, mDataModel.dataList);
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

    @Override
    public void onRefresh(int id) {
        mDataModel.fetPreService(mServiceType.id, search_order);
    }

    @Override
    public void onLoadMore(int id) {
        mDataModel.fetNextService(mServiceType.id, search_order);
    }
}
