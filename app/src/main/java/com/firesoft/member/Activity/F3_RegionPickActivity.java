package com.firesoft.member.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.BeeFramework.view.ToastView;
import com.external.androidquery.callback.AjaxStatus;
import com.BeeFramework.model.BusinessResponse;
import com.firesoft.member.Model.ChooseModel;
import com.firesoft.member.Protocol.ApiInterface;
import com.firesoft.member.Protocol.chooselistResponse;
import com.firesoft.member.R;
import com.firesoft.member.Adapter.F3_RegionPickAdapter;

import org.json.JSONException;
import org.json.JSONObject;

public class F3_RegionPickActivity extends Activity implements BusinessResponse {

	private TextView title;
	private ListView listView;
	private F3_RegionPickAdapter spinnerAdapter;
	private ChooseModel chooseModel;
	
	private int choose_id;
	private String choose_name;
	private String str_title;
	private int choose_state;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.f3_region_pick);

		Intent intent = getIntent();
		str_title = intent.getStringExtra("title");
		choose_state=intent.getIntExtra("state",0);
        ToastShow(str_title);

		
		title = (TextView) findViewById(R.id.choose_title);
		listView = (ListView) findViewById(R.id.choose_list);


        title.setText(str_title);

		chooseModel = new ChooseModel(this);
		chooseModel.addResponseListener(this);
		chooseModel.getList();

		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				// TODO Auto-generated method stub

				choose_id = chooseModel.dataList.get(position).id;
				choose_name = chooseModel.dataList.get(position).name;

				Intent intent = new Intent();
				intent.putExtra("uid", choose_id);
				intent.putExtra("name", choose_name);

				setResult(choose_state, intent);
				finish();

			}
		});
	}

	public  void ToastShow(String atr){
		ToastView toast = new ToastView(F3_RegionPickActivity.this, atr);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}
	
	@Override
	public void OnMessageResponse(String url, JSONObject jo, AjaxStatus status)
			throws JSONException {
		if(url.endsWith(ApiInterface.CHOOSE_LIST))
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
