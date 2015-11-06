package com.firesoft.member.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.Utils.ImageUtil;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.BusinessResponse;
import com.external.androidquery.callback.AjaxStatus;
import com.external.eventbus.EventBus;
import com.external.maxwin.view.IXListViewListener;
import com.external.maxwin.view.XListView;
import com.firesoft.member.Adapter.S1_ProductListAdapter;
import com.firesoft.member.MessageConstant;
import com.firesoft.member.Model.Product;
import com.firesoft.member.Model.ProductListModel;
import com.firesoft.member.Protocol.ApiInterface;
import com.firesoft.member.Protocol.ENUM_SEARCH_ORDER;
import com.firesoft.member.Protocol.SERVICE_TYPE;
import com.firesoft.member.Protocol.SIMPLE_PRODUCT;
import com.firesoft.member.Protocol.productlistResponse;
import com.firesoft.member.R;
import com.firesoft.member.Utils.LocationManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class S1_ProductListActivity extends BaseActivity implements BusinessResponse, IXListViewListener {

    S1_ProductListAdapter mListWithServiceAdapter;
    XListView mListView;
    ProductListModel mDataModel;
    SERVICE_TYPE mServiceType;
    ImageView mBackButton;
    TextView mTitleTextView;
    TextView                        mPublishButton;
    ENUM_SEARCH_ORDER search_order = ENUM_SEARCH_ORDER.location_asc;
    View footView               ;
    public static final int REQUESTCODE1 = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.s1_product);

        mTitleTextView = (TextView)findViewById(R.id.top_view_title);

        mListView = (XListView)findViewById(R.id.c0_user_list);
        mListView.setXListViewListener(this, 0);
        mListView.setPullLoadEnable(true);
        mListView.setPullRefreshEnable(true);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position - 1 >= 0 && position - 1 < mDataModel.dataList.size()) {
                    SIMPLE_PRODUCT product = mDataModel.dataList.get(position - 1);

                    Intent intent_profile = new Intent(S1_ProductListActivity.this, S1_ProductUpdateActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("product",product);
                    intent_profile.putExtras(bundle);

                    startActivityForResult(intent_profile, REQUESTCODE1 );
                    S1_ProductListActivity.this.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
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


        mDataModel = new ProductListModel(this);
        mDataModel.addResponseListener(this);
        mServiceType =  new SERVICE_TYPE();

        mServiceType.id=5;
        mServiceType.title="项目维护";

        if (null != mServiceType.title)
        {
            mTitleTextView.setText(mServiceType.title);
        }

        mDataModel.fetPreService(mServiceType.id, ENUM_SEARCH_ORDER.location_asc);


        mPublishButton = (TextView)findViewById(R.id.c0_publish_button1);
        mPublishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_profile = new Intent(S1_ProductListActivity.this, S1_ProductAddActivity.class);
                startActivity(intent_profile);
                S1_ProductListActivity.this.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
            }
        });

        EventBus.getDefault().register(this);

        LocationManager.getInstance().refreshLocation();

    }

    public void onEvent(Object event) {

        Message message = (Message) event;
        if (message.what == MessageConstant.REFRESH_LIST) {
            mDataModel.fetPreService(mServiceType.id, ENUM_SEARCH_ORDER.location_asc);
        }
    }


    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status) throws JSONException
    {

        mListView.stopRefresh();
        mListView.stopLoadMore();
        if(url.endsWith(ApiInterface.PRODUCT_LIST))
        {
            if (null != jo)
            {
                productlistResponse response = new productlistResponse();
                response.fromJson(jo);

                if (null == mListWithServiceAdapter)
                {
                    mListWithServiceAdapter = new S1_ProductListAdapter(this, mDataModel.dataList);
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

/*    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ArrayList<Product> productArrayList;
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUESTCODE1){
            if(resultCode == Activity.RESULT_OK){
                mDataModel.fetPreService(mServiceType.id, ENUM_SEARCH_ORDER.location_asc);
            }
        }
    }*/

}
