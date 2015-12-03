package com.firesoft.member.Model;

import android.content.Context;

import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.BeeCallback;
import com.external.androidquery.callback.AjaxStatus;
import com.firesoft.member.MemberAppConst;
import com.firesoft.member.Protocol.ApiInterface;
import com.firesoft.member.Protocol.ENUM_SEARCH_ORDER;
import com.firesoft.member.Protocol.SIMPLE_GRADE;
import com.firesoft.member.Protocol.gradelistRequest;
import com.firesoft.member.Protocol.gradelistResponse;
import com.firesoft.member.SESSION;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/11/4.
 */
public class GradeListModel extends BaseModel {
    public ArrayList<SIMPLE_GRADE> dataList = new ArrayList<SIMPLE_GRADE>();

    public static final int NUMPERPAGE = 10;

    public GradeListModel(Context context)
    {
        super(context);
    }

    public void fetPreService(String shopid,ENUM_SEARCH_ORDER search_order)
    {
        gradelistRequest request = new gradelistRequest();

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
                    GradeListModel.this.callback(this, url, jo, status);
                    if (null != jo)
                    {
                        gradelistResponse response = new gradelistResponse();
                        response.fromJson(jo);

                        if(response.succeed == 1)
                        {
                            dataList.clear();
                            dataList.addAll(response.grades);
                            GradeListModel.this.OnMessageResponse(url,jo,status);
                        }
                        else
                        {
                            GradeListModel.this.callback(url, response.error_code, response.error_desc);
                        }
                    }else{
                        GradeListModel.this.OnMessageResponse(url,jo,status);
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
        if(isSendingMessage(ApiInterface.GRADE_LIST)){
            return;
        }
        cb.url(ApiInterface.GRADE_LIST).type(JSONObject.class).params(params);
        ajaxProgress(cb);

    }

    public void fetNextService(String shopid,ENUM_SEARCH_ORDER search_order)
    {
        gradelistRequest request = new gradelistRequest();

        request.sid = SESSION.getInstance().sid;
        request.uid = SESSION.getInstance().uid;
        request.sort_by = search_order.value();
        request.ver = MemberAppConst.VERSION_CODE;
        request.count = NUMPERPAGE;
        request.shopid=shopid;

        if (dataList.size() > 0)
        {
            request.by_no = (int)Math.ceil(dataList.size()*1.0/NUMPERPAGE) +1;;
        }

        request.count = NUMPERPAGE;


        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                try {
                    GradeListModel.this.callback(this, url, jo, status);
                    if (null != jo)
                    {
                        gradelistResponse response = new gradelistResponse();
                        response.fromJson(jo);

                        if(response.succeed == 1)
                        {
                            dataList.addAll(response.grades);
                            GradeListModel.this.OnMessageResponse(url,jo,status);
                        }
                        else
                        {
                            GradeListModel.this.callback(url, response.error_code, response.error_desc);
                        }
                    }else{
                        GradeListModel.this.OnMessageResponse(url,jo,status);
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
        if(isSendingMessage(ApiInterface.GRADE_LIST)){
            return;
        }
        cb.url(ApiInterface.GRADE_LIST).type(JSONObject.class).params(params);
        ajax(cb);

    }
}
