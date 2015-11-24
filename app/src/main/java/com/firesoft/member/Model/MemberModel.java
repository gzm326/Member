package com.firesoft.member.Model;

import android.content.Context;

import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.BeeCallback;
import com.external.androidquery.callback.AjaxStatus;
import com.firesoft.member.APIErrorCode;
import com.firesoft.member.Protocol.ApiInterface;
import com.firesoft.member.Protocol.SIMPLE_MEMBER;
import com.firesoft.member.Protocol.memberaddRequest;
import com.firesoft.member.Protocol.memberaddResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 15-10-1.
 */
public class MemberModel extends BaseModel {
    private Context context;
    public SIMPLE_MEMBER member;
    public MemberModel(Context context) {
        super(context);
        this.context = context;

    }
    public void add(SIMPLE_MEMBER member) {
        memberaddRequest request = new memberaddRequest();
        request.member_no =member.member_no;
        request.member_name = member.member_name;
        request.mobile_no = member.mobile_no;
        request.shopid=member.shopid;
        request.shopname=member.shopname;
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                try {
                    MemberModel.this.callback(this, url, jo, status);

                    if (null != jo) {
                        memberaddResponse response = new memberaddResponse();
                        response.fromJson(jo);
                        if (response.succeed == 1) {
                            MemberModel.this.OnMessageResponse(url, jo, status);
                        } else {
                            if(response.error_code== APIErrorCode.NICKNAME_EXIST){

                                MemberModel.this.OnMessageResponse(url, jo, status);
                            }
                            MemberModel.this.callback(url, response.error_code, response.error_desc);
                        }
                    }

                } catch (JSONException e) {

                }
                ;
            }
        };
        Map<String, String> params = new HashMap<String, String>();
        try {
            params.put("json", request.toJson().toString());
        } catch (JSONException e) {

        }

        cb.url(ApiInterface.MEMBER_ADD).type(JSONObject.class).params(params);
        //aq.ajax(cb);
        ajaxProgress(cb);
    }

    public void update(SIMPLE_MEMBER member) {
        memberaddRequest request = new memberaddRequest();

        request.member_no =member.member_no;
        request.member_name = member.member_name;
        request.mobile_no = member.mobile_no;
        request.shopid=member.shopid;
        request.shopname=member.shopname;
        request.uid=member.id;
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                try {
                    MemberModel.this.callback(this, url, jo, status);

                    if (null != jo) {
                        memberaddResponse response = new memberaddResponse();
                        response.fromJson(jo);
                        if (response.succeed == 1) {
                            MemberModel.this.OnMessageResponse(url, jo, status);
                        } else {
                            if(response.error_code== APIErrorCode.NICKNAME_EXIST){

                                MemberModel.this.OnMessageResponse(url, jo, status);
                            }
                            MemberModel.this.callback(url, response.error_code, response.error_desc);
                        }
                    }

                } catch (JSONException e) {

                }
                ;
            }
        };
        Map<String, String> params = new HashMap<String, String>();
        try {
            params.put("json", request.toJson().toString());
        } catch (JSONException e) {

        }

        cb.url(ApiInterface.MEMBER_UPDATE).type(JSONObject.class).params(params);
        //aq.ajax(cb);
        ajaxProgress(cb);
    }

    public void del(int uid) {
        memberaddRequest request = new memberaddRequest();

        request.uid=uid;
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                try {
                    MemberModel.this.callback(this, url, jo, status);

                    if (null != jo) {
                        memberaddResponse response = new memberaddResponse();
                        response.fromJson(jo);
                        if (response.succeed == 1) {
                            MemberModel.this.OnMessageResponse(url, jo, status);
                        } else {
                            if(response.error_code== APIErrorCode.NICKNAME_EXIST){

                                MemberModel.this.OnMessageResponse(url, jo, status);
                            }
                            MemberModel.this.callback(url, response.error_code, response.error_desc);
                        }
                    }

                } catch (JSONException e) {

                }
                ;
            }
        };
        Map<String, String> params = new HashMap<String, String>();
        try {
            params.put("json", request.toJson().toString());
        } catch (JSONException e) {

        }

        cb.url(ApiInterface.MEMBER_DEL).type(JSONObject.class).params(params);
        //aq.ajax(cb);
        ajaxProgress(cb);
    }

    public void getinfo(String member_no)
    {
        memberaddRequest memberrequest = new memberaddRequest();

        memberrequest.member_no = member_no;



        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                try {
                    MemberModel.this.callback(this, url, jo, status);
                    if (null != jo)
                    {
                        memberaddResponse response = new memberaddResponse();
                        response.fromJson(jo);

                        if(response.succeed == 1)
                        {
                           member= response.member;
                            MemberModel.this.OnMessageResponse(url,jo,status);
                        }
                        else
                        {
                            MemberModel.this.callback(url, response.error_code, response.error_desc);
                        }
                    }else{
                        MemberModel.this.OnMessageResponse(url, jo, status);
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
        if(isSendingMessage(ApiInterface.MEMBER_MINFO)){
            return;
        }
        cb.url(ApiInterface.MEMBER_MINFO).type(JSONObject.class).params(params);
        ajaxProgress(cb);

    }
}
