package com.firesoft.member.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;


import com.firesoft.member.Activity.McardAddActivity;
import com.firesoft.member.Activity.S0_ProductTypeListActivity;
import com.firesoft.member.Activity.S1_ProductListActivity;
import com.firesoft.member.R;



//我
public class Fragment_Profile extends Fragment implements OnClickListener {
	private Activity ctx;
	private View layout;
	private TextView tvname, tv_accout;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (layout == null) {
			ctx = this.getActivity();
			layout = ctx.getLayoutInflater().inflate(R.layout.fragment_profile,
					null);
			initViews();
			initData();
			setOnListener();
		} else {
			ViewGroup parent = (ViewGroup) layout.getParent();
			if (parent != null) {
				parent.removeView(layout);
			}
		}
		return layout;
	}

	private void initViews() {
		tvname = (TextView) layout.findViewById(R.id.tvname);
		tv_accout = (TextView) layout.findViewById(R.id.tvmsg);
		/*String id = Utils.getValue(getActivity(), Constants.User_ID);
		tv_accout.setText(getString(R.string.wechat_id) + "：" + id);
		if (GloableParams.UserInfos != null) {
			String name = UserUtils.getUserName(ctx);
			if (name != null && !TextUtils.isEmpty(name))
				tvname.setText(name);
		}*/
	}

	private void setOnListener() {
		layout.findViewById(R.id.view_user).setOnClickListener(this);
		layout.findViewById(R.id.txt_album).setOnClickListener(this);
		layout.findViewById(R.id.txt_collect).setOnClickListener(this);
		layout.findViewById(R.id.txt_money).setOnClickListener(this);
		layout.findViewById(R.id.txt_card).setOnClickListener(this);
		layout.findViewById(R.id.txt_producttype).setOnClickListener(this);
		layout.findViewById(R.id.txt_product).setOnClickListener(this);
		layout.findViewById(R.id.txt_setting).setOnClickListener(this);
	}

	private void initData() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.view_user:
			//Utils.start_Activity(getActivity(), MyCodeActivity.class);
			break;
		case R.id.txt_album:// 相册
			/*Utils.start_Activity(getActivity(), PublicActivity.class,
					new BasicNameValuePair(Constants.NAME,
							getString(R.string.my_posts)));*/
			break;
		case R.id.txt_collect:// 收藏
			/*Utils.start_Activity(getActivity(), PublicActivity.class,
					new BasicNameValuePair(Constants.NAME,
							getString(R.string.collection)));*/
			break;
		case R.id.txt_money:// 钱包
			/*Utils.start_Activity(getActivity(), PublicActivity.class,
					new BasicNameValuePair(Constants.NAME,
							getString(R.string.wallet)));*/
			break;
		case R.id.txt_card:// 相册
			/*Utils.start_Activity(getActivity(), PublicActivity.class,
					new BasicNameValuePair(Constants.NAME,
							getString(R.string.card_bag)));*/
			break;
		case R.id.txt_producttype:// 表情
			Intent mProductTypeIntent = new Intent(getActivity(), S0_ProductTypeListActivity.class);
			startActivity(mProductTypeIntent);
			getActivity().overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
			break;

		case R.id.txt_product:
			Intent mProductIntent = new Intent(getActivity(), S1_ProductListActivity.class);
			startActivity(mProductIntent);
			getActivity().overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
			break;
		case R.id.txt_setting:// 设置
			//Utils.start_Activity(getActivity(), SettingActivity.class);
			Intent mUpdateIntent = new Intent(getActivity(), McardAddActivity.class);
			startActivity(mUpdateIntent);
			getActivity().overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
			break;
		default:
			break;
		}
	}


}