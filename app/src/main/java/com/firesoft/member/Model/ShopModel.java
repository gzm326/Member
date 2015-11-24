package com.firesoft.member.Model;

import android.content.Context;

import com.BeeFramework.model.BaseModel;
import com.BeeFramework.model.BeeCallback;
import com.external.androidquery.callback.AjaxStatus;
import com.firesoft.member.APIErrorCode;
import com.firesoft.member.Protocol.ApiInterface;
import com.firesoft.member.Protocol.SIMPLE_SHOP;
import com.firesoft.member.Protocol.shopaddRequest;
import com.firesoft.member.Protocol.shopaddResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/11/9.
 */
public class ShopModel extends BaseModel {
    private Context context;
    public SIMPLE_SHOP mshop;
    public ShopModel(Context context) {
        super(context);
        this.context = context;

    }
    public void add(SIMPLE_SHOP shop) {
        shopaddRequest request = new shopaddRequest();

        request.lxr = shop.lxr;
        request.name =shop.name ;
        request.lxdh =shop.lxdh;
        request.userid=shop.userid;
        request.adress_province = shop.adress_province;
        request.adress_province_name = shop.adress_province_name;
        request.adress_city = shop.adress_city;
        request.adress_city_name = shop.adress_city_name;
        request.adress_county = shop.adress_county;
        request.adress_county_name =shop.adress_county_name ;
        request.adress_detail =shop.adress_detail;
        request.grade_credit = shop.grade_credit;
        request.grade_discount = shop.grade_discount;
        request.sale_credit = shop.sale_credit;
        request.sale_discount = shop.sale_discount;
        request.birth_credit = shop.birth_credit;
        request.birth_discount = shop.birth_discount;
        request.shop_credit = shop.shop_credit;
        request.shop_discount = shop.shop_discount;
        request.rem = shop.rem;

        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                try {
                    ShopModel.this.callback(this, url, jo, status);

                    if (null != jo) {
                        shopaddResponse response = new shopaddResponse();
                        response.fromJson(jo);
                        if (response.succeed == 1) {
                            ShopModel.this.OnMessageResponse(url, jo, status);
                        } else {
                            if(response.error_code== APIErrorCode.NICKNAME_EXIST){

                                ShopModel.this.OnMessageResponse(url, jo, status);
                            }
                            ShopModel.this.callback(url, response.error_code, response.error_desc);
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

        cb.url(ApiInterface.SHOP_ADD).type(JSONObject.class).params(params);
        //aq.ajax(cb);
        ajaxProgress(cb);
    }

    public void update( SIMPLE_SHOP shop) {
        shopaddRequest request = new shopaddRequest();

        request.lxr = shop.lxr;
        request.name =shop.name ;
        request.lxdh =shop.lxdh;
        request.adress_province = shop.adress_province;
        request.adress_province_name = shop.adress_province_name;
        request.adress_city = shop.adress_city;
        request.adress_city_name = shop.adress_city_name;
        request.adress_county = shop.adress_county;
        request.adress_county_name =shop.adress_county_name ;
        request.adress_detail =shop.adress_detail;
        request.grade_credit = shop.grade_credit;
        request.grade_discount = shop.grade_discount;
        request.sale_credit = shop.sale_credit;
        request.sale_discount = shop.sale_discount;
        request.birth_credit = shop.birth_credit;
        request.birth_discount = shop.birth_discount;
        request.shop_credit = shop.shop_credit;
        request.shop_discount = shop.shop_discount;
        request.rem = shop.rem;
        request.id=shop.id;

        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                try {
                    ShopModel.this.callback(this, url, jo, status);

                    if (null != jo) {
                        shopaddResponse response = new shopaddResponse();
                        response.fromJson(jo);
                        if (response.succeed == 1) {
                            ShopModel.this.OnMessageResponse(url, jo, status);
                        } else {
                            if(response.error_code== APIErrorCode.NICKNAME_EXIST){

                                ShopModel.this.OnMessageResponse(url, jo, status);
                            }
                            ShopModel.this.callback(url, response.error_code, response.error_desc);
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

        cb.url(ApiInterface.SHOP_UPDATE).type(JSONObject.class).params(params);
        //aq.ajax(cb);
        ajaxProgress(cb);
    }

    public void del(int uid) {
        shopaddRequest request = new shopaddRequest();

        request.id=uid;
        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {
            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                try {
                    ShopModel.this.callback(this, url, jo, status);

                    if (null != jo) {
                        shopaddResponse response = new shopaddResponse();
                        response.fromJson(jo);
                        if (response.succeed == 1) {
                            ShopModel.this.OnMessageResponse(url, jo, status);
                        } else {
                            if(response.error_code== APIErrorCode.NICKNAME_EXIST){

                                ShopModel.this.OnMessageResponse(url, jo, status);
                            }
                            ShopModel.this.callback(url, response.error_code, response.error_desc);
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

        cb.url(ApiInterface.SHOP_DEL).type(JSONObject.class).params(params);
        //aq.ajax(cb);
        ajaxProgress(cb);
    }

    public void getinfo(int id)
    {
        shopaddRequest request = new shopaddRequest();

        request.id = id;



        BeeCallback<JSONObject> cb = new BeeCallback<JSONObject>() {

            @Override
            public void callback(String url, JSONObject jo, AjaxStatus status) {
                try {
                    ShopModel.this.callback(this, url, jo, status);
                    if (null != jo)
                    {
                        shopaddResponse response = new shopaddResponse();
                        response.fromJson(jo);

                        if(response.succeed == 1)
                        {
                            mshop= response.shop;
                            ShopModel.this.OnMessageResponse(url,jo,status);
                        }
                        else
                        {
                            ShopModel.this.callback(url, response.error_code, response.error_desc);
                        }
                    }else{
                        ShopModel.this.OnMessageResponse(url, jo, status);
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
        if(isSendingMessage(ApiInterface.SHOP_INFO)){
            return;
        }
        cb.url(ApiInterface.SHOP_INFO).type(JSONObject.class).params(params);
        ajaxProgress(cb);

    }
}
