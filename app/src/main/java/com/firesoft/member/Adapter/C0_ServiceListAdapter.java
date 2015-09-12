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

package com.firesoft.member.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.Utils.Utils;
import com.BeeFramework.adapter.BeeBaseAdapter;
import com.firesoft.member.Member;
import com.firesoft.member.Protocol.SIMPLE_USER;
import com.firesoft.member.R;
import com.firesoft.member.Utils.LocationManager;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

public class C0_ServiceListAdapter extends BeeBaseAdapter
{
    protected ImageLoader mImageLoader = ImageLoader.getInstance();

    public C0_ServiceListAdapter(Context c, ArrayList dataList)
    {
        super(c, dataList);
    }

    public class C0_UserCellHolder extends BeeCellHolder
    {
        TextView    c0_mcard_xm;
        TextView    c0_mcard_kh;
        TextView 	c0_mcard_phone;


    }

    @Override
    protected BeeCellHolder createCellHolder(View cellView)
    {
        C0_UserCellHolder holder = new C0_UserCellHolder();
        holder.c0_mcard_kh  = (TextView)cellView.findViewById(R.id.c0_mcard_kh);
        holder.c0_mcard_xm  = (TextView)cellView.findViewById(R.id.c0_mcard_xm);
        holder.c0_mcard_phone  	 = (TextView) cellView.findViewById(R.id.c0_mcard_phone);

        return holder;
    }

    @Override
    protected View bindData(int position, View cellView, ViewGroup parent, BeeCellHolder h)
    {
        SIMPLE_USER user = (SIMPLE_USER)dataList.get(position);
        C0_UserCellHolder holder = (C0_UserCellHolder)h;
        holder.c0_mcard_xm.setText(user.nickname);

        if (null != user.location && user.location.lat > 0 && user.location.lon > 0)
        {
            //holder.c0_mcard_kh.setText(LocationManager.getLocation(user.location.lat, user.location.lon));
            holder.c0_mcard_kh.setText("12345678");
        }
        if(user.current_service_price != null) {
        	//holder.c0_mcard_phone.setText(Utils.formatBalance(user.current_service_price));
            holder.c0_mcard_phone.setText("13937126072");
        }

        return null;
    }

    @Override
    public View createCellView()
    {
        return mInflater.inflate(R.layout.c0_user_cell,null);
    }
}
