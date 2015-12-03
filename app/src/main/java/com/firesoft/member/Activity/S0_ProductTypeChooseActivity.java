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
import com.firesoft.member.Adapter.F3_RegionPickAdapter;
import com.firesoft.member.MemberAppConst;

import com.firesoft.member.Model.ChooseProductTypeModel;
import com.firesoft.member.Protocol.ApiInterface;
import com.firesoft.member.Protocol.chooselistResponse;
import com.firesoft.member.R;

import org.json.JSONException;
import org.json.JSONObject;

public class S0_ProductTypeChooseActivity extends Activity implements BusinessResponse {

    private TextView title;
    private ListView listView;
    private F3_RegionPickAdapter spinnerAdapter;
    private ChooseProductTypeModel chooseModel;

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

        chooseModel = new ChooseProductTypeModel(this);
        chooseModel.addResponseListener(this);
        chooseModel.getList(nShopid);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub

                choose_id = chooseModel.dataList.get(position).id;
                choose_name = chooseModel.dataList.get(position).name;

                //ToastShow(Integer.toString(choose_id));

                Intent intent = new Intent();
                intent.putExtra("uid", Integer.toString(choose_id));
                intent.putExtra("name", choose_name);

                setResult(choose_state, intent);
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
        if(url.endsWith(ApiInterface.PRODUCTTYPE_CHOOSELIST))
        {
            if (null != jo)
            {
                chooselistResponse response = new chooselistResponse();
                response.fromJson(jo);

                if (null == spinnerAdapter)
                {

                    spinnerAdapter = new F3_RegionPickAdapter(this, chooseModel.dataList);
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
