//
//       _/_/_/                      _/            _/_/_/_/_/
//    _/          _/_/      _/_/    _/  _/              _/      _/_/      _/_/
//   _/  _/_/  _/_/_/_/  _/_/_/_/  _/_/              _/      _/    _/  _/    _/
//  _/    _/  _/        _/        _/  _/          _/        _/    _/  _/    _/
//   _/_/_/    _/_/_/    _/_/_/  _/    _/      _/_/_/_/_/    _/_/      _/_/
//
//
//  Copyright (c) 2015-2016, Geek Zoo Studio
//  http://www.geek-zoo.com
//
//
//  Permission is hereby granted, free of charge, to any person obtaining a
//  copy of this software and associated documentation files (the "Software"),
//  to deal in the Software without restriction, including without limitation
//  the rights to use, copy, modify, merge, publish, distribute, sublicense,
//  and/or sell copies of the Software, and to permit persons to whom the
//  Software is furnished to do so, subject to the following conditions:
//
//  The above copyright notice and this permission notice shall be included in
//  all copies or substantial portions of the Software.
//
//  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
//  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
//  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
//  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
//  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
//  FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
//  IN THE SOFTWARE.
//

package com.firesoft.member.Fragment;


import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.firesoft.member.Activity.C0_ServiceListActivity;
import com.firesoft.member.Activity.D0_McardycActivity;
import com.firesoft.member.Activity.D1_McardccActivity;
import com.firesoft.member.Activity.E1_McardjcxfActivity;
import com.firesoft.member.Utils.LocationManager;
import com.firesoft.member.Activity.C1_PublishOrderActivity;
import com.external.eventbus.EventBus;

import com.firesoft.member.R;




public class A0_HomeFragment extends Fragment {

	private View view;
	private LinearLayout mcard_add,mcard_manage,mcard_ycje,mcard_yccc,mcard_jcxf;


	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		LocationManager locationManager = new LocationManager(getActivity());
        locationManager.refreshLocation();
		
		view = inflater.inflate(R.layout.a0_home, null);
		mcard_add= (LinearLayout)view.findViewById(R.id.left);
		mcard_manage=(LinearLayout)view.findViewById(R.id.center);
		mcard_ycje=(LinearLayout)view.findViewById(R.id.right);
		mcard_yccc=(LinearLayout)view.findViewById(R.id.left2);
		mcard_jcxf=(LinearLayout)view.findViewById(R.id.center2);


		mcard_add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getActivity(), C1_PublishOrderActivity.class);
				startActivity(intent);
				getActivity().overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);

			}
		});

		mcard_manage.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getActivity(), C0_ServiceListActivity.class);
				startActivity(intent);
				getActivity().overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);

			}
		});

		mcard_ycje.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getActivity(), D0_McardycActivity.class);
				startActivity(intent);
				getActivity().overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);

			}
		});

		mcard_yccc.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getActivity(), D1_McardccActivity.class);
				startActivity(intent);
				getActivity().overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);

			}
		});

		mcard_jcxf.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getActivity(), E1_McardjcxfActivity.class);
				startActivity(intent);
				getActivity().overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);

			}
		});



		

		
		if (!EventBus.getDefault().isregister(this)) {
            EventBus.getDefault().register(this);
        }
		
		return view;
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		view.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
            }
        });

	}
	


	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		if (EventBus.getDefault().isregister(this)) {
			EventBus.getDefault().unregister(this);
		}
		super.onDestroyView();
	}
	public void onEvent(Object event) {

	}
}

