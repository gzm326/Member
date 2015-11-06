package com.firesoft.member.Activity;

import android.app.Activity;
import android.content.Intent;
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
import com.external.activeandroid.util.Log;
import com.external.androidquery.callback.AjaxStatus;
import com.external.eventbus.EventBus;
import com.external.maxwin.view.IXListViewListener;
import com.external.maxwin.view.XListView;
import com.firesoft.member.Adapter.S0_ProductTypeListAdapter;
import com.firesoft.member.MessageConstant;
import com.firesoft.member.Model.Product;
import com.firesoft.member.Model.ProductTypeListModel;
import com.firesoft.member.Protocol.ApiInterface;
import com.firesoft.member.Protocol.ENUM_SEARCH_ORDER;
import com.firesoft.member.Protocol.SERVICE_TYPE;
import com.firesoft.member.Protocol.SIMPLE_PRODUCTTYPE;
import com.firesoft.member.Protocol.producttypelistResponse;
import com.firesoft.member.R;
import com.firesoft.member.Utils.LocationManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class S0_ProductTypeListActivity extends BaseActivity implements BusinessResponse, IXListViewListener {

    S0_ProductTypeListAdapter mListWithServiceAdapter;
    XListView mListView;
    ProductTypeListModel mDataModel;
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
        setContentView(R.layout.s0_product_type);

        mTitleTextView = (TextView)findViewById(R.id.top_view_title);

        mListView = (XListView)findViewById(R.id.c0_user_list);
        mListView.setXListViewListener(this, 0);
        mListView.setPullLoadEnable(true);
        mListView.setPullRefreshEnable(true);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position - 1 >= 0 && position - 1 < mDataModel.dataList.size()) {
                    SIMPLE_PRODUCTTYPE producttype = mDataModel.dataList.get(position - 1);

                    Intent intent_profile = new Intent(S0_ProductTypeListActivity.this, S0_ProductTypeUpdateActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("producttype",producttype);
                    intent_profile.putExtras(bundle);

                    startActivityForResult(intent_profile, REQUESTCODE1 );
                    S0_ProductTypeListActivity.this.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
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


        mDataModel = new ProductTypeListModel(this);
        mDataModel.addResponseListener(this);
        mServiceType =  new SERVICE_TYPE();

        mServiceType.id=5;
        mServiceType.title="项目分类维护";

        if (null != mServiceType.title)
        {
            mTitleTextView.setText(mServiceType.title);
        }

        mDataModel.fetPreService(mServiceType.id, ENUM_SEARCH_ORDER.location_asc);


        mPublishButton = (TextView)findViewById(R.id.c0_publish_button1);
        mPublishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_profile = new Intent(S0_ProductTypeListActivity.this, S0_ProductTypeAddActivity.class);
                startActivity(intent_profile);
                S0_ProductTypeListActivity.this.overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
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

        if(url.endsWith(ApiInterface.PRODUCTTYPE_LIST))
        {
            if (null != jo)
            {
                producttypelistResponse response = new producttypelistResponse();
                response.fromJson(jo);

                if (null == mListWithServiceAdapter)
                {
                    mListWithServiceAdapter = new S0_ProductTypeListAdapter(this, mDataModel.dataList);
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
        ToastView toast = new ToastView(S0_ProductTypeListActivity.this, atr);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    @Override
    public void onRefresh(int id) {
        mDataModel.fetPreService(mServiceType.id, search_order);
    }

    @Override
    public void onLoadMore(int id) {
        mDataModel.fetNextService(mServiceType.id, search_order);
    }

   /* protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ArrayList<Product> productArrayList;
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUESTCODE1){
            if(resultCode == Activity.RESULT_OK){
                mDataModel.fetPreService(mServiceType.id, ENUM_SEARCH_ORDER.location_asc);
            }
        }
    }*/
}
