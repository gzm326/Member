package com.firesoft.member.Model;

/**
 * Created by Administrator on 2015/9/28.
 */

import android.content.Context;
import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.BeeCallback;
import com.external.androidquery.callback.AjaxStatus;
import com.firesoft.member.MemberAppConst;
import com.firesoft.member.Protocol.*;
import com.firesoft.member.SESSION;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MemberListModel extends BaseModel{
    public ArrayList<SIMPLE_MEMBER> dataList = new ArrayList<SIMPLE_MEMBER>();
    public static final int NUMPERPAGE = 10;
    public MemberListModel(Context context)
    {
        super(context);
    }

    public void fetPreService(int serviceId,ENUM_SEARCH_ORDER search_order)
    {
        memberlistRequest memberrequest = new memberlistRequest();

        memberrequest.sid = SESSION.getInstance().sid;
        memberrequest.uid = SESSION.getInstance().uid;
        memberrequest.by_no = 1;
        memberrequest.sort_by = search_order.value();
        memberrequest.ver = MemberAppConst.VERSION_CODE;
        memberrequest.count = NUMPERPAGE;



        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                try {
                    MemberListModel.this.callback(this, url, jo, status);
                    if (null != jo)
                    {
                        memberlistResponse response = new memberlistResponse();
                        response.fromJson(jo);

                        if(response.succeed == 1)
                        {
                            dataList.clear();
                            dataList.addAll(response.members);
                            MemberListModel.this.OnMessageResponse(url,jo,status);
                        }
                        else
                        {
                            MemberListModel.this.callback(url, response.error_code, response.error_desc);
                        }
                    }else{
                        MemberListModel.this.OnMessageResponse(url,jo,status);
                    }

                } catch (JSONException e) {

                }
            }
        };

        Map<String, Object> params = new HashMap<String, Object>();
        try {
            JSONObject jsonObject = memberrequest.toJson();
            params.put("json", memberrequest.toJson().toString());

        } catch (JSONException e) {

        }
        if(isSendingMessage(ApiInterface.MEMBER_LIST)){
            return;
        }
        cb.url(ApiInterface.MEMBER_LIST).type(JSONObject.class).params(params);
        ajaxProgress(cb);

    }

    public void fetNextService(int serviceId,ENUM_SEARCH_ORDER search_order)
    {
        memberlistRequest memberrequest = new memberlistRequest();

        memberrequest.sid = SESSION.getInstance().sid;
        memberrequest.uid = SESSION.getInstance().uid;
        memberrequest.sort_by = search_order.value();
        memberrequest.ver = MemberAppConst.VERSION_CODE;
        memberrequest.count = NUMPERPAGE;

        if (dataList.size() > 0)
        {
            memberrequest.by_no = (int)Math.ceil(dataList.size()*1.0/NUMPERPAGE) +1;;
        }

        memberrequest.count = NUMPERPAGE;


        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                try {
                    MemberListModel.this.callback(this, url, jo, status);
                    if (null != jo)
                    {
                        memberlistResponse response = new memberlistResponse();
                        response.fromJson(jo);

                        if(response.succeed == 1)
                        {
                            dataList.addAll(response.members);
                            MemberListModel.this.OnMessageResponse(url,jo,status);
                        }
                        else
                        {
                            MemberListModel.this.callback(url, response.error_code, response.error_desc);
                        }
                    }else{
                        MemberListModel.this.OnMessageResponse(url,jo,status);
                    }

                } catch (JSONException e) {

                }
            }
        };

        Map<String, Object> params = new HashMap<String, Object>();
        try {
            JSONObject requestJson = memberrequest.toJson();
            params.put("json", requestJson.toString());

        } catch (JSONException e) {

        }
        if(isSendingMessage(ApiInterface.MEMBER_LIST)){
            return;
        }
        cb.url(ApiInterface.MEMBER_LIST).type(JSONObject.class).params(params);
        ajax(cb);

    }
}
