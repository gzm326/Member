package com.firesoft.member.Model;

import android.content.Context;

import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.BeeCallback;
import com.external.androidquery.callback.AjaxStatus;
import com.firesoft.member.APIErrorCode;
import com.firesoft.member.Protocol.ApiInterface;
import com.firesoft.member.Protocol.SIMPLE_GUIDE;
import com.firesoft.member.Protocol.guideaddRequest;
import com.firesoft.member.Protocol.guideaddResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/12/12.
 */
public class GuideModel extends BaseModel {
    private Context context;
    public SIMPLE_GUIDE guide;
    public GuideModel(Context context) {
        super(context);
        this.context = context;

    }
    public void add( SIMPLE_GUIDE obj) {
        guideaddRequest request = new guideaddRequest();
        request.phone=obj.phone;
        request.user_flag=obj.user_flag;
        request.name =obj.name;
        request.shopid=obj.shopid;
        request.shopname=obj.shopname;
        request.password=obj.password;

        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                try {
                    GuideModel.this.callback(this, url, jo, status);

                    if (null != jo) {
                        guideaddResponse response = new guideaddResponse();
                        response.fromJson(jo);
                        if (response.succeed == 1) {
                            GuideModel.this.OnMessageResponse(url, jo, status);
                        } else {
                            if(response.error_code== APIErrorCode.NICKNAME_EXIST){

                                GuideModel.this.OnMessageResponse(url, jo, status);
                            }
                            GuideModel.this.callback(url, response.error_code, response.error_desc);
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

        cb.url(ApiInterface.GUIDE_ADD).type(JSONObject.class).params(params);
        //aq.ajax(cb);
        ajaxProgress(cb);
    }

    public void update( SIMPLE_GUIDE obj) {
        guideaddRequest request = new guideaddRequest();
        request.name =obj.name;
        request.uid=obj.id;
        request.phone=obj.phone;
        request.user_flag=obj.user_flag;
        request.shopid=obj.shopid;
        request.shopname=obj.shopname;
        request.password=obj.password;
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                try {
                    GuideModel.this.callback(this, url, jo, status);

                    if (null != jo) {
                        guideaddResponse response = new guideaddResponse();
                        response.fromJson(jo);
                        if (response.succeed == 1) {
                            GuideModel.this.OnMessageResponse(url, jo, status);
                        } else {
                            if(response.error_code== APIErrorCode.NICKNAME_EXIST){

                                GuideModel.this.OnMessageResponse(url, jo, status);
                            }
                            GuideModel.this.callback(url, response.error_code, response.error_desc);
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

        cb.url(ApiInterface.GUIDE_UPDATE).type(JSONObject.class).params(params);
        //aq.ajax(cb);
        ajaxProgress(cb);
    }

    public void del(int uid) {
        guideaddRequest request = new guideaddRequest();

        request.uid=uid;
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                try {
                    GuideModel.this.callback(this, url, jo, status);

                    if (null != jo) {
                        guideaddResponse response = new guideaddResponse();
                        response.fromJson(jo);
                        if (response.succeed == 1) {
                            GuideModel.this.OnMessageResponse(url, jo, status);
                        } else {
                            if(response.error_code== APIErrorCode.NICKNAME_EXIST){

                                GuideModel.this.OnMessageResponse(url, jo, status);
                            }
                            GuideModel.this.callback(url, response.error_code, response.error_desc);
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

        cb.url(ApiInterface.GUIDE_DEL).type(JSONObject.class).params(params);
        //aq.ajax(cb);
        ajaxProgress(cb);
    }

    public void getinfo(int id)
    {
        guideaddRequest memberrequest = new guideaddRequest();

        memberrequest.uid = id;



        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                try {
                    GuideModel.this.callback(this, url, jo, status);
                    if (null != jo)
                    {
                        guideaddResponse response = new guideaddResponse();
                        response.fromJson(jo);

                        if(response.succeed == 1)
                        {
                            guide= response.guide;
                            GuideModel.this.OnMessageResponse(url,jo,status);
                        }
                        else
                        {
                            GuideModel.this.callback(url, response.error_code, response.error_desc);
                        }
                    }else{
                        GuideModel.this.OnMessageResponse(url, jo, status);
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
        if(isSendingMessage(ApiInterface.GUIDE_INFO)){
            return;
        }
        cb.url(ApiInterface.GUIDE_INFO).type(JSONObject.class).params(params);
        ajaxProgress(cb);

    }
}
