package com.firesoft.member.Model;

import android.content.Context;

import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.BeeCallback;
import com.external.androidquery.callback.AjaxStatus;
import com.firesoft.member.MemberAppConst;
import com.firesoft.member.Protocol.ApiInterface;
import com.firesoft.member.Protocol.ENUM_SEARCH_ORDER;
import com.firesoft.member.Protocol.SIMPLE_NUMBER;
import com.firesoft.member.Protocol.numberlistRequest;
import com.firesoft.member.Protocol.numberlistResponse;
import com.firesoft.member.SESSION;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/12/3.
 */
public class NumberListModel extends BaseModel {
    public ArrayList<SIMPLE_NUMBER> dataList = new ArrayList<SIMPLE_NUMBER>();

    public static final int NUMPERPAGE = 10;

    public NumberListModel(Context context)
    {
        super(context);
    }

    public void fetPreService(SIMPLE_NUMBER number,ENUM_SEARCH_ORDER search_order)
    {
        numberlistRequest request = new numberlistRequest();

        request.sid = SESSION.getInstance().sid;
        request.uid = SESSION.getInstance().uid;
        request.by_no = 1;
        request.shopid=number.shopid;
        request.member_no=number.member_no;
        request.sort_by = search_order.value();
        request.ver = MemberAppConst.VERSION_CODE;
        request.count = NUMPERPAGE;



        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                try {
                    NumberListModel.this.callback(this, url, jo, status);
                    if (null != jo)
                    {
                        numberlistResponse response = new numberlistResponse();
                        response.fromJson(jo);

                        if(response.succeed == 1)
                        {
                            dataList.clear();
                            dataList.addAll(response.numbers);
                            NumberListModel.this.OnMessageResponse(url,jo,status);
                        }
                        else
                        {
                            NumberListModel.this.callback(url, response.error_code, response.error_desc);
                        }
                    }else{
                        NumberListModel.this.OnMessageResponse(url,jo,status);
                    }

                } catch (JSONException e) {

                }
            }
        };

        Map<String, Object> params = new HashMap<String, Object>();
        try {
            JSONObject jsonObject = request.toJson();
            params.put("json", request.toJson().toString());

        } catch (JSONException e) {

        }
        if(isSendingMessage(ApiInterface.NUMBER_LIST)){
            return;
        }
        cb.url(ApiInterface.NUMBER_LIST).type(JSONObject.class).params(params);
        ajaxProgress(cb);
    }

    public void fetNextService(SIMPLE_NUMBER number,ENUM_SEARCH_ORDER search_order)
    {
        numberlistRequest request = new numberlistRequest();

        request.sid = SESSION.getInstance().sid;
        request.uid = SESSION.getInstance().uid;
        request.shopid=number.shopid;
        request.member_no=number.member_no;
        request.sort_by = search_order.value();
        request.ver = MemberAppConst.VERSION_CODE;
        request.count = NUMPERPAGE;

        if (dataList.size() > 0)
        {
            request.by_no = (int)Math.ceil(dataList.size()*1.0/NUMPERPAGE) +1;;
        }

        request.count = NUMPERPAGE;


        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                try {
                    NumberListModel.this.callback(this, url, jo, status);
                    if (null != jo)
                    {
                        numberlistResponse response = new numberlistResponse();
                        response.fromJson(jo);

                        if(response.succeed == 1)
                        {
                            dataList.addAll(response.numbers);
                            NumberListModel.this.OnMessageResponse(url,jo,status);
                        }
                        else
                        {
                            NumberListModel.this.callback(url, response.error_code, response.error_desc);
                        }
                    }else{
                        NumberListModel.this.OnMessageResponse(url,jo,status);
                    }

                } catch (JSONException e) {

                }
            }
        };

        Map<String, Object> params = new HashMap<String, Object>();
        try {
            JSONObject requestJson = request.toJson();
            params.put("json", requestJson.toString());

        } catch (JSONException e) {

        }
        if(isSendingMessage(ApiInterface.NUMBER_LIST)){
            return;
        }
        cb.url(ApiInterface.NUMBER_LIST).type(JSONObject.class).params(params);
        ajax(cb);

    }
}
