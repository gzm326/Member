package com.firesoft.member.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;


import com.BeeFramework.view.ToastView;
import com.firesoft.member.Activity.A1_ShopAddActivity;
import com.firesoft.member.Activity.McardAddActivity;
import com.firesoft.member.Activity.S0_ProductTypeListActivity;
import com.firesoft.member.Activity.S1_ProductListActivity;
import com.firesoft.member.Activity.S2_GradeListActivity;
import com.firesoft.member.MemberAppConst;
import com.firesoft.member.R;



//我
public class Fragment_Profile extends Fragment implements OnClickListener {
	private Activity ctx;
	private View layout;
	private TextView tvname, tv_accout;

	private SharedPreferences mShared;

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

	public  void ToastShow(String atr){
		ToastView toast = new ToastView(getActivity(), atr);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.view_user:
			//Utils.start_Activity(getActivity(), MyCodeActivity.class);
			break;
		case R.id.txt_album:// 门店维护
			/*Utils.start_Activity(getActivity(), PublicActivity.class,
					new BasicNameValuePair(Constants.NAME,
							getString(R.string.my_posts)));*/
			Intent mShopIntent = new Intent(getActivity(), A1_ShopAddActivity.class);
			startActivity(mShopIntent);
			getActivity().overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
			break;

		case R.id.txt_collect:// 会员级别维护
			/*Utils.start_Activity(getActivity(), PublicActivity.class,
					new BasicNameValuePair(Constants.NAME,
							getString(R.string.collection)));*/
			Intent mGradeIntent = new Intent(getActivity(), S2_GradeListActivity.class);
			startActivity(mGradeIntent);
			getActivity().overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
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
		case R.id.txt_producttype:// 项目分类维护
			Intent mProductTypeIntent = new Intent(getActivity(), S0_ProductTypeListActivity.class);
			startActivity(mProductTypeIntent);
			getActivity().overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
			break;

		case R.id.txt_product:
			Intent mProductIntent = new Intent(getActivity(), S1_ProductListActivity.class);
			startActivity(mProductIntent);
			getActivity().overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
			break;
		case R.id.txt_setting:// 项目维护
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