package com.firesoft.member.Activity;

//
//                       __
//                      /\ \   _
//    ____    ____   ___\ \ \_/ \           _____    ___     ___
//   / _  \  / __ \ / __ \ \    <     __   /\__  \  / __ \  / __ \
//  /\ \_\ \/\  __//\  __/\ \ \\ \   /\_\  \/_/  / /\ \_\ \/\ \_\ \
//  \ \____ \ \____\ \____\\ \_\\_\  \/_/   /\____\\ \____/\ \____/
//   \/____\ \/____/\/____/ \/_//_/         \/____/ \/___/  \/___/
//     /\____/
//     \/___/
//
//  Powered by BeeFramework
//

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.external.androidquery.callback.AjaxStatus;
import com.external.maxwin.view.IXListViewListener;
import com.external.maxwin.view.XListView;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.BusinessResponse;
import com.BeeFramework.view.MyDialog;
import com.firesoft.member.Model.Product;
import com.firesoft.member.Model.ProductDetail;
import com.firesoft.member.Model.UserListModel;
import com.firesoft.member.R;
import com.firesoft.member.Adapter.E4_HistoryAdapter;

import com.firesoft.member.Protocol.ApiInterface;

import com.firesoft.member.Protocol.ORDER_INFO;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class E4_HistoryActivity extends BaseActivity implements BusinessResponse, IXListViewListener {
	
	private ImageView back;
	private XListView xlistView;
	private E4_HistoryAdapter tradeAdapter;
	private View null_paView;
	private ArrayList<Product> productA2;
	private Product product1,product2,product3;
	private ProductDetail pd1,pd2,pd3;



	@Override
	protected void onCreate(Bundle savedInstanceState) {		
        Resources resource = (Resources) getBaseContext().getResources();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.e4_history);

		back = (ImageView) findViewById(R.id.top_view_back);
		back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {				
				finish();
			}
		});
				
		null_paView = findViewById(R.id.null_pager);
		xlistView = (XListView) findViewById(R.id.trade_list);
		xlistView.setPullLoadEnable(true);
		xlistView.setRefreshTime();
		xlistView.setXListViewListener(this, 1);

		productA2=initProduct();
		tradeAdapter= new E4_HistoryAdapter(this,productA2);
		xlistView.setAdapter(tradeAdapter);

	}


	private ArrayList<Product> initProduct(){
		ArrayList<Product> productAl = new ArrayList<Product>();
		product1= new Product();


		product1.str_productid="0101";
		product1.str_productname="面部护理";
		product1.it_maxnum=10;
		product1.it_num=1;

		/*pd1 = new ProductDetail();
		pd2 = new ProductDetail();
		pd3 = new ProductDetail();

		pd1.name="面部护理";
		pd1.num=1.00;
		pd1.total=10.00;

		pd2.name="面部护理";
		pd2.num=2.00;
		pd2.total=20.00;

		pd3.name="面部护理";
		pd3.num=3.00;
		pd3.total=30.00;

		product1.productDetails.add(pd1);
		product1.productDetails.add(pd2);
		product1.productDetails.add(pd3);*/


		productAl.add(product1);


		return productAl;
	}

	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
			throws JSONException {


	}

	@Override
	public void onRefresh(int id) {

	}

	@Override
	public void onLoadMore(int id) {

	}
    @Override
    public void onResume() {
        super.onResume();
        /*if(EcmobileManager.getUmengKey(this)!=null){
            MobclickAgent.onPageStart(getIntent().getStringExtra("flag"));
            MobclickAgent.onResume(this, EcmobileManager.getUmengKey(this),"");
        }*/
}
    @Override
    public void onPause() {
        super.onPause();
        /*if (EcmobileManager.getUmengKey(this) != null) {
            MobclickAgent.onPageEnd(getIntent().getStringExtra("flag"));
            MobclickAgent.onPause(this);
        }*/
    }


}
