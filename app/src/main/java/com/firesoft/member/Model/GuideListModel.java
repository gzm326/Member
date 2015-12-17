package com.firesoft.member.Model;

import android.content.Context;

import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.BeeCallback;
import com.external.androidquery.callback.AjaxStatus;
import com.firesoft.member.MemberAppConst;
import com.firesoft.member.Protocol.ApiInterface;
import com.firesoft.member.Protocol.ENUM_SEARCH_ORDER;
import com.firesoft.member.Protocol.SIMPLE_GUIDE;
import com.firesoft.member.Protocol.guidelistRequest;
import com.firesoft.member.Protocol.guidelistResponse;
import com.firesoft.member.SESSION;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/12/12.
 */
public class GuideListModel extends BaseModel {
    public ArrayList<SIMPLE_GUIDE> dataList = new ArrayList<SIMPLE_GUIDE>();

    public static final int NUMPERPAGE = 10;

    public GuideListModel(Context context)
    {
        super(context);
    }

    public void fetPreService(String shopid,ENUM_SEARCH_ORDER search_order)
    {
        guidelistRequest request = new guidelistRequest();

        request.sid = SESSION.getInstance().sid;
        request.uid = SESSION.getInstance().uid;
        request.by_no = 1;
        request.shopid=shopid;
        request.sort_by = search_order.value();
        request.ver = MemberAppConst.VERSION_CODE;
        request.count = NUMPERPAGE;



        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                try {
                    GuideListModel.this.callback(this, url, jo, status);
                    if (null != jo)
                    {
                        guidelistResponse response = new guidelistResponse();
                        response.fromJson(jo);

                        if(response.succeed == 1)
                        {
                            dataList.clear();
                            dataList.addAll(response.guides);
                            GuideListModel.this.OnMessageResponse(url,jo,status);
                        }
                        else
                        {
                            GuideListModel.this.callback(url, response.error_code, response.error_desc);
                        }
                    }else{
                        GuideListModel.this.OnMessageResponse(url,jo,status);
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
        if(isSendingMessage(ApiInterface.GUIDE_LIST)){
            return;
        }
        cb.url(ApiInterface.GUIDE_LIST).type(JSONObject.class).params(params);
        ajaxProgress(cb);

    }

    public void fetNextService(String shopid,ENUM_SEARCH_ORDER search_order)
    {
        guidelistRequest request = new guidelistRequest();

        request.sid = SESSION.getInstance().sid;
        request.uid = SESSION.getInstance().uid;
        request.shopid=shopid;
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
                    GuideListModel.this.callback(this, url, jo, status);
                    if (null != jo)
                    {
                        guidelistResponse response = new guidelistResponse();
                        response.fromJson(jo);

                        if(response.succeed == 1)
                        {
                            dataList.addAll(response.guides);
                            GuideListModel.this.OnMessageResponse(url,jo,status);
                        }
                        else
                        {
                            GuideListModel.this.callback(url, response.error_code, response.error_desc);
                        }
                    }else{
                        GuideListModel.this.OnMessageResponse(url,jo,status);
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
        if(isSendingMessage(ApiInterface.GUIDE_LIST)){
            return;
        }
        cb.url(ApiInterface.GUIDE_LIST).type(JSONObject.class).params(params);
        ajax(cb);

    }
}
