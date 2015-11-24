package com.firesoft.member.Model;

import android.content.Context;

import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.BeeCallback;
import com.external.androidquery.callback.AjaxStatus;
import com.firesoft.member.APIErrorCode;
import com.firesoft.member.Protocol.ApiInterface;
import com.firesoft.member.Protocol.SIMPLE_PRODUCTTYPE;
import com.firesoft.member.Protocol.producttypeaddRequest;
import com.firesoft.member.Protocol.producttypeaddResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/10/30.
 */
public class ProductTypeModel extends BaseModel {
    private Context context;
    public SIMPLE_PRODUCTTYPE producttype;
    public ProductTypeModel(Context context) {
        super(context);
        this.context = context;

    }
    public void add( SIMPLE_PRODUCTTYPE mProductType) {
        producttypeaddRequest request = new producttypeaddRequest();
        request.bz=mProductType.bz;
        request.name =mProductType.name;
        request.shopid=mProductType.shopid;
        request.shopname=mProductType.shopname;

        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                try {
                    ProductTypeModel.this.callback(this, url, jo, status);

                    if (null != jo) {
                        producttypeaddResponse response = new producttypeaddResponse();
                        response.fromJson(jo);
                        if (response.succeed == 1) {
                            ProductTypeModel.this.OnMessageResponse(url, jo, status);
                        } else {
                            if(response.error_code== APIErrorCode.NICKNAME_EXIST){

                                ProductTypeModel.this.OnMessageResponse(url, jo, status);
                            }
                            ProductTypeModel.this.callback(url, response.error_code, response.error_desc);
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

        cb.url(ApiInterface.PRODUCTTYPE_ADD).type(JSONObject.class).params(params);
        //aq.ajax(cb);
        ajaxProgress(cb);
    }

    public void update( SIMPLE_PRODUCTTYPE mProductType) {
        producttypeaddRequest request = new producttypeaddRequest();
        request.name =mProductType.name;
        request.uid=mProductType.id;
        request.bz=mProductType.bz;
        request.shopid=mProductType.shopid;
        request.shopname=mProductType.shopname;
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                try {
                    ProductTypeModel.this.callback(this, url, jo, status);

                    if (null != jo) {
                        producttypeaddResponse response = new producttypeaddResponse();
                        response.fromJson(jo);
                        if (response.succeed == 1) {
                            ProductTypeModel.this.OnMessageResponse(url, jo, status);
                        } else {
                            if(response.error_code== APIErrorCode.NICKNAME_EXIST){

                                ProductTypeModel.this.OnMessageResponse(url, jo, status);
                            }
                            ProductTypeModel.this.callback(url, response.error_code, response.error_desc);
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

        cb.url(ApiInterface.PRODUCTTYPE_UPDATE).type(JSONObject.class).params(params);
        //aq.ajax(cb);
        ajaxProgress(cb);
    }

    public void del(int uid) {
        producttypeaddRequest request = new producttypeaddRequest();

        request.uid=uid;
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                try {
                    ProductTypeModel.this.callback(this, url, jo, status);

                    if (null != jo) {
                        producttypeaddResponse response = new producttypeaddResponse();
                        response.fromJson(jo);
                        if (response.succeed == 1) {
                            ProductTypeModel.this.OnMessageResponse(url, jo, status);
                        } else {
                            if(response.error_code== APIErrorCode.NICKNAME_EXIST){

                                ProductTypeModel.this.OnMessageResponse(url, jo, status);
                            }
                            ProductTypeModel.this.callback(url, response.error_code, response.error_desc);
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

        cb.url(ApiInterface.PRODUCTTYPE_DEL).type(JSONObject.class).params(params);
        //aq.ajax(cb);
        ajaxProgress(cb);
    }

    public void getinfo(int id)
    {
        producttypeaddRequest memberrequest = new producttypeaddRequest();

        memberrequest.uid = id;



        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                try {
                    ProductTypeModel.this.callback(this, url, jo, status);
                    if (null != jo)
                    {
                        producttypeaddResponse response = new producttypeaddResponse();
                        response.fromJson(jo);

                        if(response.succeed == 1)
                        {
                            producttype= response.producttype;
                            ProductTypeModel.this.OnMessageResponse(url,jo,status);
                        }
                        else
                        {
                            ProductTypeModel.this.callback(url, response.error_code, response.error_desc);
                        }
                    }else{
                        ProductTypeModel.this.OnMessageResponse(url, jo, status);
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
        if(isSendingMessage(ApiInterface.PRODUCTTYPE_INFO)){
            return;
        }
        cb.url(ApiInterface.PRODUCTTYPE_INFO).type(JSONObject.class).params(params);
        ajaxProgress(cb);

    }
}
