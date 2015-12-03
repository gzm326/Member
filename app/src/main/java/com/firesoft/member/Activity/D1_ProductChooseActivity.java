package com.firesoft.member.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.BeeFramework.model.BusinessResponse;
import com.BeeFramework.view.ToastView;
import com.external.androidquery.callback.AjaxStatus;
import com.firesoft.member.Adapter.D1_ProductChooseAdater;
import com.firesoft.member.MemberAppConst;
import com.firesoft.member.Model.ProductListModel;
import com.firesoft.member.Protocol.ApiInterface;
import com.firesoft.member.Protocol.ENUM_SEARCH_ORDER;
import com.firesoft.member.Protocol.SIMPLE_PRODUCT;
import com.firesoft.member.Protocol.productlistResponse;
import com.firesoft.member.R;

import org.json.JSONException;
import org.json.JSONObject;

public class D1_ProductChooseActivity extends Activity implements BusinessResponse {

    private TextView title;
    private ListView listView;
    private D1_ProductChooseAdater spinnerAdapter;
    private ProductListModel chooseModel;

    private int choose_id;
    private String choose_name;
    private String str_title;
    private int choose_state;

    private SharedPreferences mShared;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.f3_region_pick);

        Intent intent = getIntent();
        str_title = intent.getStringExtra("title");
        choose_state=intent.getIntExtra("state", 0);



        title = (TextView) findViewById(R.id.choose_title);
        listView = (ListView) findViewById(R.id.choose_list);


        title.setText(str_title);

        mShared =getSharedPreferences(MemberAppConst.USERINFO, 0);

        String nShopid=mShared.getString("shopid", "0");

        chooseModel = new ProductListModel(this);
        chooseModel.addResponseListener(this);
        chooseModel.fetPreService(nShopid, ENUM_SEARCH_ORDER.location_asc);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub


                    SIMPLE_PRODUCT product = chooseModel.dataList.get(position);

                    Intent intent_profile = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("product", product);
                    intent_profile.putExtras(bundle);
                    setResult(choose_state, intent_profile);
                    finish();



            }
        });
    }

    public  void ToastShow(String atr){
        ToastView toast = new ToastView(this, atr);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    @Override
    public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
            throws JSONException {
        if(url.endsWith(ApiInterface.PRODUCT_LIST))
        {
            if (null != jo)
            {
                productlistResponse  response = new productlistResponse();
                response.fromJson(jo);

                if (null == spinnerAdapter)
                {

                    spinnerAdapter = new D1_ProductChooseAdater(this, chooseModel.dataList);
                    listView.setAdapter(spinnerAdapter);
                }
                else
                {
                    spinnerAdapter.notifyDataSetChanged();
                }

            }
        }
    }
}
